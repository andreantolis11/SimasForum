create table "pin"(
    id BIGSERIAL primary key,
    thread_id int8 references "thread" (id),
    reply_id int8 references "reply" (id),
    is_pin boolean,
    date date
);