--===================================
--kh계정
--===================================
show user;

--테이블을 만들려면 권한이 또 필요함
-- table sample 생성
create table sample(
id number
);
--테이블 만들기 권한이 없으면, table도 만들지 못함, 그냥 접속만 한 상태
--오류 : insufficient privileges

--현재 계정의 소유 테이블 목록 조회
select*from tab;

--사원테이블
select*from employee;
--부서테이블
select*from department;
--직급테이블
select*from job;
--지역테이블
select*from location;
--국가테이블
select*from nation;
--급여등급테이블
select*from sal_grade;

--테이블 table |entity | relation(entity가 확장된 형태)
--데이터를 보관하는 객체
--열 column | field | attribute / 세로 / 데이터가 담길 형식
--행 row | record | tuple /가로 / 실질적데이터를 담고있음
--도메인 domain 하나의 column에서 취할 수 있는 값의 그룹(범위)
        --ex. 남/녀 , y/n , 값을 그 중에서 선택해야 함

--테이블 명세 (describe)
--컬럼명       널여부     자료형+(해당크기)
--대소문자 구분이 없음
--(empid, EMPID나 다 같음.. 그래서 언더스코어를 이용해서 단어구분
-- -> emp_id, EMP_ID)
-- 값이 없을 수 있으면 null (선택, 생략가능), 값이 없을 수 없으면 not null(꼭 기입해야함, 필수)
-- 자료형 종류 : 날짜형, 숫자형, 문자형
desc employee;
describe employee;

--==========================================
--DATA TYPE
--==========================================
--1. 문자형 varchar2|char
--2. 숫자형 number
--3. 날짜시간형 date|timestamp
--4. LOB
--데이터 타입은 컬럼에 지정해서 값을 제한적으로 허용하기 위함
--해당컬럼에 지정해준 type만 올 수 있도록
--ex. salary -> 숫자만 , hire_date -> 날짜만
---------------------------------------------
--문자형
---------------------------------------------
--고정형 char(byte) - 최대 2000byte
-- char(10) 'korea' 영소문자는 글자당 1byte씩 처리됨 -> korea 실제데이터크기는 5byte
-- -> 10byte로 지정해두었으니, 남은공간은 알아서 채워서 10byte로 저장됨
-- 10byte를 초과할수는 없음
--문자열은 홑따음표로 감쌈
--'안녕' 한글은 글자당 3byte(11g EX)이므로 실제크기 6byte, 고정형 10byte에 저장됨
--가변형 varchar2(byte) - 최대 4000byte
--'korea' 영소문자는 글자당 1byte씩 처리됨 ->실제데이터의 크기에 맞게 가변형 5byte로 저장됨
--'안녕' 한글은 글자당 3byte(11g EX)이므로 실제크기 6byte, 가변형 6byte로 저장됨
--cf. 고정형, 가변형 모두 지정한 크기 이상의 값은 추가불가

--가변형 long : 최대 2GB (문자형임, 숫자가 아님!)
--숫자를 써도 되는데, 문자로 저장됨
--LOB타입(Large Object) 중의 CLOB(Character LOB)는 최대 4GB까지 지원
    


create table tb_datatype (
-- 컬럼명 자료형 널여부 기본값
    a char(10),
    b varchar2(10)
);

--테이블조회
--select * -- *는 모든 컬럼
select a, b -- 컬럼을 지정
from tb_datatype; --테이블명

--데이터추가 : 한행을 추가
insert into tb_datatype --테이블명
values('hello', 'hello');

insert into tb_datatype
values('123', '안녕');

insert into tb_datatype
values('에브리바디', '안녕');

--데이터가 변경(insert, update, delete)되는 경우, 메모리상에서 먼저 처리됨
-- 메모리상에서 먼저 처리되고, 반영되는 건 아님
--commit을 통해 실제 database에 적용해야 한다
commit;

--lengthb(컬럼명); number - 저장된 데이터의 실제크기를 리턴
select a, lengthb(a), b, lengthb(b)
from tb_datatype;

---------------------------------------------
--숫자형
---------------------------------------------
--정수, 실수를 구분하지 않는다
--number(p, s)
--p : 표현가능한 전체 자리수
--s : p 중 소수점 이하 자리수
/*
값 1234.567 
--------------
number      1234.567
number(7)       1235
 (why? 7, 0이랑 똑같음 소수점이하 자리수를 설정해주지 않으면, 0 이 생략됨)
number(7, 1) 1234.6 --자동반올림
number(7, -2) 1200 --반올림
*/


