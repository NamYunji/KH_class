select * from tab;

--총 19개 테이블
select * from tabs;
--챌린지
CREATE TABLE challenge (
	challenge_id	number		      NOT NULL,
	challenge_term_type	char(1)		  NOT NULL,
	challenge_level	number	      NOT NULL,
	challenge_name	varchar2(4000)	  NOT NULL,
	challenge_info	varchar2(4000)	  NOT NULL,
	challenge_point	number		      NOT NULL,
	challenge_term	number default 1  NOT NULL,
    
    constraint pk_challenge_id primary key(challenge_id),
    constraint ck_challenge_term_type check(challenge_term_type in ('S','L')),      -- challenge 하루/기간 구분(S/L)
    constraint ck_challenge_level check(challenge_level in (1,2,3))         -- challenge 강도 구분(1-하 / 2-중/ 3-상)
);

--유저
CREATE TABLE member (
	member_id varchar2(15)		NOT NULL,
	member_pw 	varchar2(300)		NOT NULL,
	member_name 	varchar2(50)		NOT NULL,
	member_nickname	varchar2(50)		NOT NULL,
	member_email	varchar2(100)		NULL,
	member_phone	char(11)		NOT NULL,
	member_team	char(1)	DEFAULT 'N'	NOT NULL,
	member_picture	varchar2(4000)	DEFAULT '기본 이미지'	NOT NULL,
	member_role	char(1)	DEFAULT 'U'	NOT NULL,
	access_token	varchar2(4000)		NULL,

    constraint pk_member_id primary key(member_id),
    constraint ck_member_team check(member_team in ('N','Y')),      -- 팀에 속해있지 않다('N') / 팀에 속해 있다('Y')
    constraint ck_member_role check(member_role in ('U','A'))         -- 'U' : 일반 사용자 / 'A' : 관리자
);

--포인트 (개인)
CREATE TABLE personal_point (
	p_point_id	number		    NOT NULL,
	challenge_id	number,
	member_id	varchar2(15)		NOT NULL,
	point	number	DEFAULT 0	NOT NULL,
	point_date	date	DEFAULT sysdate	NOT NULL,
    
    constraint pk_personal_point_id primary key(p_point_id),
    constraint fk_personal_point_member_id foreign key(member_id) references member(member_id) on delete cascade,                       --탈퇴시 포인트 삭제
    constraint fk_personal_point_challenge_id foreign key(challenge_id) references challenge(challenge_id) on delete set null   --챌린지 삭제 시 그대로 남아 있음
);

--뱃지
CREATE TABLE badge (
	badge_id number         NOT NULL,
	challenge_id number,
	member_id	varchar2(15)    NOT NULL,
	badge_img_id	number	NOT NULL,
	badge_date date DEFAULT sysdate	NOT NULL,
    
    constraint pk_badge_id primary key(badge_id),
    constraint fk_badge_member_id foreign key(member_id) references member(member_id) on delete cascade,                             --사용자 탈퇴시 같이 삭제
    constraint fk_badge_challenge_id foreign key(challenge_id) references challenge(challenge_id) on delete  set null,       --챌린지 삭제해도 그대로 존재
    constraint fk_badge_badge_img_id foreign key(badge_img_id) references badge_image(badge_img_id) on delete cascade        --뱃지 이미지 삭제 시 같이 삭제
);

--뱃지 이미지
CREATE TABLE badge_image (
	badge_img_id	number		NOT NULL,
	badge_name	varchar2(100)		NOT NULL,
	badge_img	varchar2(4000)		NOT NULL,
    
    constraint pk_badge_img_id primary key(badge_img_id)    
);

COMMENT ON COLUMN badge_image.badge_img IS '이미지 경로';

--게시글 (공지사항/커뮤니티)
CREATE TABLE article (
	a_id	number		NOT NULL,
	member_id	varchar2(15),
	a_type	varchar2(15)		NOT NULL,
	a_title	varchar2(1000)		NOT NULL,
	a_content	varchar2(4000)		NOT NULL,
	a_reg_date	date	DEFAULT sysdate	NOT NULL,
	a_read_count	number	DEFAULT 0	NOT NULL,
	a_like	number	DEFAULT 0	NOT NULL,
    
    constraint pk_aritlce_id primary key(a_id),
    constraint fk_member_id foreign key(member_id) references member(member_id) on delete set null          --사용자 탈퇴해도 그대로 존재
);

