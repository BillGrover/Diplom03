/********* У всех тестовых юзеров пароль - 123456 *********/
/********* Тестовый юзер-модератор и его пост *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (1, 'First_User', 'user1@mail.ru', 1, '$2y$12$qTOfI/ButHbu7rbJI7S3kea.CIHSx04O73QmP3XxYq63svM.vbh0G', CURRENT_DATE);

/********* Второй тестовый юзер и его коммент *********/
insert into users (id, name, email, is_moderator, password, reg_time)
    values (2, 'Second_User', 'user2@mail.ru', 0, '$2y$12$qTOfI/ButHbu7rbJI7S3kea.CIHSx04O73QmP3XxYq63svM.vbh0G', CURRENT_DATE);