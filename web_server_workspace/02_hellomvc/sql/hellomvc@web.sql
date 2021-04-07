--============================================================
-- 관리자계정
--============================================================
-- web 계정 생성
create user web
identified by web
default tablespace users;

-- 권한부여
grant connect, resource to web;

--============================================================
-- WEB계정
--============================================================
show user;

-- 회원테이블 생성
create table member (
    member_id varchar2(15),
    password varchar2(300) not null,
    member_name varchar2(50) not null,
    member_role char(1) default 'U' not null,
    gender char(1),
    birthday date,
    email varchar2(100),
    phone char(11) not null,
    address varchar2(200),
    hobby varchar2(200), -- 운동,등산,독서,게임,여행
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_gender check(gender in ('M', 'F')), -- M 남자, F 여자
    constraint ck_member_role check(member_role in ('U', 'A')) -- U 일반사용자, A 관리자
);

--회원 추가
insert into member
values (
    'honggd', '1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==', '홍길동', 'U', 'M', to_date('20000909','yyyymmdd'),
    'honggd@naver.com', '01012341234', '서울시 강남구', '운동,등산,독서', default
);
insert into member
values (
    'qwerty', '1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==', '쿠어티', 'U', 'F', to_date('19900215','yyyymmdd'),
    'qwerty@naver.com', '01012341234', '서울시 송파구', '운동,등산', default
);
insert into member
values (
    'admin', '1ARVn2Auq2/WAqx2gNrL+q3RNjAzXpUfCXrzkA6d4Xa22yhRLy4AC50E+6UTPoscbo31nbOoq51gvkuXzJ6B2w==', '관리자', 'A', 'M', to_date('19801010','yyyymmdd'),
    'admin@naver.com', '01056785678', '서울시 관악구', '게임,독서', default
);

commit;

-- 페이징
-- 방법 1. rownum : 행추가시 자동으로 부여되는 넘버
--select rownum, M.*
--from member M
--order by enroll_date desc;

-- enroll_date로 정렬된 순서대로 1~10건을 처리 -> rownum 새로 부여 (INLINE 뷰)
-- but 1부터가 아니라 11부터 20을 요청하면 안나옴
-- why? where절이 끝나야 rownum을 완성하기 때문에
-- -> 3단으로 만들기
select *
from (
        select rownum rnum, M.*
        from (
                select M.*
                from member M
                order by enroll_date desc
                ) M
        ) M
where rnum between 11 and 20;

--방법2. window함수 row_number
--2단으로 처리 가능
--105건의 경우
--cPage = 1 : 1 ~ 10
--cPage = 2 : 11 ~ 20
--cPage = 3 : 21 ~ 30
--...
--cPage = 10 : 91 ~ 100
--cPage = 11 : 101 ~ 105
--현재페이지를 가지고 start rownum, end rownum을 계산해내야함

select *
from (
        select row_number() over(order by enroll_date desc) rnum,
                    M.*
        from member M
        ) M
where rnum between 11 and 20;

select * from member;

select count(*) from member;
