--===============================================
--chun계정
--===============================================



--학과테이블
SELECT * FROM tb_department;
--department_no, department_name, category, open_yn, capacity

--학생테이블
SELECT * FROM tb_student;
--student_no, department_no, student_name, student_ssn, student_address
--entrance_date, absence_yn, coach_professor_no

--과목테이블
SELECT * FROM tb_class;
--class_no, department_no, preattending_class_no, class_name ,class_type

--교수테이블
SELECT * FROM tb_professor;
--class_no, professor_no

--교수과목테이블
SELECT * FROM tb_class_professor;
--professor_no, professor_name, professor_ssn, professor_address, department_no

--점수등급테이블
SELECT * FROM tb_grade;
--term_no, class_no, student_no, POINT

-------------------------------------------------


-------------------------------------------------
--hw_0126 [Basic SELECT]
-------------------------------------------------

--1. 춘 기술대학교의 학과 이름과 계열을 표시하시오.
--단, 출력 헤더는 "학과 명", "계열" 으로 표시하도록 한다.

SELECT department_name "학과 명", CATEGORY "계열"
FROM tb_department;


--2. 학과의 학과 정원을 다음과 같은 형태로 화면에 출력한다.

SELECT department_name|| '의 정원은' || CAPACITY || '명 입니다.' 학과별정원
FROM tb_department;


--3. "국어국문학과" 에 다니는 여학생 중 현재 휴학중인 여학생을 찾아달라는 요청이 들어왔다.
--누구인가? (국문학과의 '학과코드'는 학과 테이블(TB_DEPARTMENT)을 조회해서 찾아 내도록 하자)

SELECT student_name
FROM tb_student
WHERE department_no = 001 AND absence_yn = 'Y' AND substr(student_ssn, 8, 1) = 2;


--도서관에서 대출 도서 장기 연체자 들을 찾아 이름을 게시하고자 한다.
--그 대상자들의 학번이 다음과 같을 때 대상자들을 찾는 적절한 SQL 구문을 작성하시오.
--A513079, A513090, A513091, A513110, A513119

SELECT student_name
FROM tb_student
WHERE student_no IN('A513079', 'A513090', 'A513091', 'A513110', 'A513119')
ORDER BY student_name DESC;


--5. 입학정원이 20명 이상 30명 이하인 학과들의 학과 이름과 계열을 출력하시오.

SELECT department_name, CATEGORY
FROM tb_department
WHERE CAPACITY BETWEEN 20 AND 30;


--6. 춘 기술대학교는 총장을 제외하고 모든 교수들이 소속 학과를 가지고 있다.
--그럼 춘 기술대학교 총장의 이름을 알아낼 수 있는 SQL 문장을 작성하시오.

SELECT professor_name
FROM tb_professor
WHERE department_no IS NULL;


--7. 혹시 전산상의 착오로 학과가 지정되어 있지 않은 학생이 있는지 확인하고자 한다.
--어떠한 SQL 문장을 사용하면 될 것인지 작성하시오.

SELECT student_name
FROM tb_student
WHERE department_no IS NULL;


--8. 수강신청을 하려고 한다. 선수과목 여부를 확인해야 하는데,
--선수과목이 존재하는 과목들은 어떤 과목인지 과목번호를 조회해보시오.

SELECT class_no
FROM tb_class
WHERE preattending_class_no IS NOT NULL;


--9. 춘 대학에는 어떤 계열(CATEGORY)들이 있는지 조회해보시오.

SELECT DISTINCT CATEGORY
FROM tb_department
ORDER BY CATEGORY;


--10. 02학번 전주 거주자들의 모임을 만들려고 한다.
--휴학한 사람들은 제외한 재학중인 학생들의 학번, 이름, 주민번호를 출력하는 구문을 작성하시오.

SELECT student_no, student_name, student_ssn
FROM tb_student
WHERE substr(student_address, 1, 2) = '전주'
            AND substr(entrance_date, 1, 2) = 02
            AND absence_yn = 'N';
            
-------------------------------------------------
--class_0127
-------------------------------------------------
            
--1. 학과테이블에서 계열별 정원의 평균을 조회(정원 내림차순 정렬)
SELECT  CATEGORY, TRUNC(AVG(CAPACITY)) "정원 평균"
FROM tb_department
GROUP BY CATEGORY
ORDER BY "정원 평균" DESC;


--2. 휴학생을 제외하고, 학과별로 학생수를 조회(학과별 인원수 내림차순)
SELECT department_no, COUNT(*) 인원수
FROM tb_student
WHERE absence_yn = 'N'
GROUP BY department_no
ORDER BY 인원수 DESC;


--3. 과목별 지정된 교수가 2명이상이 과목번호와 교수인원수를 조회

SELECT class_no, COUNT(professor_no)
FROM tb_class_professor
GROUP BY class_no
HAVING COUNT(class_no) >= 2;


--4. 학과별로 과목을 구분했을때, 과목구분이 "전공선택"에 한하여
--과목수가 10개 이상인 행의 학과번호, 과목구분(class_type), 과목수를 조회(전공선택만 조회)

