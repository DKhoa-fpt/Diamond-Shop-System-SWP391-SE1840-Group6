create table diamond
(
    id         binary(255) not null
        primary key,
    carat      int         null,
    clarity_id binary(255) null,
    color_id   binary(255) null,
    create_at  datetime    null,
    cut_id     binary(255) null,
    polish_id  binary(255) null,
    quantity   int         null,
    shape_id   binary(255) null,
    update_at  datetime    null,
    image_id   bigint      null,
    origin_id  binary(255) null
);

INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (1 , '2024-05-01 10:30:00', 1, '2024-05-01 10:30:00', '3ly6');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (2 , '2024-05-10 13:25:00', 2, '2024-05-10 13:25:00', '3ly9');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (1 , '2024-05-02 11:45:00', 2, '2024-05-02 11:45:00', '4ly1');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (2 , '2024-05-03 14:20:00', 1, '2024-05-03 14:20:00', '4ly5');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (2 , '2024-05-04 09:15:00', 3, '2024-05-04 09:15:00', '5ly');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (1 , '2024-05-05 16:30:00', 1, '2024-05-05 16:30:00', '5ly2');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (2 , '2024-05-06 08:50:00', 2, '2024-05-06 08:50:00', '6ly');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (1 , '2024-05-07 12:30:00', 1, '2024-05-07 12:30:00', '7ly');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (2 , '2024-05-08 15:40:00', 3, '2024-05-08 15:40:00', '8ly');
INSERT INTO diamond_shop.diamond (carat, create_at, quantity, update_at, name) VALUES (1 , '2024-05-09 10:00:00', 1, '2024-05-09 10:00:00', '9ly');
