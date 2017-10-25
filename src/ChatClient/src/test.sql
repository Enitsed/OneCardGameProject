conn sys/a1234 as sysdba
연결성공
grant all on hr.mem_info to hr;

-- 권한부여

CREATE TABLE mem_info(
   id varchar2(20) primary key,
   password varchar2(20) not null,
   name varchar2(20),
   sex varchar2(4),
   age number(3),
   email varchar2(20),
   location varchar2(10),
   join_date DATE
);

-- 회원정보 mem_info
CREATE TABLE win_lose(
   id varchar2(20) not null,
   win_rate number(3),
   win_count number(5),
   lose_count number(5)
);

select * from mem_info
select * from win_lose
select * from grade_info
-- 회원 승패 win_lose

CREATE TABLE grade_info(
   id varchar2(20) not null,
   grade varchar2(50),
   rank_score number(20),
   ranking number(20)
);
select MEM_INFO;

-- 회원 등급 grade_info

drop table grade_info;
drop table win_lose;
drop table mem_info;


alter table win_lose
add constraint win_lose_id_fk foreign key(id) references mem_info(id);

alter table grade_info
add constraint grade_info_id_fk foreign key(id) references mem_info(id);


