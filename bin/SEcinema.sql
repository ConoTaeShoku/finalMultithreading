drop table sec_reserve;
drop table sec_movie;
drop sequence sec_movie_seq;

-- SE Cinema 테이블 구조 --

-- 영화 정보 테이블 생성 SQL문 작성
create table sec_movie (
	num number(11) constraint sec_movie_num_pk primary key,
	playdate date,
	title varchar2(50),
	price number(11) default 0
);

-- 영화 정보 일련번호 시퀀스 생성 SQL문 작성
create sequence sec_movie_seq;

-- 예매 정보 테이블 생성 SQL문 작성
create table sec_reserve (
	id varchar2(20) not null,
	num number(11),
	seatnum number(3),
	status varchar2(10)
);
alter table sec_reserve add constraint sec_reserve_num_fk foreign key(num) references sec_movie(num);
alter table sec_reserve add constraint sec_reserve_status_in check (status in('reserve','cancel'));

-- 영화정보 데이터
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-22', '스노우타임', 5000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-22', '판도라', 6000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-22', '라라랜드', 5000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-23', '형', 5000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-23', '실버벨', 7000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-23', '밀정', 5000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-24', '신비한 동물사전', 8000);
insert into sec_movie values (sec_movie_seq.nextval, '2016-12-24', '공조', 8000);

-- 예약 정보 데이터 예
insert into sec_reserve values ('aaa', 1, 1, 'reserve');


commit