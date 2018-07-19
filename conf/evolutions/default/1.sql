# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            bigint auto_increment not null,
  email                         varchar(255),
  password                      varchar(255),
  last_logined_date             datetime(6),
  created_date                  datetime(6) not null,
  last_updated_date             datetime(6) not null,
  constraint pk_user primary key (id)
);

create table user_detail (
  id                            bigint auto_increment not null,
  email                         varchar(255),
  password                      varchar(255),
  last_logined_date             datetime(6),
  created_date                  datetime(6) not null,
  last_updated_date             datetime(6) not null,
  constraint pk_user_detail primary key (id)
);


# --- !Downs

drop table if exists user;

drop table if exists user_detail;

