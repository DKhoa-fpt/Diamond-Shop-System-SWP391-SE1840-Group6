create table polish
(
    id        binary(255)  not null
        primary key,
    create_at datetime     null,
    polish    varchar(255) null,
    update_at datetime     null
);

