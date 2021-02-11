create table captcha_codes
(
    id          integer     not null auto_increment,
    code        TINYTEXT    not null,
    secret_code TINYTEXT    not null,
    time        datetime(6) not null,
    primary key (id)
) engine = InnoDB;

create table global_settings
(
    id    integer      not null auto_increment,
    code  varchar(255) not null,
    name  varchar(255) not null,
    value varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table hibernate_sequence
(
    next_val bigint not null
) engine = InnoDB;

create table post_comments
(
    id        integer     not null auto_increment,
    parent_id integer,
    text      TEXT        not null,
    time      datetime(6) not null,
    post_id   integer     not null,
    user_id   integer     not null,
    primary key (id)
) engine = InnoDB;

create table posts
(
    id                integer      not null auto_increment,
    is_active         tinyint      not null,
    moderation_status varchar(255) not null,
    text              TEXT         not null,
    time              datetime(6)  not null,
    title             varchar(255) not null,
    view_count        integer      not null,
    moderator_id      integer,
    user_id           integer      not null,
    primary key (id)
) engine = InnoDB;

create table post_votes
(
    id      integer     not null auto_increment,
    time    datetime(6) not null,
    value   tinyint     not null,
    post_id integer     not null,
    user_id integer     not null,
    primary key (id)
) engine = InnoDB;

create table tag2post
(
    id      integer not null auto_increment,
    post_id integer not null,
    tag_id  integer not null,
    primary key (id)
) engine = InnoDB;

create table tags
(
    id    integer      not null auto_increment,
    title varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table users
(
    id           integer      not null auto_increment,
    code         varchar(255),
    email        varchar(255) not null,
    is_moderator tinyint      not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    photo        TEXT,
    reg_time     datetime(6)  not null,
    primary key (id)
) engine = InnoDB;

create table roles
(
    id   integer     not null auto_increment,
    name varchar(20) not null,
    primary key (id)
) engine = InnoDB;

create table users_roles
(
    id      integer not null auto_increment,
    user_id integer not null,
    role_id integer not null,
    primary key (id)
) engine = InnoDB;

alter table users_roles
    add constraint FKj6m8fwv7oqv74fcehir1a9ffy
        foreign key (role_id) references roles (id);

alter table users_roles
    add constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id) references users (id);

alter table post_comments
    add constraint comment2post_fk
        foreign key (post_id) references posts (id);

alter table post_comments
    add constraint comment2user_fk
        foreign key (user_id) references users (id);

alter table posts
    add constraint post2moder_fk
        foreign key (moderator_id) references users (id);

alter table posts
    add constraint post2user_fk
        foreign key (user_id) references users (id);

alter table post_votes
    add constraint vote2post_fk
        foreign key (post_id) references posts (id);

alter table post_votes
    add constraint vote2user_fk
        foreign key (user_id) references users (id);

alter table tag2post
    add constraint tag2post_fk
        foreign key (post_id) references posts (id);

alter table tag2post
    add constraint tag2user_fk
        foreign key (tag_id) references tags (id);
