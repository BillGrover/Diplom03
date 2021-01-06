/********* Тестовый юзер-модератор, его пост и тег *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (1, 'First_User', 'email@mail.ru', 1, '123', CURRENT_DATE);

insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    VALUES (1, 1, 'ACCEPTED', 'Это текст тестового поста', CURRENT_TIME, 'Заголовок тестового поста', 0, NULL, 1);

insert into tags (id, title) VALUES (1, 'test');

/********* Второй тестовый юзер и его коммент *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (2, 'Second_User', 'email@mail.ru', 0, '123', CURRENT_DATE);

insert into post_comments (id, parent_id, text, time, post_id, user_id)
    VALUES (1, NULL, 'тестовый коммент', CURRENT_TIME, 1, 2);

insert into tag2post (id, post_id, tag_id) VALUES (1, 1, 1);