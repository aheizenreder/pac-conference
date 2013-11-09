-- database script for creation tables
-- for location domain in pac-conference application
-- author: Andreas Heizenreder (andreas.heizenreder@prodyna.com)

-- create database pac-conference if not exists
create database if not exists pac_conference;

-- select database
use pac_conference;

-- location table
create table location_location
(
	id bigint not null auto_increment,
	name varchar (50) not null,
	description varchar(256),
	street varchar (50) not null,
	house_number varchar (10) not null,
	city varchar (50) not null,
	postalcode varchar (10) not null,
	country varchar (50),
	
	constraint location_location_pk primary key (id),
	     index location_location_pk_idx (id)
) ENGINE=INNODB;

-- room table
create table location_room
(
	location_id bigint not null,
	id integer not null auto_increment,
	name varchar(50) not null,
	description varchar(256),
	capacity integer not null,
	
	constraint location_room_pk primary key (id),
	constraint location_room_fk foreign key (location_id)
								references location_location(id)
								on delete no action
								on update no action,
		 index location_room_pk_idx (location_id, id)
) ENGINE=INNODB;