--인증 게시판 게시글
CREATE TABLE a_confirm (
	a_id	number		NOT NULL,
	member_id	varchar2(15),
	challenge_id	number,
	confirm_type	varchar2(10)		NOT NULL,
	a_title	varchar2(1000)		NOT NULL,
	a_content	varchar2(4000)		NOT NULL,
	a_reg_date	date	DEFAULT sysdate	NOT NULL,
	a_read_count	number	DEFAULT 0	NOT NULL,
	a_like	number	DEFAULT 0	NOT NULL,
    
    constraint pk_a_confirm_id primary key(a_id),
    constraint fk_a_confirm_member_id foreign key(member_id) references member(member_id) on delete set null,                     --사용자 탈퇴해도 그대로 존재
    constraint fk_a_confirm__challenge_id foreign key(challenge_id) references challenge(challenge_id) on delete set null  --챌린지 삭제해도 팀 존재
);

--팀원 찾기 게시판 게시글
CREATE TABLE a_search_team (
	a_id	number		NOT NULL,
	member_id	varchar2(15),
	challenge_id	number,
	a_title	varchar2(1000)		NOT NULL,
	a_content	varchar2(4000)		NOT NULL,
	a_reg_date	date	DEFAULT sysdate	NOT NULL,
	a_read_count	number	DEFAULT 0	NOT NULL,
	a_like	number	DEFAULT 0	NOT NULL,
	s_team_count	number	DEFAULT 4	NOT NULL,
    
    constraint pk_a_search_team_id primary key(a_id),
    constraint fk_a_search_team_member_id foreign key(member_id) references member(member_id) on delete set null,                     --사용자 탈퇴해도 그대로 존재
    constraint fk_a_search_team_challenge_id foreign key(challenge_id) references challenge(challenge_id) on delete set null  --챌린지 삭제해도 팀 존재
);

--우리 팀 게시판 게시글
CREATE TABLE a_team (
	team_a_id	number		NOT NULL,
	member_id	varchar2(15)		NOT NULL,
	a_id	number		NOT NULL,
	a_title	varchar2(1000)		NOT NULL,
	a_content	varchar2(4000)		NOT NULL,
	a_reg_date	date	DEFAULT sysdate	NOT NULL,
	a_read_count	number	DEFAULT 0	NOT NULL,
	a_like	number	DEFAULT 0	NOT NULL,
    
    constraint pk_a_team_id primary key(team_a_id),
    constraint fk_a_team_member_id foreign key(member_id) references member(member_id) on delete set null,                       --사용자 탈퇴해도 그대로 존재                   
    constraint fk_a_team_a_id foreign key(a_id) references a_search_team(a_id) on delete cascade --챌린지 삭제(챌린지 끝)나면 같이 삭제
);

--팀
CREATE TABLE team (
	team_id	number		    NOT NULL,
	member_id	varchar2(15)	NOT NULL,
	a_id	number		NOT NULL,
	team_start_day date DEFAULT sysdate	NOT NULL,
    
    constraint pk_team_id primary key(team_id),
    constraint fk_team_member_id foreign key(member_id) references member(member_id) on delete cascade,      --member 탈퇴 시 팀도 같이 탈퇴
    constraint fk_team_a_id foreign key(a_id) references a_search_team(a_id)  --참조 시 부모(팀원찾기게시글) 삭제 불가
);

--포인트(팀)
CREATE TABLE team_point (
	t_point_id	number		NOT NULL,
	member_id	varchar2(15)		NOT NULL,
	a_id	number		NOT NULL,
	point	number	DEFAULT 0	NOT NULL,
	point_date	date	DEFAULT sysdate	NOT NULL,
    
    constraint pk_team_point_id primary key(t_point_id),
    constraint fk_team_point_member_id foreign key(member_id) references member(member_id) on delete cascade,        --회원탈퇴시 같이 삭제
    constraint fk_team_point_a_id foreign key(a_id) references a_search_team(a_id)   --참조 시 부모(팀원찾기게시글) 삭제 불가
);