--rollback; --마지막 commit시점이후 변경사항은 취소된다
create table tb_datatype_number (
    a number,
    b number(7),
    c number(7,1),
    d number(7,-2)
);

select * from tb_datatype_number;

insert into tb_datatype_number 
values(1234.567, 1234.567, 1234.567, 1234.567);

--지정한 크기보다 큰 숫자는 ORA-01438: value larger than specified precision allowed for this column 유발
insert into tb_datatype_number 
values(1234567890.123, 1234567.567, 12345678.5678, 1234.567);

drop table tb_datatype_number;

create table tb_datatype_number (
    a number,
    b number(7),
    c number(7,1),
    d number(7,-2)
);

select * from tb_datatype_number;

insert into tb_datatype_number 
values(1234.567, 1234.567, 1234.567, 1234.567);

--지정한 크기보다 큰 숫자는 ORA-01438: value larger than specified precision allowed for this column 유발
insert into tb_datatype_number 
values(1234567890.123, 1234567.567, 12345678.5678, 1234.567);


commit;
--마지막 commit시점 이후 변경사항은 취소된다.
rollback; 

---------------------------------------------
--날짜시간형
---------------------------------------------
--date 년월일시분초 (보통의 경우)
--timestamp 년월일시분초 밀리초 지역대 (정밀하게 다뤄야할 경우)

create table tb_datatype_date (
    a date,
    b timestamp
 );
 
 --문자열로 표현하는 함수 : to_char
 select to_char(a, 'yyyy/mm/dd hh24:mi:ss'), b
from tb_datatype_date;
 
insert into tb_datatype_date
values (sysdate, systimestamp);

--날짜형 - 날짜형 = 숫자(1=하루)
--날짜형 +- 숫자(1=하루) = 날짜형
 select to_char(a , 'yyyy/mm/dd hh24:mi:ss'),
             to_char(a - 1, 'yyyy/mm/dd hh24:mi:ss'),
            b
from tb_datatype_date;

--날짜형-날짜형 = 숫자(1=하루)
select sysdate - a --0.009일차
from tb_datatype_date;

--to_date : 문자열을 날짜형으로 변환하는 함수
select to_date('2021/01/23') - a
from tb_datatype_date;

--dual 가상테이블 -> create하지 않아도 가능ㅁ
select (sysdate + 1) - sysdate
from dual;

--===================================================
--DQL (Data Query Language)
--===================================================
--데이터 조회(검색)을 위한 언어
--select문
--쿼리 조회결과를 ResultSet (결과집합)이라고 하며, 0행以上을 포함한다
--from절에 조회하고자 하는 테이블 명시
--where절에 의해 특정행을 filtering 가능 (특정행을 추릴수 있음)
--select절에 의해 컬럼을 filtering 또는 추가 가능
--order by절에 의해 행을 정렬할 수 있다 (어떤 컬럼기준으로 행을 정렬할지)
/*
select 컬럼名 (5)                      필수
from 테이블名 (1)                     필수
where 조건点 (2)                     선택
group by 그룹기준 컬럼 (3)    선택
having 그룹조건点 (4)             선택
order by 정렬기준컬럼 (6)      선택
*/

select *
from employee
where dept_code = 'D9' -- 데이터는 대소문자 구분
order by emp_name asc; --오름차순

--1. job테이블에서 job_name컬럼정보만 출력



--2. department테이블에서 모든 컬럼을 출력

--3. employee테이블에서 이름, 이메일, 전화번호, 입사일 출력

--4. employee테이블에서 급여가 2,500,000원 이상인 사원의 이름과 급여출력

--5. employee테이블에서 급여가 3,500,000원 이상이며, 직급코드가 a3인 사원을 조회

--not && || / but and or


--6. employee테이블에서 현재 근무중인 사원을 이름오름차순으로 정렬


----------------------------------------------------------------------------
--select
----------------------------------------------------------------------------

--select절에 쓸 수 있는 것
--table에 존재하는 컬럼
--만들어 낸 가상컬럼(산술연산 가능)
--literal 자체를 찍을 수 있음
--각각의 컬럼은 별칭(alias)를 가질 수 있음
--(as) (")별칭(") (as와 쌍따음표 생략가능)
--if 별칭 생략하면, 그냥 컬럼명으로 들어감
--별칭에 공백, 특수문자가 있거나 숫자로 시작하는 경우 쌍따음표 필수
--가상컬럼 : 실제로는 존재하지 않지만, 마치 존재하는 것처럼 만들 수 있음
select emp_name as "사원명",
            phone "전화번호",
            salary 급여,
            salary *12 "연 봉",--공백이 있음  --가상컬럼
            123 "1abc",--숫자로 시작  --임의의 값
            '안녕' --임의의 값

