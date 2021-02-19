--==========================================================
--관리자 계정
--==========================================================
--student 계정 생성 및 권한부여

--student 계정 생성
create user student
identified by student
default tablespace users;
--User STUDENT이(가) 생성되었습니다.

--student 권한부여
grant connect, resource to student;
--Grant을(를) 성공했습니다.


--==========================================================
--STUDENT 계정
--==========================================================

create table member(
    member_id varchar2(20),
    password varchar2(20) not null,
    member_name varchar2(100) not null,
    gender char(1),
    age number,
    email varchar2(200),
    phone char(11) not null,
    address varchar2(1000),
    hobby varchar2(100),            --농구,음악감상,영화
    enroll_date date default sysdate,
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in ('M', 'F'))
);

insert into member
values(
    'honggd', '1234', '홍길동', 'M', 33, 
    'honggd@naver.com', '01012341234',
    '서울 강남구 테헤란로', '등산,그림,요리', default
);
insert into member
values(
    'sinsa', '1234', '신사임당', 'F', 48, 
    'sinsa@naver.com', '01012344321',
    '서울 강동구', '음악감상', default
);
insert into member
values(
    'sejong', '1234', '세종', 'M', 76, 
    'sejong@naver.com', '01099998888',
    '서울 중구', '육식', default
);
insert into member
values(
    'leess', '1234', '이순신', 'M', 45, 
    'leess@naver.com', '01012121212',
    '전남 여수', '목공예', default
);
insert into member
values(
    'ygsgs', '1234', '유관순', 'F', null, 
    null, '01031313131',
    null, null, default
);

select * from member;

commit;


--삭제트리거 생성
	--resource 롤에 create trigger권한이 있기때문에 별도의 DCL없이 진행할 수 있음.
	create or replace trigger trig_delete_member
		before delete on member
		for each row
	begin
		insert into member_del
		values(:old.member_id, :old.password, :old.member_name, :old.gender, :old.age, :old.email, :old.phone, :old.address, :old.hobby, :old.enroll_date, sysdate);
	end;
	/
	--데이터확인
	select * from member_del;
    
    
    
------------------------------------------------------------
--최종 실습문제
------------------------------------------------------------


create table PRODUCT_STOCK (
    PRODUCT_ID varchar2(30) primary key,
    PRODUCT_NAME varchar2(30) not null,
    PRICE number(10) not null,
    description varchar2(50),
    STOCK number default 0
);

create table product_io (
    io_no number primary key, -- sequence처리할 것.
    product_id varchar2(30), --PRODUCT_STOCK테이블 PRODUCT_ID 참조
    iodate date default sysdate,
    amount number,
    status char(1),
    constraints ck_status check(status in ('I', 'O')),
    constraint fk_io_stock_prod_id foreign key(product_id)
                                                                 references product_stock(product_id)
                                                                 --상품정보를 삭제하면, 해당 입출고 데이터도 삭제되도록 처리
                                                                 on delete cascade
);

drop table product_io;



--시퀀스 생성
--drop sequence seq_product;

create sequence seq_product
    start with 1
    increment by 1;
    
insert into PRODUCT_STOCK values('nb_ss7', '삼성노트북', 1570000, '시리즈 7', 55);
insert into PRODUCT_STOCK values('nb_macbook_air', '맥북에어', 1200000, '애플 울트라북', 0);
insert into PRODUCT_STOCK values('pc_ibm', 'ibmPC', 750000, 'windows 8', 10);



--트리거를 이용한 상품 재고 관리
--입출고 데이터가 insert되면, 해당상품의 재고수량을 변경하는 트리거
CREATE OR REPLACE TRIGGER trg_prod
    BEFORE
    INSERT ON product_io --io테이블의 insert가 일어나면 트리거 작동
    FOR EACH ROW --매 행마다
BEGIN
--분기
    --입고
    IF :NEW.status = 'I' THEN --status가 'I'인 경우
      UPDATE product_stock --product테이블을 업데이트 할건데
      SET stock = stock + :NEW.amount -- amount만큼 플러스로 설정해라 
      WHERE product_stock.product_id = :NEW.product_id; 
    --출고
    ELSE --status가 'O'인 경우
      UPDATE product_stock 
      SET stock = stock - :NEW.amount --amount만큼 마이너스로 설정해라 
      WHERE product_stock.product_id = :NEW.product_id; 
    END IF;

END;
/


insert into PRODUCT_STOCK values('nb_ss7', '삼성노트북', 1570000, '시리즈 7', 55);
insert into PRODUCT_IO VALUES(SEQ_PRODUCT.nextval, 'nb_ss7', sysdate, 5, 'I');

select * from product_io;
select * from product_stock;

commit;

DESC PRODUCT_IO;

DESC PRODUCT_STOCK;