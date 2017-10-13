CREATE TABLE member(
	mem_code number primary key,
	mem_id varchar2(50) not null,
	mem_name varchar2(50) unique,
	mem_password number not null
);

CREATE sequence mem_code_seq
	start with 1
	increment by 1
	nocache
	nocycle;

INSERT INTO member(mem_code, mem_id, mem_name, mem_password)
VALUES(mem_code_seq.nextval, 'id', 'name', 'password');

SELECT *
FROM member;

DROP TABLE member;

CREATE TABLE winlose(
	mem_code number,
	mem_id varchar2(50),
	mem_wins number,
	mem_loses number,
	constraint mem_code_fk foreign key(mem_code) references member(mem_code)
);