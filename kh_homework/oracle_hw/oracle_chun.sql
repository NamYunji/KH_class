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
select  category, trunc(avg(capacity)) "정원 평균"
from tb_department
group by category
order by "정원 평균" desc;


--2. 휴학생을 제외하고, 학과별로 학생수를 조회(학과별 인원수 내림차순)
select department_no, count(*) 인원수
from tb_student
where absence_yn = 'N'
group by department_no
order by 인원수 desc;


--3. 과목별 지정된 교수가 2명이상이 과목번호와 교수인원수를 조회

select class_no, count(professor_no)
from tb_class_professor
group by class_no
having count(class_no) >= 2;


--4. 학과별로 과목을 구분했을때, 과목구분이 "전공선택"에 한하여
--과목수가 10개 이상인 행의 학과번호, 과목구분(class_type), 과목수를 조회(전공선택만 조회)

select department_no, class_type, count(*)
from tb_class
group by department_no, class_type
having count(class_no) >= 10 and class_type = '전공선택';  


-------------------------------------------------
--hw_0127 [Additional SELECT - 함수]
-------------------------------------------------

--1. 영어영문학과(학과코드 002) 학생들의 학번과 이름, 입학 년도를
--입학 년도가 빠른 순으로 표시하는 SQL 문장을 작성하시오.
--단, 헤더는 "학번", "이름", "입학년도" 가 표시되도록 한다.)

select student_no 학번,
            student_name 이름, 
            to_char(entrance_date, 'yyyy-mm-dd') 입학년도
from tb_student
where department_no = 002
order by 입학년도;


--2. 춘 기술대학교의 교수 중 이름이 세 글자가 아닌 교수가 한 명 있다고 한다.
--그 교수의 이름과 주민번호를 화면에 출력하는 SQL 문장을 작성해 보자. 
--(* 이때 올바르게 작성한 SQL 문장의 결과 값이 예상과 다르게 나올 수 있다. 원인이 무엇일지 생각해볼 것)

select professor_name, professor_ssn
from tb_professor
where professor_name not like '___';


--3. 춘 기술대학교의 남자 교수들의 이름과 나이를 출력하는 SQL 문장을 작성하시오.
--단 이때 나이가 적은 사람에서 많은 사람 순서로 화면에 출력되도록 만드시오.
--(단, 교수 중 2000년 이후 출생자는 없으며 출력 헤더는 "교수이름", "나이"로 한다. 나이는 ‘만’으로 계산한다.)

--방법1.
select professor_name 교수이름, 
case
--현재월 > 출생월    -> 현재년도 - 출생년도
when extract(month from sysdate) > substr(professor_ssn, 3, 2)
then extract(year from sysdate) - (1900+substr(professor_ssn, 1, 2))
--현재월 = 출생월 & 현재날짜 >= 출생날짜    -> 현재년도 - 출생년도
when extract(month from sysdate) = substr(professor_ssn, 3, 2)
        and extract(day from sysdate) >= substr(professor_ssn, 5, 2)
then extract(year from sysdate) - (1900+substr(professor_ssn, 1, 2)) 
--else    -> 현재년도 - 출생년도 - 1
else extract(year from sysdate) - (1900 + substr(professor_ssn, 1, 2)) - 1
end 나이
from tb_professor
order by 나이;


--방법2.
select professor_name 교수이름,
            --버림((현재날짜 - 출생날짜 ) / 12)
           trunc(months_between(sysdate, (19||substr(professor_ssn, 1, 6)))/12)나이
from tb_professor
order by 나이;


--4. 교수들의 이름 중 성을 제외한 이름만 출력하는 SQL 문장을 작성하시오.
--출력 헤더는 "이름" 이 찍히도록 한다. (성이 2자인 경우는 교수는 없다고 가정하시오)

select substr(professor_name, 2) 이름
from tb_professor
order by professor_no;


--5. 춘 기술대학교의 재수생 입학자를 구하려고 한다.
--어떻게 찾아낼 것인가? 이때, 19살에 입학하면 재수를 하지 않은 것으로 간주한다.

select student_no, student_name
from tb_student
where (extract(year from entrance_date) - extract(year from to_date((19 || substr(student_ssn, 1, 6)), 'yyyy/mm/dd' ))) > 19;

--6. 2020년 크리스마스는 무슨 요일인가?

SELECT to_char(to_date('2020/12/25'), 'dy')||'요일' "2020년 크리스마스"
FROM dual;


--7. TO_DATE('99/10/11','YY/MM/DD'), TO_DATE('49/10/11','YY/MM/DD') 은 각각 몇 년 몇 월 몇 일을 의미할까?
--또 TO_DATE('99/10/11','RR/MM/DD'), TO_DATE('49/10/11','RR/MM/DD') 은 각각 몇 년 몇 월 몇 일을 의미할까?