SELECT department_no, class_type, COUNT(*)
FROM tb_class
GROUP BY department_no, class_type
HAVING COUNT(class_no) >= 10 AND class_type = '전공선택';  


-------------------------------------------------
--hw_0127 [Additional SELECT - 함수]
-------------------------------------------------

--1. 영어영문학과(학과코드 002) 학생들의 학번과 이름, 입학 년도를
--입학 년도가 빠른 순으로 표시하는 SQL 문장을 작성하시오.
--단, 헤더는 "학번", "이름", "입학년도" 가 표시되도록 한다.)

SELECT student_no 학번,
            student_name 이름, 
            to_char(entrance_date, 'yyyy-mm-dd') 입학년도
FROM tb_student
WHERE department_no = 002
ORDER BY 입학년도;


--2. 춘 기술대학교의 교수 중 이름이 세 글자가 아닌 교수가 한 명 있다고 한다.
--그 교수의 이름과 주민번호를 화면에 출력하는 SQL 문장을 작성해 보자. 
--(* 이때 올바르게 작성한 SQL 문장의 결과 값이 예상과 다르게 나올 수 있다. 원인이 무엇일지 생각해볼 것)

SELECT professor_name, professor_ssn
FROM tb_professor
WHERE professor_name NOT LIKE '___';


--3. 춘 기술대학교의 남자 교수들의 이름과 나이를 출력하는 SQL 문장을 작성하시오.
--단 이때 나이가 적은 사람에서 많은 사람 순서로 화면에 출력되도록 만드시오.
--(단, 교수 중 2000년 이후 출생자는 없으며 출력 헤더는 "교수이름", "나이"로 한다. 나이는 ‘만’으로 계산한다.)

--방법1.
SELECT professor_name 교수이름, 
CASE
--현재월 > 출생월    -> 현재년도 - 출생년도
WHEN EXTRACT(MONTH FROM sysdate) > substr(professor_ssn, 3, 2)
THEN EXTRACT(YEAR FROM sysdate) - (1900+substr(professor_ssn, 1, 2))
--현재월 = 출생월 & 현재날짜 >= 출생날짜    -> 현재년도 - 출생년도
WHEN EXTRACT(MONTH FROM sysdate) = substr(professor_ssn, 3, 2)
        AND EXTRACT(DAY FROM sysdate) >= substr(professor_ssn, 5, 2)
THEN EXTRACT(YEAR FROM sysdate) - (1900+substr(professor_ssn, 1, 2)) 
--else    -> 현재년도 - 출생년도 - 1
ELSE EXTRACT(YEAR FROM sysdate) - (1900 + substr(professor_ssn, 1, 2)) - 1
END 나이
FROM tb_professor
ORDER BY 나이;


--방법2.
SELECT professor_name 교수이름,
            --버림((현재날짜 - 출생날짜 ) / 12)
           TRUNC(months_between(sysdate, (19||substr(professor_ssn, 1, 6)))/12)나이
FROM tb_professor
ORDER BY 나이;


--4. 교수들의 이름 중 성을 제외한 이름만 출력하는 SQL 문장을 작성하시오.
--출력 헤더는 "이름" 이 찍히도록 한다. (성이 2자인 경우는 교수는 없다고 가정하시오)

SELECT substr(professor_name, 2) 이름
FROM tb_professor
ORDER BY professor_no;


--5. 춘 기술대학교의 재수생 입학자를 구하려고 한다.
--어떻게 찾아낼 것인가? 이때, 19살에 입학하면 재수를 하지 않은 것으로 간주한다.

SELECT student_no, student_name
FROM tb_student
WHERE (EXTRACT(YEAR FROM entrance_date) - EXTRACT(YEAR FROM TO_DATE((19 || substr(student_ssn, 1, 6)), 'yyyy/mm/dd' ))) > 19;

--6. 2020년 크리스마스는 무슨 요일인가?

SELECT to_char(TO_DATE('2020/12/25'), 'dy')||'요일' "2020년 크리스마스"
FROM dual;


--7. TO_DATE('99/10/11','YY/MM/DD'), TO_DATE('49/10/11','YY/MM/DD') 은 각각 몇 년 몇 월 몇 일을 의미할까?
--또 TO_DATE('99/10/11','RR/MM/DD'), TO_DATE('49/10/11','RR/MM/DD') 은 각각 몇 년 몇 월 몇 일을 의미할까?

--yy는 현재년도 기준으로 현재세기(2000~2099)에서 추측한다.
--rr은 현재년도 기준으로 앞 뒤 50년단위로(1950~2049) 추측한다.

SELECT to_char(TO_DATE('99/10/11','YY/MM/DD'), 'yyyy/mm/dd'),
           to_char(TO_DATE('49/10/11','YY/MM/DD'), 'yyyy/mm/dd'),
           to_char(TO_DATE('99/10/11','RR/MM/DD'), 'yyyy/mm/dd'), 
           to_char(TO_DATE('49/10/11','RR/MM/DD'), 'yyyy/mm/dd')
FROM dual;


