select * from a_search_team;

update a_search_team set a_read_count = a_read_count+1 where a_id = 184;

rollback;
commit;
select * from a_search_team;
select count(*) cnt from request_team where a_id = 184 ;
update a_search_team set a_like = a_like +1 where a_id = 184;
update a_search_team set a_like = a_like +1 where a_id = 184;

select a_id, count(request_id) cnt from request_team group by a_id;
select count(*) cnt from request_team where member_id = 'honggd';
select * from request_team;
select count(*) a_id from request_team where a_id = 263 group by a_id;
select * from (select (select count(*) from request_team where a_id = b.a_id) request_team_cnt left join a_search_team) s where a_id = 282;
select r.*, a.* from (select a_id, count(request_id) cnt from request_team where a_id= 263 group by a_id)r left join a_search_team a on r.a_id =a.a_id;
select r.*, a.* from (select a_id, count(request_id) cnt from request_team where a_id= 263 group by a_id)r left join a_search_team a on r.a_id =a.a_id;