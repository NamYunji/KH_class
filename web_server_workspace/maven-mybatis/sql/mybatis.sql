--=============================================
-- 관리자(system) 계정
--=============================================
create user mybatis
identified by mybatis
default tablespace users;

grant connect, resource to mybatis;

--=============================================
-- mybatis 계정
--=============================================
create table student (
    no number,
    name varchar2(100) not null,
    tel char(11) not null,
    reg_date date default sysdate,
    constraint pk_student_no primary key(no)
);

create sequence seq_student_no;

select * from student;

--=============================================
-- mybatis 계정
--=============================================
-- oracle synonym 객체
--동의어객체. 별칭객체. - 별칭을 객체로 저장한 것
--mybatis계정에서 kh계정의 테이블 접근
select * from kh.employee; 
select * from kh.department;
select * from kh.job;
--"table or view does not exist"
--사용자이름.테이블명
--모든 객체가 사용자 소속, 주인없이 만들어지는 객체는 없음
--그냥 테이블명만 쓰는 경우는 접속계정의 테이블이기에 사용자이름을 생략한 것

--kh계정의 데이터에 mybatis가 제멋대로 접근 불가
--존재하긴 하지만, 접근권한이 없어서 그럼
--주인장인 kh가 권한을 주거나, 관리자가 권한을 줘야함
-- -> mybatis로 접속해서 kh.테이블명하면 언제든지 그 테이블 조회 가능 (employee테이블의 경우에는 조회, 수정도 가능)

--별칭부여 (동의어 생성)
--kh.employee -> emp, kh.department -> dept kh.job -> job
create synonym emp for kh.employee;
-- Synonym EMP이(가) 생성되었습니다.
create synonym dept for kh.department;
--Synonym DEPT이(가) 생성되었습니다.
create synonym job for kh.job;
--Synonym JOB이(가) 생성되었습니다.

--create synonym의 경우, 
--connect, resource의 권한으로만은 부족
--resource role에 create synonym은 포함되어 있지 않기 때문

select * from emp;
select * from dept;
select * from job;

--============================================
--관리자계정
--============================================
--관리자계정으로 mybatis에 kh계정의 테이블들에 대한 권한 양도
grant all on kh.employee to mybatis; --모든권한 부여
grant select on kh.department to mybatis; --조회권한만 부여
grant select on kh.job to mybatis; --조회권한만 부여
--create synonym 권한 부여
grant create synonym to mybatis;