--8. 춘 기술대학교의 2000년도 이후 입학자들은 학번이 A로 시작하게 되어있다.
--2000년도 이전 학번을 받은 학생들의 학번과 이름을 보여주는 SQL 문장을 작성하시오.

SELECT student_name, student_no
FROM tb_student
WHERE student_no NOT LIKE 'A%'
ORDER BY student_name;


--9. 학번이 A517178 인 한아름 학생의 학점 총 평점을 구하는 SQL 문을 작성하시오.
--단, 이때 출력 화면의 헤더는 "평점" 이라고 찍히게 하고, 점수는 반올림하여 소수점 이하 한 자리까지만 표시한다.

SELECT round(AVG(POINT), 1) 평점
FROM tb_grade
GROUP BY student_no
HAVING student_no = 'A517178';


--10. 학과별 학생수를 구하여 "학과번호", "학생수(명)" 의 형태로 헤더를 만들어 결과값이 출력되도록 하시오.

SELECT department_no 학과번호, COUNT(*) "학생수(명)"
FROM tb_student
GROUP BY department_no
ORDER BY department_no;


--11. 지도 교수를 배정받지 못한 학생의 수는 몇 명 정도 되는지 알아내는 SQL 문을 작성하시오.

SELECT COUNT(*) - COUNT(coach_professor_no)
FROM tb_student;


--12. 학번이 A112113인 김고운 학생의 년도 별 평점을 구하는 SQL 문을 작성하시오.
--단, 이때 출력 화면의 헤더는 "년도", "년도 별 평점" 이라고 찍히게 하고,
--점수는 반올림하여 소수점 이하 한 자리까지만 표시한다.

SELECT substr(term_no, 1, 4) 년도 , round(AVG(POINT), 1) "년도 별 평점"
FROM tb_grade
WHERE student_no = 'A112113'
GROUP BY substr(term_no, 1, 4)
ORDER BY 년도;


--13. 학과 별 휴학생 수를 파악하고자 한다. 학과 번호와 휴학생 수를 표시하는 SQL 문장을 작성하시오.

SELECT *
FROM tb_student;
SELECT department_no,
COUNT(student_name)
FROM tb_student
WHERE absence_yn = 'Y'
GROUP BY department_no
ORDER BY 1;


--14. 춘 대학교에 다니는 동명이인(同名異人) 학생들의 이름을 찾고자 한다. 어떤 SQL 문장을 사용하면 가능하겠는가?

SELECT student_name "동일이름", COUNT(*) "동명인 수"
FROM tb_student
GROUP BY student_name
HAVING COUNT(student_name) >= 2
ORDER BY "동일이름";

--15. 학번이 A112113 인 김고운 학생의 년도, 학기 별 평점과 년도 별 누적 평점 , 총 평점을 구하는 SQL 문을 작성하시오.
--(단, 평점은 소수점 1자리까지만 반올림하여 표시한다.)

SELECT*
FROM tb_grade;

SELECT decode(GROUPING(substr(term_no,1,4)),0, substr(term_no,1,4), '　') 년도,
         decode(GROUPING(substr(term_no,5,2)),0, substr(term_no,5,2), '　') 학기,
         round(AVG(POINT),1) 평점
FROM tb_grade
WHERE student_no = 'A112113'
GROUP BY ROLLUP(substr(term_no,1,4), 
             substr(term_no,5,2))
ORDER BY 1,2;   




-------------------------------------------------
--class_0128
-------------------------------------------------

--student_no / student_name / professor_name
--학번/학생명/담당교수명 조회
--1. 두 테이블의 기준컬럼 파악
--2. on조건절에 해당되지 않는 데이터파악

--1. 두 테이블의 기준컬럼 파악
SELECT * FROM tb_student; --coach_professor_no
SELECT * FROM tb_professor; --professor_no

--2. on조건절에 해당되지 않는 데이터파악
--담당교수가 배정되지 않은 학생이나 교수 제외 - inner -579
--담당교수가 배정되지 않은 학생 포함 left -588 (579+9)
--담당학생이 없는 교수 포함 right -580 (579+1)

SELECT COUNT(*)
FROM tb_student S RIGHT JOIN tb_professor P
    ON S.coach_professor_no = P.professor_no;
    
--1) 교수배정을 받지 않은 학생 조회
SELECT COUNT(*)
FROM tb_student
WHERE coach_professor_no IS NULL;
--출력 : 9 

--2) 담당 학생이 한명도 없는 교수 조회

--전체 교수 수
SELECT COUNT(*)
FROM tb_professor;
--출력 : 114

--중복없는 담당교수 수 (중복없음 : 한 교수가 여러명을 담당하고 있을 경우를 대비함)
SELECT COUNT(DISTINCT coach_professor_no) --113
FROM tb_student;
--출력 : 113

--->담당학생이 한명도 없는 교수 : 1명

--담당학생이 없는 교수 포함 right -580 (579+1)
SELECT COUNT(*)
FROM tb_student S RIGHT JOIN tb_professor P
    ON S.coach_professor_no = P.professor_no;

SELECT S.student_no , S.student_name, P.professor_name
FROM tb_student S RIGHT JOIN tb_professor P
    ON S.coach_professor_no = P.professor_no;




