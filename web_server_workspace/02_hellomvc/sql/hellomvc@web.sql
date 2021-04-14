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

-- 댓글 테이블 작성
-- 댓글 고유번호(pk), 댓글레벨(1)
-- 작성자 (fk - member 테이블 member_id)
    -- member 테이블에서 member_id가 삭제되면, 해당 테이블의 writer는 NULL로 변경
-- 내용
-- 게시물 번호 (fk - board 테이블 no)
    -- board 테이블에서 no가 삭제되면, 해당 테이블의 board_no도 함께 삭제
-- 댓글 참조 (fk - 해당 테이블 no) / 대댓글의 경우에만 사용, 댓글의 경우에는 null
    -- 해당 테이블에서 no가 삭제되면, 해당 테이블의 comment_ref도 함께 삭제
-- 등록일(sysdate)
create table board_comment (
    no number, --pk
    comment_level number default 1,
    writer varchar(15),
    content varchar2(2000),
    board_no number,
    comment_ref number,
    reg_date date default sysdate,
    constraint pk_board_comment_no primary key(no),
    constraint fk_board_comment_writer foreign key(writer)
                                                                    references member(member_id)
                                                                    on delete set null,
     constraint fk_board_comment_board_no foreign key(board_no)
                                                                    references board(no)
                                                                    on delete cascade,
    constraint fk_board_comment_ref foreign key(comment_ref)
                                                                    references board_comment(no)
                                                                    on delete cascade
);

comment on column board_comment.no is '게시판댓글번호';
comment on column board_comment.comment_level is '게시판댓글 레벨';
comment on column board_comment.writer is '게시판댓글 작성자';
comment on column board_comment.content is '게시판댓글';
comment on column board_comment.board_no is '참조원글번호';
comment on column board_comment.comment_ref is '게시판댓글 참조번호';
comment on column board_comment.reg_date is '게시판댓글 작성일';

-- 시퀀스 생성
create sequence seq_board_comment_no;

-- 댓글 샘플 테스트
-- 110번 게시글에 대한 댓글
-- reg_date를 제외한 6개 컬럼에 값 추가
-- comment_level로 1 - 댓글
-- comment_ref로 null - 댓글이니까 참조할 댓글이 없음
insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'honggd', '잘읽었습니다.', 110, null);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'admin', '하하하', 110, null);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 1, 'kamsayoyo', 'wow ㅇㅅㅇ', 110, null);

-- 대댓글 샘플 테스트
-- comment_level로 2 - 대댓글
-- comment_ref로 1 - 어떤 댓글에 대한 대댓글인지
insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'admin', '읽어주셔서 감사합니다.', 110, 1);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'abcd', '거짓말~', 110, 1);

insert into board_comment(no, comment_level, writer, content, board_no, comment_ref)
values(seq_board_comment_no.nextval, 2, 'abcd', '키키키', 110, 2);

select * from board_comment;

-- 계층형 쿼리
-- 기준컬럼을 이용해 행간의 수직구조를 표현한 쿼리
-- 댓글, 조직도, 메뉴 등의 트리구조를 표현할 수 있음
-- start with 최상위 행 지정
-- connect by 부모행-자식행의 관계 작성
     -- 부모행의 컬럼앞에 prior키워드 작성 -> 해당행이 부모행임을 알려줌
     -- connect by prior 부모행 = 자식행
-- -> 지정해준 부모행 밑에 해당 자식컬럼들이 나옴
select lpad(' ',(level - 1) * 5) || content,  -- level값에 따른 들여쓰기 처리
            level, -- pseudo column, comment_level과 동일
            bc.*
from board_comment bc
-- 최상위 행은 comment_level이 1인 행이다
start with comment_level = 1
-- 부모행인 no가 자식행인 comment_ref와 동일하다면 연결해라
connect by prior no = comment_ref
order siblings by reg_date desc;