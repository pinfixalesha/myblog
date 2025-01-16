-- Таблица с постов
create table if not exists blogs(
    id integer auto_increment primary key,
    datetime timestamp now(),
    title varchar(512),
    text clob,
    picture clob,
    tags varchar(256));

-- Таблица комментарии
create table if not exists comments(
    id integer auto_increment primary key,
    datetime timestamp now(),
    blog integer not null,
    comment varchar(1024) not null);

-- Таблица лайки. Реализуем отдельную таблицу для лайков, т.к. это правильнее. В дальнейшем возможно привязать лайк
-- к пользователю, устанавливать ограничение один лайк на пользователя и т.д.
create table if not exists likes(
   id integer auto_increment primary key,
   datetime timestamp now(),
   blog integer not null);
