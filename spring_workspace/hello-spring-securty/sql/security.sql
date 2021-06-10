------------------------------------------------
-- security.sql
------------------------------------------------

--member
select * from member;

desc member;

--authority 테이블 생성
create table authority (
    id varchar2(20) not null, -- 회원아이디
    authority varchar2(20) not null, -- 권한
    constraint pk_authority primary key (id, authority),
    constraint fk_authority_member_id foreign key(id) references member(id)
);

-- 한 회원이 여러 권한을 가질 수 있도록 id, authority를 묶어서 pk로 설정 (복합 pk)
-- honggd, ROLE_USER
-- honggd, ROLE_ADMIN
-- -> 두개를 묶어서 하나의 고유한 값이 나오면 되므로 id가 중복되어도 ok

insert into authority values ('qwerty', 'ROLE_USER');
insert into authority values ('honggd', 'ROLE_USER');
insert into authority values ('admin', 'ROLE_USER');
insert into authority values ('admin', 'ROLE_ADMIN');

select * from authority;

commit;

-- 회원정보와 회원권한을 각각 따로 조회해야 함

--회원정보 조회
select * from member where id = 'admin';

--회원권한 조회
select * from authority where id = 'admin';
