create table users
(
    id             bigint auto_increment
        primary key,
    email          varchar(255) not null,
    email_verified bit          not null,
    image_url      varchar(255) null,
    name           varchar(255) not null,
    password       varchar(255) null,
    provider       varchar(255) not null,
    provider_id    varchar(255) null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

