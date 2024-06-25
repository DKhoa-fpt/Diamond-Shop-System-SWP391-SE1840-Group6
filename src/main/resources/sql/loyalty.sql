create table loyalty
(
    id          binary(255) not null
        primary key,
    create_at   datetime    null,
    end_user_id binary(255) null,
    order_id    binary(255) null,
    point       int         null,
    update_at   datetime    null
);

