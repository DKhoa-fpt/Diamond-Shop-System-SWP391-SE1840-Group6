create table diamond_shop_config_value
(
    id          binary(16)   not null
        primary key,
    `key`       varchar(255) null,
    value       varchar(255) null,
    description varchar(255) null,
    create_at   datetime     null,
    update_at   datetime     null,
    constraint uc_diamond_shop_config_value_key
        unique (`key`)
);