from employee;

--실급여 : salary + (Salary * bonus)
select emp_name,
        salary,
        bonus,
        salary + (salary * bonus) 실급여
from employee;

--null값하고는산술연산 불가
--why? 결과는 무조건 null이기 때문
--null%1 (나머지연산) 불가
select null + 1, 
            null  - 1, 
            null * 1, 
            null / 1
from dual; --1행짜리 가상테이블

--null처리 함수 : nvl(처리해줄 col명, null일때 처리해줄 값)
--null의 값을 어떤 값으로 바꾸어서 처리함
--col의 값이 null이 아니면, col값 리턴
--col의 값이 null이면, 두번째 인자인 null일때 값을 리턴)
select bonus,
            nvl(bonus, 0) null처리후
from employee;


select emp_name,
        salary,
        bonus,
        salary + (salary * nvl(bonus, 0)) 실급여
from employee;

--중복제거용 keyword : distinct
--select절에 단 한번 사용가능
--직급코드를 중복없이 출력

select distinct job_code
from employee;

--두 개의 값을 하나로 보고, 두개가 일치하면 제거
--여러 컬럼사용시 컬럼을 묶어서 고유한 값으로 취급한다
select distinct job_code, dept_code
from employee;

--문자 연결연산자 ||
--더하기(+) 사용불가, 산술연산만 가능하다
--더하기 연산이면 오라클은 숫자라고 생각하고, 연산했다가, 숫자가 아니면 오류!
--select '안녕' + '하세요'
--from dual;

select '안녕'||'하세요'||123
from dual;

select emp_name || '(' || phone || ')'
from employee;

--------------------------------------------------------------------
-- WHERE
--------------------------------------------------------------------
--where : 행 필터링
--테이블의 모든 행 중 결과집합에 포함된 행을 필터링한다
--특정행에 대해서 true(포함)혹은 false(제외)결과를 리턴한다 (내부적으로 처리, true면 결과집합에 포함, false면 제외)
/*
연산자
(=은 하나만 써줌)
=                           같은가
!=   ^=     <>         다른가
>   <   >= <=           크기비교 (단순숫자, 날짜형)
between .. and      범위연산 (~이상 ~이하인가) (상한 값과 하한 값의 경계도 포함)
like , not like         문자패턴연산
is null , is not null    null여부
in , not in                 값목록에 포함여부

and             A이면서 B인가
or                A또는 B인가
not             제시한 조건 반전
*/

--부서코드가 D6인 사원 조회
select emp_name, dept_code  --3 --행이 결정된 후 (where절), 그 후에 컬럼을 추려냄
from employee  --1
--where dept_code = 'D6';  --2 
--처리 : dept_code가 D6과 같니? 같으면 TRUE, 다르면 FALSE -> TRUE인 애들만 추려내서 결과집합에 반영해줌
--D6이 아닌 사원
--where not dept_code = 'D6';  --2 
where dept_code <> 'D6';  --2  != ^= <>

--급여가 2,000,000원보다 많은 사원 조회
--세자리마다 콤마쓸 수 없음
select emp_name, salary
from employee
where salary >= 2000000;

--부서코드가 D6이거나 D9인 사원조회
select emp_name, dept_code
from employee
where dept_code = 'D6' or dept_code = 'D9';

--날짜형 크기비교 가능
--과거가 작은 것, 이후가 큰 것 (과거 < 미래)
select emp_name, hire_date
from employee
where hire_date < '2000/01/01';  --날짜형식의 문자열은 자동으로 날짜형으로 형변환