--첨부파일(공지사항/커뮤니티)
CREATE TABLE attachment (
	attachment_id	number		NOT NULL,
	a_id	number		NOT NULL,
	original_filename	varchar2(255)		NOT NULL,
	renamed_filename	varchar2(255)		NOT NULL,
	attachment_status	char(1)	DEFAULT 'Y'	NOT NULL,
    
    constraint pk_attachment_id primary key(attachment_id),
    constraint fk_attachment_a_id foreign key(a_id) references article(a_id) on delete cascade,   --게시글 삭제시 같이 삭제
	constraint ck_attachment_status check(attachment_status in ('Y','N'))
);

--첨부파일(인증)
CREATE TABLE confirm_attachment (
	attachment_id	number		NOT NULL,
	a_id	number		NOT NULL,
	original_filename	varchar2(255)		NOT NULL,
	renamed_filename	varchar2(255)		NOT NULL,
	attachment_status	char(1)	DEFAULT 'Y'	NOT NULL,
    
    constraint pk_confirm_attachment_id primary key(attachment_id),
    constraint fk_confirm_attachment_a_id foreign key(a_id) references a_confirm(a_id) on delete cascade,   --게시글 삭제시 같이 삭제
	constraint ck_confirm_attachment_status check(attachment_status in ('Y','N'))
);
select * from
s_team_attachment;


--첨부파일(팀원찾기) ** 팀원찾기 = s_
CREATE TABLE s_team_attachment (
	attachment_id	number		NOT NULL,
	a_id	number		NOT NULL,
	original_filename	varchar2(255)		NOT NULL,
	renamed_filename	varchar2(255)		NOT NULL,
	attachment_status	char(1)	DEFAULT 'Y'	NOT NULL,
    
    constraint pk_s_team_attachment_id primary key(attachment_id),
    constraint fk_s_team_attachment_a_id foreign key(a_id) references a_search_team(a_id) on delete cascade,   --게시글 삭제시 같이 삭제
	constraint ck_s_team_attachment_status check(attachment_status in ('Y','N'))
);

--첨부파일(우리팀 게시글) ** 우리팀 = a_
CREATE TABLE a_team_attachment (
	attachment_id	number		NOT NULL,
	team_a_id	number		NOT NULL,
	original_filename	varchar2(255)		NOT NULL,
	renamed_filename	varchar2(255)		NOT NULL,
	attachment_status	char(1)	DEFAULT 'Y'	NOT NULL,
    
    constraint pk_a_team_attachment_id primary key(attachment_id),
    constraint fk_a_team_attachment_team_a_id foreign key(team_a_id) references a_team(team_a_id) on delete cascade,   --게시글 삭제시 같이 삭제
	constraint ck_a_team_attachment_status check(attachment_status in ('Y','N'))
);

--댓글(공지사항/커뮤니티)
create table a_comment (
    comment_id number not null,
    member_id varchar2(15),
    a_id number not null,
    comment_content varchar2(2000) not null,
    comment_reg_date date default sysdate,
    comment_level number default 1 not null,
    comment_ref number,
    
    constraint pk_a_comment_id primary key(comment_id),
    constraint fk_a_comment_member_id foreign key(member_id) references member(member_id) on delete set null,                     --사용자 탈퇴시 null 처리
    constraint fk_a_comment_a_id foreign key(a_id) references article(a_id) on delete cascade,          --게시글 삭제시 같이 삭제
    constraint fk_a_comment_comment_ref foreign key(comment_ref) references a_comment(comment_id) on delete set null        --참조 댓글 삭제해도 그대로.(null처리)
);

--댓글(인증)
CREATE TABLE confirm_comment (
	comment_id	number		NOT NULL,
	member_id	varchar2(15),
	a_id	number		NOT NULL,
	comment_content	varchar2(2000)		NOT NULL,
	comment_reg_date	date	DEFAULT sysdate	NOT NULL,
	comment_level	number	DEFAULT 1	NOT NULL,
	comment_ref	number,
    
    constraint pk_confirm_comment_id primary key(comment_id),
    constraint fk_confirm_comment_member_id foreign key(member_id) references member(member_id) on delete set null,                             --사용자 탈퇴시 null 처리
    constraint fk_confirm_comment_a_id foreign key(a_id) references a_confirm(a_id) on delete cascade,          --게시글 삭제시 같이 삭제
    constraint fk_confirm_comment_comment_ref foreign key(comment_ref) references confirm_comment(comment_id) on delete set null        --참조 댓글 삭제해도 그대로.(null처리)  
);