--yy는 현재년도 기준으로 현재세기(2000~2099)에서 추측한다.
--rr은 현재년도 기준으로 앞 뒤 50년단위로(1950~2049) 추측한다.

select to_char(TO_DATE('99/10/11','YY/MM/DD'), 'yyyy/mm/dd'),
           to_char(TO_DATE('49/10/11','YY/MM/DD'), 'yyyy/mm/dd'),
           to_char(TO_DATE('99/10/11','RR/MM/DD'), 'yyyy/mm/dd'), 
           to_char(TO_DATE('49/10/11','RR/MM/DD'), 'yyyy/mm/dd')
from dual;


--8. 춘 기술대학교의 2000년도 이후 입학자들은 학번이 A로 시작하게 되어있다.
--2000년도 이전 학번을 받은 학생들의 학번과 이름을 보여주는 SQL 문장을 작성하시오.

select student_name, student_no
from tb_student
where student_no not like 'A%'
order by student_name;


--9. 학번이 A517178 인 한아름 학생의 학점 총 평점을 구하는 SQL 문을 작성하시오.
--단, 이때 출력 화면의 헤더는 "평점" 이라고 찍히게 하고, 점수는 반올림하여 소수점 이하 한 자리까지만 표시한다.

select round(avg(point), 1) 평점
from tb_grade
group by student_no
having student_no = 'A517178';


--10. 학과별 학생수를 구하여 "학과번호", "학생수(명)" 의 형태로 헤더를 만들어 결과값이 출력되도록 하시오.

select department_no 학과번호, count(*) "학생수(명)"
from tb_student
group by department_no
order by department_no;


--11. 지도 교수를 배정받지 못한 학생의 수는 몇 명 정도 되는지 알아내는 SQL 문을 작성하시오.

select count(*) - count(coach_professor_no)
from tb_student;


--12. 학번이 A112113인 김고운 학생의 년도 별 평점을 구하는 SQL 문을 작성하시오.
--단, 이때 출력 화면의 헤더는 "년도", "년도 별 평점" 이라고 찍히게 하고,
--점수는 반올림하여 소수점 이하 한 자리까지만 표시한다.

select substr(term_no, 1, 4) 년도 , round(avg(point), 1) "년도 별 평점"
from tb_grade
where student_no = 'A112113'
group by substr(term_no, 1, 4)
order by 년도;


--13. 학과 별 휴학생 수를 파악하고자 한다. 학과 번호와 휴학생 수를 표시하는 SQL 문장을 작성하시오.

select *
from tb_student;
select department_no,
count(student_name)
from tb_student
where absence_yn = 'Y'
group by department_no
order by 1;


--14. 춘 대학교에 다니는 동명이인(同名異人) 학생들의 이름을 찾고자 한다. 어떤 SQL 문장을 사용하면 가능하겠는가?

select student_name "동일이름", count(*) "동명인 수"
from tb_student
group by student_name
having count(student_name) >= 2
order by "동일이름";

--15. 학번이 A112113 인 김고운 학생의 년도, 학기 별 평점과 년도 별 누적 평점 , 총 평점을 구하는 SQL 문을 작성하시오.
--(단, 평점은 소수점 1자리까지만 반올림하여 표시한다.)

select*
from tb_grade;

select decode(grouping(substr(TERM_NO,1,4)),0, substr(TERM_NO,1,4), '　') 년도,
         decode(grouping(substr(TERM_NO,5,2)),0, substr(TERM_NO,5,2), '　') 학기,
         round(avg(point),1) 평점
from tb_grade
where STUDENT_NO = 'A112113'
group by rollup(substr(TERM_NO,1,4), 
             substr(TERM_NO,5,2))
order by 1,2;   




-------------------------------------------------
--class_0128
-------------------------------------------------

--학번/학생명/담당교수명 조회
--1. 두 테이블의 기준컬럼 파악
--2. on조건절에 해당되지 않는 데이터파악

select * from tb_student; --coach_professor_no
select * from tb_professor; --professor_no

--담당교수가 배정되지 않은 학생이나 교수 제외 - inner
--담당교수가 배정되지 않은 학생 포함 left
--담당학생이 없는 교수 포함 right

select count(*)
from tb_student S right join tb_professor P
    on S.coach_professor_no = P.professor_no;
    
--1. 교수배정을 받지 않은 학생 조회
select count(*)
from tb_student
where coach_professor_no is null;

--2. 담당 학생이 한명도 없는 교수 조회
--전체 교수 수
select count(*)
from tb_professor;

--중복없는 담당교수 수
select count(distinct coach_professor_no) --113
from tb_student;


