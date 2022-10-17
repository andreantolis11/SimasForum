create table "report" (
    id BIGSERIAL primary key,
    thread_id int8 references "thread" (id),
    reply_id int8 references "reply" (id),
    user_id int8 references "simas_user" (id),
    reason_for_report text
)