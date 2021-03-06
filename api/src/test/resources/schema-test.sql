create table flyway_schema_history (
                                       installed_rank int not null,
                                       version varchar(50) null,
                                       description varchar(200) not null,
                                       type varchar(20) not null,
                                       script varchar(1000) not null,
                                       checksum int null,
                                       installed_by varchar(100) not null,
                                       installed_on timestamp not null default current_timestamp,
                                       execution_time int not null,
                                       success tinyint not null,
                                       primary key (installed_rank)
);
create index flyway_schema_history_s_idx on flyway_schema_history(success);
create table hibernate_sequence (
    next_val bigint null
);
create table users (
                       id bigint not null,
                       name varchar(50) not null,
                       email varchar(50) not null,
                       created_date timestamp not null default current_timestamp,
                       primary key (id),
                       constraint email
                           unique (email)
);
create table giftees (
                         id bigint not null generated by default as identity,
                         name varchar(50) not null,
                         created_date timestamp not null default current_timestamp,
                         user_id bigint not null,
                         primary key (id),
                         constraint user_fk
                             foreign key (user_id)
                                 references users (id) on delete cascade
);
create table occasions (
                           id bigint not null generated by default as identity,
                           name varchar(50) not null,
                           date date null,
                           created_date timestamp not null default current_timestamp,
                           modified_date timestamp not null default '0000-00-00 00:00:00',
                           giftee_id bigint not null,
                           primary key (id),
                           constraint giftee_fk
                               foreign key (giftee_id)
                                   references giftees (id) on delete cascade
);
create table gifts (
                       id bigint not null generated by default as identity,
                       name varchar(50) not null,
                       description clob null,
                       created_date timestamp not null default current_timestamp,
                       modified_date timestamp not null default '0000-00-00 00:00:00',
                       occasion_id bigint not null,
                       primary key (id),
                       constraint occasion_fk
                           foreign key (occasion_id)
                               references occasions (id) on delete cascade
);