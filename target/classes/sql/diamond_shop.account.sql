create table account
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
        unique (email),
    constraint UKq0uja26qgu1atulenwup9rxyr
        unique (email)
);

INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (1, 'thanhpdhe1410@gmail.com', false, null, 'thanhpdhe1410@gmail.com', '$2a$10$XPFojPDE0F.IqmK0CCCjH.DILAgokfHGdjew75NS.HoqFQiC2CzR.', 'local', null);
INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (2, 'thanhpdhe141032@gmail.com', false, null, 'thanhpdhe141032@gmail.com', '$2a$10$zQf8KP3xKJm6V6rXxVktCuTLgN3.tgnl84tHwaFnpjQ9ke8yvWwCu', 'local', null);
INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (3, 'thanhpd1410@gmail.com', false, 'https://lh3.googleusercontent.com/a/ACg8ocJCqzC7SOEZDRTWZ8AQnwk4Qf7xetKZtTfylCaTbRFhf36PTA=s96-c', 'Thành Phạm', null, 'google', '113357566108791399835');
INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (4, 'thanhpd.work@gmail.com', false, 'https://lh3.googleusercontent.com/a/ACg8ocKOSSBRUlUev115IqGUM5tpcFv15DG2zafFvtBXb_gXqclP3g=s96-c', 'Thành', null, 'google', '115046611592180350413');
INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (5, 'thanhpdhe141032@fpt.edu.vn', false, 'https://lh3.googleusercontent.com/a/ACg8ocL3swL0S2le5Xbdt8yp5TaJMQ1bOCX38mlhqKOzgJ9fuszGRQY=s96-c', 'Pham Dang Thanh (K14 HL)', null, 'google', '107687501542828364798');
INSERT INTO diamond_shop.account (id, email, email_verified, image_url, name, password, provider, provider_id) VALUES (6, 'thaodp@gmail.com', false, null, 'thaopd', '$2a$10$QplO0qKRu.6YcLFqiT042OIUUIRYV2UXRl8BBcKJftiywo8I/q.ga', 'local', null);
