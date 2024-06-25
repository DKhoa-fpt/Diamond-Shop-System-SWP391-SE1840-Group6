create table jewelry_type
(
    id                int auto_increment
        primary key,
    jewelry_type_name varchar(100) null,
    jewelry_type_code varchar(100) null,
    id_guide          int          null,
    created_at        timestamp    null,
    created_by        varchar(100) null,
    updated_at        timestamp    null,
    updated_by        varchar(100) null
);

INSERT INTO diamond_shop.jewelry_type (jewelry_type_name, jewelry_type_code, id_guide, created_at, created_by, updated_at, updated_by) VALUES ('Nhẫn Kim Cương Nam', 'NKCNA', 1, null, 'Tran Khoa', null, null);
INSERT INTO diamond_shop.jewelry_type (jewelry_type_name, jewelry_type_code, id_guide, created_at, created_by, updated_at, updated_by) VALUES ('Nhẫn Kim Cương Nữ', 'NKCNNU', 1, null, 'Tran Khoa', null, null);
INSERT INTO diamond_shop.jewelry_type (jewelry_type_name, jewelry_type_code, id_guide, created_at, created_by, updated_at, updated_by) VALUES ('Bông Tai Kim Cương', 'BTKC', 1, null, 'Tran Khoa', null, null);
INSERT INTO diamond_shop.jewelry_type (jewelry_type_name, jewelry_type_code, id_guide, created_at, created_by, updated_at, updated_by) VALUES ('Dây Chuyền Kim Cương', 'DCKC', 1, null, 'Tran Khoa', null, null);
INSERT INTO diamond_shop.jewelry_type (jewelry_type_name, jewelry_type_code, id_guide, created_at, created_by, updated_at, updated_by) VALUES ('Lắc tay Kim Cương', 'LTKC', 1, null, 'Tran Khoa', null, null);