--댓글(우리팀)
CREATE TABLE team_comment (
	comment_id	number		NOT NULL,
	team_a_id	number		NOT NULL,
	member_id	varchar2(15),
	comment_content	varchar2(2000)		NOT NULL,
	comment_reg_date	date	DEFAULT sysdate	NOT NULL,
	comment_level	number	DEFAULT 1	NOT NULL,
	comment_ref	number,
    
    constraint pk_comment_id primary key(comment_id),
    constraint fk_comment_team_a_id foreign key(team_a_id) references a_team(team_a_id) on delete cascade,          --게시글 삭제시 같이 삭제
    constraint fk_comment_member_id foreign key(member_id) references member(member_id) on delete set null,                                         --사용자 탈퇴시 null 처리
    constraint fk_comment_comment_ref foreign key(comment_ref) references team_comment(comment_id) on delete set null                       --참조 댓글 삭제해도 그대로.(null처리)
);

--팀 신청자
CREATE TABLE request_team (
	request_id	number		NOT NULL,
	member_id	varchar2(15),
	a_id	number		NOT NULL,
    
    constraint pk_request_team_id primary key(request_id),
    constraint fk_request_team_member_id foreign key(member_id) references member(member_id) on delete set null,          --사용자 탈퇴시 null 처리
    constraint fk_request_team_a_id foreign key(a_id) references a_search_team(a_id)      -- 팀원찾기 게시글 삭제 못함
);

--sequence
create sequence seq_team_id;
create sequence seq_a_comment_id;
create sequence seq_p_point_id;
create sequence seq_badge_id;
create sequence seq_badge_img_id;
create sequence seq_attachment_id;
create sequence seq_t_point_id;
create sequence seq_article_id;
create sequence seq_a_confirm_id;
create sequence seq_a_search_team_id;
create sequence seq_confirm_attachment_id;
create sequence seq_search_team_attachment_id;
create sequence seq_confirm_comment_id;
create sequence seq_team_comment_id;
create sequence seq_a_team_id;
create sequence seq_request_id;
create sequence seq_team_attachment_id;
create sequence seq_challenge_id;

--
select * from member; --사용자
select * from article; --게시글(공지사항/커뮤니티)
select * from a_confirm; --인증 게시판의 게시글
select * from a_search_team; --팀원 찾기 게시판의 게시글
select * from a_team; --우리팀 게시판의 게시글
select * from challenge; --챌린지

--테스트용 유저
insert into member
values (
'honggd', '1234', '홍길동', '길동', 'honggd@naver.com', '01011111111','Y', default, default, 'access_token_1234'
);

insert into member
values (
'qwerty', '1234', '쿠어티', '어티', 'qwerty@naver.com', '01022222222','N', default, default, 'access_token_2345'
);

insert into member
values (
'aaaaa', '1234', '아아아', '아아', 'aaaaa@naver.com', '01033333333','Y', default, default, 'access_token_3456'
);

insert into member
values (
'bbbbb', '1234', '비비비', '비비', 'bbbbb@naver.com', '01034343434','N', default, default, 'access_token_4567'
);

insert into member
values (
'ccccc', '1234', '씨씨씨', '씨씨', 'ccccc@naver.com', '01045454545','Y', default, default, 'access_token_5678'
);

insert into member
values (
'ddddd', '1234', '디디디', '디디', 'ddddd@naver.com', '01056565656','Y', default, default, NULL
);

insert into member
values (
'eeeee', '1234', '이이이', '이이', 'eeeee@naver.com', '01067676767','Y', default, default, NULL
);

insert into member
values (
'fffff', '1234', '에프프', '에프', 'fffff@naver.com', '01078787878','Y', default, default, 'access_token_6789'
);

insert into member
values (
'ggggg', '3456', '쥐쥐쥐', '쥐쥐', 'ggggg@naver.com', '01090909090','Y', default, default, 'access_token_0000'
);

