conn sys/a1234 as sysdba
연결성공
grant all on hr.mem_info to hr;

-- 권한부여

/*
 * Created 2017-10-13
 * Modified 2017-10-25
 * Project 원카드 게임
 * Model onecard database
 * Author 정슬기, 최유림
 * Version 1.0
 * Database oracle 10g
 */

DROP TABLE grade_info;
DROP TABLE win_lose;
DROP TABLE mem_info;

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

-- 회원 승패 win_lose

CREATE TABLE grade_info(
   id varchar2(20) not null,
   grade varchar2(50),
   rank_score number(20),
   ranking number(20)
);

-- 회원 등급 grade_info

alter table win_lose
add constraint win_lose_id_fk foreign key(id) references mem_info(id);

alter table grade_info
add constraint grade_info_id_fk foreign key(id) references mem_info(id);


/* 회원 */
INSERT INTO mem_info (id, name, sex, age, email, location, join_date, password) 
VALUES ('Honggildong', '홍길동', '남', 20,'gildong@naver.com', '경기', sysdate, 'gildong12');

/* 승패 */
INSERT INTO win_lose (id, win_rate, win_count, lose_count)
VALUES ('Honggildong', 0, 0, 0);

/* 랭킹 및 등급 */
INSERT INTO grade_info (id, grade, rank_score, ranking)
VALUES ('Honggildong', '짐승', 0, 0);


UPDATE win_lose SET win_rate = win_count / (win_count + lose_count) * 100;

select * from win_lose;
