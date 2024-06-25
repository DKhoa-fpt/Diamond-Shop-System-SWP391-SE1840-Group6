create table clarity
(
    id        bigint  not null
        primary key,
    clarity   varchar(255) null,
    create_at datetime     null,
    update_at datetime     null
);

INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'VS', '2024-05-10 15:14:29', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'IF_VVS', '2024-05-10 15:14:29', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ('I2', '2024-05-10 15:14:31', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'SI1', '2024-05-10 15:14:30', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'I3', '2024-05-10 15:14:31', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'SI2', '2024-05-10 15:14:30', null);
INSERT INTO diamond_shop.clarity (clarity, create_at, update_at) VALUES ( 'I1', '2024-05-10 15:14:31', null);
