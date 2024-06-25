create table origin
(
    id          binary(255)  not null
        primary key,
    origin_name varchar(255) null,
    constraint UK_d6mrs4ptcnev11b9vnmfmvdfw
        unique (origin_name)
);

