conn sys/a1234 as sysdba
연결성공
grant all on hr.mem_info to hr;

-- 권한부여

CREATE TABLE mem_info(
   id varchar2(20) primary key,
   password varchar2(20) not null,
   name varchar2(20) constraint name_uk unique,
   sex varchar2(4) not null,
   age number(3) not null,
   email varchar2(20),
   location varchar2(10),
   join_date DATE
);

-- 회원정보 mem_info

INSERT INTO mem_info (id, name, sex, age, email, location, join_date, password)
VALUES ('khacademy', '정우', '남', 26, 'jeongwo@naver.com', '수원', sysdate, 'khacademy');

select * from mem_info

CREATE TABLE game_room(
   gaming_room number(3) constraint gaming_room_pk primary key,
   room_password varchar2(20),
   room_max number(3) not null,
   room_min number(3) not null,
   room_chk char(3) not null,
   batting varchar2(50) not null
);

-- 게임방 정보 game_room

CREATE TABLE mem_money(
   id varchar2(20),
   user_money number(20) not null
);

alter table mem_money
add constraint money_id_fk foreign key(id) references mem_info(id);

-- 회원 보유금액 mem_money

CREATE TABLE win_lose(
   id varchar2(20),
   ranking number(20) constraint ranking_pk primary key,
   win_rate number(3) not null,
   win_count number(5) not null,
   lose_count number(5) not null
);

alter table win_lose
add constraint winlose_id_fk foreign key(id) references mem_info(id);

-- 회원 승패 win_lose

CREATE TABLE grade_info(
   id varchar2(20),
   rank_score number(20),
   grade varchar2(50),
   mem_value number(20) not null
);

alter table grade_info
add constraint grade_info_id_fk foreign key(id) references mem_info(id);

alter table grade_info
add constraint rank_score_fk  foreign key(rank_score) references win_lose(ranking);

alter table grade_info
add constraint grade_fk foreign key(grade) references grade(grade);


-- 회원 계급 grade_info

CREATE TABLE grade(
   grade varchar2(50) constraint grade_pk primary key,
   grade_num number(20)
);

-- 계급 grade