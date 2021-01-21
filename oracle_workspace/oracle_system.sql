--====================================
-- system 관리자 계정
--====================================
--주석
/*
여러줄 주석
*/
show user;

--현재 등록된 사용자목록 조회
--실행하는 것은 한번씩 사용하고 날리는 것
--명령어 전달 -> 결과받아오기 -> 끝
--sys 슈퍼관리자 : db생성/삭제 권한 有
--system 일반관리자 : db생성/삭제 권한 无
--알맞은 관리자를 사용하는 것이 중요, 우리는 일반관리자를 사용할 것

SELECT*
FROM dba_users;

--sql문 /query : 대소문자 구분하지 않음
--alt + 홑따음표
--더블클릭 : 전체화면
--대소문자 구분의 두가지 경우
--1.사용자계정의 비밀번호 2.테이블내의 데이터


--관리자는 일반사용자 생성가능
create user kh
identified by kh --비밀번호 (대소문자 구분)
default tablespace users;--데이터가 저장될 영역 (system|users)
--한번 만들고 나면, 또 실행할 수 없음

--사용자 삭제
--drop user kh;

create user kh
identified by kh 
default tablespace users;

--테이블은 무조건 소유주가 있음, 주인없는 테이블 존재불가
--사용자를 삭제하면, 안에 있는 table들을 모두 삭제하는 것!
--그러면 각 테이블 안의 데이터들이 다 날아감! 주의!

--접속권한 create session이 포함된 role(권한묶음) connect 부여
--grant connect to kh;
--테이블等 객체 생성권한이 포함된 role resource 부여
--grant resource to kh;
--한번에 부여하기
--테이블 생성 권한만 주고 싶다면 (권한 줄 대상만 작성해주면 됨)
--grant create table to kh;

--grant connect resource to kh;
grant connect,resource to kh;


