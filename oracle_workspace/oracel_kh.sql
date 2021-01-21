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
select *
from job
where job_name;


--2. department테이블에서 모든 컬럼을 출력

--3. employee테이블에서 이름, 이메일, 전화번호, 입사일 출력

--4. employee테이블에서 급여가 2,500,000원 이상인 사원의 이름과 급여출력

--5. employee테이블에서 급여가 3,500,000원 이상이며, 직급코드가 a3인 사원을 조회

--not && || / but and or


--6. employee테이블에서 현재 근무중인 사원을 이름오름차순으로 정렬





