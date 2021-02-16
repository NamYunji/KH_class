-- 한줄주석
/*
여러줄 주석
*/
--====================================
--헤더부
--====================================

--tip. 더블클릭 : 전체화면
--tip. Alt + '(홑따음표) - 여러 타입의 대소문자로 변경


--====================================
-- system 관리자 계정
--====================================

--DB쪽으로 어떤 계정을 접속할지 요청을 보냄
--어떤 계정으로 접속되어있는지 보여줌
SHOW USER;


--현재 등록된 사용자목록 조회
--실행하는 것은 한번씩 사용하고 날리는 것
--명령어 전달 -> 결과받아오기 -> 끝
--sys 슈퍼관리자 : db생성/삭제 권한 有
--system 일반관리자 : db생성/삭제 권한 无
--알맞은 관리자를 사용하는 것이 중요, 우리는 일반관리자를 사용할 것

SELECT *
FROM dba_users;


--sql문 / query : 대소문자 구분하지 않음
--<Alt + 홑따음표> -> 여러 타입으로 대소문자 변경가능
--내가 설정해둔 타입 : 예약어 : 대문자, 이외 요소 : 소문자
--대소문자 구분의 두가지 경우
--1. 사용자계정의 비밀번호
--2. 테이블 내의 데이터

--관리자는 관리자대로 두고, 일반사용자 계정을 만들어서 사용할 것!

--관리자는 일반사용자 생성 가능
--but 일반사용자는 사용자 생성 불가

--일반사용자 생성
CREATE USER kh 
--비밀번호 (대소문자 구분, kh와 KH는 다름)
IDENTIFIED BY kh
--실제 데이터가 저장될 영역 지정 (system 시스템 영역 | users 일반 사용자 영역)
DEFAULT TABLESPACE USERS; --users 쪽으로 데이터를 저장

--한번 만들고 나면, 또 같은 사용자 생성을 실행할 수 없음
--user name 'KH' conflicts with another user or role name (kh 유저명이 다른 유저명과 충돌남)
--There is already a user or role with that name. (이미 그 이름을 가진 유저가 있음)
--why? 명령어 한번 전달 -> 결과 받아옴 -> 끝

--잘못 실행하였을 경우, 사용자 삭제
--drop user kh;
--추후 혹시라도 실행될 경우를 위해, 사용 후에는 주석처리 필요
--데이터베이스 스키마 서비스 접속

--테이블은 무조건 소유주가 있음, 주인없는 테이블 존재불가
--사용자를 삭제하면, 안에 있는 table들을 모두 삭제하는 것!
--그러면 각 테이블 안의 데이터들이 다 날아감! 주의!

--접속권한 create session이 포함된 role(권한묶음) connect 부여
--grant connect to kh;
--테이블等 객체 생성권한이 포함된 role resource 부여
--grant resource to kh;
--if 테이블 생성 권한만 주고 싶다면 (권한 줄 대상만 작성해주면 됨)
--grant create table to kh;

--connect와 resource 권한 한번에 부여하기
--grant connect resource to kh;
GRANT CONNECT,RESOURCE TO kh;

--권한 삭제
--REVOKE CONNECT,RESOURCE FROM kh;


--chun계정 생성
CREATE USER chun
IDENTIFIED BY chun
DEFAULT TABLESPACE USERS;

--connect, resource 부여
GRANT CONNECT, RESOURCE TO chun;

-- role(권한 묶음)에 포함된 권한 확인
--DataDictionary : db의 각 객체에 대한 메타정보를 확인할 수 있는 read-only 테이블
SELECT *
FROM dba_sys_privs
WHERE grantee IN ('CONNECT', 'RESOURCE');




