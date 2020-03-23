use abc;

drop table test_board;
drop table files;
drop table comments;
create table TEST_BOARD(
	IDX int auto_increment,
    BOARD_TYPE varchar(10) not null,
    TITLE varchar(30) not null,
    CONTENT varchar(500) not null,
	INSERT_DATE int,
    UPDATE_DATE int,
    COMMENT_SUM int default 0,
    WRITER varchar(10),
    HIT_NO int DEFAULT 0,
	JUNO int DEFAULT 0,
    FILE_IDX int,
    primary key (IDX)
)char set = utf8;
CREATE TABLE COMMENTS(
	IDX INT PRIMARY KEY auto_increment,
    PARENT INT DEFAULT 1,
    DEPTH INT DEFAULT 1,
    CONTENT TEXT NOT NULL,
    HIDDEN VARCHAR(2) DEFAULT 'X',
    DEL VARCHAR(2) DEFAULT 'X',
    SEQ int default 0,
    POST_IDX INT,
    foreign key (POST_IDX) references TEST_BOARD(IDX)
)CHAR SET = utf8;
create table FILES(
	IDX int,
    FILE_SIZE int,
    ORIGIN_NAME varchar(50),
    FILE_NAME varchar(50),
    foreign key(IDX) references TEST_BOARD(IDX)
)char set = utf8;
ALTER TABLE TEST_BOARD add constraint comment_idx foreign key(IDX) references COMMENT(POST_IDX);
ALTER TABLE FILES convert to charset utf8;
ALTER TABLE FILES MODIFY IDX int AUTO_INCREMENT;

insert into TEST_BOARD(BOARD_TYPE,TITLE,CONTENT,INSERT_DATE,WRITER,FILE_IDX) values('븐루3','제목3','내용3',unix_timestamp(),'저작3',1);
insert into TEST_BOARD(BOARD_TYPE,TITLE,CONTENT,INSERT_DATE,WRITER) values('븐루2','제목2','내용2',unix_timestamp(),'저작2');
insert into TEST_BOARD(BOARD_TYPE,TITLE,CONTENT,INSERT_DATE,WRITER) values('븐루1','제목1','내용1',unix_timestamp(),'저작1');
select *  from TEST_BOARD;

insert into comments values (default, 0, default, '으아기가아', default, default, default, 1);
insert into comments values (default, 0, default, '으겍', default, default, default, 1);
insert into comments values (default, 1, 3, '으겍', default, default, 1, 3);

select * from comments;

select IDX, CONTENT, PARENT, DEPTH, HIDDEN, DEL, SEQ from comments  where comments.POST_IDX = 1;

select unix_timestamp(INSERT_DATE) as 시간 from TEST_BOARD;
SELECT * FROM TEST_BOARD order by IDX desc limit 0,10;