-------------------------------------------------
--hw_0128 [join]
-------------------------------------------------

--1. 학번, 학생명, 학과명 조회
-- 학과 지정이 안된 학생은 존재하지 않는다.

SELECT S.student_no 학번, S.student_name 학생명, D.department_name 학과명
FROM tb_student S
        JOIN tb_department D
            ON S.department_no = D.department_no;
            

--2. 수업번호, 수업명, 교수번호, 교수명 조회
--(담당교수가 배정되지 않은 과목까지 출력)

SELECT C.class_no 수업번호, C.class_name 수업명, P.professor_no 교수번호, P.professor_name 교수명
FROM tb_class C
        LEFT JOIN tb_class_professor cp
            ON C.class_no = cp.class_no
        LEFT JOIN tb_professor P
            ON cp.professor_no = P.professor_no;


--3. 송박선 학생의 모든 학기 과목별 점수를 조회(학기, 학번, 학생명, 수업명, 점수)

SELECT S.student_name 학생명, S.student_no 학번, G.term_no 학기, G.POINT 점수, C.class_name 수업명
FROM tb_student S
        JOIN tb_grade G
            ON S.student_no = G.student_no
        JOIN tb_class C
            ON G.class_no = C.class_no
WHERE S.student_name = '송박선';


--4. 학생별 전체 평점(소수점이하 첫째자리 버림) 조회 (학번, 학생명, 평점)
--같은 학생이 여러학기에 걸쳐 같은 과목을 이수한 데이터 있으나, 전체 평점으로 계산함.

/*
--join할때 on S.student_no & G.student_no

--학생테이블
SELECT * FROM tb_student;
--student_no, student_name

--점수등급테이블
SELECT * FROM tb_grade;
--student_no, point
*/

SELECT S.student_no, S.student_name, TRUNC(AVG(G.POINT), 1)
FROM tb_student S
        JOIN tb_grade G
            ON S.student_no = G.student_no
GROUP BY S.student_no, S.student_name;


--5. 교수번호, 교수명, 담당학생명수 조회
--단, 5명 이상을 담당하는 교수만 출력

/*
--join할때 on P.professor_no & S.coach_professor_no

--학생테이블
SELECT * FROM tb_student;
--group by coach_professor_no , count(*)

--교수테이블
SELECT * FROM tb_professor;
--professor_no, professor_name
*/

SELECT P.professor_no, P.professor_name, COUNT(*)
FROM tb_professor P
        JOIN tb_student S
            ON P.professor_no = S.coach_professor_no
GROUP BY P.professor_no, P.professor_name
HAVING COUNT(*) >= 5;



-------------------------------------------------
--hw_0123 [Additional SELECT - Option]
-------------------------------------------------
--학과테이블
SELECT * FROM tb_department;
--학생테이블
SELECT * FROM tb_student;
--과목테이블
SELECT * FROM tb_class;
--교수테이블
SELECT * FROM tb_professor;
--교수과목테이블
SELECT * FROM tb_class_professor;
--점수등급테이블
SELECT * FROM tb_grade;


--1. 학생이름과 주소지를 표시하시오.
--단, 출력 헤더는 "학생 이름", "주소지"로 하고, 정렬은 이름으로 오름차순 표시하도록 한다.

SELECT student_name "학생 이름", student_address 주소지
FROM tb_student
order by "학생 이름";


--2. 휴학중인 학생들의 이름과 주민번호를 나이가 적은 순서로 화면에 출력하시오.

SELECT student_name 이름, student_ssn 주민번호
FROM tb_student
where absence_yn = 'Y'
order by student_ssn desc;


--3. 주소지가 강원도나 경기도인 학생들 중 1900년대 학번을 가진 학생들의 이름과 학번, 주소를 이름의 오름차순으로 화면에 출력하시오.
--단, 출력헤더에는 "학생이름","학번", "거주지 주소" 가 출력되도록 한다.

SELECT STUDENT_NAME AS "학생이름",
       STUDENT_NO AS "학번",
          STUDENT_ADDRESS AS "거주지 주소" 
FROM   TB_STUDENT
WHERE  ( STUDENT_ADDRESS LIKE '%강원도%'
        OR STUDENT_ADDRESS LIKE '%경기도%' )
        AND TO_CHAR(ENTRANCE_DATE, 'RRRR') LIKE '19%' 
ORDER BY 1 ;



--4. 현재 법학과 교수 중 가장 나이가 맋은 사람부터 이름을 확인핛 수 있는 SQL 문장을 작성하시오.
--(법학과의 '학과코드'는 학과 테이블(TB_DEPARTMENT)을 조회해서 찾아 내도록 하자)

SELECT DEPARTMENT_NO 
FROM   TB_DEPARTMENT 
WHERE  DEPARTMENT_NAME = '법학과';

SELECT PROFESSOR_NAME,
       PROFESSOR_SSN
FROM   TB_PROFESSOR
WHERE  DEPARTMENT_NO = '005'
ORDER BY 2;