insert into member
values (
'hhhhh', '3456', '에이치', '에치', 'hhhhh@naver.com', '01034561234','N', default, default, 'access_token_0666'
);

insert into member
values (
'iiiii', '3456', '아이아', '아이', 'iiiii@naver.com', '01011122221','N', default, default, 'access_token_7700'
);

insert into member
values (
'jjjjj', '3456', '제이제', '제이', 'jjjjj@naver.com', '01037373737','N', default, default, 'access_token_0880'
);

insert into member
values (
'kkkkk', '3456', '케케케', '케케', 'kkkkk@naver.com', '01023122312','N', default, default, 'access_token_4354'
);

insert into member
values (
'temp0', '3456', '템프영', '템0', 'temp0@naver.com', '01012123434','N', default, default, NULL
);

insert into member
values (
'temp1', '3456', '템프일', '템1', 'temp1@naver.com', '01056567676','N', default, default, NULL
);
insert into member
values (
'admin1', '1234', '관리자1', '관리자2', 'admin1@naver.com', '01044444444',default, '관리자이미지', 'A', NULL
);

insert into member
values (
'admin2', '1234', '관리자1', '관리자2', 'admin2@naver.com', '01055555555',default, '관리자이미지', 'A', NULL
);

--게시글(공지사항/커뮤니티)
insert into article
values (
seq_article_id.nextval,'honggd','커뮤니티','안녕하세요, 게시판입니다 - 1','안녕하세요 본문 - 1', to_date('18/02/14','RR/MM/DD'), default, default
);
insert into article
values (
seq_article_id.nextval,'aaaaa','커뮤니티','안녕하세요, 게시판입니다 - 2','안녕하세요 본문 - 2',to_date('18/03/16','RR/MM/DD'), default, default
);
insert into article
values (
seq_article_id.nextval,'bbbbb','커뮤니티','안녕하세요, 게시판입니다 - 3','안녕하세요 본문 - 3',to_date('18/03/24','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'ccccc','커뮤니티','안녕하세요, 게시판입니다 - 4','안녕하세요 본문 - 4',to_date('18/04/14','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'aaaaa','커뮤니티','안녕하세요, 게시판입니다 - 5','안녕하세요 본문 - 5',to_date('18/04/24','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'admin1','공지사항','안녕하세요, 공지사항입니다 - 1','안녕하세요 공지 본문 - 1',to_date('18/05/08','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'ddddd','커뮤니티','안녕하세요, 게시판입니다 - 6','안녕하세요 본문 - 6',to_date('18/05/25','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'eeeee','커뮤니티','안녕하세요, 게시판입니다 - 7','안녕하세요 본문 - 7',to_date('18/06/12','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'eeeee','커뮤니티','안녕하세요, 게시판입니다 - 8','안녕하세요 본문 - 8',to_date('18/06/14','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'eeeee','커뮤니티','안녕하세요, 게시판입니다 - 9','안녕하세요 본문 - 9',to_date('18/07/10','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'admin2','공지사항','안녕하세요, 공지사항입니다 - 2','안녕하세요 공지 본문 - 2',to_date('18/07/23','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'eeeee','커뮤니티','안녕하세요, 게시판입니다 - 10','안녕하세요 본문 - 10',to_date('18/08/18','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'eeeee','커뮤니티','안녕하세요, 게시판입니다 - 11','안녕하세요 본문 - 11',to_date('18/09/29','RR/MM/DD'),default, default
);
insert into article
values (
seq_article_id.nextval,'admin2','공지사항','안녕하세요, 공지사항입니다 - 3','안녕하세요 공지 본문 - 3',to_date('18/10/10','RR/MM/DD'),default, default
);

--인증 게시판의 게시글
insert into a_confirm
values (
seq_a_confirm_id.nextval,'ggggg',4,'하루','업사이클링 인증이요','안녕하세요 업사이클링 인증입니다',to_date('18/01/01','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'qwerty',15,'기간','분리수거 인증이요','안녕하세요 분리수거 인증입니다',to_date('18/02/05','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'eeeee',20,'팀','다회용 마스크 인증이요','안녕하세요 다회용 마스크 인증입니다',to_date('18/03/22','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'ddddd',17,'팀','텀블러 인증이요','안녕하세요 텀블러 인증입니다',to_date('18/04/14','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'eeeee',3,'하루','계단 인증이요','안녕하세요 계단 인증입니다',to_date('18/05/05','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'hhhhh',15,'기간','분리수거 인증이요','안녕하세요 분리수거 인증입니다',to_date('18/05/27','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'jjjjj',18,'기간','장바구니 인증이요','안녕하세요 장바구니 인증입니다',to_date('18/06/13','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'ccccc',10,'하루','쓰레기 줍기 인증이요','안녕하세요 쓰레기 줍기 인증입니다',to_date('18/06/26','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'ggggg',15,'팀','분리수거 인증이요','안녕하세요 분리수거 인증입니다',to_date('18/07/02','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'fffff',24,'팀','배달음식 줄이기 인증이요','안녕하세요 배달음식 줄이기 인증입니다',to_date('18/07/17','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'ccccc',12,'하루','환경 캠페인 집회 인증이요','안녕하세요 집회 참여 인증입니다',to_date('18/08/26','RR/MM/DD'),default, default
);

insert into a_confirm
values (
seq_a_confirm_id.nextval,'honggd',11,'하루','개인 용기 인증이요','안녕하세요 개인 용기 인증입니다',to_date('18/08/30','RR/MM/DD'),default, default
);

--팀원 찾기 게시판의 게시글
insert into a_search_team
values (
seq_a_search_team_id.nextval,'bbbbb',15,'분리수거 같이 하실분?','분리수거 같이해요~ 저 포함 4명이서 합시다~',to_date('18/01/01','RR/MM/DD'),default,default,default
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'hhhhh',17,'한 달 동안 텀블러 사용해요~','저 포함 10명이서 합시다~',to_date('18/02/02','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'iiiii',18,'한 달 동안 장바구니 사용해요~','저 포함 10명이서 합시다~',to_date('18/03/03','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'jjjjj',24,'한 달 동안 배달음식 줄입시다~','저 포함 10명이서 합시다~',to_date('18/03/13','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'kkkkk',21,'한 달 동안 손수건 사용해요~','저 포함 10명이서 합시다~',to_date('18/04/04','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'qwerty',20,'한 달 동안 다회용마스크 사용해요~','저 포함 10명이서 합시다~',to_date('18/05/05','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'temp0',22,'한 달 동안 에어컨 적정온도 유지해요~','저 포함 10명이서 합시다~',to_date('18/06/06','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'temp1',15,'한 달 동안 분리수거 같이해요~','저 포함 10명이서 합시다~',to_date('18/07/07','RR/MM/DD'),default,default,10
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'kkkkk',25,'한 달 동안 물티슈 덜사용해요~','저 포함 9명이서 합시다~',to_date('18/08/08','RR/MM/DD'),default,default,9
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'jjjjj',26,'한 달 동안 핸드폰 덜 사용해요~','저 포함 4명이서 합시다~',to_date('18/09/09','RR/MM/DD'),default,default,4
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'iiiii',17,'한 달 동안 텀블러 사용해요~','저 포함 5명이서 합시다~',to_date('18/10/01','RR/MM/DD'),default,default,5
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'hhhhh',18,'한 달 동안 장바구니 사용해요~','저 포함 6명이서 합시다~',to_date('18/11/01','RR/MM/DD'),default,default,6
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'bbbbb',17,'한 달 동안 텀블러 사용해요~','저 포함 7명이서 합시다~',to_date('18/12/01','RR/MM/DD'),default,default,7
);

insert into a_search_team
values (
seq_a_search_team_id.nextval,'jjjjj',17,'한 달 동안 텀블러 사용해요~','저 포함 8명이서 합시다~',to_date('18/12/12','RR/MM/DD'),default,default,8
);

--우리팀 게시판의 게시글
insert into a_team
values (
seq_a_team_id.nextval,'ddddd',1,'분리수거 잘 하고 계세요1?','분리수거',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'honggd',2,'텀블러 사용이요1','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'aaaaa',1,'분리수거 잘 하고 계세요2?','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'ccccc',1,'분리수거 잘 하고 계세요3?','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'ddddd',2,'텀블러 사용이요2','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'eeeee',1,'분리수거 잘 하고 계세요?4','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'fffff',2,'텀블러 사용이요3','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'ggggg',2,'텀블러 사용이요4','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'ccccc',1,'분리수거 질문이요1','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'eeeee',1,'분리수거 질문이요2','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'fffff',2,'텀블러 질문이요1','안녕하세요~',default,default,default
);

insert into a_team
values (
seq_a_team_id.nextval,'honggd',2,'텀블러 질문이요2','안녕하세요~',default,default,default
);

commit;
--챌린지
select * from challenge;

insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 출퇴근시 자가용 이용 안 하기', '오늘 자가용 대신에 대중교통/ 걷기/ 자전거로 출퇴근 하고 인증 사진을 제출해주세요.', 100,1
);

update challenge
set challenge_point = 700
where challenge_level = 3 and challenge_term_type='L';

insert into challenge
values (
seq_challenge_id.nextval, 'S', 1, '오늘 환경 보호 관련 SNS 콘텐츠 게시', '오늘 SNS에 환경 보호 관련 콘텐츠를 게시한 후 게시물을 캡쳐해주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 엘리베이터 대신 계단 이용하기', '오늘 엘리베이터 대신 계단 이용하고 계단과 신발이 보이도록 사진을 찍어 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 3, '오늘 업사이클링하기', '오늘 업사이클한 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 구매시 제로 웨이스트 매장에서 구매하기', '오늘 일반 매장 대신 제로 웨이스트 매장에서 구매하고 구매한 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 1, '오늘 통지서, 안내문 전자 문서로 수신 및 열람 신청하기', '오늘 종이 우편물을 줄이기 위해서 통지서, 안내문 전자 문서로 수신 및 열람 신청하고 신청한 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 1, '오늘 가전제품 및 전기용품 미 사용시 코드 빼기', '오늘 가전제품 및 전기 용품 미 사용시 코드를 빼고 인증 사진을 올려주세요.',100 ,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 1, '오늘 음식 남기지 않고 다 먹기', '오늘 음식을 남기지 않고 다 먹고 다 먹은 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 주문시 친환경 포장재 사용하는 곳에서 주문하기', '오늘 친환경 포장재 사용하는 곳에서 주문한 뒤 인증 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 3, '오늘 길거리 쓰레기 줍기', '오늘 길거리에 있는 쓰레기를 줍고 주운 쓰레기 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 포장시 개인용기 사용하기', '오늘 음식을 포장할 때 개인용기에 포장하고 인증 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 3, '환경 캠페인 집회 참여', '오늘 환경 캠페인 집회에 참여한 사진을 올려주세요.', 100,1
);
insert into challenge
values (
seq_challenge_id.nextval, 'S', 2, '오늘 메일함 비우기', '오늘 메일함을 비운 사진을 올려주세요.', 100,1
);

--기간 챌린지
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 일회용품 사용 최소한으로 하기',
'즉석밥 대신 밥솥으로 직접 밥을 해먹거나, 생수 주문시 빈 병을 회수하는 생수로 주문하는 등 일회용품을 최소한으로 사용하는 한달 챌린지', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 분리수거 제대로 하기',
'꼼꼼하게 분리수거를 하는 한 달 챌린지', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 3, '친환경 세제 만들어서 한 달 써보기', 
'직접 친환경 세제를 만들고 만든 세제를 사용하는 한 달 챌린지 ',500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 1, '한 달 동안 텀블러 챙기고 생활하기',
'외출할 때 텀블러를 챙기고  ',500 ,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 1, '한 달 동안 장바구니 챙기고 생활하기','temp',500 ,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 샴푸 대신 샴푸바 사용하기', 'temp', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 다회용 마스크 착용하기', 'temp', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 1, '한 달 동안 휴지 대신 손수건 들고 다니기', 'temp', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 에어컨 사용시 적정온도(26도) 유지하기', 'temp', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 낮 시간에는 조명 대신 자연광 이용하기', 'temp',500 ,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 3, '한 달 동안 배달음식 줄이기', 'temp',500 ,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 2, '한 달 동안 물티슈 사용 줄이기', 'temp', 500,30
);
insert into challenge
values (
seq_challenge_id.nextval, 'L', 1, '한 달 동안 핸드폰 덜 하기', 'temp', 500,30
);

