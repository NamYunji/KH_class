select * from a_search_team;

update a_search_team set a_read_count = a_read_count+1 where a_id = 184;

rollback;
commit;
select * from a_search_team;
select count(*) cnt from request_team where a_id = 184 ;
update a_search_team set a_like = a_like +1 where a_id = 184;
update a_search_team set a_like = a_like +1 where a_id = 184;

select * from (select row_number() over(order by b.a_id desc) rnum, b.*,  a.attachment_id, a.original_filename, a.renamed_filename, a.attachment_status from a_team b left join a_team_attachment a on b.a_id = a.team_a_id and a.attachment_status = 'Y') b where rnum between 1 and 5;

delete from team_comment where comment_id = 3;
select * from (select row_number() over(order by b.team_a_id desc) rnum, b.*,  a.attachment_id, a.original_filename, a.renamed_filename, a.attachment_status from a_team b left join a_team_attachment a on b.a_id = a.team_a_id and a.attachment_status = 'Y') b where rnum between 1 and 5;
select * from team_comment;
commit;
select * from a_team;
select * from a_team_attachment;
select count(*) cnt from a_team;
select * from request_team;
select* from member;
select * from a_team_attachment where team_a_id = 70;
select * from a_team where team_a_id = 65;
delete from a_team where team_a_id = 65;
commit;
update a_team set a_title = 'ㅎㅇ', a_content = 'ㅎㅇ' where team_a_id = 65;

select * from personal_point;

select * from challenge_join;

select * from challenge;

commit;

select * from personal_point;

insert into personal_point values (100, 41, 'honggd', 50, sysdate);
insert into personal_point values (101, 42, 'ggggg', 100, sysdate);
insert into personal_point values (102, 43, 'aaaaa', 150, sysdate);
insert into personal_point values (103, 44, 'bbbbb', 200, sysdate);
insert into personal_point values (104, 45, 'ccccc', 300, sysdate);
insert into personal_point values (105, 46, 'ddddd', 50, sysdate);
insert into personal_point values (106, 47, 'ddddd', 50, sysdate);


select rownum, personal_point.*
from (
    select *
    from personal_point
    order by point desc)
where rownum between 1 and 5;

    select *
    from personal_point
    order by point desc;

-- 달
select p_point_id, member_id, challenge_id, point, extract(month from point_date)month from personal_point;
select p_point_id, member_id, challenge_id, point, extract(month from point_date)month from personal_point group by member_id;


declare v_member_id varchar2(500);
                v_month number;
begin
    select member_id, extract(month from point_date), sum(point) sum
    into v_member_id, v_month, v_sum
    from personal_point 
    group by member_id, extract(month from point_date);
end;
/



create or replace procedure ranking_personal_point_2
is
v_member_id personal_point.member_id%type;
v_month number;
v_sum number;
begin
    select member_id, extract(month from point_date), sum(point)
    into v_member_id
    from personal_point 
    group by member_id, extract(month from point_date)
    order by sum desc;
end;
/

set serveroutput on;

  
    select member_id, sum(point) sum
    from personal_point 
    group by member_id, extract(month from point_date), extract(year from point_date)
    having extract(month from point_date) = extract(month from sysdate) and extract(year from point_date) = extract(year from sysdate)
    order by sum desc;

 
EXEC proc_personal;

SET SERVEROUTPUT ON;

select * from challenge_join;
 insert into challenge_join values ('honggd', 41, sysdate, sysdate);
 insert into challenge_join values ('ggggg', 42, sysdate, sysdate);
 insert into challenge_join values ('aaaaa', 43, sysdate, sysdate);
 insert into challenge_join values ('bbbbb', 44, sysdate, sysdate);
 insert into challenge_join values ('ccccc', 45, sysdate, sysdate);
 insert into challenge_join values ('ddddd', 46, sysdate, sysdate);
 insert into challenge_join values ('ddddd', 47, sysdate, sysdate);
 
 set serveroutput on;
 exec proc_ranking;
 
 create or replace procedure proc_ranking
is
    --변수선언
        ranking_m_id personal_point.member_id%TYPE;
        ranking_point personal_point.point%TYPE;
        
    cursor c_name is
        select member_id, sum(point) sum
        from personal_point
        group by member_id, extract(month from point_date), extract(year from point_date)
        having extract(month from point_date) = extract(month from sysdate) and extract(year from point_date) = extract(year from sysdate)
        order by sum desc;

begin
    open c_name;
    loop
        fetch c_name into ranking_m_id, ranking_point;
        exit when c_name%notfound;
        
        dbms_output.put_line(ranking_m_id || ' ' || ranking_point);
    end loop;
    commit;
end;
/
 

select s_team_count from a_search_team where a_id = 184;

select s_team_count from a_search_team where a_id = 184;

select * from member;


select * from user_jobs;



BEGIN
DBMS_SCHEDULER.CREATE_PROGRAM(
program_name => 'RANKING_PROGRAM',
program_action => 'RANKING_SELECT',
program_type => 'STORED_PROCEDURE',
comments => 'Service desk stats main batch program',
enabled => TRUE);
END;


BEGIN
    DBMS_SCHEDULER.CREATE_SCHEDULE (
        repeat_interval  => 'FREQ=MONTHLY;BYMONTHDAY=-1,1',
        start_date => TO_TIMESTAMP_TZ('2021-04-26 12:16:58.000000000 ASIA/SEOUL','YYYY-MM-DD HH24:MI:SS.FF TZR'),
        end_date        => null,
        schedule_name  => '"SCHEDULE_MONTHLY_RANKING"');
END;

