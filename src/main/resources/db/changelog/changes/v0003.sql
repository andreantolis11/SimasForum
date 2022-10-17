create table "role" (
    id BIGSERIAL primary key,
    role_name varchar(50)
);

insert into "role"(id, role_name) values (1, 'user'),(2, 'admin');

alter table "simas_user" add column role_id int8 references "role" (id);

insert into "simas_user"(name, password, email, role_id) values ('admin', 'admin12345', 'admin@gmail.com', 2);
