create table categories
(
    id   integer not null auto_increment,
    name varchar(255),
    primary key (id)
) engine = InnoDB;

create table news
(
    id               integer not null auto_increment,
    publication_date datetime(6),
    text             varchar(255),
    title            varchar(255),
    category_id      integer,
    primary key (id)
) engine = InnoDB;

alter table news
    add constraint FK6itmfjj4ma8lfpj10jx24mhvx foreign key (category_id) references categories (id);