--조인사용시
--학과가 정해지지 않은 교수도 있지만, 위의 경우에는 해당되지 않기때문에 inner join으로도 가능함.
SELECT * FROM TB_PROFESSOR
WHERE DEPARTMENT_NO IS NULL;

SELECT PROFESSOR_NAME,
         PROFESSOR_SSN
FROM   TB_PROFESSOR
JOIN   TB_DEPARTMENT USING (DEPARTMENT_NO)
WHERE  DEPARTMENT_NAME = '법학과'
ORDER BY 2;




--5. 2004년2학기에 'C3118100' 과목을 수강한 학생들의 학점을 조회하려고 한다.
--학점이 높은 학생부터 표시하고, 학점이 같으면 학번이 낮은 학생부터 표시하는 구문을 작성해보시오.

SELECT STUDENT_NO, POINT
FROM   TB_GRADE
WHERE  TERM_NO = '200402'
AND    CLASS_NO = 'C3118100'
ORDER BY 2 DESC, 1;


--6. 학생 번호, 학생 이름, 학과 이름을 학생 이름으로 오름차순 정렬하여 출력하는 SQL 문을 작성하시오.

SELECT STUDENT_NO, STUDENT_NAME, DEPARTMENT_NAME
FROM TB_STUDENT
 JOIN TB_DEPARTMENT USING (DEPARTMENT_NO)
ORDER BY 2;


--7. 춘 기술대학교의 과목 이름과 과목의 학과 이름을 출력하는 SQL 문장을 작성하시오.

-- ANSI 표준
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS
JOIN TB_DEPARTMENT USING(DEPARTMENT_NO)
ORDER BY 2;

--오라클 전용
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS C, TB_DEPARTMENT D 
WHERE C.DEPARTMENT_NO = D.DEPARTMENT_NO
ORDER BY 2;


--8. 과목별 교수 이름을 찾으려고 한다. 과목 이름과 교수 이름을 출력하는 SQL 문을 작성하시오.

-- ANSI 표준
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR
JOIN TB_CLASS USING (CLASS_NO)
JOIN TB_PROFESSOR USING (PROFESSOR_NO);

-- 오라클 전용
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR CP, TB_CLASS C, TB_PROFESSOR P 
WHERE CP.CLASS_NO = C.CLASS_NO
AND CP.PROFESSOR_NO = P.PROFESSOR_NO;


--9. 8번의 결과 중 ‘인문사회’ 계열에 속한 과목의 교수 이름을 찾으려고 한다.
--이에 해당하는 과목 이름과 교수 이름을 출력하는 SQL 문을 작성하시오.

--ANSI 표준
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR CP
JOIN TB_CLASS C ON (C.CLASS_NO = CP.CLASS_NO)
JOIN TB_PROFESSOR P ON (P.PROFESSOR_NO = CP.PROFESSOR_NO)
JOIN TB_DEPARTMENT D ON (D.DEPARTMENT_NO = P.DEPARTMENT_NO)
WHERE CATEGORY = '인문사회';

--오라클 전용
SELECT CLASS_NAME, PROFESSOR_NAME
FROM TB_CLASS_PROFESSOR CP, TB_CLASS C, TB_PROFESSOR P, TB_DEPARTMENT D
WHERE C.CLASS_NO = CP.CLASS_NO
AND P.PROFESSOR_NO = CP.PROFESSOR_NO
AND D.DEPARTMENT_NO = P.DEPARTMENT_NO
AND CATEGORY = '인문사회';


--10. ‘음악학과’ 학생들의 평점을 구하려고 한다. 
--음악학과 학생들의 "학번", "학생 이름", "전체 평점"을 출력하는 SQL 문장을 작성하시오.
--(단, 평점은 소수점 1자리까지만 반올림하여 표시한다.)

SELECT STUDENT_NO  AS "학번", STUDENT_NAME AS "학생 이름", ROUND(AVG(POINT), 1) AS "전체 평점"
FROM TB_STUDENT
JOIN TB_GRADE USING (STUDENT_NO)
JOIN TB_DEPARTMENT USING (DEPARTMENT_NO)
WHERE DEPARTMENT_NAME = '음악학과'
GROUP BY STUDENT_NO, STUDENT_NAME
ORDER BY 3 DESC, 1;

-- 오라클 전용
SELECT S.STUDENT_NO, S.STUDENT_NAME, ROUND(AVG(G.POINT), 1)
FROM TB_STUDENT S, TB_GRADE G, TB_DEPARTMENT D
WHERE S.STUDENT_NO = G.STUDENT_NO
AND S.DEPARTMENT_NO = D.DEPARTMENT_NO
AND D.DEPARTMENT_NAME = '음악학과'
GROUP BY S.STUDENT_NO, S.STUDENT_NAME
ORDER BY 3 DESC, 1;


--11. 학번이 A313047인 학생이 학교에 나오고 있지 않다. 지도 교수에게 내용을 전달하기 위한 학과 이름, 학생 이름과 지도 교수 이름이 필요하다.
--이때 사용할 SQL 문을 작성하시오. 
--단, 출력헤더는 "학과이름", "학생이름", "지도교수이름"으로 출력되도록 한다.

