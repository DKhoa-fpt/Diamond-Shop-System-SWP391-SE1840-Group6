create table end_user
(
    id           bigint not null
        primary key,
    account_id   bigint null,
    address      varchar(255) null,
    age          int null,
    full_name    varchar(255) null,
    is_male      bit null,
    phone_number varchar(255) null,
    create_at    datetime null,
    update_at    datetime null
);

