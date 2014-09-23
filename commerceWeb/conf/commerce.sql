create database commerce if not exists;

use commerce;

create table if not exists user(
	id integer auto_increment primary key,
	userName varchar(50) not null,
	nickName varchar(20) not null,
  	password varchar(20) not null,
  	mobilePhone char(11) not null,
  	email varchar(50) not null,
  	address varchar(200) not null,
  	gmtCreated datetime not null default current_timestamp,
  	gmtModified datetime not null default current_timestamp on update current_timestamp,
  	key idx_userName(userName),
  	key idx_nickName(nickName)
)engine=innodb  default charset=utf8;

create table if not exists merchant(
	id integer auto_increment primary key,
	name varchar(128) not null comment '商户名称',
	nickName varchar(20) not null,
	password varchar(20) not null,
	mobilePhone char(11) not null,
  	email varchar(50) not null,
	address varchar(200) not null,
	identification char(18) not null commment '身份证号码',
	description varchar(512) not null comment '商家描述',
	gmtCreated datetime not null default current_timestamp,
  	gmtModified datetime not null default current_timestamp on update current_timestamp,
  	key idx_name(name),
  	key idx_nickName(nickName)
)engine=innodb  default charset=utf8;

create table if not exists commodity(
	id integer auto_increment primary key,
	merchantId integer not null comment '商户ID',
	name varchar(64) not null comment '商品名称',
	description varchar(256) not null comment '商品描述',
	price decimal(10,2) not null comment '商品价格',
	gmtCreated datetime not null default current_timestamp,
  	gmtModified datetime not null default current_timestamp on update current_timestamp,
  	key idx_merchantId(merchantId)
)engine=innodb  default charset=utf8;

create table if not exists commodity_photo(
	id integer auto_increment primary key,
	commodityId integer not null comment '商品id',
	photoUrl varchar(128) not null comment '商品图片',
	photoDesc varchar(256) not null comment '图片描述',
	gmtCreated datetime not null default current_timestamp,
  	gmtModified datetime not null default current_timestamp on update current_timestamp,
  	key idx_commodityId(commodityId)
)engine=innodb  default charset=utf8;

create table if not exists commodity_comment(
	id integer auto_increment primary key,
	nickName varchar(20) not null comment '用户别名',	
	commodityId integer not null comment '商品id',
	comment varchar(256) not null comment '评论',
	gmtCreated datetime not null default current_timestamp,
  	gmtModified datetime not null default current_timestamp on update current_timestamp,
  	key idx_nickName(nickName),
  	key idx_commodityId(commodityId)
)engine=innodb  default charset=utf8;

