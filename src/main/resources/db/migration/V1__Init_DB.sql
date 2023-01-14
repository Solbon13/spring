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
    first_name     varchar(255),
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
create table post
(
    id             int8 not null,
    creation_date  timestamp,
    deleted        boolean,
    description    varchar(255),
    title          varchar(255),
    departament_id int8,
    menu_site_id   int8,
    person_id      int8,
    primary key (id)
);
create table post_likes
(
    post_id   int8 not null,
    person_id int8 not null,
    primary key (post_id, person_id)
);
create table post_file
(
    id              int8 not null,
    creation_date   timestamp,
    deleted         boolean,
    filename_client varchar(255),
    filename_server varchar(255),
    post_id         int8,
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
create table status_task
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(255),
    primary key (id)
);
create table task
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    deadline      timestamp,
    description   varchar(255),
    phone         varchar(255),
    title         varchar(255),
    person_id     int8,
    type_task_id  int8,
    primary key (id)
);
create table task_executor
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    person_id     int8,
    status_id     int8,
    task_id       int8,
    primary key (id)
);
create table task_file
(
    id              int8 not null,
    creation_date   timestamp,
    deleted         boolean,
    filename_client varchar(255),
    filename_server varchar(255),
    task_id         int8,
    primary key (id)
);
create table type_task
(
    id            int8 not null,
    creation_date timestamp,
    deleted       boolean,
    name          varchar(255),
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
alter table if exists menu_site add constraint menu_organization_fk foreign key (org_id) references organization;
alter table if exists person add constraint person_departament_fk foreign key (departament_id) references departament;
alter table if exists person add constraint person_position_fk foreign key (position_id) references position;
alter table if exists person add constraint person_users_fk foreign key (id) references users;
alter table if exists post add constraint post_departament_fk foreign key (departament_id) references departament;
alter table if exists post add constraint post_menu_fk foreign key (menu_site_id) references menu_site;
alter table if exists post add constraint post_person_fk foreign key (person_id) references person;
alter table if exists post_likes add constraint post_likes_person_fk foreign key (person_id) references person;
alter table if exists post_likes add constraint post_likes_post_fk foreign key (post_id) references post;
alter table if exists post_file add constraint post_file_post_fk foreign key (post_id) references post;
alter table if exists task add constraint task_person_fk foreign key (person_id) references person;
alter table if exists task add constraint task_type_tasl_fk foreign key (type_task_id) references type_task;
alter table if exists task_executor add constraint task_executor_person_fk foreign key (person_id) references person;
alter table if exists task_executor add constraint task_executor_status_task_fk foreign key (status_id) references status_task;
alter table if exists task_executor add constraint task_executor_task_fk foreign key (task_id) references task;
alter table if exists task_file add constraint task_file_task foreign key (task_id) references task;
alter table if exists user_roles add constraint user_roles_roles_fk foreign key (role_id) references roles;
alter table if exists user_roles add constraint user_roles_users_fk foreign key (user_id) references users;


