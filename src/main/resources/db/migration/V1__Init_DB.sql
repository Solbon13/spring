create sequence role_sequence start 1 increment 1;
create sequence user_sequence start 1 increment 1;
create sequence hibernate_sequence start 5 increment 1;
create table roles
(
    id   int8 not null DEFAULT nextval('role_sequence'),
    name varchar(20),
    primary key (id)
);
create table user_roles
(
    user_id int8 not null,
    role_id int8 not null,
    primary key (user_id, role_id)
);
create table users
(
    id       int8 not null DEFAULT nextval('user_sequence'),
    email    varchar(50),
    password varchar(120),
    username varchar(20),
    primary key (id)
);
alter table if exists users
    add constraint UKr43af9ap4edm43mmtq01oddj6 unique (username);
alter table if exists users
    add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table if exists user_roles
    add constraint FKh8ciramu9cc9q3qcqiv4ue8a6
    foreign key (role_id) references roles;
alter table if exists user_roles
    add constraint FKhfh9dx7w3ubf1co1vdev94g3f
    foreign key (user_id) references users;