-- ANSI 표준
SELECT DEPARTMENT_NAME 학과이름, STUDENT_NAME 학생이름, PROFESSOR_NAME 지도교수이름
FROM TB_STUDENT S
JOIN TB_DEPARTMENT D ON (S.DEPARTMENT_NO = D.DEPARTMENT_NO)
JOIN TB_PROFESSOR P ON (S.COACH_PROFESSOR_NO = P.PROFESSOR_NO)
WHERE STUDENT_NO = 'A313047';

-- 오라클 전용
SELECT DEPARTMENT_NAME 학과이름, STUDENT_NAME 학생이름, PROFESSOR_NAME 지도교수이름
FROM TB_STUDENT S, TB_DEPARTMENT D, TB_PROFESSOR P
WHERE S.DEPARTMENT_NO = D.DEPARTMENT_NO
AND S.COACH_PROFESSOR_NO = P.PROFESSOR_NO
AND STUDENT_NO = 'A313047';


--12. 2007년도에 '인간관계론' 과목을 수강한 학생을 찾아 학생이름과 수강학기름 표시하는 SQL 문장을 작성하시오.

--ANSI 표준
SELECT STUDENT_NAME, TERM_NO
FROM TB_STUDENT S
JOIN TB_GRADE G ON (S.STUDENT_NO = G.STUDENT_NO)
JOIN TB_CLASS C ON (G.CLASS_NO = C.CLASS_NO)
WHERE TERM_NO LIKE '2007%'
AND C.CLASS_NAME = '인간관계론';

--오라클 전용
SELECT STUDENT_NAME, TERM_NO
FROM TB_STUDENT S, TB_GRADE G, TB_CLASS C
WHERE S.STUDENT_NO = G.STUDENT_NO
AND G.CLASS_NO = C.CLASS_NO
AND TERM_NO LIKE '2007%'
AND C.CLASS_NAME  = '인간관계론';


--13. 예체능 계열 과목 중 과목 담당교수를 한 명도 배정받지 못한 과목을 찾아 그 과목 이름과 학과 이름을 출력하는 SQL 문장을 작성하시오.

-- ANSI 표준
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS_PROFESSOR CP
RIGHT JOIN TB_CLASS C ON (CP.CLASS_NO = C.CLASS_NO)
JOIN TB_DEPARTMENT D ON (C.DEPARTMENT_NO = D.DEPARTMENT_NO)
WHERE CP.CLASS_NO IS NULL
AND CATEGORY = '예체능';

select c.class_name,
      d.department_name
from tb_class c
    left join tb_department d on c.department_no = d.department_no
    left join tb_class_professor cp on c.class_no = cp.class_no
where d.category = '예체능' and cp.class_no is null;

-- 오라클 전용
SELECT CLASS_NAME, DEPARTMENT_NAME
FROM TB_CLASS_PROFESSOR CP, TB_CLASS C, TB_DEPARTMENT D
WHERE CP.CLASS_NO(+) = C.CLASS_NO
AND C.DEPARTMENT_NO = D.DEPARTMENT_NO
AND CP.CLASS_NO IS NULL
AND CATEGORY = '예체능';


--14. 춘 기술대학교 서반아어학과 학생들의 지도교수를 게시하고자 한다. 
--학생이름과 지도교수 이름을 찾고 만일 지도 교수가 없는 학생일 경우 "지도교수 미지정"으로 표시하도록 하는 SQL 문을 작성하시오. 
--단, 출력헤더는 "학생이름", "지도교수"로 표시하며 고학번 학생이 먼저 표시되도록 한다.

-- ANSI 표준
SELECT STUDENT_NAME 학생이름, NVL(P.PROFESSOR_NAME,'지도교수 미지정') 지도교수
FROM TB_STUDENT S
LEFT JOIN TB_PROFESSOR P ON (S.COACH_PROFESSOR_NO = P.PROFESSOR_NO)
JOIN TB_DEPARTMENT D ON (S.DEPARTMENT_NO = D.DEPARTMENT_NO)
WHERE DEPARTMENT_NAME = '서반아어학과';

-- 오라클 전용
SELECT STUDENT_NAME 학생이름, NVL(P.PROFESSOR_NAME,'지도교수 미지정') 지도교수
FROM TB_STUDENT S, TB_PROFESSOR P, TB_DEPARTMENT D
WHERE S.COACH_PROFESSOR_NO = P.PROFESSOR_NO(+)
AND S.DEPARTMENT_NO = D.DEPARTMENT_NO
AND DEPARTMENT_NAME = '서반아어학과';


--15. 휴학생이 아닌 학생 중 평점이 4.0 이상인 학생을 찾아 그 학생의 학번, 이름, 학과 이름, 평점을 출력하는 SQL 문을 작성하시오.

SELECT S.STUDENT_NO 학번, S.STUDENT_NAME 이름, D.DEPARTMENT_NAME "학과 이름",
       ROUND(AVG(G.POINT),1) 평점
