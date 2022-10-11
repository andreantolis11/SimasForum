create table "vote"(
    id BIGSERIAL primary key,
    threadid BIGSERIAL,
    replyid BIGSERIAL,
    userid BIGSERIAL not null,
    isupvote boolean not null
);