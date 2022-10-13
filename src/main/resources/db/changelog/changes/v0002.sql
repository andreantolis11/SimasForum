create table "reply" (
    id BIGSERIAL primary key,
    thread_id int8 references "thread" (id),
    reply_id int8 references "reply" (id),
    reply_name varchar(255),
    content text,
    user_id int8 references "simas_user" (id),
    vote_score int
);
create table "vote"(
                       id BIGSERIAL primary key,
                       thread_id int8 references "thread" (id),
                       reply_id int8 references "reply" (id),
                       user_id int8 references "simas_user" (id),
                       is_up_vote boolean not null
);