FROM TB_STUDENT S
JOIN TB_DEPARTMENT D ON (S.DEPARTMENT_NO = D.DEPARTMENT_NO)
JOIN TB_GRADE G ON (S.STUDENT_NO = G.STUDENT_NO)
WHERE S.ABSENCE_YN = 'N'
GROUP BY S.STUDENT_NO, S.STUDENT_NAME, D.DEPARTMENT_NAME
HAVING AVG(G.POINT) >= 4.0 
ORDER BY S.STUDENT_NO;

-- 오라클 전용
SELECT S.STUDENT_NO 학번, S.STUDENT_NAME 이름, D.DEPARTMENT_NAME "학과 이름",
       ROUND(AVG(G.POINT),1) 평점
FROM TB_STUDENT S, TB_DEPARTMENT D, TB_GRADE G
WHERE S.DEPARTMENT_NO = D.DEPARTMENT_NO
AND S.STUDENT_NO = G.STUDENT_NO
AND S.ABSENCE_YN = 'N'
GROUP BY S.STUDENT_NO, S.STUDENT_NAME, D.DEPARTMENT_NAME
HAVING AVG(G.POINT) >= 4.0 
ORDER BY S.STUDENT_NO;


--16. 환경조경학과 전공과목들의 과목 별 평점을 파악할 수 있는 SQL 문을 작성하시오.

SELECT  CLASS_NO, 
         CLASS_NAME,
         TRUNC(AVG(POINT), 8) AS "AVG(POINT)"
FROM TB_DEPARTMENT
 JOIN TB_CLASS USING (DEPARTMENT_NO)
 JOIN TB_GRADE USING (CLASS_NO)
WHERE DEPARTMENT_NAME = '환경조경학과'
  AND CLASS_TYPE LIKE '%전공%'
GROUP BY CLASS_NO, CLASS_NAME
ORDER BY 1;


--17. 춘 기술대학교에 다니고 있는 최경희 학생과 같은 과 학생들의 이름과 주소를 출력하는 SQL 문을 작성하시오.

SELECT STUDENT_NAME,
        STUDENT_ADDRESS
FROM TB_STUDENT
WHERE DEPARTMENT_NO IN (SELECT DEPARTMENT_NO
                            FROM TB_STUDENT
                            WHERE STUDENT_NAME = '최경희');       


--18. 국어국문학과에서 총 평점이 가장 높은 학생의 이름과 학번을 표시하는 SQL문을 작성하시오.

--having 조건절에 all사용(최대값보다 같거나 큰)
SELECT STUDENT_NO, STUDENT_NAME
FROM TB_CLASS C
 JOIN TB_GRADE G USING (CLASS_NO)
 JOIN TB_STUDENT S USING (STUDENT_NO)
WHERE C.DEPARTMENT_NO = (SELECT DEPARTMENT_NO FROM TB_DEPARTMENT WHERE DEPARTMENT_NAME = '국어국문학과')
GROUP BY STUDENT_NO, STUDENT_NAME
HAVING AVG(POINT) >= ALL(
    SELECT AVG(POINT)
    FROM TB_CLASS C2
     JOIN TB_GRADE G2 USING (CLASS_NO)
     JOIN TB_STUDENT S2 USING (STUDENT_NO)
    WHERE C2.DEPARTMENT_NO = (SELECT DEPARTMENT_NO FROM TB_DEPARTMENT WHERE DEPARTMENT_NAME = '국어국문학과')
    GROUP BY STUDENT_NO, STUDENT_NAME
);

--with, rownum사용

WITH V_STDT_GRADE AS(SELECT STUDENT_NO, AVG(POINT) AVG                              
                    FROM TB_GRADE G JOIN TB_STUDENT S USING (STUDENT_NO)
                    GROUP BY STUDENT_NO)
SELECT STUDENT_NO, STUDENT_NAME
FROM(
    SELECT STUDENT_NO, STUDENT_NAME, AVG
    FROM TB_STUDENT S
    JOIN TB_DEPARTMENT D ON S.DEPARTMENT_NO = D.DEPARTMENT_NO                    
    JOIN V_STDT_GRADE USING(STUDENT_NO)
    WHERE D.DEPARTMENT_NAME = '국어국문학과'
    ORDER BY AVG DESC)
WHERE ROWNUM = 1;


--19. 춘 기술대학교의 "환경조경학과"가 속한 같은 계열 학과들의 학과 별 전공과목 평점을 파악하기 위한 적절한 SQL 문을 찾아내시오. 
--단, 출력헤더는 "계열 학과명", "전공평점"으로 표시되도록 하고, 평점은 소수점 한 자리까지만 반올림하여 표시되도록 한다.

SELECT DEPARTMENT_NAME AS "계열 학과명",
          ROUND(AVG(POINT), 1) AS "전공평점"
FROM TB_DEPARTMENT 
 JOIN TB_CLASS C USING (DEPARTMENT_NO)
 JOIN TB_GRADE USING (CLASS_NO)
WHERE CATEGORY = (SELECT CATEGORY
                      FROM TB_DEPARTMENT
                      WHERE DEPARTMENT_NAME = '환경조경학과')
     AND CLASS_TYPE LIKE '%전공%'
GROUP BY DEPARTMENT_NAME
ORDER BY 1;

