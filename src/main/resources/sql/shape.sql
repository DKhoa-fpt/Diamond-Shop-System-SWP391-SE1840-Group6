create table shape
(
    id        binary(255) not null
        primary key,
    create_at datetime    null,
    image     binary(255) null,
    shape     int         null,
    update_at datetime    null
);

