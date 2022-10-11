--ini dummy aja
create table "simas_user" (
  id BIGSERIAL primary key,
  name varchar(50) not null,
  password varchar(20) not null,
  email varchar(50) not null
);

create table "thread" (
    id BIGSERIAL primary key,
    userid int8,
    title varchar(255),
    content text,
    vote int,
    datePost date
)