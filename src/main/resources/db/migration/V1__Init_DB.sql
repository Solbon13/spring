create sequence role_sequence start 1 increment 1;
create sequence user_sequence start 1 increment 1;
create sequence hibernate_sequence start 5 increment 1;
create table departament
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(255),
    org_id        int8,
    primary key (id)
);
create table menu_site
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(255),
    org_id        int8,
    primary key (id)
);
create table organization
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(200),
    primary key (id)
);
create table person
(
    id             int8 not null,
    fast_name      varchar(255),
    last_name      varchar(255),
    middle_name    varchar(255),
    departament_id int8,
    position_id    int8,
    primary key (id)
);
create table position
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(255),
    primary key (id)
);
create table roles
(
    id            int8 not null DEFAULT nextval('role_sequence'),
    creation_date timestamp,
    deleted       boolean,
    name          varchar(20),
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
    id              int8 not null DEFAULT nextval('user_sequence'),
    creation_date   timestamp,
    deleted         boolean,
    activation_code varchar(120),
    active          boolean,
    email           varchar(50),
    password        varchar(120),
    username        varchar(20),
    primary key (id)
);
alter table if exists users add constraint users_username_u unique (username);
alter table if exists departament add constraint departament_organization_fk foreign key (org_id) references organization;
alter table if exists menuSite add constraint menu_organization_fk foreign key (org_id) references organization;
alter table if exists person add constraint person_departament_fk foreign key (departament_id) references departament;
alter table if exists person add constraint person_position_fk foreign key (position_id) references position;
alter table if exists person add constraint person_users_fk foreign key (id) references users;
alter table if exists user_roles add constraint user_roles_roles_fk foreign key (role_id) references roles;
alter table if exists user_roles add constraint user_roles_users_fk foreign key (user_id) references users;
