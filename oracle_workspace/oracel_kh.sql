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
    a CHAR(10),
    b VARCHAR2(10)
);


--테이블조회
--SELECT 컬럼
--FROM 테이블

--SELECT * -- *는 모든 컬럼
SELECT a, b -- 컬럼을 직접 지정
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
    a NUMBER,
    b NUMBER(7),
    c NUMBER(7,1),
    d NUMBER(7,-2)
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
    a DATE,
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
SELECT to_char(a, 'yyyy/mm/dd hh24:mi:ss'), b
FROM tb_datatype_date;
--a 출력 : 2021/01/26 03:29:35
--b 출력 : 21/01/26 03:29:35.108000000


--날짜형은 산술연산이 가능하다!
--날짜형 - 날짜형 = 숫자(1=하루)
--날짜형 +- 숫자(=하루) = 날짜형


--날짜형 +- 숫자(=하루) = 날짜형
 SELECT to_char(a , 'yyyy/mm/dd hh24:mi:ss'),
              to_char(a + 1, 'yyyy/mm/dd hh24:mi:ss'),
              to_char(a - 1, 'yyyy/mm/dd hh24:mi:ss'),
              b
FROM tb_datatype_date;
--1컬럼 출력 : 2021/01/26 03:29:35
--2컬럼 출력 : 2021/01/27 03:29:35
--3컬럼 출력 : 2021/01/25 03:29:35
--4컬럼 출력 : 21/01/26 03:29:35.108000000


--날짜형 - 날짜형 = 숫자(1=하루)
SELECT sysdate - a 
FROM tb_datatype_date;
--출력 : 0.009일차

--to_date : 문자열을 날짜형으로 변환하는 함수
--TO_DATE('날짜형으로 변환해줄 문자열')
--년월일만 써주면 자정으로 세팅됨
SELECT TO_DATE('2021/01/27') - a
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

