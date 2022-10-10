
create table "reply" (
    id BIGSERIAL primary key,
    threadid int8,
    replyid int8,
    level int,
    replyname varchar(255),
    content text,
    userid int8,
    upVote int,
    downVote int
)