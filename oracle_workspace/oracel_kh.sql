--===================================
--kh계정
--===================================

SHOW USER;


-- table sample 생성 (id라는 이름의 컬럼, 그 컬럼의 data-type은 Num)
CREATE TABLE SAMPLE(
ID NUMBER
);

--오류 : insufficient privileges
--현재는 접속만 한 상태
--테이블을 만들려면 create table이라는 권한 필요함
---> 관리자 계정으로 가서 권한 부여 작업 필요


--현재 계정의 소유 테이블 목록 조회
SELECT*FROM TAB;

--사원테이블
SELECT*FROM employee;
--부서테이블
SELECT*FROM department;
--직급테이블
SELECT*FROM JOB;
--지역테이블
SELECT*FROM LOCATION;
--국가테이블
SELECT*FROM nation;
--급여등급테이블
SELECT*FROM sal_grade;



--테이블 table | entity | relation(entity가 확장된 형태)
--데이터를 보관하는 객체
--열 column | field | attribute / 세로 / 데이터가 담길 형식
--행 row | record | tuple /가로 / 실질적데이터를 담고있음
--도메인 domain 하나의 column에서 취할 수 있는 값의 그룹(범위)
        --ex. 남/녀 , y/n , 값을 그 중에서 선택해야 함
        
        
--테이블 명세 (describe)
--컬럼명       널여부     자료형+(해당크기)  
        
--1. 컬럼명
--대소문자 구분이 없음
--(empid, empId, EMPID 모두 다 같음.. -> Camel-Casing도 의미가 없음
--> 그래서 언더스코어를 이용해서 단어구분
-- -> emp_id, EMP_ID)

--2. 널여부
-- 값이 없을 수 있으면 null (선택, 생략가능),
-- 값이 없을 수 없으면 not null(꼭 기입해야함, 필수)
--like 회원가입's 필수, 선택 표시

--3. 자료형(해당크기)
-- 자료형 종류 : 날짜형, 숫자형, 문자형

DESC employee;
DESCRIBE employee;




--==========================================
--DATA TYPE
--==========================================

--data type의 목적 : 컬럼에 지정해서 값을 제한적으로 허용하기 위함
    --> 해당컬럼에 지정해준 type만 올 수 있도록
--1. 문자형 varchar2|char
--2. 숫자형 number
--3. 날짜시간형 date|timestamp
--4. LOB

--ex. salary -> 숫자만 , hire_date -> 날짜만
    --> 다른 type을 쓴다면 error!
    
    
---------------------------------------------
--문자형
---------------------------------------------
--영소문자 : 글자당 1byte로 처리됨
--한글 : 글자당 3byte로 처리됨 (11g XE Version / 다른 버전은 2byte)
--문자열 : 홑따음표(' ')로 감쌈
--cf. 고정형, 가변형 모두 지정된 크기 이상의 값은 추가 불가

--1) 고정형

--고정형 | char(byte) | 최대 2000byte

-- char(10)
--'korea' -> 1byte * 5 -> 5byte -> 고정형 10byte에 저장됨
--'안녕' -> 3byte * 2 -> 6byte -> 고정형 10byte에 저장됨
--> 실제 데이터크기와 상관없이 정해준 만큼 크기가 고정됨


--2) 가변형

--가변형 | varchar2(byte) | 최대 4000byte

--'korea' -> 1byte * 5 -> 실제데이터의 크기에 맞게 가변형 5byte로 저장됨
--'안녕' -> 3byte * 2 -> 실제데이터의 크기에 맞게 가변형 6byte로 저장됨
-->실제 데이터크기 = 저장되는 데이터크기

--가변형 | long | 최대 2GB (문자형임, 숫자가 아님!)

--LOB타입(Large Object) 중의 CLOB(Character LOB)는 단일컬럼 최대 4GB까지 지원
    
  
  
--세미콜론(;)
--이전 세미콜론 끝부터 현재 세미콜론 끝까지를 실행하도록 구역을 구분하는 구분자

--테이블생성
--CREATE TABLE 테이블명 (
--컬럼명 자료형(크기)[ 널여부 기본값 ]
--...
--);

CREATE TABLE tb_datatype (
    A CHAR(10),
    b VARCHAR2(10)
);


--테이블조회
--SELECT 컬럼
--FROM 테이블

--SELECT * -- *는 모든 컬럼
SELECT A, b -- 컬럼을 직접 지정
FROM tb_datatype; --테이블명
 
 
--데이터추가 : 한행을 추가
--INSERT INTO 테이블명
--VALUES(1번째 컬럼의 값, 2번째 컬럼의 값 ...)

INSERT INTO tb_datatype 
VALUES('hello', 'hello');

--숫자로 기입하여도 문자형으로 저장됨
INSERT INTO tb_datatype
VALUES('123', '안녕');

--지정 데이터크기를 벗어난 경우, 추가 불가 -> 데이터 무결성 (모든 데이터 하나하나가 정확해야 한다)
--Error : value too large for column (actual : 15, maximum : 10)
INSERT INTO tb_datatype
VALUES('에브리바디', '안녕'); 


--데이터가 변경(insert, update, delete)되는 경우,
--not DB에 실제 적용되는 것, but 메모리상에서 먼저 처리됨 , 실제 반영되는 건 아님
--commit을 통해 실제 database에 적용해야 한다
--실제 database에 적용하는 명령어 : COMMIT
--한번 COMMIT한 것은 복구할 수 없음
COMMIT;
--ROLLBACK; --마지막 commit시점 이후 변경사항은 취소된다


--lengthb(컬럼명): number - 저장된 데이터의 실제크기를 리턴
SELECT A, lengthb(A), b, lengthb(b)
FROM tb_datatype; 


---------------------------------------------
--숫자형
---------------------------------------------

--number(p, s)
--p : 표현가능한 전체 자리수
--s : p 개수 중 소수점 이하 자리수
--cf. 표현가능한 정수 자리수 = p - s
    --0, 양수 : 소수점 이하
    --음수 : 소수점 이상

--정수, 실수를 구분하지 않는다 (int, double x)
--지정해준 자리수까지 반올림된다

/*
값 1234.567 
--------------
number      1234.567 --저장에 제한이 없음
number(7)       1235 --소수점 이하 자리수는 허용하지 않음. 반올림되어 정수까지만 표현됨
 (why? 소수점이하 자리수를 설정해주지 않으면, 0 이 생략됨 -> 7, 0과 같음)
number(7, 1) 1234.6 --소수점 이하 2의 자리에서 반올림 -> 소수점 1까지만 표현됨
number(7, -2) 1200 --소수점 이상 2의 자리에서 반올림됨
*/




CREATE TABLE tb_datatype_number (
    A NUMBER,
    b NUMBER(7),
    C NUMBER(7,1),
    D NUMBER(7,-2)
);

SELECT * FROM tb_datatype_number;

INSERT INTO tb_datatype_number 
VALUES(1234.567, 1234.567, 1234.567, 1234.567);
--출력 : 1234.567, 1235, 1234.6, 1200

--지정한 크기보다 큰 자리수의 숫자를 넣으면
--Error : value larger than specified precision allowed for this column

--소수점 이하의 자리수는 지정 자리수보다 넘치면 잘라내면 되지만,
--소수점 이상의 자리수는 잘라낼 수 없음
INSERT INTO tb_datatype_number 
VALUES(1234567890.123, 1234567.567, 12345678.5678, 1234.567);

DROP TABLE tb_datatype_number;

CREATE TABLE tb_datatype_number (
    A NUMBER,
    b NUMBER(7),
    C NUMBER(7,1),
    D NUMBER(7,-2)
);

SELECT * FROM tb_datatype_number;

INSERT INTO tb_datatype_number 
VALUES(1234.567, 1234.567, 1234.567, 1234.567);

--지정한 크기보다 큰 숫자는 value larger than specified precision allowed for this column 유발
INSERT INTO tb_datatype_number 
VALUES(1234567890.123, 1234567.567, 12345678.5678, 1234.567);


COMMIT;




---------------------------------------------
--날짜시간형
---------------------------------------------

--1) date - sysdate
--년월일시분초 (보통의 경우)
--2) timestamp - systimestampe
--년월일시분초 밀리초 지역대 (정밀하게 다뤄야할 경우)

CREATE TABLE tb_datatype_date (
    A DATE,
    b TIMESTAMP
 );
 
SELECT *
FROM tb_datatype_date;
 
INSERT INTO tb_datatype_date
VALUES (sysdate, systimestamp);
--a 출력 : 21/01/26 (년월일만 출력되어 보임)
--b 출력 : 21/01/26 03:29:35.108000000

--to_char : 날짜/숫자를 문자열로 표현하는 함수
--TO_CHAR(컬럼, '표현할 형식') 
-- 해당 컬럼을 지정한 포맷으로 표현함
SELECT to_char(A, 'yyyy/mm/dd hh24:mi:ss'), b
FROM tb_datatype_date;
--a 출력 : 2021/01/26 03:29:35
--b 출력 : 21/01/26 03:29:35.108000000


--날짜형은 산술연산이 가능하다!
--날짜형 - 날짜형 = 숫자(1=하루)
--날짜형 +- 숫자(=하루) = 날짜형


--날짜형 +- 숫자(=하루) = 날짜형
 SELECT to_char(A , 'yyyy/mm/dd hh24:mi:ss'),
              to_char(A + 1, 'yyyy/mm/dd hh24:mi:ss'),
              to_char(A - 1, 'yyyy/mm/dd hh24:mi:ss'),
              b
FROM tb_datatype_date;
--1컬럼 출력 : 2021/01/26 03:29:35
--2컬럼 출력 : 2021/01/27 03:29:35
--3컬럼 출력 : 2021/01/25 03:29:35
--4컬럼 출력 : 21/01/26 03:29:35.108000000


--날짜형 - 날짜형 = 숫자(1=하루)
SELECT sysdate - A 
FROM tb_datatype_date;
--출력 : 0.009일차

--to_date : 문자열을 날짜형으로 변환하는 함수
--TO_DATE('날짜형으로 변환해줄 문자열')
--년월일만 써주면 자정으로 세팅됨
SELECT TO_DATE('2021/01/27') - A
FROM tb_datatype_date;
--출력 : 0.854(27일 자정이 되기까지 0.854일 남음)


--dual 가상테이블 -> create하지 않아도 가능
SELECT (sysdate + 1) - sysdate
FROM dual;
--출력 : 1 

--===================================================
--DQL (Data Query Language)
--===================================================
--데이터 조회(검색)을 위한 언어
--select문
--쿼리 조회결과를 ResultSet (결과집합)이라고 하며, 0행以上을 포함한다
    --조회된 결과가 0행일 수도 있음
--from절에 조회하고자 하는 테이블 명시
--where절에 의해 특정행을 filtering 가능 (특정행을 추릴수 있음)
--select절에 의해 컬럼을 filtering 또는 추가(가공) 가능
--order by절에 의해 행정렬 가능 (어떤 컬럼기준으로 행을 정렬할지)
/*
select 컬럼名 (5)                   필수
from 테이블名 (1)                   필수
where 조건点 (2)                   선택
group by 그룹기준 컬럼 (3)     선택
having 그룹조건点 (4)            선택
order by 정렬기준컬럼 (6)       선택
*/

SELECT *
FROM employee
WHERE dept_code = 'D9' -- 데이터는 대소문자 구분
ORDER BY emp_name ASC; --오름차순


--1. job테이블에서 job_name컬럼정보만 출력
    SELECT job_name
    FROM JOB;

--2. department테이블에서 모든 컬럼을 출력
    SELECT *
    FROM department;

--3. employee테이블에서 이름, 이메일, 전화번호, 입사일 출력
    SELECT emp_name, email, phone, hire_date
    FROM employee;

--4. employee테이블에서 급여가 2,500,000원 이상인 사원의 이름과 급여출력
    SELECT emp_name, salary
    FROM employee
    WHERE salary > 2500000;

--5. employee테이블에서 급여가 3,500,000원 이상이며, 직급코드가 j3인 사원을 조회
--not && || / but and or
    SELECT emp_name, salary, job_code
    FROM employee
    WHERE salary > 2500000 AND job_code = 'J3';
    
--6. employee테이블에서 현재 근무중인 사원을 이름오름차순으로 정렬
    SELECT emp_name
    FROM employee
    WHERE quit_yn = 'N'
    ORDER BY emp_name;

----------------------------------------------------------------------------
--select
----------------------------------------------------------------------------

--select절에 쓸 수 있는 것

--col
--1) table에 이미 존재하는 컬럼
--2) 만들어 낸 가상컬럼(산술연산 가능)
--3) literal 자체를 찍을 수 있음

--각각의 컬럼은 별칭(alias)를 가질 수 있음
--col (as) (")alias(") (as와 쌍따음표 생략가능)
--if 별칭 생략하면, 원래의 컬럼명으로 지정됨
--별칭에 공백, 특수문자가 있거나 숫자로 시작하는 경우 쌍따음표 필수

--가상컬럼 : 실제로는 존재하지 않지만, 마치 존재하는 것처럼 만들 수 있는 col
--또한 별칭을 가질 수 있음

--alias
SELECT emp_name AS "사원명", -- as + " "
            phone "전화번호", -- " "
            salary 급여, -- 모두 생략
            salary "급 여", -- 공백이 있는 경우, 쌍따음표(" ") 생략 불가
            salary "1급여",--숫자로 시작하는 경우, 쌍따음표(" ") 생략 불가
            salary 급여1, -- 숫자를 포함하지만, 맨 처음에 오는 경우가 아니라면, 쌍따음표(" ")생략 가능
            salary "급*여" --특수문자를 포함한 경우, 쌍따음표(" ") 생략 불가
FROM employee;

--가상컬럼
SELECT salary 급여, --이미 존재하는 컬럼
             salary *12 연봉, --가상컬럼 (컬럼 가공)
             123, --literal 자체 (임의의 값) / 숫자
             '안녕' --literal 자체 (임의의 값) / 문자열
FROM employee;


--실급여 : salary + (salary * bonus)
SELECT emp_name,
             salary,
             bonus,
             salary + (salary * bonus) 실급여
FROM employee;

--null값하고는 산술연산 불가
--why? 결과는 무조건 null이기 때문
--null%1 (나머지연산) 불가
SELECT NULL + 1, 
             NULL  - 1, 
             NULL * 1, 
             NULL / 1
FROM dual; --1행짜리 가상테이블

--null처리 함수 : nvl(처리해줄 col명, null일때 처리해줄 값)
--null의 값을 어떤 값으로 바꾸어서 처리함
--col의 값이 null이 아니면, col값 리턴
--col의 값이 null이면, 두번째 인자인 null일때 값을 리턴)
SELECT bonus,
            nvl(bonus, 0) null처리후
FROM employee;


SELECT emp_name,
             salary,
             bonus,
             salary + (salary * nvl(bonus, 0)) 실급여
FROM employee;

--중복제거용 keyword : distinct
--select절에 단 한번 사용가능
--직급코드를 중복없이 출력

SELECT DISTINCT job_code
FROM employee;

--두 개의 값을 하나로 보고, 두개가 일치하면 제거
--여러 컬럼사용시 컬럼을 묶어서 고유한 값으로 취급한다
SELECT DISTINCT job_code, dept_code
FROM employee;

--문자 연결연산자 ||
--더하기(+) 사용불가, 산술연산만 가능하다
--더하기 연산이면 오라클은 숫자라고 생각하고, 연산했다가, 숫자가 아니면 오류!

--Error : The specified number was invalid.
--select '안녕' + '하세요'
--from dual;

SELECT '안녕'||'하세요'||123
FROM dual;

SELECT emp_name || '(' || phone || ')'
FROM employee;

--------------------------------------------------------------------
-- WHERE
--------------------------------------------------------------------
--where : 행 필터링
--테이블의 모든 행 중 결과집합에 포함된 행을 필터링한다
--특정행에 대해서 true(포함)혹은 false(제외)결과를 리턴한다 (내부적으로 처리, true면 결과집합에 포함, false면 제외)
/*
연산자
(=은 하나만 써줌)
=                               같은가
!=   ^=     <>               다른가
>   <   >= <=               크기비교 (단순숫자, 날짜형)
between .. and          범위연산 (~이상 ~이하인가) (상한 값과 하한 값의 경계도 포함)
like , not like           문자패턴연산
is null , is not null   null여부
in , not in                 값목록에 포함여부

and             A이면서 B인가
or                A또는 B인가
not             제시한 조건 반전
*/