-- 단계별로 쿼리만들기
--19.1 환경조경학과 계열 구하기
select category
from tb_department
where department_name = '환경조경학과';

--19.2 같은 계열 학과구하기
select *
from tb_department
where category = (select category
                  from tb_department
                  where department_name = '환경조경학과');

--19.3 학과별 수업 및 전공과목 구하기
select *
from tb_department
 join TB_CLASS using(department_no)
where class_type like '%전공%';

select *
from tb_department
 join TB_CLASS using(department_no)
where category = (select category
                  from tb_department
                  where department_name = '환경조경학과')
  and class_type like '%전공%';

--19.4 과목테이블 조인 및 과목별 점수구하기
select *
from tb_department
 join TB_CLASS using(department_no)
 join tb_grade using(class_no)
where category = (select category
                  from tb_department
                  where department_name = '환경조경학과')
  and class_type like '%전공%';

--19.5 학과별 전공과목 평점 구하기
select department_name 학과명, round(avg(point),1) 평점
from tb_department
 join TB_CLASS using(department_no)
 join tb_grade using(class_no)
where category = (select category
                  from tb_department
                  where department_name = '환경조경학과')
  and class_type like '%전공%'
group by department_name
order by 2 desc;


-------------------------------------------------
--hw_0205 [DDL]
-------------------------------------------------

--1. 계열 정보를 저장핛 카테고리 테이블을 맊들려고 핚다. 다음과 같은 테이블을 작성하시오.

--2. 과목 구분을 저장할 테이블을 만들려고 한다. 다음과 같은 테이블을 작성하시오.

--3. TB_CATAGORY 테이블의 NAME 컬럼에 PRIMARY KEY를 생성하시오. 
--(KEY 이름을 생성하지 않아도 무방함. 
--만일 KEY 이를 지정하고자 한다면 이름은 본인이 알아서 적당한 이름을 사용한다.)

--4. TB_CLASS_TYPE 테이블의 NAME 컬럼에 NULL 값이 들어가지 않도록 속성을 변경하시오.

--5. 두 테이블에서 컬럼 명이 NO인 것은 기존 타입을 유지하면서 크기는 10 으로, 컬럼명이 NAME 인 것은 마찬가지로 기존 타입을 유지하면서 크기 20 으로 변경하시오.

--6. 두 테이블의 NO 컬럼과 NAME 컬럼의 이름을 각 각 TB_ 를 제외한 테이블 이름이 앞에 붙은 형태로 변경한다.
--(ex. CATEGORY_NAME)

--7. TB_CATAGORY 테이블과 TB_CLASS_TYPE 테이블의 PRIMARY KEY 이름을 다음과 같이 변경하시오.
--Primary Key의 이름은 "PK_ + 컬럼이름"으로 지정하시오. (ex. PK_CATEGORY_NAME )

--8. 다음과 같은 INSERT 문을 수행한다.
--INSERT INTO TB_CATEGORY VALUES ('공학','Y'); 
--INSERT INTO TB_CATEGORY VALUES ('자연과학','Y'); 
--INSERT INTO TB_CATEGORY VALUES ('의학','Y'); 
--INSERT INTO TB_CATEGORY VALUES ('예체능','Y'); 
--INSERT INTO TB_CATEGORY VALUES ('인문사회','Y'); 
--COMMIT;

--9.TB_DEPARTMENT의 CATEGORY 컬럼이 TB_CATEGORY 테이블의 CATEGORY_NAME 컬럼을 부모 값으로 참조하도록 FOREIGN KEY를 지정하시오. 
--이 때 KEY 이름은 FK_테이블이름_컬럼이름으로 지정한다. (ex. FK_DEPARTMENT_CATEGORY )

--10. 춘 기술대학교 학생들의 정보만이 포함되어 있는 학생일반정보 VIEW를 만들고자 한다. 아래 내용을 참고하여 적절한 SQL 문을 작성하시오.

--11. 춘 기술대학교는 1년에 두 번씩 학과별로 학생과 지도교수가 지도 면담을 진행한다. 
--이를 위해 사용할 학생이름, 학과이름, 담당교수이름 으로 구성되어 있는 VIEW 를 만드시오. 
--이때 지도 교수가 없는 학생이 있을 수 있음을 고려하시오 
--(단, 이 VIEW 는 단순 SELECT 만을 할 경우 학과별로 정렬되어 화면에 보여지게 만드시오.)

--12. 모든 학과의 학과별 학생 수를 확인한 수 있도록 적절한 VIEW 를 작성해 보자.

--13. 위에서 생성한 학생일반정보 View를 통해서 학번이 A213046인 학생의 이름을 본인 이름으로 변경하는 SQL 문을 작성하시오.

--14. 13번에서와 같이 VIEW를 통해서 데이터가 변경될 수 있는 상황을 막으려면 VIEW를 어떻게 생성해야 하는지 작성하시오.

--15. 춘 기술대학교는 매년 수강신청 기간만 되면 특정 인기 과목들에 수강 신청이 몰려 문제가 되고 있다. 
--최근 3년을 기준으로 수강인원이 가장 많았던 3 과목을 찾는 구문을 작성해보시오. 
