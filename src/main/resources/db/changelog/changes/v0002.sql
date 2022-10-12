create table "vote"(
    id BIGSERIAL primary key,
    threadid BIGSERIAL,
    replyid BIGSERIAL,
    userid BIGSERIAL not null,
    isupvote boolean not null
);

create table "reply" (
    id BIGSERIAL primary key,
    thread_id int8 references "thread" (id),
    reply_id int8 references "reply" (id),
    reply_name varchar(255),
    content text,
    user_id int8 references "simas_user" (id),
    vote_score int
);