BEGIN
DBMS_SCHEDULER.CREATE_JOB (
job_name =>'JOB_MONTHLY_RANKING',
program_name =>'RANKING_PROGRAM',
schedule_name =>'SCHEDULE_MONTHLY_RANKING',
enabled =>TRUE);
END;
/




select * from team_comment;
insert into team_comment(comment_id, team_a_id, member_id, comment_content, comment_level, comment_ref) values(seq_a_comment_id.nextval, ?, ?, ?, ?, ?)

insert into team_comment(comment_id, team_a_id, member_id, comment_content, comment_level, comment_ref)
values(seq_a_comment_id.nextval, 127, 'honggd', '할룽', 2, 2);

select tc.* from team_comment tc where team_a_id = ? start with comment_level = 1 connect by prior comment_id = comment_ref order siblings by comment_reg_date asc


select * from (select row_number() over(order by b.a_id desc) rnum, b.*,  a.attachment_id, a.original_filename, a.renamed_filename, a.attachment_status from a_team b left join a_team_attachment a on b.a_id = a.team_a_id and a.attachment_status = 'Y') b where rnum between 1 and 5;

update a_team set a_like = a_like +1 where team_a_id = 43;

select * from a_team_attachment;


commit;
update a_team_attachment set attachment_status = 'N' where attachment_id = 18;

insert into a_team_attachment(attachment_id, team_a_id, original_filename, renamed_filename) values (seq_team_attachment_id.nextVal, ?, ?, ?)

select * from a_team where team_a_id = 43;

select * from request_team where member_id = 'honggd';

select * from a_search_team where member_id = 'qwerty';

select seq_a_team_id.currval a_id from dual;


insert into a_team_attachment(attachment_id, team_a_id, original_filename, renamed_filename) values (seq_team_attachment_id.nextVal, ?, ?, ?);
insert into a_team_attachment(attachment_id, team_a_id, original_filename, renamed_filename) values (seq_team_attachment_id.nextVal, 43, 'gd', 'gd');



insert into a_team(team_a_id, member_id, a_id, a_title, a_content, a_read_count, a_like)values (seq_a_team_id.nextVal, ?, ?, ?, ?, 0, 0);

insert into a_team(team_a_id, member_id, a_id, a_title, a_content, a_read_count, a_like)values (seq_a_team_id.nextVal, 'honggd', 43, 'gd', 'gd', 0, 0);

--참여하고 있는 챌린지(21/4/21)
CREATE TABLE challenge_join (
    member_id varchar2(4000) NOT NULL,
    challenge_id number NOT NULL,
    confirm_date date DEFAULT sysdate   ,
    end_date date DEFAULT sysdate NOT NULL, -- sysdate : 오늘 챌린지 / sysdate + 29 : 기간 챌린지 /  팀 시작일 + 29 : 팀 챌린지
    
    constraint pk_challenge_join primary key(member_id, challenge_id),
    constraint fk_challenge_join_member_id foreign key(member_id) references member(member_id) on delete cascade, -- 사용자 삭제되면 같이 삭제
    constraint fk_challenge_join_challenge_id foreign key(challenge_id) references challenge(challenge_id) on delete cascade -- 챌린지 삭제되면 같이 삭제
);

select * from challenge_join;










select * from request_team;
select count(*) a_id from request_team where a_id = 263 group by a_id;
select * from (select (select count(*) from request_team where a_id = b.a_id) request_team_cnt left join a_search_team) s where a_id = 282;
select r.*, a.* from (select a_id, count(request_id) cnt from request_team where a_id= 263 group by a_id)r left join a_search_team a on r.a_id =a.a_id;
select r.*, a.* from (select a_id, count(request_id) cnt from request_team where a_id= 263 group by a_id)r left join a_search_team a on r.a_id =a.a_id;












commit;

















;


BEGIN
DBMS_SCHEDULER.CREATE_PROGRAM(
program_name => 'RANKING_PROGRAM',
program_action => 'RANKING_SELECT',
program_type => 'STORED_PROCEDURE',
comments => 'Service desk stats main batch program',
enabled => TRUE);
END;
/

BEGIN
    DBMS_SCHEDULER.CREATE_SCHEDULE (
        repeat_interval  => 'FREQ=MONTHLY;BYMONTHDAY=-1,1',
        start_date => TO_TIMESTAMP_TZ('2021-04-26 12:16:58.000000000 ASIA/SEOUL','YYYY-MM-DD HH24:MI:SS.FF TZR'),
        end_date        => null,
        schedule_name  => '"SCHEDULE_MONTHLY_RANKING"');
END;
/

BEGIN
DBMS_SCHEDULER.CREATE_JOB (
job_name =>'JOB_MONTHLY_RANKING',
program_name =>'RANKING_PROGRAM',
schedule_name =>'SCHEDULE_MONTHLY_RANKING',
enabled =>TRUE);
END;
/
set serveroutput on;

create or replace procedure ranking_select
is
    --변수선언
        ranking_m_id personal_point.member_id%TYPE;
        ranking_point personal_point.point%TYPE;
        
    cursor c_name is
        select member_id, sum(point) sum
        from personal_point
        group by member_id, extract(month from point_date), extract(year from point_date)
        having extract(month from point_date) = extract(month from sysdate) and extract(year from point_date) = extract(year from sysdate)
        order by sum desc;

begin
    open c_name;
    loop
        fetch c_name into ranking_m_id, ranking_point;
        exit when c_name%notfound;
        
        dbms_output.put_line(ranking_m_id || ' ' || ranking_point);
    end loop;
    commit;
end;
/

exec ranking_select;

commit;







