--ini dummy aja
create table "simas_user" (
  id BIGSERIAL primary key,
  name varchar(50) not null,
  password varchar(20) not null,
  email varchar(50) not null
);

create table "thread" (
    id int8 primary key,
    user_id int8,
    title varchar(255),
    content text,
    up_vote int,
    down_vote int,
    date_post date
)