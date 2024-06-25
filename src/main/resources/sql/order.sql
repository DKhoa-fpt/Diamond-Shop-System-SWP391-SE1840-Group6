create table `order`
(
    id          binary(16) not null
        primary key,
    order_id    binary(16) null,
    end_user_id binary(16) null,
    jewelry_id  binary(16) null,
    is_paid     bit        null,
    create_at   datetime   null,
    update_at   datetime   null,
    constraint uc_order_order
        unique (order_id)
);

