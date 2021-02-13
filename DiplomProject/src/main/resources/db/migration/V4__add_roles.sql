insert into roles (id, name) VALUES (1, 'USER');
insert into roles (id, name) VALUES (2, 'MODERATOR');

insert into users_roles (user_id, role_id) VALUES (1, 1);
insert into users_roles (user_id, role_id) VALUES (1, 2);
insert into users_roles (user_id, role_id) VALUES (2, 1);