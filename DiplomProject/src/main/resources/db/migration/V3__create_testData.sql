/********* Тестовый юзер-модератор, его пост и тег *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (1, 'First_User', 'email@mail.ru', 1, '123', CURRENT_DATE);

insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    VALUES (1, 1, 'ACCEPTED', 'Это <h3>текст</h3> тестового <b>поста</b>', CURRENT_DATE, 'Заголовок тестового поста', 0, NULL, 1);

insert into tags (id, title) VALUES (1, 'test');

/********* Второй тестовый юзер и его коммент *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (2, 'Second_User', 'email@mail.ru', 0, '123', CURRENT_DATE);

insert into post_comments (id, parent_id, text, time, post_id, user_id)
    VALUES (1, NULL, 'тестовый коммент', CURRENT_TIME, 1, 2);

insert into tag2post (id, post_id, tag_id) VALUES (1, 1, 1);

/********* НЕАКТИВНЫЙ ПОСТ (is_active = 0) *********/
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    VALUES (3, 0, 'ACCEPTED', 'Этот пост неактивен', CURRENT_TIME, 'Заголовок неактивного поста', 0, NULL, 1);

/********* НЕДОПУЩЕННЫЙ ПОСТ (moderationStatus = NEW) *********/
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    VALUES (4, 1, 'NEW', 'Этот пост недопущен', CURRENT_TIME, 'Заголовок недопущенного поста', 0, NULL, 1);

/********* ПОСТ С ОТЛОЖЕННОЙ ДАТОЙ ПУБЛИКАЦИИ *********/
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
    VALUES (5, 1, 'ACCEPTED', 'Этот пост с датой публикации 2025 год', '2028/03/14', 'Заголовок отложенного поста', 0, NULL, 1);

/********* СТАРИННЫЙ ПОСТ *********/
insert into posts (id, is_active, moderation_status, text, time, title, view_count, moderator_id, user_id)
VALUES (2, 1, 'ACCEPTED', 'Этот пост с датой публикации 1988 год', '1988/03/14', 'Заголовок старинного поста', 0, NULL, 1);

/********* ЛАЙКИ: пост id=2 - 2 лайка; пост id=1 - 1 лайк *********/
insert into post_votes (id, time, value, post_id, user_id) VALUES (1, CURRENT_DATE, 1, 2, 1);
insert into post_votes (id, time, value, post_id, user_id) VALUES (2, CURRENT_DATE, 1, 2, 2);
insert into post_votes (id, time, value, post_id, user_id) VALUES (3, CURRENT_DATE, 1, 1, 1);

/********* Ещё комменты *********/
insert into post_comments (id, parent_id, text, time, post_id, user_id)
VALUES (2, NULL, 'тестовый коммент 2', CURRENT_TIME, 1, 2);

insert into post_comments (id, parent_id, text, time, post_id, user_id)
VALUES (3, NULL, 'тестовый коммент 3', CURRENT_TIME, 1, 1);

insert into post_comments (id, parent_id, text, time, post_id, user_id)
VALUES (4, NULL, 'тестовый коммент 4', CURRENT_TIME, 2, 2);

insert into post_comments (id, parent_id, text, time, post_id, user_id)
VALUES (5, NULL, 'тестовый коммент 5', CURRENT_TIME, 2, 1);