--부서코드가 D6인 사원 조회
SELECT emp_name, dept_code  --3 --행이 결정된 후 (where절), 그 후에 컬럼을 추려냄
FROM employee  --1
--where dept_code = 'D6';  --2 
--처리 : dept_code가 D6과 같니? 같으면 TRUE, 다르면 FALSE -> TRUE인 애들만 추려내서 결과집합에 반영해줌
--D6이 아닌 사원
--where not dept_code = 'D6';  --2 
WHERE dept_code <> 'D6';  --2  != ^= <>

--급여가 2,000,000원보다 많은 사원 조회
--세자리마다 콤마쓸 수 없음
SELECT emp_name, salary
FROM employee
WHERE salary >= 2000000;

--부서코드가 D6이거나 D9인 사원조회
SELECT emp_name, dept_code
FROM employee
WHERE dept_code = 'D6' OR dept_code = 'D9';

--날짜형 크기비교 가능
--과거가 작은 것, 이후가 큰 것 (과거 < 미래)
SELECT emp_name, hire_date
FROM employee
WHERE hire_date < '2000/01/01';  --날짜형식의 문자열은 자동으로 날짜형으로 형변환


--20년이상 근무한 사원 조회 (날짜형 - 날짜형 = 숫자(1=하루)
--날짜 자동형변환은 산술연산 불가 -> 명시적 형변환 필요(to_date)
SELECT emp_name, hire_date
FROM employee
--where quit_yn = 'N' and sysdate - hire_date > 365 * 20;
--N은 퇴사여부 (데이터값이 N으로 되어있으니, 소문자(n)으로 입력불가
WHERE quit_yn = 'N' AND TO_DATE('2021/01/22') - hire_date > 365*20;

--범위 연산
--급여가 200만원 이상, 400만원 이하인 사원 조회(사원명, 급여)
SELECT emp_name, salary
FROM employee
WHERE salary >= 2000000 AND salary <= 4000000;
--where salary between 2000000 and 4000000; --이상, 이하 // 초과, 미만은 between 쓸 수 없음

--입사일이 1990/01/01 ~ 2000/12/31인 사원 조회(사원명, 입사일)
SELECT emp_name, hire_date
FROM employee
--where hire_date between to_date('1990/01/01') and to_date('2000/12/31')  and quit_yn = 'N';
WHERE hire_date > TO_DATE('1990/01/01') AND hire_date < TO_DATE('2000/12/31') AND quit_yn = 'N';

--like, not like : 문자열 패턴 비교 연산
--ex. 글자가 세글자 있는것, 문자열 a를 포함하는 것 등
--wildcard : 패턴 의미를 가지는 특수문자
--_       아무 문자 한 글자
--%     아무 문자 0개 이상

SELECT emp_name
FROM employee
WHERE emp_name LIKE '전%'; --'전'으로 시작하는 0개 이상의 문자가 존재하는가
--전, 전차, 전진, 전형돈 가능
--파전 불가

SELECT emp_name
FROM employee
WHERE emp_name LIKE '전__'; --언더스코어 두개 / '전'으로 시작하고 뒤에 연달아 두개의 문자가 존재하는가
--전형돈, 전전전 가능 (딱 세글자여야함)
--전진, 전, 파전, 전당포아저씨 불가

--이름에 가운데글자가 '옹'인 사원 조회(단, 이름은 세글자)
SELECT emp_name
FROM employee
WHERE emp_name LIKE '_옹_'; 

--이름에 '이'가 들어가는 사원 조회
SELECT emp_name
FROM employee
WHERE emp_name LIKE '%이%'; 

--email컬럼값의 '_' 이전글자가 세글자인 이메일
SELECT email
FROM employee
--where email like '____%'; --네글자 이후, 0개 이상의 문자열이 뒤따르는가
--문자열과 와일드카드를 구분하지 못함
--> escape처리 : \문자 + 뒤에 escape문자등록 (escape 'escape문자')
--where email like '___\_%' escape '\';
--escape문자가 \로만 제한되는 것은 아님
--임의의 escaping 문자 등록, 데이터에 존재하지 않을 것
WHERE email LIKE '___*_%' ESCAPE '*';

--in, not in 값목록에 포함여부
--부서코드가 D6 또는 D8인 사원 조회
SELECT emp_name, dept_code
FROM employee
--where dept_code = 'D6' or dept_code = 'D8'; 
--where dept_code in 'D6' or dept_code in 'D8'; 
--괄호 안에 값 나열하면 됨 (개수 제한 없음)
WHERE dept_code IN ('D6' , 'D8'); 
--in - or

--부서코드가 D6 또는 D8이 아닌 사원 조회
SELECT emp_name, dept_code
FROM employee
--where dept_code != 'D6' and dept_code != 'D8'; --D6도 아니면서 D8도 아니어야 하니까, AND사용
WHERE dept_code NOT IN ('D6' , 'D8'); 
--not in - and

--in이나 not in이냐는 이게 아니면 저거 이니까, in과 not in을 합치면 전체일까? no!
--부서가 지정되지 않은 null값은 포함되지 않음!, null은 산술연산 불가, 또한 비교대상에도 제외됨

--인턴사원 조회
SELECT emp_name, dept_code
FROM employee
WHERE dept_code = NULL; --그러면 null인 사원만 따로 조회할 수 있을까? no!
--결론 -> null값은 산술연산, 비교연산 모두 불가능
--then how?

--null 비교연산으로 처리 ---> is null, is not null (null전용 연산자)
SELECT emp_name, dept_code
FROM employee
--where dept_code is null; --null인 사원 
WHERE dept_code IS NOT NULL; --null이 아닌 사원

--D6, D8부서원이 아닌 사원조회(인턴사원 포함)
SELECT emp_name, dept_code
FROM employee
WHERE dept_code NOT IN ('D6' , 'D8') OR dept_code IS NULL; 



--nvl버전 (null을 특정값으로 대치해서 대립값을 만듦)
--select emp_name, dept_code
SELECT emp_name, nvl(dept_code, '인턴') --원본값을 바꿔준 것이 아니라, 값이 없다면 인턴이라고 임시로 출력해줘라 (출력이니까, 인턴이라고 바뀌어서 출력)
FROM employee
WHERE nvl(dept_code, 'D0') NOT IN ('D6' , 'D8'); --NULL이면 D0으로 바꿔라 -> D0을 D6, D8과 비교 -> D6,D8이 아님
--원본값이 바뀐 것이 아니라 조건을 위해 임시로 설정해줌  -> 조건이니까 실제로 출력되지도 않음


------------------------------------------------------------------------------------
--ORDER BY
------------------------------------------------------------------------------------
--select구문 중 가장 마지막에 처리
--지정한 컬럼 기준으로 결과집합을 정렬해서 보여줌

--오름차순 (<) , 내림차순(>)
--number 0 < 10
--string ㄱ < ㅎ , a < z
--date 과거 < 미래(시간이 쌓여서 커지니까)
--null값 위치를 결정가능 (비교연산이 불가하니까) nulls first | nulls last
--정확한 정렬결과를 얻고 싶다면, 반드시 order by문 필요
--언뜻보면, 특정 행을 기준으로 정렬된 것 같아보이지만, 어떤 경우에는 이 정렬기준이 틀어질 수 있음
--asc 오름차순(기본값)
--desc 내림차순
--복수개의 컬럼을 차례로 정렬가능 (앞에 컬럼 먼저 정렬, 그 후 다음 것 정렬)

SELECT emp_id, emp_name, dept_code, job_code, hire_date, salary
FROM employee
--order by dept_code; --특정행을 기준으로 (asc가 생략되어있음)
--order by dept_code asc; --특정행을 기준으로 오름차순
--order by dept_code desc; --특정행을 기준으로 내림차순
--order by dept_code desc nulls last; --null값을 뒤로
--order by dept_code, job_code; --두개의 기준컬럼을 세움, 앞에 것 먼저 처리, 뒤에 것을 다음으로 처리
--order by dept_code, emp_name; --두개의 기준컬럼을 세움, 앞에 것 먼저 처리, 뒤에 것을 다음으로 처리
ORDER BY salary DESC; --많은 것부터 하려면 desc

--select절의 alias를 order by절에서 사용가능
--alias가 반영될 수 있는 것은 order by절에만 가능
--why? 처리순서가 select -> order by
SELECT emp_id 사번,
             emp_name 사원명
FROM employee
ORDER BY 사원명; --컬럼명 그대로 써줘도 되지만, alias를사용해줘도 됨

--1부터 시작하는 컬럼순서 사용가능
SELECT *
FROM employee
ORDER BY 9 DESC; --컬럼순서를 1부터 시작해서 넘버링
--but 비추! 컬럼 추가, 삭제시 컬럼 수가 바뀌니까 결과가 달라짐



--===================================
--BUILT-IN FUNCTION
--===================================
--METHOD나 FUNCTION이나 정의해놓고 호출해서 쓰는건 동일하지만,
--FUNCTION이 더 폭이 넓음 (function안에 method가 포함됨)
--OBJECT안에 포함된 함수를 METHOD라고 부르는 경향이 있음
--METHOD와 FUNCTION의 비교
--공통점 : 일련의 실행 코드 묶음을 작성해두고 -> 호출해서 사용
--                실행할 것을 묶어놓은 것이라고 생각하면 됨
--다른점 : FUNCTION은 반드시 하나의 리턴값을 갖는다
--                CF. METHOD는 VOID도 포함

--1. 단일행 함수 : 각 행마다 반복 호출되어서 호출된 수만큼 결과를 리턴함
--      a. 문자처리함수
--      b. 숫자처리함수
--      c. 날짜처리함수
--      d. 형변환함수
--      e. 기타함수
--2. 그룹함수 : 여러행을 grouping한 후, 그룹당 하나의 결과를 리턴함
--10개의 값이 있다면, 단일행 함수는 10개의 결과가 나오고, 그룹함수는 그 10개를 묶어서 1개의 결과가 나옴


----------------------------------------------------------------------
--단일행 함수
----------------------------------------------------------------------
--****************************************
--      a. 문자처리함수
--****************************************

--length(col) -> 문자열의 길이 리턴
SELECT emp_name, LENGTH(emp_name)
FROM employee;

--조건에도 사용가능
SELECT emp_name, email
FROM employee
WHERE LENGTH(email) < 20;  --리턴값이 무조건 하나는 있어야 비교 가능

--lengthb(col)
--값의 byte수 리턴
SELECT emp_name, lengthb(emp_name) --한글은 3byte씩 처리 -> 글자길이 * 3 = byte수
        email, lengthb(email) --영문자, 숫자는 1byte씩 처리 -> 글자길이 = byte수
FROM employee;

--instr(string, search[, startPosition[,occurence]])
--[] 대괄호는 생략가능함을 의미
--특정 문자열(String)에서 찾고자 하는(search) 문자가 위치한 index를 반환
--oracle : 1-based index, 1부터 시작
--oracle은 0부터 시작하는 것이 아무것도 없음
--빈칸도 1byte로 처리

SELECT instr('kh정보교육원 국가정보원', '정보'), -- -> 3을 리턴 --> 문자열에서 정보의 위치 : 3번
        instr('kh정보교육원 국가정보원', '안녕'), -- -> 0을 리턴 --> 값이 없으면 0리턴
        instr('kh정보교육원 국가정보원', '정보', 5), --어디서 부터 찾을지 지정 (5번지부터 찾기 시작해서, '정보'라는 것을 찾고, '정보'ㅛ 의 첫번째 부터의 인덱스를 구함)
        instr('kh정보교육원 국가정보원 정보문화사', '정보', 1, 3), --1번 인덱스부터 찾되, 3번째 나온 정보를 찾음
        instr('kh정보교육원 국가정보원', '정보', -1) --음수면 뒤의 '정보'부터 찾아서, 그 '정보'의 인덱스 번호를 리턴함
FROM dual;

--email 컬럼값 중 '@'의 위치는? (이메일, 인덱스)
SELECT instr(email, '@'), email
FROM employee;

--substr(string, startIndex[, length])
--[] 대괄호는 생략가능함을 의미
--string에서 startIndex부터 length개수만큼 잘라내어 리턴
--length생략 시에는 문자열 끝까지 반환

SELECT substr('show me the money', 6, 2), --> me 리턴
        substr('show me the money', 6), --> 마지막 인자를 생략함 --> startIndex부터 있는 문자열 전체 리턴 --> me the money
        substr('show me the money', -5, 3), --> mon리턴 (뒤에서 거꾸로 5개 인덱스까지 물러난다음, 그 다음 앞에서부터 차례대로 3개를 리턴
        substr('show me the money', -6, 3) --> 공백도 하나의 문자이기 때문에 --> 공백+m+o
FROM dual;


--사원명에서 성(1글자로 가정)만 중복없이 사전순으로 출력
SELECT DISTINCT substr(emp_name, 1, 1) 성
FROM employee
ORDER BY 1; --컬럼 번호
--order by 성; -- 별칭 행으로 정렬

--lpad | rpad (string, byte [, padding_char])
--byte수의 공간에 string을 대입하고, 남은 공간은 padding_char를 (왼쪽|오른쪽) 채워라
--padding char는 생략시 공백문자가 대입됨
--byte만큼의 공간을 확보한 후, 값을 넣고 남은 공간은 padding_char로 채움

SELECT lpad(email, 20, '#'),
          rpad(email, 20, '#'),
          '[' || lpad(email, 20) || ']',
          '[' || rpad(email, 20) || ']'
FROM employee;

--남자사원만 사번, 사원명, 주민번호, 연봉 조회
--주민번호 뒤 6자리는 ****** 숨김처리
SELECT  emp_id,
            emp_name,
            substr(emp_no, 1, 8) || '******' emp_no, --방법1
            rpad(substr(emp_no, 1, 8), 14, '*') emp_no, --방법2
            (salary + (salary * nvl(bonus, 0))) * 12 annual_pay
FROM employee
WHERE substr(emp_no, 8, 1) IN ('1', '3');


--****************************************
--      b. 숫자처리함수
--****************************************
--mod(피젯수, 젯수) : 나머지 함수
--나머지연산자 %가 없다

SELECT MOD(10, 2),
            MOD(10, 3),
            MOD(10, 4)
FROM dual;

--입사년도가 짝수인 사원 조회
SELECT emp_name,
        EXTRACT(YEAR FROM hire_date) YEAR --날짜함수 : 년도 추출
FROM employee
WHERE MOD(EXTRACT(YEAR FROM hire_date), 2) = 0
ORDER BY YEAR;


--ceil(number)
--소수점기준으로 올림
SELECT ceil(123.456),
            ceil(123.456 * 100) / 100 --부동소수점 방식으로 처리 (소수점을 옮겨다닐 수 있음)
FROM dual;

--floor(number)
--소수점 기준으로 버림
SELECT floor(456.789),
            floor(456.789 * 10) / 10
FROM dual ;

--round(number[, position])
--position기준(소수점 기준)으로 반올림처리
--position 정의해주지 않으면 0 (기본값)
--음수면 소수점 왼쪽으로 position자리까지 반올림처리
SELECT round(234.567),
            round(234.567, 2),
            round(234.567, -1)
FROM dual;

--trunc(number[, position])
--버림

SELECT TRUNC(123.567),
            TRUNC(123.567, 2)
FROM dual;





--****************************************
--      c. 날짜처리함수
--****************************************
--add_months(date, number)
--date기준으로 몇달(number) 전후의 날짜형을 리턴

--날짜형 + 숫자 = 날짜형
--날짜형 - 날짜형 = 숫자

SELECT sysdate,
            sysdate + 5,
            add_months(sysdate, 1),
            add_months(sysdate, -1),
            add_months(sysdate + 5 , 1)
FROM dual;

--months_between(미래, 과거)
--두 날짜형의 개월수 차이를 리턴

SELECT sysdate,
            TO_DATE('2021/07/08'), -- 날짜형으로 변환하는 함수
            TRUNC(months_between(TO_DATE('2021/07/08'), sysdate), 1) diff
FROM dual;

--이름, 입사일, 근무개월수(n개월), 근무개월수(n년 m개월) 조회

SELECT emp_name 이름,
        hire_date 입사일,
        sysdate 오늘,
      TRUNC(months_between(sysdate, hire_date)) || '개월' "근무 개월수",
      TRUNC((months_between(sysdate, hire_date))/12) || '년' ||
      TRUNC(MOD(months_between(sysdate, hire_date), 12)) ||'개월' "근무 개월수"
FROM employee;

--extract(year | month | day | hour | minute | second  from date) : number
--날짜형 데이터에서 특정필드만 숫자형으로 리턴


SELECT
            --년월일
            EXTRACT(YEAR FROM sysdate) yyyy,
            EXTRACT(MONTH FROM sysdate) mm, -- 1부터 12를 리턴 ( 1부터 시작 )
            EXTRACT(DAY FROM sysdate) dd,
            --시분초 -> timestamp로 바꿔주는 cast함수 사용
            EXTRACT(HOUR FROM CAST(sysdate AS TIMESTAMP)) hh,
            EXTRACT(MINUTE FROM CAST(sysdate AS TIMESTAMP)) mi,
            EXTRACT(SECOND FROM CAST(sysdate AS TIMESTAMP)) ss
FROM dual;


--trunc(date)
--시분초 정보를 제외한 년월일 정보만 리턴
SELECT to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss') date1, --날짜형을 문자열로 바꿔주는 함수
            to_char(TRUNC(sysdate), 'yyyy/mm/dd hh24:mi:ss') date2
FROM dual;


--****************************************
--      d. 형변환함수
--****************************************
--형변환함수를 쓰지 않아도 자동형변환이 되는 경우가 있으나, 명시적 형변환해줘서 오류 방지

/*
            to_char       to_date
            ------>     ------>
     number        string        date
            <------     <------
            to_number   to_char
*/

--to_char(date | number[, format])
SELECT to_char(sysdate, 'yyyy/mm/dd (dy) hh24:mi:ss') NOW, -- date format API 참고하기
              to_char(sysdate, 'fmyyyy/mm/dd (dy) hh24:mi:ss') NOW, --fm : 형식문자로 인한 앞글자 0을 제거
              to_char(sysdate, 'yyyy"년" mm"월" dd"일"') NOW --한글로 표현하고 싶을때, 인식하지 못하는 한글을 쌍따음표로 감싸주기
FROM dual;


SELECT to_char(1234567, 'fmL9,999,999,999'), --L은 지역화폐, 한국이니까 원화가 표시됨
            to_char(1234567, 'fmL9,999'), --포맷문자가 실제값보다 커야함. 자리 수가 모자라면 안됨 (#########)
            --형식문자열을 충분히 주고, 남는 것은 fm으로 지우기
            to_char(123.4, 'fm9999.99'), --9 : 소수점 이상의 빈자리는 공란으로 취급, 소수점이하 빈자리는 0처리
            to_char(123.4, 'fm0000.00') --0 : 빈자리는 모두 0으로 처리
            --불필요한 공백, 0은 모두 fm으로 제거해주기
            --포맷문자 0은 fm으로 제거되지 않음. 0을 쓰는 경우는 보통 숫자 고정길이로 표현
FROM dual;

--이름, 급여, 입사일을 조회
--급여는 세자리마다 콤마, 입사일은 1990-9-3(화) 형식으로 조회

SELECT emp_name 이름,
            to_char(salary, 'fmL9,999,999,999') 급여,
           to_char(hire_date, 'fmyyyy-mm-dd (dy)' ) 입사일
FROM employee;

--to_number(string, format) 
--문자열을 받아서 어떤 형식의 문자열인지 알려주는 것 -> 결과적으로 숫자형을 받아냄
SELECT to_number('1,234,567', '9,999,999') + 100,
    --  '1,234,567' + 100 (더하기 연산에서 문자열은 더할 수 없음 -> 위와 같은 형변환 필요
          to_number('￦3,000', 'L9,999') + 100
FROM dual;

--자동형변환 지원
SELECT '1000' + 100, --온전히 숫자만 문자열 안에 있는 경우는 자동형변환하여 숫자 + 숫자로 인식함, 더하기 좌우로는 산술연산을 기대하기 때문
            '99' + 1,
            '99' || '1' --붙이기 => 문자열로 바꿔줘야 함
FROM dual;


--to_date(string, format) 이 문자열은 이런 형식으로 쓰인 정보야! 라고 알려줌
--string이 작성된 형식문자를 그대로 format으로 전달해야 함
--형식이 맞지 않다면 오류남 (ex. yyyy-mm-dd -> error!)
--날짜형식으로 리턴됨 -> 날짜 연산을 지원
SELECT TO_DATE('2020/09/09', 'yyyy/mm/dd') + 1 
FROM dual;


--'2021/07/08 21:50:00'를 2시간 후의 날짜 정보를 yyyy/mm/dd hh24:mi:ss형식으로 출력
SELECT to_char(
TO_DATE('2021/07/08 21:50:00', 'yyyy/mm/dd hh24:mi:ss') + (2/24), 
'yyyy/mm/dd hh24:mi:ss') RESULT
FROM dual;


--현재시각 기준 1일 2시간 3분 4초 후의 날짜 정보를 yyyy/mm/dd hh24:mi:ss형식으로 출력
--1시간 : 1 / 24
--1분 : 1 / (24 * 60)
--1초 : 1 / (24 * 60 * 60)
SELECT to_char(
            sysdate + 1 + (2/24) + (3 / (24*60)) + (4 / (24*60*60)),
            'yyyy/mm/dd hh24:mi:ss'
            )
FROM dual;

--sysdate 같은 경우는 그 순간만을 가리킴 why? 실행하고 나면 바로 그 순간이 지나버리기 때문

--기간타입 (순간이 아닌, 시간차이를 가지고 있음)
--interval year to month : 년월 기간
--interval date to second : 일시분초 기간

--지금으로부터 1년 2개월 3일 4시간 5분 6초 후 조회

SELECT to_char(
            add_months(sysdate, 14) + 3 + (4/24) + (5/24/60) + (6/24/60/60),
            'yyyy/mm/dd hh24:mi:ss') RESULT
FROM dual;
    
    --년월 / 앞에 부호값을 줘서 빼거나 더할수도 있음
SELECT to_char(
            sysdate + to_yminterval('01-02') + to_dsinterval('3 04:05:06'),
            'yyyy/mm/dd hh24:mi:ss'
            ) RESULT
FROM dual;


--numtodsinterval(diff, unit) (일시분초)
--numtoyminterval(diff, unit) (연월)
--숫자를 interval타입으로 바꾸는 것
--diff : 날짜간 차이
--unit : year | month | day | hour | minute 중 원하는 단위

SELECT 
    numtodsinterval(
    TO_DATE('20210708', 'yyyymmdd') - sysdate,
    'day'
    )
FROM dual;
--출력 : +163 04:50:38.000000

--날짜만 가져오고 싶을 때
SELECT EXTRACT( DAY FROM
    numtodsinterval(
    TO_DATE('20210708', 'yyyymmdd') - sysdate,
    'day'
    )) diff
FROM dual;
--출력 : 163



--****************************************
--      e. 기타함수
--****************************************


--null처리함수
--nvl(col, null_value) --> null값만 처리
--nvl2(col, not_null_value, null_value) --> null값, null이 아닌 값 모두 처리
    --col값이 null이 아니면, 두번째 인자를 리턴,
    --col값이 null이면, 세번째 인자를 리턴

SELECT emp_name,
            bonus,
            nvl(bonus, 0) nvl1,
            nvl2(bonus, '있음', '없음') nvl2
FROM employee;


--선택함수1
--decode(expr(표현식, col | col을 가공한 값), 값1(조건1), 결과값1, 값2(조건2), 결과값2, ....[, 기본값 (아무조건에도 해당하지 않을 경우)])

SELECT emp_name,
            emp_no,
            decode(substr(emp_no, 8, 1), '1', '남', '2', '여', '3', '남', '4', '여' ) gender, --기본값 미사용
            decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여' ) gender --기본값 사용
FROM employee;

--직급코드에 따라서 J1-> 대표, J2/J3 -> 임원, 나머지는 평사원으로 출력 (사원명, 직급코드, 직위)
SELECT emp_name 사원명, 
            job_code 직급코드,
            decode(job_code, 'J1', '대표', 'J2', '임원', 'J3', '임원', '평사원') 직위
FROM employee;

--선택함수는 where절에도 사용가능
--여사원만 조회
SELECT emp_name,
            emp_no,
            decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여' ) gender
FROM employee
WHERE  decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여' ) = '여';


--선택함수2
--타입이 두가지라서 주의해서 써야함!
--case

/*
type1 (decode와 유사)

case 표현식
    when 값1 then 결과1
    when 값2 then 결과2
    .......
    [else 기본값]
    end

--

type2

case
    when 조건식1 then 결과1
    when 조건식2 then 결과2
    ......
    [else 기본값]
    end
    
    ->true/false로 떨어질 수 있는 것을 줘야함
    -- where절에 썼던 것처럼 조건식을 when ~ then에 써줌
    -->조건식을 하나로 합쳐줄수도 있음

*/


--type1
SELECT emp_no,
            CASE substr(emp_no, 8, 1)
                WHEN '1' THEN '남'
                WHEN '3' THEN '남'
                ELSE '여'
                END gender
FROM employee;


--type2
SELECT emp_no,
            CASE
            WHEN substr(emp_no, 8, 1) = '1' THEN '남'
            WHEN substr(emp_no, 8, 1) = '3' THEN '남'
            ELSE '여'
            END gender
   FROM employee;     
   
SELECT emp_no,
            CASE
            WHEN substr(emp_no, 8, 1) IN ('1', '3') THEN '남' --한꺼번에 써주기
            ELSE '여'
            END gender            
FROM employee;


SELECT emp_name 사원명, 
            job_code 직급코드,
            CASE
            WHEN job_code = 'J1' THEN '대표'
            WHEN job_code IN ('J2', 'J3') THEN '임원'       
            ELSE '평사원'
            END 직위
FROM employee;


----------------------------------------------------------------------
--GROUP FUNCTION (그룹 함수)
----------------------------------------------------------------------
--여러행을 그루핑하고, 그룹당 하나의 결과를 리턴하는 함수
--모든 행을 하나의 그룹, 또는 group by를 통한 세부그룹 지정 가능


--모든 행을 하나의 그룹으로 묶어서 처리
--sum(col)

--한달 모든 직원 급여의 합을 구함
SELECT SUM(salary) --모든 행을 하나의 그룹으로 묶어서 더함 -> 하나의 결과를 가져옴
FROM employee;
--전체 employee테이블에서 salary컬럼의 모든 행의 합계를 구하는 것
--별도의 그룹조건이 없다면 전체행을 하나의 그룹으로 봄
--전체 행을 더한 결과가 sum으로 나온 것
--그렇다고 여러행을 합할 수 없음. 무조건 단일컬럼에서 진행


-- cf. 그룹함수의 결과와 일반컬럼을 동시에 조회불가
--select emp_name, sum(salary)
--from employee;
--error : not a single-group group function

--그룹함수 간은 동시에 조회가능
SELECT SUM(salary), AVG(salary)
FROM employee;

SELECT SUM(salary), SUM(bonus) --null인 컬럼은 제외하고 누계처리
FROM employee;

--실제 존재하지 않고 가공된 컬럼도 그룹함수 가능
SELECT SUM(salary),
            SUM(bonus),
            SUM(salary + (salary * nvl(bonus, 0))) SUM
FROM employee;



--avg(col)
--평균
SELECT round(AVG(salary), 1) AVG,
          to_char(round(AVG(salary), 1), 'fml9,999,999,999.9') AVG --포매팅
FROM employee;

--부서코드가 D5인 부서원의 평균급여 조회
SELECT to_char(round(AVG(salary), 1), 'fml9,999,999,999.9') AVG
FROM employee
WHERE dept_code = 'D5';

--남자사원의 평균급여 조회
SELECT  to_char(round(AVG(salary), 1), 'fml9,999,999,999.9')
FROM employee
WHERE substr(emp_no, 8, 1) IN ('1', '3') ;


--count(col)
--null이 아닌 col의 개수 세기

SELECT COUNT(emp_id)
FROM employee;
--출력 : 24 (이름에는 null이 없으니 모든 행의 길이가 나옴)

--* 모든컬럼, 즉 하나의 행을 의미
--리턴된 행이 몇행인지 물어봄
SELECT COUNT (*), --결과 : 24
          COUNT (bonus), --결과 : 9 (bonus컬럼이 null이 아닌 행의 수)
          COUNT(dept_code) --22명이 있음
FROM employee;

--보너스를 받는 사원수 조회
SELECT COUNT(*)
FROM employee
WHERE bonus IS NOT NULL;

SELECT SUM(
           CASE
            WHEN bonus IS NOT NULL THEN 1
 --           when bonus is null then 0 (null인 값은 그룹함수 연산에서 제외되기 때문에 생략가능) 
            END)
FROM employee;

--사원이 속한 부서 총수 (중복없음)

SELECT COUNT(DISTINCT dept_code)
FROM employee;


--max(col) | min(col)
--해당 컬럼 중 제일 큰 값 | 제일 작은 값
--숫자, 날짜(max 미래, min 과거), 문자(max ㅎ, min ㄱ) 모두 가능

SELECT MAX(salary), MIN(salary),
            MAX(hire_date), MIN(hire_date), --max : 가장 늦은 입사일, min : 가장 이른 입사일
            MAX(emp_name), MIN(emp_name) --max : 가나다 순에서 제일 뒤, min : 가나다 순에서 제일 앞
FROM employee;


--나이 추출시 주의점
--한국식나이 : 현재년도 - 탄생년도 + 1
--cf. 만나이 : 생일이 지나면, 현재년도 - 탄생년도
            --생일이 안지나면, 현재년도 - 탄생년도 - 1
SELECT emp_name,
            emp_no,
            substr(emp_no, 1, 2),
--           extract(year from to_date(substr(emp_no, 1, 2), 'yy')),
--           extract(year from sysdate) -  extract(year from to_date(substr(emp_no, 1, 2), 'yy')) + 1,
--           extract(year from to_date(substr(emp_no, 1, 2), 'rr')),
--           extract(year from sysdate) -  extract(year from to_date(substr(emp_no, 1, 2), 'rr')) + 1
               EXTRACT(YEAR FROM sysdate) -  
               (decode(substr(emp_no, 8, 1), '1', 1900, '2', 1900, 2000) + substr(emp_no, 1, 2)) + 1 age
FROM employee;
--yy는 현재년도 기준으로 현재세기(2000~2099)에서 추측한다.
--rr은 현재년도 기준으로 앞 뒤 50년단위로(1950~2049) 추측한다.




-------------------------------------------------
--GROUP BY
-------------------------------------------------
--지정컬럼기준으로 세부적인 그룹핑이 가능하다
--group by구문이 없다면 전체를 하나의 그룹으로 취급한다
--group by절에 명시한 컬럼만 select절에 사용가능
--group by절에는 일반컬럼 | 가공컬럼 모두 가능
--콤마(,) 이용하여 group by를 여러개 해줄 수 있음
   --두개 이상의 컬럼을 그룹핑 가능

--아무런 그룹지정이 없기 때문에, 전체를 하나의 그룹으로 처리한 것
SELECT SUM(salary)
FROM employee
GROUP BY (); --이것과 동일함


--전체 24행
SELECT emp_name, dept_code, salary 
FROM employee;

--전체 24행 중, dept_code가 같은 것끼리 묶어냄
--그룹핑 된 것 중 salary를 더해 결과값을 냄
--부서별로 급여의 합계를 더함
SELECT dept_code, SUM(salary)
FROM employee
GROUP BY dept_code; 


--group 함수가 아닌 컬럼은 group by절에 명시한 컬럼만 select절에 사용가능
SELECT dept_code, 
          emp_name,
          SUM(salary)
FROM employee
GROUP BY dept_code; 
--Error : not a GROUP BY expression

SELECT job_code, AVG(salary)
FROM employee
GROUP BY job_code
ORDER BY job_code;

--부서코드별 사원수 조회
--dept_code는 null값을 count하지 않음
--*은 null값을 count함 why? 전체행을 하나로 취급하기 때문
SELECT dept_code, COUNT(dept_code), COUNT(*)
FROM employee
GROUP BY dept_code;

--부서코드별 사원수, 급여평균, 급여합계 조회
SELECT dept_code, COUNT(dept_code),
             to_char(TRUNC(AVG(salary), 1), 'fml9,999,999,999.0'),
             to_char(SUM(salary), 'fml9,999,999,999')
FROM employee
GROUP BY dept_code;

--cf. 테이블의 정렬을 보고, 숫자인지 문자열인지 확인가능
    --오른쪽 정렬 : 숫자, 왼쪽 정렬 : '문자열'


--가상컬럼으로 행 구분도 가능
--성별 인원수, 평균급여 조회
--각 행이 남, 여로 구분되어 그룹핑됨
SELECT decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender,
            COUNT(*) COUNT,
            to_char(TRUNC(AVG(salary), 1), 'fml9,999,999,999.0') AVG
FROM employee
GROUP BY decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여');


--직급코드 J1을 제외하고, 입사년도별 인원수 조회
SELECT EXTRACT(YEAR FROM hire_date)||'년' 입사년도,
           COUNT(*)||'명' 인원수
FROM employee
WHERE job_code != 'J1'
GROUP BY EXTRACT(YEAR FROM hire_date)
ORDER BY 입사년도;


--두개 이상의 컬럼을 그룹핑 가능 (순서대로 처리됨)
--같은 부서끼리 그룹핑, 그 부서내에서 직급이 같은 것끼리 또 그룹핑
--null도 하나의 group으로 인식, null을 없애기 위한 nvl처리
SELECT nvl(dept_code, '인턴') dept_code,
           job_code, COUNT(*)
FROM employee
GROUP BY dept_code, job_code
ORDER BY 1, 2;

--부서별 성별 인원수
SELECT nvl(dept_code, '인턴') dept_code,
           decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender,
           COUNT(*) "num/gender"
FROM employee
GROUP BY dept_code, decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여')
ORDER BY 1, 2;



-------------------------------------------------
--HAVING
-------------------------------------------------
--group by 이후 조건절
--where절도 조건절이었지만,
--group by 이후 having을 통해 다시 filtering 가능함
--그룹핑한 결과에 대해 다시 조건을 붙이는 것임 -> having 단독으로 사용 불가

--부서별 평균 급여가 3,000,000원 이상인 부서만 조회
SELECT dept_code,
            TRUNC(AVG(salary)) AVG
FROM employee
GROUP BY dept_code
HAVING AVG(salary) >= 3000000;

--직급별 인원수가 3명이상인 직급 조회
SELECT job_code 직급, COUNT(*) 인원수
FROM employee
GROUP BY job_code
HAVING COUNT(*) >= 3;


--관리하는 사원이 두명 이상인 manager의 id, 관리하는 사원수 조회

--방법1. where절로 행을 제외한 후 group by
SELECT manager_id ID, COUNT(*) "관리하는 사원수"
FROM employee
WHERE manager_id IS NOT NULL
GROUP BY manager_id
HAVING COUNT(*) >= 2
ORDER BY ID;

-- 방법2. group by한 후, manager_id를 count함 -> 그룹함수이니까 null은 count되지 않음
SELECT manager_id ID, COUNT(*) "관리하는 사원수"
FROM employee
WHERE manager_id IS NOT NULL
GROUP BY manager_id
HAVING COUNT(manager_id) >= 2
ORDER BY ID;


--group by절에서 사용하는 함수
--rollup | cube(col1, col2 ....)
--사용 이유 : 그룹핑 결과에 대해 소계(합계)를 제공

--rollup : 지정컬럼에 대해 단방향 소계 제공
--cube : 지정컬럼에 대해 양방향 소계 제공
--지정컬럼이 하나인 경우, rollup/cube의 결과는 같다
--지정컬럼이 두개 이상부터, 쓰임에 차이가 있다!

--rollup
SELECT dept_code, COUNT(*)
FROM employee
GROUP BY ROLLUP(dept_code);
--	출력 : (null) 24 (행 추가됨)

--컬럼이 하나일때는 cube도 rollup과 결과가 같음
SELECT dept_code, COUNT(*)
FROM employee
GROUP BY CUBE(dept_code);
--	출력 : (null) 24 (행 추가됨)

--grouping() 함수
--실제데이터 | 집계 데이터 컬럼을 구분하는 함수
--실제데이터 : 0리턴 | 집계데이터 : 1리턴


--nvl처리만 해줄 경우
SELECT nvl(dept_code, '인턴') , COUNT(*)
FROM employee
GROUP BY ROLLUP(dept_code);
--인턴 2
--인턴 24 --소계도 같이 '인턴'처리됨

SELECT dept_code, decode(GROUPING(dept_code), 0, nvl(dept_code, '인턴'), 1, '합계') dept_code, COUNT(*)
FROM employee
GROUP BY ROLLUP(dept_code);




--두개 이상의 컬럼을 rollup 또는 cube에 전달하는 경우

--rollup 사용
SELECT decode(GROUPING(dept_code), 0, nvl(dept_code, '인턴'), '합계') dept_code, 
            decode(GROUPING(job_code), 0, job_code, '소계') job_code,
            COUNT(*)
FROM employee
GROUP BY ROLLUP(dept_code, job_code)
ORDER BY 1, 2;


--출력 : 부서코드별 소계가 나옴
--전체합계,
--그냥 dept_code gropu by 했을때의 결과만 돌려줌



--cube 사용
SELECT decode(GROUPING(dept_code), 0, nvl(dept_code, 'intern'), '소계') dept_code,
            decode(GROUPING(job_code), 0, job_code, '소계') job_code,
            COUNT(*)
FROM employee
GROUP BY CUBE(dept_code, job_code)
ORDER BY 1, 2;
--전체합계,
--dept_code group by 했을때의 소계
--job_code group by 했을때의 소계


---------------------------------------------------------------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------

--relation으로 합쳐 만들어질 것을 대비하여 entity로 나누어서 관리함
--entity와 entity를 합쳐 relation을 만드는 방법
--가로 방향으로 합치기 : join (행 + 행)
--세로 방향으로 합치기 : union (열 + 열)
--cf. union은 열과 열을 그대로 붙임
--cf. join은 행대로 그대로 붙이지 않고, 엇갈려서도 합칠수 있음, , 한줄 한줄을 붙여서, 긴 한 줄을 만듦
--실제로 존재하는 테이블이 아니고, 잠깐 합쳐져서 출력되는 것 뿐, create과정 필요x

--===============================================
--JOIN
--===============================================
--두개 이상의 테이블을 연결해서 하나의 가상테이블(relation)을 생성
--cf. 가상 테이블이라고는 하지만, 여전히 결과집합이긴 함
--기준컬럼을 가지고 행을 연결시킴


--송중기 사원의 부서명을 조회
SELECT dept_code
FROM employee
WHERE emp_name = '송종기';
--출력 : D9
--부서명은 없고 dept_code가 있음
--이 dept_code를 가지고 department 테이블에 가면 알 수 있음

SELECT dept_title
FROM department
WHERE dept_id = 'D9';
--출력 : 총무부

--만약 employee테이블과 department테이블이 하나의 테이블이었다면?
--D9이 총무부라고 적혀있었다면...?
--하나의 쿼리 안에서 찾아볼 수 있었을텐데..

--JOIN
SELECT *
FROM employee E JOIN department D
    ON E.dept_code = D.dept_id; --조건
    
--출력 : dept_id, dept_title, location_id가 추가됨
--but 행은 여러번 반복됨

--cf. 테이블에서 alias 사용가능
--as나 쌍따음표 사용 x


--어떻게 합쳐진건지
SELECT * FROM employee;
SELECT * FROM department;



SELECT D.dept_title
FROM employee E JOIN department D --employee 테이블과 department 테이블을 기준컬럼으로 하여
        ON E.dept_code = D.dept_id --Employee테이블의 dept_code와 department테이블의 dept_id를 비교하여 기준 컬럼값들이 같다면 합쳐라
WHERE E.emp_name = '송종기';
--컬럼명이 두 테이블에 유일하다면 별칭을 생략할 수도 있음
--but 되도록이면 별칭을 써주기



--join 종류
--1. EQUI-JOIN | 동등조인 | 동등비교조건(=)에 의한 조인 
   --기존 컬럼 값이 같으면 합쳐라
   --대부분의 JOIN이 EQUI-JOIN을 사용
--2. NON-EQUI JOIN | 동등비교조건이 아닌(between...and..., in, not in, !=, like) 조인
   -- =이 아니면 다 NON-EQUI JOIN에 해당함
   
--join 문법
--1. ANSI 표준문법 : 모든 DBMS 공통문법 | join 키워드 사용
--2. Vendor사별 문법 : DBMS별로 지원하는 문법. 오라클전용문법도 있음
        --다른 DBMS program에서 사용할 수 없음
        --오라클 전용문법 | ,(콤마) 키워드 사용



--테이블 별칭
--as나 쌍따음표를 쓰지 않고, keyword를 바로 적어줌 
--cf. 별칭을 한번 사용하면 계속 별칭으로 사용할 것!

SELECT * FROM employee;
SELECT * FROM JOB;
--employee테이블의 job_code와 job테이블의 job_code가 연결되어 있음

--별칭을 뺀다면 테이블명을 그대로 적어줌
     --why? 따로 부를 이름이 없기 때문
     
--select all의 경우
SELECT * 
FROM employee JOIN JOB
    ON employee.job_code = JOB.job_code;

--원하는 정보만 select하는 경우
SELECT emp_name, job_code, job_name
FROM employee JOIN JOB
    ON employee.job_code = JOB.job_code;
--Error : "column ambiguously defined"
--emp_name은 employee에만 있고, job_name은 job테이블에만 있음. -> 안써줘도 헷갈리지 않음
--but job_code는 테이블명 또는 별칭을 명시하지 않으면, 오류
--두 테이블의 컬럼명이 동일함
--job_code의 경우 어느 테이블에 있는 job_code인지 모르기 때문
--cf. 기준 컬럼명이 다르다면 안써줘도 상관X
    
--테이블명을 반드시 명시해줘야함
SELECT employee.emp_name,
             JOB.job_code, --반드시 명시해주기 
             JOB.job_name
FROM employee JOIN JOB
    ON employee.job_code = JOB.job_code;
--but 테이블명을 일일이 써주기란 번거로움 -> 별칭 사용

--별칭 사용
SELECT E.emp_name, j.job_code, j.job_name
FROM employee E JOIN JOB j
    ON E.job_code = j.job_code;
--더 간결하게 작성 가능
    
    
--using(col名)
--기준 컬럼명이 좌우테이블에서 동일하다면, on 대신 using 사용가능
--ORA-25154: column part of USING clause cannot have qualifier
SELECT E.emp_name, --다른 컬럼은 별칭 사용가능
         --E.job_code, --기준컬럼명은 별칭 사용 불가
         --J.job_code, --기준컬럼명은 별칭 사용불가
             job_code, 
             j.job_name
FROM employee E JOIN JOB j
    USING(job_code);
--출력 : using에 사용하는 컬럼이 맨 앞컬럼으로 빼나오면서, 중복된 것을 한번만 출력해줌
--E.jobcode, J.jobcode와 같은 해당컬럼에 별칭을 사용할 수 없다
--why? 공통된 것을 하나로 합쳐서 공통된 컬럼으로 만들었기 때문


---------------------------------------------------------------------------------------------------------------------------

--equi-join 종류
/*
1. equi join 교집합 (공통된 부분만 추려냄)

2. outer join 합집합
    - left outer join 좌측테이블 기준 합집합
    - right outer join 우측테이블 기준 합집합
    - full outer join 양테이블 기준 합집합

3. cross join
    두테이블간의 조인할 수 있는 최대 경우의 수를 표현
    (행과 행이 만날 수 있는 모든 경우를 보여줌)

4. self join
    같은 테이블의 조인

5. multiple join (다중조인)
    3개 이상의 테이블을 조인


*/

-------------------------------------------------
-- INNER JOIN
-------------------------------------------------
--가장 기본이 되는 조인
--A (inner) join B
--'inner' keyword 생략 가능 why? 기본값 : inner join

--교집합
--어떤 행들이 결과집합에서 제외되는가?
--1. 기준 컬럼값이 null인 경우
--2. 기준 컬럼값이 상대테이블에 存在하지 않는 경우

SELECT *
FROM employee;
--출력 : 24행 (null포함)
SELECT *
FROM department;
--출력 : dept_id - D1, D2, D3, D4, D5, D6, D7, D8, D9

SELECT *
FROM employee E JOIN department D
    ON E.dept_code = D.dept_id;
--출력 : 22행
--1. employee에서 dept_code가 null인 행 (인턴사원) 제외
--2. department에서 dept_id가 D3, D4, D7인 행 제외
        --why? 상대테이블에 D3,  D4, D7인 값이 不存在

SELECT *
FROM employee E JOIN JOB j
    ON E.job_code = j.job_code;
--출력 : 24행 - 제외된 행이 없음

-----------------------
--(oracle)
--join -> 콤마로 연결
--on조건절 -> where 조건절

SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id;

--추가되는 where절의 조건은 and로 연결
SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id AND E.dept_code = 'D5';

SELECT *
FROM employee E, JOB j
WHERE E.job_code = j.job_code;

-------------------------------------------------
-- OUTER JOIN
-------------------------------------------------
--'outer' keyword 생략 가능
    
--1. LEFT (outer) join
--좌측테이블 기준
--좌측테이블 모든 행 포함, 우측테이블에는 on조건절에 만족하는 행만 포함
SELECT *
FROM employee E LEFT OUTER JOIN department D
    ON E.dept_code = D.dept_id;
--출력 : 24행  (22 +2(null))
--교집합 + (dept_code가 null인 사원들도 포함) 좌측 테이블의 모든 행이 포함되었기 때문
--해당하는 값이 없으면, NULL처리되어 출력됨
    

--2. RIGHT (outer) join
--우측테이블 기준
--우측테이블 모든 행이 포함, 좌측테이블에는 on조건절에 만족하는 행만 포함
SELECT *
FROM employee E RIGHT JOIN department D
    ON E.dept_code = D.dept_id;
--출력 : 25행 (22 + 3(D3, D4, D7))
--교집합 + 우측 테이블의 모든 행이 포함되었기 때문 (D1, D2, D3, D4, D5, D6, D7, D8, D9)
--해당하는 값이 없으면, NULL처리되어 출력됨


 --3. FULL (outer) join
 --완전 join
 --좌우 테이블 모두 포함
 
 SELECT *
 FROM employee E FULL JOIN department D
    ON E.dept_code = D.dept_id;
--출력 : 27행 (교집합 + 2(left) + 3(right))
--dept_code의 하동운, 이오리 포함
--D3,  D4, D7포함

--기준 컬럼이 무엇인지 파악
--기준 컬럼에 해당하지 않는 것이 있는지 파악

--사원명/부서명 조회시
--부서 지정이 안된 사원은 제외 : inner join
--부서 지정이 안된 사원도 포함 : left join
--사원 배정이 안된 부서도 포함 : right join

-----------------------
--(oracle)
--기준테이블의 반대편 테이블의 컬럼에 (+)를 추가
--(+) - 빠진 컬럼에 반창고를 붙여준 모양으로 생각하기

--left outer join
SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id(+);

--right outer join
SELECT *
FROM employee E, department D
WHERE E.dept_code(+) = D.dept_id;

--full outer join은 oracle 문법에 존재하지 X

-------------------------------------------------
-- CROSS JOIN
-------------------------------------------------
--상호조인
--on 조건절 없이, 좌측테이블 행과 우측테이블 행이 연결될 수 있는 모든 경우의 수를 포함한 결과집합 리턴
--Cartesian's Product

SELECT *
FROM employee E CROSS JOIN department D;
--출력 : 216행 = 24(employee) * 9(department)


--일반 컬럼, 그룹함수 결과를 함께 조회할 용도

--Error : ORA-00937: not a single-group group function
SELECT emp_name, salary, AVG(salary)
FROM employee;

SELECT TRUNC(AVG(salary))
FROM employee;
--출력 : 3107343

SELECT *
FROM employee E CROSS JOIN (SELECT TRUNC(AVG(salary))
                                                   FROM employee) A;

SELECT emp_name,
             salary,
             AVG,
             salary - AVG diff
FROM employee E CROSS JOIN (SELECT TRUNC(AVG(salary)) AVG
                                                   FROM employee) A;

--리턴한 결과집합을 하나의 테이블처럼 사용 가능

-----------------------
--(oracle)
--모든 경우의 수를 포함하는 것 -> where조건절을 주지 않음

SELECT *
FROM employee E, department D;

--------------------------------------------
-- SELF JOIN
--------------------------------------------
--조인시 같은 테이블을 좌/우측 테이블로 두번 사용
--양쪽 테이블이 무조건 같은 테이블이기 때문에, 별칭 필수로 사용해야 함

--사번, 사원명, 관리자사번, 관리자명 조회

/*
select *
from employee;
*/
--manager_id가 같은 테이블에 emp_id로 존재함

/*
select *
from employee E1 join employee E2
    on E1.manager_id = E2.emp_id;
*/
--manager_id와 emp_id를 이어줌

SELECT e1.emp_id, --두 테이블에 모두 emp_id가 있기 때문에 정확히 별칭으로 명시해줘야함
            e1.emp_name, 
            e1.manager_id,
            e2.emp_id,
            e2.emp_name
FROM employee e1 JOIN employee e2
    ON e1.manager_id  = e2.emp_id;
--select 작업

-----------------------
--(oracle)

SELECT e1.emp_id, 
            e1.emp_name, 
            e1.manager_id,
            e2.emp_id,
            e2.emp_name
FROM employee e1, employee e2
WHERE e1.manager_id = e2.emp_id;

------------------------------------------
-- MULTIPLE JOIN
------------------------------------------
--한번에 좌우 두 테이블씩 조인하여 3개 以上의 테이블을 연결함.

--사원명, 부서명, 지역명, 직급명 

/*
select * from employee; --E.dept_code
select * from department; --D.dept_id / D.location_id
select * from location; --L.local_code
*/



SELECT *
FROM employee E
    JOIN department D
        ON E.dept_code = D.dept_id
    JOIN LOCATION L
        ON D.location_id = L.local_code;
--join 1. employee + department -> join 2. (employee + department) + location

SELECT E.emp_name,
            D.dept_title,
            L.local_name,
            j.job_name
FROM employee E 
    LEFT JOIN department D
        ON E.dept_code = D.dept_id
    LEFT JOIN LOCATION L
        ON D.location_id = L.local_code
    JOIN JOB j
        ON E.job_code = j.job_code;

--left join -> 인턴사원 포함
--left join으로 시작했으면, 끝까지 유지해줘야 데이터가 누락되지 않음
--+job.job_code (job의 경우는 순서 상관 X)
--join1. employee + department -> join2. (employee + department) + location -> join3. (employee + department + location) + job

--조인하는 순서를 잘 고려할 것
--bridge역할을 해주는 공통된 역할을 해주는 테이블의 순서 고려



--직급이 대리,과장이면서 ASIA지역에 근무하는 사원 조회
--사번, 이름, 직급명, 부서명,  급여, 근무지역, 국가

SELECT *
FROM employee;
--job_code, dept_code

SELECT *
FROM JOB;
--job_code, job_name

SELECT *
FROM LOCATION;
--local_code, local_name, national_code

SELECT *
FROM department;
--dept_id, location_id

SELECT *
FROM nation;
--national_code, national_name


SELECT *
FROM employee E, department D, LOCATION L, JOB j
WHERE E.dept_code = D.dept_id(+) 
    AND D.location_id = L.local_code(+)
    AND E.job_code = j.job_code;


SELECT E.emp_id, E.emp_name, j.job_name, D.dept_title, E.salary, L.local_name, N.national_name
FROM employee E
        JOIN JOB j
            ON E.job_code = j.job_code
        JOIN department D
            ON E.dept_code = D.dept_id
        JOIN LOCATION L
            ON D.location_id = L.local_code
        JOIN nation N
            ON L.national_code =N.national_code
WHERE j.job_name IN ('대리', '과장')
          AND L.local_name LIKE 'ASIA%';

--테이블 구조를 살펴볼 때 유용한 tip
--tip. 테이블명 커서 - Ctrl + Click -> 테이블에 관한 정보
--열 - 테이블 명세 + comment (desc 테이블名)
--데이터 - 조회한 것과 같은 전체 테이블 제공
    
    
-----------------------
--(oracle)
--oracle에서는 join하는 순서가 중요치 않음
--from절 : join하고자 하는 테이블을 콤마를 통해 나열해주기만 하면됨
--where절 : where 조건절에서 and로 join의 조건을 한번에 써줌

SELECT *
FROM employee E, department D, LOCATION L, JOB j
WHERE E.dept_code = D.dept_id
    AND D.location_id = L.local_code
    AND E.job_code = j.job_code;

SELECT *
FROM employee E, department D, LOCATION L, JOB j
WHERE E.dept_code = D.dept_id(+)
    AND D.location_id = L.local_code(+)
    AND E.job_code = j.job_code;

------------------------------------------------------
--ORACLE 전용문법
------------------------------------------------------

--INNER JOIN

--join -> 콤마로 연결
--on조건절 -> where 조건절

SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id;

--추가되는 where절의 조건은 and로 연결
SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id AND E.dept_code = 'D5';

SELECT *
FROM employee E, JOB j
WHERE E.job_code = j.job_code;


--OUTER JOIN

--기준테이블의 반대편 테이블의 컬럼에 (+)를 추가
--(+) - 빠진 컬럼에 반창고를 붙여준 모양으로 생각하기

--left outer join
SELECT *
FROM employee E, department D
WHERE E.dept_code = D.dept_id(+);

--right outer join
SELECT *
FROM employee E, department D
WHERE E.dept_code(+) = D.dept_id;

--full outer join은 oracle 문법에 존재하지 X


--CROSS JOIN

--모든 경우의 수를 포함하는 것 -> where조건절을 주지 않음

SELECT *
FROM employee E, department D;


--SELF JOIN

SELECT e1.emp_id, 
            e1.emp_name, 
            e1.manager_id,
            e2.emp_id,
            e2.emp_name
FROM employee e1, employee e2
WHERE e1.manager_id = e2.emp_id;


--MULTIPLE JOIN

--oracle에서는 join하는 순서가 중요치 않음
--from절 : join하고자 하는 테이블을 콤마를 통해 나열해주기만 하면됨
--where절 : where 조건절에서 and로 join의 조건을 한번에 써줌

SELECT *
FROM employee E, department D, LOCATION L, JOB j
WHERE E.dept_code = D.dept_id
    AND D.location_id = L.local_code
    AND E.job_code = j.job_code;

SELECT *
FROM employee E, department D, LOCATION L, JOB j
WHERE E.dept_code = D.dept_id(+)
    AND D.location_id = L.local_code(+)
    AND E.job_code = j.job_code;    

------------------------------------------
-- NON-EQUI JOIN
------------------------------------------
--employee, sal_grade테이블을 조인
--employee테이블의 sal_level컬럼이 없다고 가정
--employee.salary컬럼과 sal_grade.min_sal | sal_grade.max_sal을 비교해서 join

SELECT * FROM employee;
SELECT * FROM sal_grade;

SELECT *
FROM employee E
    JOIN sal_grade S
        ON E.salary BETWEEN S.min_sal AND S.max_sal;
        --동등 조건이 아닌 min_sal에서 max_sal사이에 해당하는 행이 있다면, 그 행을 합쳐라
        
--조인조건절에 따라 1행에 여러행이 연결된 결과를 얻을 수 있다
--조건에 따라 여러행이 붙을 수도 있음
SELECT *
FROM employee E
    JOIN department D
        ON E.dept_code != D.dept_id
ORDER BY E.emp_id, D.dept_id;
        --employee한 행에 department가 여러행 붙음
        
        
--============================================================================
--SET OPERATOR
--============================================================================
--여러 개의 SELECT 결과물을 하나의 쿼리로 만드는 연산자
--집합연산자
--entity를 컬럼수가 동일하다는 조건하에 上下로 연결한 것

--조건
--1. select절의 컬럼수가 동일
--2. 자료형이 동일
    --날짜형 + 문자형 -> error
		--cf. 동일 자료형 내에서는 상호호환 가능
	    --문자형(char) + 문자형(varchar2) -> ok


--특징
--위 아래의 컬럼명이 다른 경우, 첫번째 entity의 컬럼명을 결과집합에 반영
--order by절은 마지막 entity에서 딱 한번만 사용가능

--종류
--UNION 합집합
--UNION ALL 합집합
--INTERSECT 교집합
--MINUS 차집합
                                                        

/*
A = {1, 3, 2, 5}
B = {2, 4, 6}

A union B => {1, 2, 3, 4, 5, 6} 
합치고, 중복제거, 첫번째컬럼 기준 오름차순 정렬 
A union B => {1, 3, 2, 5, 2, 4, 6}
A의 결과에 B를 뒤이어 붙여줌 (중복제거, 정렬 X)
A intersect B => {2}
교집합, 첫번째컬럼 기준 오름차순 정렬 
A minus B => {1, 3, 5}
A를 기준으로, A의 값 중 교집합에 해당하는 부분을 제외하고 리턴
첫번째컬럼 기준 오름차순 정렬 
*/

----------------------------------------------
--UNION | UNION ALL
----------------------------------------------
--중복제거 여부에 따라 UNION / UNION ALL을 결정하여 사용하기
--cf. union은 한 행만 붙이는 것이 아니라, 여러 행을 붙일 수도 있음

--A D5부서원의 사번, 사원명, 부서코드, 급여
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5';

--B 급여가 300만원이 넘는 사원조회(사번, 사원명, 부서코드, 급여)
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000;

--A UNION B
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5'
UNION
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000;
ORDER BY dept_code; --order by는 마지막 entity에서만 사용가능, order by를 써주지 않으면 첫번째 컬럼 기준으로 정렬됨
--출력 : 13행 (중복 제거, 정렬)

--A UNION ALL B
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5'
UNION ALL
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000; 


----------------------------------------------
--INTERSECT | MINUS
----------------------------------------------
--A INTERSECT B
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5'
INTERSECT
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000; 
--출력 : 2행 (중복된 행만 추려냄)

--A MINUS B
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5'
MINUS
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000; 
--출력 : 4행 (A에서 교집합을 제외)

--B MINUS A
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE salary > 3000000
MINUS
SELECT emp_id, emp_name, dept_code, salary
FROM employee
WHERE dept_code = 'D5';
--출력 : 7행


--============================================================================
--SUB QUERY
--============================================================================
--하나의 SQL문(MAIN-QUERY) 안에 종속된 또다른 SQL문(SUB-QUERY)
--존재하지 않는 값, 조건에 근거한 검색 등을 실행 할 때
--특정 행을 뽑아내고 싶은데, 그 컬럼 값으로는 바로 못 뽑고, 연산을 이용해야 할 때...

--반드시 소괄호로 묶어서 처리할 것
--sub-query 내에는 order by문법이 지원되지 않음
--연산자 오른쪽에서 사용할 것 
    -- where col = () O
    --() = where col  X
    
    
--노홍철사원의 관리자 이름 조회

----
--self join
SELECT e1.emp_id, e1.emp_name, e1.manager_id, e2.emp_name
FROM employee e1
    JOIN employee e2
        ON e1.manager_id = e2.emp_id
WHERE e1.emp_name = '노옹철';

----
--join 사용 안할 시
--1. 노옹철 사원행의 manager_id 조회
--2. emp_id가 조회한 manager_id와 동일한 행의 emp_name을 조회

--1. 노옹철 사원행의 manager_id 조회
SELECT manager_id
FROM employee
WHERE emp_name = '노옹철';

--2. emp_id가 조회한 manager_id와 동일한 행의 emp_name을 조회
SELECT emp_name
FROM employee
WHERE emp_id = '201';

----
--sub query
SELECT emp_name
FROM employee
WHERE emp_id = (SELECT manager_id
                            FROM employee
                            WHERE emp_name = '노옹철');
--메인 쿼리가 먼저 처리, 도중에 where절에서 sub query를 먼저 처리 -> 값이 201로 치환됨 -> main쿼리에 전달 -> 다시 main query 진행


/*
sub query의 리턴값의 개수에 따른 분류

1. 단일행 단일컬럼 서브쿼리 (1행 1열)
2. 다중행 단일컬럼 서브쿼리 (여러행 1열)
3. 다중열 서브쿼리 (단일행/다중행 모두 한번에 처리가능)

4. 상관 서브쿼리 (일반서브쿼리와 비교하여 생각하기)
5. 스칼라 서브쿼리 (select절에 사용)
6. inline-view (from절에 사용)
*/

------------------------------------------------------
--단일행 단일 컬럼 서브쿼리
------------------------------------------------------
--서브쿼리 조회결과가 1행1열인 경우


--(전체 평균 급여)보다 많은 급여를 받는 사원 조회
--어디가 sub쿼리 부분이어야 할 지 파악하기

/*
--main
select emp_name, salary
from employee
where salary > (전체 평균 급여);
*/

/*
--sub : 전체 평균 급여
select avg(salary)
from employee;
*/

--where절
SELECT emp_name, salary
FROM employee
WHERE salary > (SELECT AVG(salary)
                            FROM employee);
                            
                            
--그룹함수는 sub쿼리로 있으면 select절에서도 일반컬럼과 함께 사용 가능                           
SELECT emp_name, salary, TRUNC((SELECT AVG(salary)
                                                         FROM employee)) AVG
FROM employee
WHERE salary > (SELECT AVG(salary)
                            FROM employee);                     
                            
                            
--윤은혜 사원과 같은 급여를 받는 사원 조회 (사번, 이름, 급여)

/*
select emp_id, emp_name, salary
from employee
where salary = (윤은혜 사원과 같은 급여)
          and emp_name != '윤은해';
*/

/*
select salary
from employee
where emp_name = '윤은해'
*/

SELECT emp_id, emp_name, salary
FROM employee
WHERE salary = (SELECT salary
                        FROM employee
                        WHERE emp_name = '윤은해')
            AND emp_name != '윤은해';
            



--D1, D2 부서 중 (D5 부서의 평균급여)보다 많은 급여를 받는 사원 조회 (부서코드, 사번, 사원명, 급여)

SELECT dept_code, emp_id, emp_name, salary
FROM employee
WHERE salary > (SELECT TRUNC(AVG(salary))
                         FROM employee
                         WHERE dept_code = 'D5')
            AND dept_code IN ('D1', 'D2');


------------------------------------------------------
--다중행 단일 컬럼 서브쿼리
------------------------------------------------------
--서브쿼리 조회결과가 하나의 열, 여러행인 경우
--연산자 in | not in | any | all | exists와 함께 사용가능한 서브쿼리
--동등연산으로는 처리 불가

--다중행 단일 컬럼
SELECT emp_name --컬럼은 1개의 열
FROM employee;
--출력 : 24행

--송중기, 하이유 사원이 속한 부서원 조회

/*
select dept_code
from employee
where emp_name in ('송종기', '하이유');
*/

SELECT emp_name, dept_code
FROM employee
WHERE dept_code IN (SELECT dept_code
                                   FROM employee
                                   WHERE emp_name IN ('송종기', '하이유')
                                   );


--차태연, 전지연 사원의 급여등급(sal_level)과 같은 사원 조회(사원명, 직급명, 급여등급)

/*
select sal_level
from employee
where emp_name in ('차태연', '전지연');
*/

/*
select emp_name, 
          , (직급명)
          , sal_level
from employee
where sal_level in (select sal_level
                                from employee
                                where emp_name in ('차태연', '전지연')
                                )
          and emp_name not in ('차태연', '전지연');
*/

SELECT emp_name, 
          job_name,
          sal_level
FROM employee
        JOIN JOB
            USING(job_code)
WHERE sal_level IN (SELECT sal_level
                                 FROM employee
                                 WHERE emp_name IN ('차태연', '전지연')
                                );
            AND emp_name NOT IN ('차태연', '전지연');


--(직급명(job_name)이 대표, 부사장이 아닌) 사원조회 (사번, 사원명, 직급코드)

--employee테이블에는 job_name컬럼이 없으므로 비교 불가
--job_code를 이용해서 비교해야 하는데, 대표, 부사장에 해당하는 job_code를 뽑아와서 제공해줘야함

--sub query를 별개의 테이블에서 가져옴
SELECT emp_id, emp_name, job_code
FROM employee E
WHERE E.job_code NOT IN (
                                           SELECT job_code
                                           FROM JOB
                                           WHERE job_name IN ('대표', '부사장')
                                           );

--ASIA1 지역에 근무하는 사원 조회 (사원명, 부서코드)
--location.local_name : ASIA1
--department.location_id --- location.local_code
--employee.dept_code --- department.dept_id
SELECT local_code 
FROM LOCATION
WHERE local_name = 'ASIA1';

SELECT dept_id
FROM department
WHERE location_id = 'L1';

SELECT emp_name, dept_code
FROM employee
WHERE dept_code IN (
                                SELECT dept_id
                                FROM department
                                WHERE location_id = (
                                                                SELECT local_code
                                                                FROM LOCATION
                                                                WHERE local_name = 'ASIA1'                                
                                                            )
                            );


------------------------------------------------------
--다중열 서브쿼리
------------------------------------------------------
--서브쿼리에 리턴된 컬럼이 여러개인 경우

--리턴된 행이 하나인 경우
--(퇴사한 사원과 (같은 부서), (같은 직급))의 사원 조회 (사번, 부서코드, 직급코드)

SELECT dept_code, job_code
FROM employee
WHERE quit_yn = 'Y';

--방법1. 나눠서 처리할 경우
/*select emp_name,
            dept_code,
            job_code
from employee
where dept_code = ('D8') --서브쿼리1
    and job_code = ('J6'); --서브쿼리2
*/


--방법2. 두개를 합쳐서 처리할 경우
SELECT emp_name,
            dept_code,
            job_code
FROM employee
WHERE (dept_code, job_code) = (
                                                SELECT dept_code, job_code
                                                FROM employee
                                                WHERE quit_yn = 'Y'
                                                );
--메인 쿼리와 서브쿼리의 짝을 맞춰서 합칠 수도 있음
--컬럼명과 상관없이 나오는 컬럼에 들어있는 값을 가지고 판단함



--여러행을 리턴할 경우
--manager가 존재하지 않는 사원과 같은 부서코드, 직급코드를 가진 사원 조회
SELECT emp_name,
            dept_code,
            job_code
FROM employee
WHERE (dept_code, job_code) = (
                                                SELECT dept_code, job_code
                                                FROM employee
                                                WHERE manager_id IS NULL
                                                );
--Error : single-row subquery returns more than one row
--하나 이상의 서브쿼리 행을 리턴한다

--in, not in연산자는 다중행 다중컬럼 처리 가능

SELECT emp_name,
            dept_code,
            job_code
FROM employee
WHERE (dept_code, job_code) IN (
                                                SELECT dept_code, job_code
                                                FROM employee
                                                WHERE manager_id IS NULL
                                                );
--여러 행을 처리할 수 있는 구조를 만들어 주기 (=이 아닌, in 사용)
--but 아직 null 예외처리가 되지 않은 코드
--null이 중간에 껴있어서 동등비교 연산을 하지 못함 -> null이 제외됨

SELECT emp_name,
            dept_code,
            job_code
FROM employee
WHERE (nvl(dept_code, 'D0'), job_code) IN (
                                                SELECT nvl(dept_code, 'D0'), job_code
                                                FROM employee
                                                WHERE manager_id IS NULL
                                                );
--nvl 함수를 이용하여 null값을 포함시켜주기

 
--부서별 최대급여를 받는 사원 조회(사원명, 부서코드, 급여)

SELECT dept_code,
            MAX(salary)
FROM employee
GROUP BY dept_code
ORDER BY dept_code NULLS LAST;

SELECT emp_name, dept_code, salary
FROM employee
WHERE (nvl(dept_code, 'D0'), salary) IN (
                                                SELECT nvl(dept_code, 'D0'), MAX(salary)
                                                FROM employee
                                                GROUP BY dept_code
                                             )
ORDER BY dept_code NULLS LAST;


------------------------------------------------------
--상관 서브쿼리 (상호연관 서브쿼리)
------------------------------------------------------
--메인쿼리와 서브쿼리 간의 관계
--매인쿼리의 값을 서브쿼리에 전달하고, 서브쿼리 수행 후 결과를 다시 메인쿼리에 반환
--동등비교 성립 X

--직급별 평균급여보다 많은 급여를 받는 사원 조회


--방법1. join
/*
select job_code, avg(salary)
from employee
group by job_code;
*/

SELECT *
FROM employee E
    JOIN (SELECT job_code, AVG(salary) AVG
            FROM employee
            GROUP BY job_code) EA
        USING(job_code)
WHERE E.salary > EA.AVG
ORDER BY job_code;

--방법2. 상관서브쿼리로 처리
/*
select *
from employee E
where salary > (직급별 평균급여);
*/

--각행마다 비교해야 할 것이 다르기 때문에 고정값을 넣어주면 안됨
SELECT emp_name, job_code, salary
FROM employee E --메인쿼리의 테이블 별칭이 반드시 필요
WHERE salary > (
                        SELECT AVG(salary)
                        FROM employee
                        WHERE job_code = E.job_code --메인쿼리에서 온 값임을 명시해줌
                        );

       
--메인쿼리의 컬럼의 값과 서브쿼리의 컬럼의 값을 비교함

--부서별 평균급여보다 적은 급여를 받는 사원 조회
--부서별 평균급여보다 적은 급여를 받는 사원 조회(인턴포함)

SELECT emp_name, dept_code, salary
FROM employee E
WHERE salary < (
                        SELECT AVG(salary)
                        FROM employee
                        WHERE nvl(dept_code, 1) = nvl(E.dept_code, 1)
                    );


--일반서브쿼리와 상관서브쿼리의 차이점

--일반서브쿼리
SELECT emp_name
FROM employee
WHERE emp_id = (SELECT manager_id
                            FROM employee
                            WHERE emp_name = '노옹철');
--메인쿼리와 서브쿼리 간 주고받는 데이터가 없음
--서브쿼리만 블럭잡아 실행할 수 있음

--상관 서브커리

SELECT emp_name, job_code, salary
FROM employee E --메인쿼리의 테이블 별칭이 반드시 필요
WHERE salary > (
                        SELECT AVG(salary)
                        FROM employee
                        WHERE job_code = E.job_code --메인쿼리에서 온 값임을 명시해줌
                        );
--메인쿼리와 서브쿼리 간 주고받는 데이터가 있음
--서브쿼리만 블럭잡아 단독으로 실행불가
--반드시 외부로부터 주입을 받아야 실행할 수 있기 때문
--E.job_code가 매번 달라짐


--exists 연산자
--exists(sub_query)
--sub_query에 행이 존재하면 참, 행이 존재하지 않으면 거짓

SELECT *
FROM employee
WHERE 1 = 1; --무조건 true
--출력 : 24행 (모두 출력됨)

SELECT *
FROM employee
WHERE 1 = 0; --무조건 false
--출력 : 0행 (not Error, but 결과행 不存在)

--where절은 한 행, 한 행 검사해서 이 행이 결과집합에 포함되는지 아닌지를 검사하는 조건절
--매행마다 검사하는데 그 조건절이 true면, 값을 검사하는 게 아니라 무조건 true로 나옴
                            --그 조건절이 false면, 값을 검사하는 게 아니라 무조건 false로 나옴
--if. where절 생략 -> 무조건 true로 작동하는것임

--행이 존재하는 subquery -> exists결과가 true임
SELECT *
FROM employee
WHERE EXISTS(
                    SELECT *
                    FROM employee
                    WHERE 1 =1
                    );
--출력 : 24행
--메인쿼리와 상관없이 where절은 무조건 true인 경우

--행이 존재하지 않는 subquery -> exists결과가 false임
SELECT *
FROM employee
WHERE EXISTS(
                    SELECT *
                    FROM employee
                    WHERE 1 =0
                    );
--출력 : 0행
--메인쿼리와 상관없이 where절은 무조건 false인 경우


--관리하는 직원이 한명이라도 존재하는 관리자 사원 조회
--emp_id값이 누군가의 manager_id로 사용된다면 관리자 O
--emp_id값이 누군가의 manager_id로 사용되지 않는다면 관리자 X

SELECT emp_id, emp_name
FROM employee E
WHERE EXISTS (SELECT *
                      FROM employee
                      WHERE manager_id = E.emp_id
                      );
--출력 : 6행


--과정
--main query
--1. 테이블에서 모든 행을 만들어 둠
SELECT emp_id, emp_name
FROM employee E;

--subquery
--2. 한행 한행 sub_query의 E.emp_id에 담겨서 검사함
SELECT *
FROM employee
WHERE manager_id = E.emp_id;
--(200번 검사 - t -> 200번 검사 - t -> 202번 검사 - f ....) 

--실제 무슨 값이 나오는지는 중요치 않음
--단지 행의 존재 여부만 중요!


--부서테이블에서 실제 사원이 존재하는 부서만 조회 (부서코드, 부서명)

--main query
SELECT dept_id, dept_title
FROM department;

--sub query
SELECT *
FROM employee;
WHERE D.dept_id = dept_code;


SELECT dept_id, dept_title
FROM department D
WHERE EXISTS (SELECT *
                      FROM employee
                      WHERE D.dept_id = dept_code
                      );
                      

--과정
SELECT dept_id, dept_title
FROM department;
--모든 행을 가져다 놓고
SELECT *
FROM employee
WHERE dept_code = 'D1';
--'D1'부터 끝까지 대입하여 존재하는지 아닌지 검사


--부서테이블에서 실제 사원이 존재하지 않는 부서만 조회 (부서코드, 부서명)
--not exists(sub-query)
--sub_query의 결과행이 존재하지 않으면 true, 존재하면 false
SELECT dept_id, dept_title
FROM department D
WHERE NOT EXISTS (SELECT *
                            FROM employee
                            WHERE D.dept_id = dept_code
                      );
--출력 : 3행


--최대/최소값 구하기(not exists)


--가장 많은 급여를 받는 사원 조회
--가장 많은 급여를 받는다 -> 본인보다 많은 급여를 받는 사원 不存在

--main-query
SELECT *
FROM employee;

--sub-query
SELECT *
FROM employee
WHERE salary > E.salary;

SELECT emp_name, salary
FROM employee E
WHERE NOT EXISTS (SELECT *
                            FROM employee 
                            WHERE salary > E.salary);


------------------------------------------------------
--SCALA SUBQUERY
------------------------------------------------------
--SCALA = 단일값
--서브쿼리의 실행결과가 딱 하나(단일행 단일컬럼)인 SELECT절에 사용된 상관서브쿼리

--관리자 이름 조회

SELECT emp_name,
           () manager_name
FROM employee E;

SELECT emp_name,
           nvl((
           SELECT emp_name
           FROM employee
           WHERE emp_id = E.manager_id
           ), '-')manager_name
FROM employee E;
-- + manager_name이 null인 행을 nvl 처리


--사원명, 부서명, 직급명 조회
SELECT emp_name 사원명,
            () 부서명,
            () 직급명
FROM employee E;

SELECT emp_name 사원명,
            nvl((
            SELECT dept_title
            FROM department
            WHERE dept_id = E.dept_code
            ), '인턴') 부서명,
            (SELECT job_name
            FROM JOB
            WHERE job_code = E.job_code) 직급명
FROM employee E;



------------------------------------------------------
--INLINE VIEW
------------------------------------------------------
--from절에 사용된 sub-query
--가상테이블 (실존하진 않지만, 마치 하나의 테이블인 것 처럼 취급 가능)

--여사원의 사번, 사원명, 성별 조회

SELECT emp_id, emp_name,
           decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
FROM employee
WHERE decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') = '여';
--select절에서는 where조건절의 가상컬럼을 가져다 쓸 수 없음 -> 중복하여 select절에 써줘야 함

--inline view 사용
SELECT emp_id, emp_name, gender
FROM (SELECT emp_id, emp_name,
         decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
         FROM employee
        );
--마치 테이블인 것처럼 from절에 사용
--3개의 열만 가진 가상 테이블일 된 것 -> salary같은 컬럼에 존재하지 않는 것을 select할 수 없음
--가상테이블에 있는 컬럼만 select가능

--where절 추가
SELECT emp_id, emp_name, gender
FROM (SELECT emp_id, emp_name,
         decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
         FROM employee
        )
WHERE gender = '여';
--where절에서는 별칭만 사용하여 조회하면 됨

--30~50세 사이의 여사원 조회 (사번, 이름, 부서명, 나이, 성별)
--30~50세 사이의 여사원 조회(사번, 이름, 부서명, 나이, 성별)
--inline-view 나이, 성별

SELECT *
FROM (
            SELECT emp_id, 
                        emp_name,
                        nvl((SELECT dept_title FROM department WHERE dept_id = E.dept_code), '인턴') dept_title,
                        EXTRACT(YEAR FROM sysdate) -
                        (decode(substr(emp_no, 8, 1), '1', 1900, '2', 1900, 2000) + substr(emp_no, 1, 2)) + 1 age,
                        decode(substr(emp_no, 8, 1), '1', '남', '3', '남', '여') gender
            FROM employee E
        ) 
WHERE age BETWEEN 30 AND 50 
    AND gender = '여';



--============================================================================
--고급 쿼리
--============================================================================

------------------------------------------------------------------------------
--TOP-N 분석
------------------------------------------------------------------------------

--급여를 많이 받는 Top-5 조회
--입사일이 가장 최근인 Top-10 조회
--Top-N이 될수도 있고, Bottom-N이 될 수도 있음
--줄을 세우고, 잘라냄

--급여를 많이 받는 순으로 정렬
SELECT emp_name, salary
FROM employee
ORDER BY salary DESC;
--order by절을 사용 -> 정렬에 따라 줄을 세울수는 있으나, 몇 개만 잘라낼 수는 없음

--rownum | rowid
--rownum : 테이블에 레코드 추가시 1부터 1씩 증가하면서 부여된 일련번호 (insert된 순서대로)
--부여된 번호는 변경불가
--rownum이 새로 부여되는 경우
--1. inline view 생성시 2. where절 사용시
--rowid : 테이블 특정 레코드에 접근하기 위한 논리적 주소값
    --(not 실제 주소값, but 특정 레코드에 접근하기 위한 임의의 문자열 like java's hashcode)

SELECT ROWNUM,
           ROWID,
           E.*
FROM employee E
ORDER BY salary DESC; --정렬이 바뀌어도 rownum이 변경되지 않음


--where절 사용 -> rownum이 새로 부여
SELECT ROWNUM, E.*
FROM employee E
WHERE dept_code = 'D5';

--inline view 사용 -> rownum이 새로 부여
SELECT ROWNUM, E.*
FROM (
        SELECT ROWNUM OLD, emp_name, salary
        FROM employee
        ORDER BY salary DESC
        ) E
WHERE ROWNUM BETWEEN 1 AND 5; -- + TOP-N 분석
--inline view를 통한 행 정렬 -> rownum 새로 부여 -> top-n 분석


--입사일이 빠른 10명 조회
SELECT ROWNUM 순번, E.*
FROM (
        SELECT emp_name 이름, hire_date 입사일
        FROM employee
        ORDER BY hire_date ASC
        ) E
WHERE ROWNUM <= 10;


--입사일이 빠른 순서로 6번째에서 10번째 사원 조회
SELECT ROWNUM 순번, E.*
FROM (
        SELECT emp_name 이름, hire_date 입사일
        FROM employee
        ORDER BY hire_date ASC
        ) E
WHERE ROWNUM BETWEEN 6 AND 10;
--출력 : 0행
--why? rownum은 where절이 시작하면서 부여되고, where구문이 끝나면 rownum부여가 끝난다
--offset이 있다면, 정상적으로 가져올 수 없다

--해결책 : inline view를 한계층 더 사용해야 함
--rownum이 완전히 부여되고 그다음 잘라오기 위해
SELECT E.*
FROM(
                SELECT ROWNUM rnum, E.*
                FROM (
                        SELECT emp_name 이름, hire_date 입사일
                        FROM employee
                        ORDER BY hire_date ASC
                        ) E
         ) E
WHERE rnum BETWEEN 6 AND 10; --이때는 rownum이 아닌 rnum을 가져와야 함!!
--첫번째에서는 순서부여만 하고, 두번째에는 rnum을 부여해주고, 세번째에는 rnum을 가지고 필터링(6-10)
--이전 레벨에 부여된 rownum을 rnum이라는 컬럼을으로 만들어 저장했다가, rnum을 필터링해준 것


--직급이 대리인 사원중 연봉 Top-4~6위 조회 (순위, 이름, 연봉)
--employee - emp_name, salary, job_code
--job - job_code, job_name

SELECT ROWNUM, 
            E.*
FROM (
        SELECT emp_name,
                    (salary + (salary * nvl(bonus, 0))) * 12 annual_salary
        FROM employee
        WHERE job_code = (  
                                            SELECT job_code
                                            FROM JOB
                                            WHERE job_name = '대리'
                                        )
        ORDER BY annual_salary DESC
        ) E
WHERE ROWNUM BETWEEN 1 AND 3;

SELECT E.*
FROM (
        SELECT ROWNUM rnum, 
                    E.*
        FROM (
                SELECT emp_name,
                            (salary + (salary * nvl(bonus, 0))) * 12 annual_salary
                FROM employee
                WHERE job_code = (  
                                                    SELECT job_code
                                                    FROM JOB
                                                    WHERE job_name = '대리'
                                                )
                ORDER BY annual_salary DESC
                ) E
        ) E
WHERE rnum BETWEEN 4 AND 6;

--부서별 평균급여 Top-4~6위 조회 (순위, 부서명, 평균급여)
--employee - salary, dept_code
--department - dept_id, dept_title

SELECT ROWNUM, E.*
FROM (
        SELECT dept_code,
                    TRUNC(AVG(salary)) AVG
        FROM employee
        GROUP BY dept_code
        ORDER BY AVG DESC
        ) E
WHERE ROWNUM BETWEEN 1 AND 3;

SELECT E.*
FROM (
        SELECT ROWNUM rnum, E.*
        FROM (
                SELECT --nvl(dept_code, '인턴') dept_code,
                            nvl((
                                    SELECT dept_title 
                                    FROM department D 
                                    WHERE dept_id = E.dept_code
                                  ), '인턴') dept_title, 
                            TRUNC(AVG(salary)) AVG
                FROM employee E
                GROUP BY dept_code
                ORDER BY AVG DESC
                ) E
         ) E
WHERE rnum BETWEEN 4 AND 6;

--해당 레벨에서 E라고 하면, 이전 레벨의 E는 의미가 없어짐
--E를 여러번 사용해도 OK

--공식
/*
select E.*
from (
            select rownum rnum, E.*
            from (
                        <<정렬된 ResultSet>>
                    ) E
            ) E        
where rnum between 시작 and 끝;
*/


--with 구문
--inline view 서브쿼리에 별칭을 지정해서 재사용할수 있음

--이전
SELECT E.*
FROM(
                SELECT ROWNUM rnum, E.*
                FROM (
                        SELECT emp_name 이름, hire_date 입사일
                        FROM employee
                        ORDER BY hire_date ASC
                        ) E
         ) E
WHERE rnum BETWEEN 6 AND 10; 

--with 구문
WITH emp_hire_date_asc
AS
(
SELECT emp_name 이름, hire_date 입사일
FROM employee
ORDER BY hire_date ASC
)
SELECT E.*
FROM(
                SELECT ROWNUM rnum, E.*
                FROM emp_hire_date_asc E
         ) E
WHERE rnum BETWEEN 6 AND 10; 


------------------------------------------------------------------------------
--WINDOW FUNCTION (고급쿼리)
------------------------------------------------------------------------------
--행과 행간의 관계를 쉽게 정의하기 위한 오라클 표준함수
--1. 순위함수
--2. 집계함수
--3. 분석함수


--순위함수
/*
window_function(args) over([partition by절] [order by절] [windowing절])

1. args : 윈도우함수 인자 0~n개 지정
2. partition by절 : 그룹핑 기준 컬럼
3. order by절 : 정렬 기준 컬럼
4. windowing절 : 처리할 행의 범위 지정
*/

--rank () over() : 순위를 지정
    --넘버링 하되, 동률이 있다면 그 다음 번호는 그 동률 수만큼 건너뛰어서 이어짐
--dense_rank() over() : 순위를 지정
    --빠진 숫자 없이 순위를 지정

SELECT emp_name,
            salary,
            RANK() OVER(ORDER BY salary DESC) RANK, --salary의 내림차순으로 순위 부여
            DENSE_RANK() OVER(ORDER BY salary DESC) RANK
FROM employee;
--from절 뒤 order by절 없이도 넘버링 해줌
--넘버링 하되, 동률이 있다면 그 다음 번호는 그 동률 수만큼 건너뛰어서 이어짐


--그룹핑에 따른 순위 지정
SELECT E.*
FROM (
        SELECT emp_name,
                    dept_code,
                    salary,
                    RANK() OVER(PARTITION BY dept_code ORDER BY salary DESC) rank_by_dept
        FROM employee
        ) E
WHERE rank_by_dept BETWEEN 1 AND 3;



--집계함수
--sum() over()
--일반컬럼과 같이 사용할 수 있음
SELECT emp_name,
           dept_code,
--           (select sum(salary) from employee) sum,
          SUM(salary) OVER() "전체사원 급여합계",
          SUM(salary) OVER(PARTITION BY dept_code) "부서별 급여합계",
          SUM(salary) OVER(PARTITION BY dept_code ORDER BY salary) "부서별 급여누계" --급여 오름차순 --salary 순서대로 하나씩 차곡차곡 더해준 누계
FROM employee;
--그룹함수도 일반컬럼과 같이 사용가능


--avg() over()
SELECT emp_name,
            dept_code,
            salary,
            TRUNC(AVG(salary) OVER(PARTITION BY dept_code)) "부서별 평균급여"
FROM employee;

--count() over()
SELECT emp_name
            dept_code,
            COUNT(*) OVER(PARTITION BY dept_code) cnt_by_dept
FROM employee;

SELECT emp_id 사번
            ,emp_name 이름
            ,(SELECT emp_name FROM employee WHERE E.manager_id = emp_id) 매니져이름
            ,salary 월급
    FROM employee E
    WHERE manager_id IS NOT NULL
        AND salary > (SELECT AVG(salary) FROM employee);



--==========================================================================
--DML
--==========================================================================
--Data Manipulation Language 데이터 조작어
--CRUD Create Retrieve Update Delete 테이블 행에 대한 명령어
--insert 행추가
--update 행수정 = 행에 속한 컬럼값을 수정함
--delete 행삭제
--select (DQL)

----------------------------------------------------------------------------
--INSERT
----------------------------------------------------------------------------
--insert 사용법
--1. insert into 테이블 values (컬럼1값, 컬럼2값, ...)
        --모든 컬럼을 빠짐없이 순서대로 작성해야 함
--2. insert into 테이블 (컬럼1名, 컬럼2名, ...) values(컬럼1값, 컬럼2값, ...)
    `   --컬럼을 생략 가능, 컬럼 순서도 자유로움
       `--not null컬럼이면서 기본값이 없다면 생략 불가
        --원하는 컬럼만 insert해줄 수 있음

--테이블 생성
--컬럼명       널여부     자료형+(해당크기)
CREATE TABLE dml_sample(
    ID NUMBER, 
    nick_name VARCHAR2(100) DEFAULT '홍길동',  
    NAME VARCHAR2(100) NOT NULL, 
    enroll_date DATE DEFAULT sysdate NOT NULL
);

SELECT *FROM dml_sample;


--타입1--
--insert into 테이블 values (컬럼1값, 컬럼2값, ...) 
--컬럼 개수와 순서를 반드시 지킬것!

--SQL 오류: ORA-00947: not enough values
INSERT INTO dml_sample
VALUES (100, DEFAULT, '신사임당');

--SQL 오류: ORA-00913: too many values
INSERT INTO dml_sample
VALUES (100, DEFAULT, '신사임당', DEFAULT, 'ㅋㅋ');

INSERT INTO dml_sample
VALUES (100, DEFAULT, '신사임당', DEFAULT);


--타입2--
--insert into 테이블 (컬럼1名, 컬럼2名, ...) values(컬럼1값, 컬럼2값, ...)

--모든 컬럼에 값을 넣어줌
INSERT INTO dml_sample (ID, nick_name, NAME, enroll_date)
VALUES (200, '제임스', '이황', sysdate);

--장점 : 원하는 컬럼만 골라 쓸 수 있음
--nullable한 컬럼은 생략 가능
--not null컬럼은 무조건 써줘야 함
--기본값이 있다면 기본값이 적용됨
INSERT INTO dml_sample(NAME, enroll_date)
VALUES ('세종', sysdate);

--not null이면서 기본값이 지정안된 경우 생략 불가
--ORA-01400: cannot insert NULL into ("KH"."DML_SAMPLE"."NAME")
INSERT INTO dml_sample(ID, enroll_date)
VALUES (300, sysdate);
--name은 생략 불가
--why? not null이고 기본값도 지정이 안되었기 때문

--name값만 추가
INSERT INTO dml_sample(NAME)
VALUES ('윤봉길');
--id, nick_name : nullable -> 생략가능
--enroll_date : not null이지만 기본값이 있음 -> 생략가능 


--서브쿼리를 이용한 insert
--서브쿼리를 이용하면 한번에 여러행을 삽입 가능

--cf. 테이블의 구조(컬럼)만 복사하기
/*
create table <만들어줄 테이블名>
as
select *
from <테이블 名>
where <무조건 false가 될 식> ;
*/

CREATE TABLE emp_copy
AS
SELECT *
FROM employee
WHERE 1 = 2; --false -> 테이블의 구조만 복사해서 테이블을 생성

SELECT * FROM emp_copy;
--EMP_COPY 테이블의 테이블 명세 = EMPLOYEE 테이블의 테이블 명세
DESC emp_copy;
DESC employee;


--서브쿼리를 이용한 insert
--서브쿼리를 이용하면 한번에 여러행을 삽입 가능

/*
insert into 테이블名 ( 서브쿼리 - 값으로 넣고싶은 테이블의 select구문
                                    select *
                                    from <테이블名>);
*/

--테이블의 모든 행, 모든 컬럼을 insert
--서브쿼리의 값들이 고스란히 테이블에 들어감
INSERT INTO emp_copy (
                                      SELECT *
                                      FROM employee
                                      );
--24개 행 이(가) 삽입되었습니다.

ROLLBACK;

--원하는 컬럼만 insert
/*
insert into 테이블名 (원하는 컬럼 나열)
                                 ( 서브쿼리 - 값으로 넣고싶은 테이블의 select구문
                                     select 원하는 컬럼 나열
                                     from <테이블名>
                                 );
*/
INSERT INTO emp_copy(emp_id, emp_name, emp_no, job_code, sal_level)
                         (SELECT emp_id, emp_name, emp_no, job_code, sal_level
                           FROM employee
                         );
--not null인 컬럼은 모두 채워넣어야 함
SELECT * FROM emp_copy;

--emp_copy 데이터 추가


--DATA_DEFAULT : 기본값 확인 
/*
select *
from user_tab_cols
where table_name = '테이블名 대문자';
*/
--이때 테이블名은 홑따음표(' ') 안에 대문자로 써줘야 함
--cf. 기본값은 복사되지 않음

SELECT *
FROM user_tab_cols
WHERE table_name = 'EMPLOYEE';


SELECT *
FROM user_tab_cols
WHERE table_name = 'EMP_COPY';
--이때 테이블名은 홑따음표(' ') 안에 대문자로 써줘야 함
 
 
--ALTER : 테이블 구조를 바꿈
--기본값 추가
/*
alter table 테이블名
modify 컬럼名1 default 기본값
modify 컬럼名2 default 기본값
...;
*/
--cf. 문자를 기본값으로 -> 홑따음표 (' ') 사용
     --날짜를 기본값으로 -> 홑따음표 (' ') 미사용
     
ALTER TABLE emp_copy 
MODIFY quit_yn DEFAULT 'N'
MODIFY hire_date DEFAULT sysdate;  
--Table EMP_COPY이(가) 변경되었습니다.

--기본값 변경 확인
SELECT *
FROM user_tab_cols
WHERE table_name = 'EMP_COPY';


INSERT INTO dml_sample(NAME, enroll_date)
VALUES ('세종', sysdate); 

INSERT INTO emp_copy(emp_id, emp_name, emp_no, job_code, sal_level, quit_yn, hire_date)
VALUES (224, '남윤지', '970505-1234567', 'J1', 'S1', DEFAULT, DEFAULT);


--Data Migration / INSERT ALL
--insert all을 이용한 여러테이블에 동시에 데이터 추가
--하나의 테이블에 있는 데이터를 migration 작업할 때, 병합 작업시 사용
--서브쿼리를 이용해서 2개 이상의 테이블에 동시에 데이터 추가
--조건부 추가 또한 가능

--입사일 관리 테이블
--구조를 복사해준 테이블 생성
create table emp_hire_date
as  
select emp_id, emp_name, hire_date
from employee
where 1 = 2;


--매니저 관리 테이블
--구조를 복사해준 테이블 생성
create table emp_manager
as
select emp_id,
           emp_name, 
           manager_id,
           emp_name "manager_name" --언더스코어는 글자로 취급
from employee
where 1 = 2;

select * from emp_hire_date;
select * from emp_manager;

--manager_name을 null로 변경
alter table emp_manager
modify manager_name null;

--from 테이블과 to테이블의 컬럼명이 같아야 한다
insert all
into emp_hire_date values(emp_id, emp_name, hire_date)
into emp_manager values(emp_id, emp_name, manager_id, manager_name)
select E.*, 
            (select emp_name from employee where emp_id = E.manager_id) manager_name
from employee E;

--insert all을 이용한 여러행 한번에 추가

--insert into dml_sample 
--values(1, '치킨', '홍길동'); --명령어 하나에 한번

--오라클에서 다음 문법은 지원하지 않는다
--insert into dml_sample 
--values(1, '치킨', '홍길동'),(1, '치킨', '홍길동'),(1, '치킨', '홍길동'); 


--우회적으로 insert all을 이용하기
insert all
into dml_sample values(1, '치킨', '홍길동', default)
into dml_sample values(1, '고구마', '장발장', default)
into dml_sample values(1, '베베', '유관순', default)
select * from dual; --더미 쿼리 
--insert all의 문법 : 서브쿼리를 가져다가 쓰는 것
--문법상 쿼리가 하나 있어야 하는 구조라서 의미없는 쿼리를 추가해준 것!
-->한번에 처리 가능!


----------------------------------------------------------------------------
--UPDATE
----------------------------------------------------------------------------
--행을 수정
--update 실행 后, 행의 수에는 변화 无
--0행, 1행 이상을 동시에 수정
--조건에 따라 한 행을 수정할 수도, 여러 행을 수정할 수도 있음
--dml은 처리된 행의 수를 반환
   --ex. 3개 행 이(가) 삽입되었습니다.

drop table emp_copy;

create table emp_copy 
as
select * 
from employee;

select * from emp_copy;

--특정값을 업데이트
update emp_copy
set dept_code = 'D8'
where emp_id = '202'; --행을 꼭 집어줘야 함
--1 행 이(가) 업데이트되었습니다.

--존재하지 않는 행을 업데이트 할때
update emp_copy
set dept_code = 'D8'
where emp_id = '2020'; 
--오류가 나지는 않고,
--0개 행 이(가) 업데이트되었습니다.

--여러 행을 업데이트
update emp_copy
set dept_code = 'D7', job_code = 'J3'
where emp_id = '202';

--메모리 상에서만 견정된 것
--아직 데이터 수정이 완전히 이루어진 건 아님
--rollback하면 이전의 데이터로 돌릴 수 있음
--commit, rollback에 의해 최종으로 결정이 남

commit; --메모리상 변경내역을 실제파일에 저장
rollback; --마지막 커밋시점으로 되돌리기
--commit하고 나면 아무리 rollback해도 되돌릴 수 없음

--특정값이 아닌 상대적으로 값을 변경
update emp_copy
set salary = salary + 500000 --상대적 값 변경 , 복합대입연산자 (+=) 사용 불가
where dept_code = 'D5'; --한 행이 아닌, D5인 모든 행을 변경

--서브쿼리를 이용한 update
--방명수 사원의 급여를 유재식 사원과 동일하게 수정

update emp_copy
set salary = (select salary from emp_copy where emp_name = '유재식')
where emp_name = '방명수';

commit;

select * from emp_copy;
select * from job; --job_code, job_name
select * from department; --dept_id, dept_title
--임시환 사원의 직급을 과장, 부서를 해외영업3부로 수정
update emp_copy
set job_code = (select  job_code
                        from job
                        where job_name = '과장'),
      dept_code = (select dept_id
                          from department
                          where dept_title = '해외영업3부')
where emp_name = '임시환';

commit;
--올바르게 작성한 경우 commit해주기!
 --commit : f11
 --rollback : f12
 
--where절을 안써주면 모든 행이 '홍길동'으로 바뀜
update emp_copy
set emp_name = '홍길동';


----------------------------------------------------------------------------
--DELETE
----------------------------------------------------------------------------
--행을 날림
--실수로 commit되면 위험한 경우, 사용 후 주석처리 해두기!

select * from emp_copy;

--delete from emp_copy
--where emp_id = '211';

rollback;

--where절을 안쓰면 모든 행이 삭제됨
--delete from emp_copy;

rollback;

----------------------------------------------------------------------------
--TRUNCATE
----------------------------------------------------------------------------
--테이블의 행을 자르는 명령
--DDL에 포함되는 명령어 
    --(create, alter, drop, truncate 는 모두 자동커밋, DML과 섞어서 사용시에는 주의해야 함!)
--자동커밋 지원, 실행과 동시에 바로 DB서버에 반영됨
--rollback을 아무리 해도 되돌릴 수 없음
--before image 생성작업이 없음
--되돌릴 수 없다는 단점이 있으나
--실행속도가 빠르다는 장점이 있음
    --why? before image 생성작업이 없어서, 그 시간만큼 감축됨

   --cf. delete의 경우는 commit전까지는 rollback으로 되돌릴 수 있음
   --how? before image 생성작업이 있어서
   --사진 찍는 것처럼 임시저장
   
truncate table emp_copy;
--Table EMP_COPY이(가) 잘렸습니다.

select * from emp_copy;



rollback;
--롤백을 아무리 실행해도 되돌릴 수 없음

insert into emp_copy
(select * from employee);

--=============================================================================
--DDL
--=============================================================================
--Data Definition Language 데이터 정의어
--데이터베이스 객체에 대한 언어 
--(데이터베이스 객체를 생성/수정/삭제할 수 있는 명령어 모음)
--DML은 데이터(행의 값)에 대한 생성, 수정, 삭제, DDL은 데이터 객체 (테이블..)에 대한 생성, 수정 , 삭제
--create
--alter
--drop
--truncate

--객체 종류
--table, view, sequence, index, package, procedure, function, trigger, synonym, scheduler, user ...)

--주석 comment
--테이블, 컬럼에 대한 주석을 달 수 있다
select *
from user_tab_comments;

select *
from user_tab_comments
where table_name = 'EMPLOYEE';

desc tbl_files;

--테이블 주석
comment on table tbl_files is '파일경로테이블';
--Comment이(가) 생성되었습니다.

--컬럼 주석
comment on column tbl_files.fileno is '파일 고유번호';
comment on column tbl_files.filepath is '파일 경로';

--수정/삭제 명령은 없다
--....is ''; -> 삭제
--why? oracle에서 빈문자열은 null과 동일
comment on column tbl_files.filepath is '';

--============================================================
--제약조건 CONSTRAINT
--============================================================
--테이블 생성/수정 시 컬럼값에 대한 제약조건 설정할 수 있다
--제약조건 EX. NOT NULL
--데이터에 대한 무결성(integrity)을 보장하기 위한 것
--무결성 = 데이터를 정확하고, 일관되게 유지하는 것

/*
1. not null : 컬럼에 대한 제약조건. null을 허용하지 않음. 필수 값
2. unique : 중복값을 허용하지 않음 (ex. 주민번호, 아이디...)
3. primary key : not null + unique (null을 허용하지도 않고, 고유해야 함) -> 레코드 식별자로써, 테이블당 1개만 허용
4. foreign key : 외래키. 데이터 참조 무결성 보장. 부모 테이블의 데이터만 허용 (not 상속관계 but 참조관계)
5. check : 저장가능한 값의 범위/조건을 제한

값 하나만 제약조건에 위반되어도, 일절 허용하지 않음
행에 대한 명령어 자체가 취소됨
*/

--제약조건 확인
--user_constraints ( 컬럼명 无）
--user_cons_columns （컬럼명 有）

--employee에 걸려있는 제약조건 확인
select *
from user_constraints
where table_name = 'EMPLOYEE';
--컬럼명이 데이터 취급이라 대문자로 써야함

-- C check | not null
---> search_condition을 확인해줘야 함
-- U unique
-- P primary key
-- R foreign key
--ck : check 제약조건
--not null제약조건은 
--pk : primary key

select *
from user_cons_columns
where table_name = 'EMPLOYEE';

--제약조건 검색
select constraint_name,
           uc.table_name,
           ucc.column_name,
           uc.constraint_type,
           uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using (constraint_name)
where uc.table_name = 'EMPLOYEE';


----------------------------------------------------------------------------
--NOT NULL
----------------------------------------------------------------------------
--필수 입력 컬럼에 not null 제약조건을 지정한다
--default 값 다음에 컬럼레벨에 작성한다
--보통 제약조건명을 지정하지 않는다
create table tb_cons_nn(
    id varchar2(20) not null, --컬럼레벨
    name varchar2(100) 
    --테이블 레벨
);

insert into tb_cons_nn values(null, '홍길동');
--ORA-01400: cannot insert NULL into ("KH"."TB_CONS_NN"."ID")

insert into tb_cons_nn values('honggd', '홍길동');

select * from tb_cons_nn;

update tb_cons_nn
set id = ''
where id = 'honggd';
--ORA-01407: cannot update ("KH"."TB_CONS_NN"."ID") to NULL


----------------------------------------------------------------------------
--UNIQUE
----------------------------------------------------------------------------
--이메일, 주민번호, 닉네임
--전화번호는 uq 사용하지 말것
--중복 허용 X (다른 행의 값과 중복되면 안됨)

create table tb_cons_uq (
    no number not null,
    email varchar2(50),
    --테이블레벨
    constraint uq_email unique (email)
    --(여기서는 s가 있으나 없으나 똑같음)
);

insert into tb_cons_uq values(1, 'abc@naver.com');
insert into tb_cons_uq values(2, '가나다@naver.com');
insert into tb_cons_uq values(3, 'abc@naver.com');
--ORA-00001: unique constraint (KH.UQ_EMAIL) violated
--아예 입력조차 할 수 없음)
insert into tb_cons_uq values(4, null); --null 허용

select * from tb_cons_uq;

----------------------------------------------------------------------------
--PRIMARY KEY
----------------------------------------------------------------------------
--레코드(행) 식별자
--not null + unique 기능을 동시에 가지고 있음
--테이블 당 한개만 설정 가능

create table tb_cons_pk(
    id varchar2(50),
    name varchar2(100) not null,
    email varchar2(200),
    constraint pk_id primary key(id)
    
);

insert into tb_cons_pk
values('honggd', '홍길동', 'hgd@google.com');

select * from tb_cons_pk;


select constraint_name,
           uc.table_name,
           ucc.column_name,
           uc.constraint_type,
           uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using (constraint_name)
where uc.table_name = 'TB_CONS_PK';

--복합 기본키(주키 | primary key | pk)
--여러컬럼을 조합해서 하나의 PK로 사용.
--사용된 컬럼 하나라도 null이어서는 안된다.
create table tb_order_pk (
    user_id varchar2(50),
    order_date date,
    amount number default 1 not null,
    constraint pk_user_id_order_date primary key(user_id, order_date)
);

insert into tb_order_pk
values('honggd', sysdate, 3);

insert into tb_order_pk
values(null, sysdate, 3);--ORA-01400: cannot insert NULL into ("KH"."TB_ORDER_PK"."USER_ID")

select user_id,
            to_char(order_date, 'yyyy/mm/dd hh24:mi:ss') order_date,
            amount
from tb_order_pk;



-------------------------------------------
-- FOREIGN KEY
-------------------------------------------
--참조 무결성을 유지하기 위한 조건
--참조하고 있는 부모테이블의 지정 컬럼값 중에서만 값을 취할 수 있게 하는 것
--참조하고 있는 부모테이블의 지정컬럼은 PK, UQ제약조건이 걸려있어야 한다.
--department.dept_id(부모테이블)   <------  employee.dept_code(자식테이블)
--자식테이블의 컬럼에 외래키(foreign key) 제약조건을 지정

create table shop_member(
    member_id varchar2(20),
    member_name varchar2(30) not null,
    constraint pk_shop_memer_id primary key(member_id)
);

insert into shop_member values('honggd', '홍길동');
insert into shop_member values('sinsa', '신사임당');
insert into shop_member values('sejong', '세종대왕');

select * from shop_member;

--drop table shop_buy;
create table shop_buy (
    buy_no number,
    member_id varchar2(20),
    product_id varchar2(50),
    buy_date date default sysdate,
    constraints pk_shop_buy_no primary key(buy_no),
    constraints fk_shop_buy_member_id foreign key(member_id)
                                                                 references shop_member(member_id)
                                                                 on delete cascade
);


insert into shop_buy
values(1, 'honggd', 'soccer_shoes', default);

insert into shop_buy
values(2, 'sinsa', 'basketball_shoes', default);

insert into shop_buy
values(3, 'k12345', 'football_shoes', default);
--ORA-02291: integrity constraint (KH.FK_SHOP_BUY_MEMBER_ID) violated - parent key not found


select * from shop_buy;

--fk기준으로 join -> relation
--구매번호 회원아이디 회원이름 구매물품아이디 구매시각

select B.buy_no,
            member_id,
            M.member_name,
            B.product_id,
            B.buy_date
from shop_member M
    join shop_buy B
        using(member_id);


--정규화 Normalization
--이상현상 방지(anormaly)
select *
from employee;

select *
from department;

--삭제 옵션
--on delete restricted : 기본값. 참조하는 자식행이 있는 경우, 부모행 삭제불가 
--                                  자식행을 먼저 삭제후, 부모행을 삭제
--on delete set null : 부모행 삭제시 자식컬럼은 null로 변경
--on delete cascade : 부모행 삭제시 자식행 삭제

--delete from shop_buy
--where member_id= 'honggd';

delete from shop_member 
where member_id = 'honggd';
--ORA-02292: integrity constraint (KH.FK_SHOP_BUY_MEMBER_ID) violated - child record found

select * from shop_member;
select * from shop_buy;

--식별관계 | 비식별관계
--비식별관계 : 참조하고 있는 부모컬럼값을 PK로 사용하지 않는 경우. 여러행에서 참조가 가능(중복) 1:N관계
--식별관계 : 참조하고 있는 부모컬럼을 PK로 사용하는 경우. 부모행 - 자식행사이에 1:1관계

create table shop_nickname(
    member_id varchar2(20),
    nickname varchar2(100),
    constraints fk_member_id foreign key(member_id) references shop_member(member_id),
    constraints pk_member_id primary key(member_id)
);

insert into shop_nickname 
values('sinsa', '신솨112');

select *
from shop_nickname;








--------------------------------------
-- CHECK
--------------------------------------
--해당 컬럼의 값의 범위를 지정.
--null 입력 가능

--drop table tb_cons_ck
create table tb_cons_ck(
    gender char(1),
    num number,
    constraints ck_gender check(gender in ('M', 'F')),
    constraints ck_num check(num between 0 and 100)
);

insert into tb_cons_ck
values('M', 50);
insert into tb_cons_ck
values('F', 100);
insert into tb_cons_ck
values('m', 50);--ORA-02290: check constraint (KH.CK_GENDER) violated
insert into tb_cons_ck
values('M', 1000);--ORA-02290: check constraint (KH.CK_NUM) violated



----------------------------------------------------------------------------
--CREARE
----------------------------------------------------------------------------
--sub query를 이용한 create는 not null제약조건을 제외한 모든 제약조건, 기본값등을 제거한다

--제약조건 검색
select constraint_name,
           uc.table_name,
           ucc.column_name,
           uc.constraint_type,
           uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using (constraint_name)
where uc.table_name = 'EMP_BCK';

--기본값 확인
select *
from user_tab_cols
where table_name = 'EMP_BCK';

-----------------------------------------------------------------------------
--ALTER
-----------------------------------------------------------------------------
--table관련 alter문은 컬럼, 제약조건에 대해 수정이 가능
--alter table ... add/modify/rename/drop ...
/*
서브명령어
-add 컬럼, 제약조건 추가
-modify 컬럼 (자료형, 기본값) 변경 (제약조건은 변경 불가)
-rename 컬럼명, 제약조건명 변경
-drop 컬럼, 제약조건 삭제
*/

create table tb_alter (
    no number
);

--add 컬럼
--맨 마지막 컬럼으로 추가
alter table tb_alter add name varchar2(100) not null;

--desc
desc tb_alter;

--add 제약조건
--not null제약조건은 추가가 아닌 수정(modify)으로 처리

alter table tb_alter
add constraints pk_tb_alter_no primary key(no);

--제약조건 검색
select constraint_name,
           uc.table_name,
           ucc.column_name,
           uc.constraint_type,
           uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using (constraint_name)
where uc.table_name = 'TB_ALTER';

--modify 컬럼
--자료형, 기본값, null여부 변경가능
--문자열에서 호환가능타입으로는 변경가능 (char --- varchar2)

alter table tb_alter
modify name varchar2(500) default '홍길동' null;

describe tb_alter;

--행이 있다면, 변경하는데 제한이 있다
--존재하는 값보다는 작은 크기로 변경할 수 없다
--null값이 있는 컬럼을 not null로 변경불가
    --why? 기존의 데이터가 유실되기 때문.. - 데이터 무결성이 깨지기 때문
    --ex. 100으로 설정해서 90짜리 데이터를 넣어뒀는데 그보다 작은 50으로 줄이면 -> 기존 데이터 유실
    --ex. null로 넣어뒀는데, not null로 바꾸면 기존의 null인 데이터들을 처리할 수 없음
--char -> number로 변경불가

--modify 제약조건은 불가능
--제약조건은 이름 변경외에 변경불가
--제약조건을 바꾸려면 삭제 후 재생성해야 함
    --not 테이블 삭제 but 제약조건만 삭제하는 것

--rename 컬럼
--rename column기존컬럼名 to 바꿔줄컬럼名
alter table tb_alter
rename column no to num;

desc tb_alter;

--rename 제약조건

--제약조건 검색
select constraint_name,
           uc.table_name,
           ucc.column_name,
           uc.constraint_type,
           uc.search_condition
from user_constraints uc
    join user_cons_columns ucc
        using (constraint_name)
where uc.table_name = 'TB_ALTER';

alter table tbl_alter
rename constraint;

--=========================================================================
--DCL
--=========================================================================
--Data Control Language
--권한 부여/회수 관련 명령어 : grant / revoke
--TCL Transaction Contrl Language 포함 (commit / rollback / savepoint)


--=========================================================================
--DATABASE OBJECT 1
--=========================================================================
--DB의 효율적으로 관리하고, 작동하게 하는 단위
select distinct object_type
from all_objects;

---------------------------------------------------------------------------
--DATA DICTIONARY (DD)
---------------------------------------------------------------------------
--DB의 자원들을 효율적 관리하기 위해 SYSTEM에서 빌려주는것
--일반사용자가 관리자로부터 열람권한을 얻어 사용하는 정보조회테이블
--KH소유의 것이 아님
--읽기전용, 수정/삭제 불가
--객체 관련 작업을 하면 자동으로 그 내용이 반영됨

-- 1. user_xxx
    --사용자가 소유한 객체에 대한 정보 열람
-- 2. all_xxx
    --user_xxx를 포함
    -- + 다른 사용자로부터 사용권한을 부여받은 객체에 대한 정보 열람
-- 3. dba_xxx
    --관리자 전용
    --모든 사용자의 모든 객체에 대한 정보 열람

--이용가능한 모든 db조회
select *
from dictionary;
--from dict; --줄여서 dict라고 해도 동일
--all_xxx + user_xxx

--**********************************************************************************************
--USER_XXX
--**********************************************************************************************
--xxx는 객체 이름 복수형을 사용

--그 계정의 모든 테이블 조회
--user tables
select *
from user_tables;
--from tabs; --tables의 synonym
--user_table 불가

--user_sys_privs : 권한
--user_role_privs : 롤 (권한묶음)
--role_sys_privs : 사용자가 가진 롤에 포함된 모든 권한

select *
from user_sys_privs;

select *
from user_role_privs;

select *
from user_sys_privs;
    --admin_option

--xxx는 객체 이름 복수형을 사용

--sequence 객체 조회
--user_sequences
select *
from user_sequences;

--view 객체 조회
--user_views
select *
from user_views;

--index 조회
--user_indexes
select *
from user_indexes;

--constraint 조회
--user_constraints
select *
from user_constraints;

--**********************************************************************************************
--ALL_XXX
--**********************************************************************************************
--user_xxx를 포함
--현재 계정이 소유하거나 사용권한을 부여받은 객체 조회

--all_tables
select *
from all_tables;

--all_indexes
select *
from all_indexes;


--**********************************************************************************************
--DBA_XXX
--**********************************************************************************************
select * from dba_tables;
--ORA-00942: table or view does not exist

select *
from dba_tables
where owner in ('KH', 'QUERTY');



---------------------------------------------------------------------------
--STORED VIEW
---------------------------------------------------------------------------
--저장뷰
--inline view는 일회성이었으나, 이를 객체로 저장하면 재사용 가능
--가상테이블처럼 사용하지만, 실제로 데이터를 가지고 있는 것은 아님
--실제 테이블과 링크개념

--뷰객체를 이용해서 제한적인 데이터만 다른 사용자에게 제공하는 것이 가능
create view view_emp
as
select emp_id,
            emp_name,
            substr(emp_no, 8) || '******' emp_no,
            email,
            phone
from employee;

--create view 권한을 부여받아야 한다