--20년이상 근무한 사원 조회 (날짜형 - 날짜형 = 숫자(1=하루)
--날짜 자동형변환은 산술연산 불가 -> 명시적 형변환 필요(to_date)
select emp_name, hire_date
from employee
--where quit_yn = 'N' and sysdate - hire_date > 365 * 20;
--N은 퇴사여부 (데이터값이 N으로 되어있으니, 소문자(n)으로 입력불가
where quit_yn = 'N' and to_date('2021/01/22') - hire_date > 365*20;

--범위 연산
--급여가 200만원 이상, 400만원 이하인 사원 조회(사원명, 급여)
select emp_name, salary
from employee
where salary >= 2000000 and salary <= 4000000;
--where salary between 2000000 and 4000000; --이상, 이하 // 초과, 미만은 between 쓸 수 없음

--입사일이 1990/01/01 ~ 2000/12/31인 사원 조회(사원명, 입사일)
select emp_name, hire_date
from employee
--where hire_date between to_date('1990/01/01') and to_date('2000/12/31')  and quit_yn = 'N';
where hire_date > to_date('1990/01/01') and hire_date < to_date('2000/12/31') and quit_yn = 'N';

--like, not like : 문자열 패턴 비교 연산
--ex. 글자가 세글자 있는것, 문자열 a를 포함하는 것 등
--wildcard : 패턴 의미를 가지는 특수문자
--_       아무 문자 한 글자
--%     아무 문자 0개 이상

select emp_name
from employee
where emp_name like '전%'; --'전'으로 시작하는 0개 이상의 문자가 존재하는가
--전, 전차, 전진, 전형돈 가능
--파전 불가

select emp_name
from employee
where emp_name like '전__'; --언더스코어 두개 / '전'으로 시작하고 뒤에 연달아 두개의 문자가 존재하는가
--전형돈, 전전전 가능 (딱 세글자여야함)
--전진, 전, 파전, 전당포아저씨 불가

--이름에 가운데글자가 '옹'인 사원 조회(단, 이름은 세글자)
select emp_name
from employee
where emp_name like '_옹_'; 

--이름에 '이'가 들어가는 사원 조회
select emp_name
from employee
where emp_name like '%이%'; 

--email컬럼값의 '_' 이전글자가 세글자인 이메일
select email
from employee
--where email like '____%'; --네글자 이후, 0개 이상의 문자열이 뒤따르는가
--문자열과 와일드카드를 구분하지 못함
--> escape처리 : \문자 + 뒤에 escape문자등록 (escape 'escape문자')
--where email like '___\_%' escape '\';
--escape문자가 \로만 제한되는 것은 아님
--임의의 escaping 문자 등록, 데이터에 존재하지 않을 것
where email like '___*_%' escape '*';

--in, not in 값목록에 포함여부
--부서코드가 D6 또는 D8인 사원 조회
select emp_name, dept_code
from employee
--where dept_code = 'D6' or dept_code = 'D8'; 
--where dept_code in 'D6' or dept_code in 'D8'; 
--괄호 안에 값 나열하면 됨 (개수 제한 없음)
where dept_code in ('D6' , 'D8'); 

--부서코드가 D6 또는 D8이 아닌 사원 조회
select emp_name, dept_code
from employee
--where dept_code != 'D6' and dept_code != 'D8'; --D6도 아니면서 D8도 아니어야 하니까, AND사용
where dept_code not in ('D6' , 'D8'); 


--in이나 not in이냐는 이게 아니면 저거 이니까, in과 not in을 합치면 전체일까? no!
--부서가 지정되지 않은 null값은 포함되지 않음!, null은 산술연산 불가, 또한 비교대상에도 제외됨

--인턴사원 조회
select emp_name, dept_code
from employee
where dept_code = null; --그러면 null인 사원만 따로 조회할 수 있을까? no!
--결론 -> null값은 산술연산, 비교연산 모두 불가능
--then how?

--null 비교연산으로 처리 ---> is null, is not null (null전용 연산자)
select emp_name, dept_code
from employee
--where dept_code is null; --null인 사원 
where dept_code is not null; --null이 아닌 사원

--D6, D8부서원이 아닌 사원조회(인턴사원 포함)
select emp_name, dept_code
from employee
where dept_code not in ('D6' , 'D8') or dept_code is null; 

--nvl버전 (null을 특정값으로 대치해서 대립값을 만듦)
--select emp_name, dept_code
select emp_name, nvl(dept_code, '인턴') --원본값을 바꿔준 것이 아니라, 값이 없다면 인턴이라고 임시로 출력해줘라 (출력이니까, 인턴이라고 바뀌어서 출력)
from employee
where nvl(dept_code, 'D0') not in ('D6' , 'D8'); --NULL이면 D0으로 바꿔라 -> D0을 D6, D8과 비교 -> D6,D8이 아님
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

select emp_id, emp_name, dept_code, job_code, hire_date, salary
from employee
--order by dept_code; --특정행을 기준으로 (asc가 생략되어있음)
--order by dept_code asc; --특정행을 기준으로 오름차순
--order by dept_code desc; --특정행을 기준으로 내림차순
--order by dept_code desc nulls last; --null값을 뒤로
--order by dept_code, job_code; --두개의 기준컬럼을 세움, 앞에 것 먼저 처리, 뒤에 것을 다음으로 처리
--order by dept_code, emp_name; --두개의 기준컬럼을 세움, 앞에 것 먼저 처리, 뒤에 것을 다음으로 처리
order by salary desc; --많은 것부터 하려면 desc

--select절에 alias 사용가능
--alias가 반영될 수 있는 것은 order by절에만 가능
--why? 처리순서가 select -> order by
select emp_id 사번,
        emp_name 사원명
from employee
order by 사원명; --컬럼명 그대로 써줘도 되지만, alias를사용해줘도 됨

--1부터 시작하는 컬럼순서 사용가능
select *
from employee
order by 9 desc; --컬럼순서를 1부터 시작해서 넘버링
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
--length(col) -> 문자열의 길이 리턴
select emp_name, length(emp_name)
from employee;

--조건에도 사용가능
select emp_name, email
from employee
where length(email) < 20;  --리턴값이 무조건 하나는 있어야 비교 가능

--lengthb(col)
--값의 byte수 리턴
select emp_name, lengthb(emp_name) --한글은 3byte씩 처리 -> 글자길이 * 3 = byte수
        email, lengthb(email) --영문자, 숫자는 1byte씩 처리 -> 글자길이 = byte수
from employee;

--instr(string, search[, startPosition[,occurence]])
--[] 대괄호는 생략가능함을 의미
--특정 문자열(String)에서 찾고자 하는(search) 문자가 위치한 index를 반환
--oracle : 1-based index, 1부터 시작
--oracle은 0부터 시작하는 것이 아무것도 없음
--빈칸도 1byte로 처리

select instr('kh정보교육원 국가정보원', '정보'), -- -> 3을 리턴 --> 문자열에서 정보의 위치 : 3번
        instr('kh정보교육원 국가정보원', '안녕'), -- -> 0을 리턴 --> 값이 없으면 0리턴
        instr('kh정보교육원 국가정보원', '정보', 5), --어디서 부터 찾을지 지정 (5번지부터 찾기 시작해서, 정보라는 것을 찾고, 정보의 첫번째 부터의 인덱스를 구함)
        instr('kh정보교육원 국가정보원 정보문화사', '정보', 1, 3), --1번 인덱스부터 찾되, 3번째 나온 정보를 찾음
        instr('kh정보교육원 국가정보원', '정보', -1) --음수면 뒤의 정보부터 찾아서, 그 정보의 인덱스 번호를 리턴함
from dual;

--email 컬럼값 중 '@'의 위치는? (이메일, 인덱스)
select instr(email, '@'), email
from employee;

--substr(string, startIndex[, length])
--[] 대괄호는 생략가능함을 의미
--string에서 startIndex부터 length개수만큼 잘라내어 리턴
--length생략 시에는 문자열 끝까지 반환

select substr('show me the money', 6, 2), --> me 리턴
        substr('show me the money', 6), --> 마지막 인자를 생략함 --> startIndex부터 있는 문자열 전체 리턴 --> me the money
        substr('show me the money', -5, 3), --> mon리턴 (뒤에서 거꾸로 5개 인덱스까지 물러난다음, 그 다음 앞에서부터 차례대로 3개를 리턴
        substr('show me the money', -6, 3) --> 공백도 하나의 문자이기 때문에 --> 공백+m+o
from dual;


--사원명에서 성(1글자로 가정)만 중복없이 사전순으로 출력
select distinct substr(emp_name, 1, 1) 성
from employee
order by 1; --컬럼 번호
--order by 성; -- 별칭 행으로 정렬

--lpad | rpad (string, byte [, padding_char])
--byte수의 공간에 string을 대입하고, 남은 공간은 padding_char를 (왼쪽|오른쪽) 채워라
--padding char는 생략시 공백문자가 대입됨
--byte만큼의 공간을 확보한 후, 값을 넣고 남은 공간은 padding_char로 채움

select lpad(email, 20, '#'),
          rpad(email, 20, '#'),
          '[' || lpad(email, 20) || ']',
          '[' || rpad(email, 20) || ']'
from employee;

--남자사원만 사번, 사원명, 주민번호, 연봉 조회
--주민번호 뒤 6자리는 ****** 숨김처리
select  emp_id,
            emp_name,
            substr(emp_no, 1, 8) || '******' emp_no, --방법1
            rpad(substr(emp_no, 1, 8), 14, '*') emp_no, --방법2
            (salary + (salary * nvl(bonus, 0))) * 12 annual_pay
from employee
where substr(emp_no, 8, 1) in ('1', '3');

--****************************************
--      b. 숫자처리함수


--****************************************
--      c. 날짜처리함수



--****************************************
--      d. 형변환함수



--****************************************
--      e. 기타함수







----------------------------------------------------------------------
--그룹 함수
----------------------------------------------------------------------





