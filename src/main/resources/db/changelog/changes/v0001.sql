--ini dummy aja
create table "simas_user" (
  id BIGSERIAL primary key,
  name varchar(50) not null,
  password varchar(20) not null,
  email varchar(50) not null
);

create table "thread" (
    id BIGSERIAL primary key,
    userid int8 not null references "simas_user" (id),
    title varchar(255),
    content text,
    upVote int,
    downVote int,
    datePost date
)