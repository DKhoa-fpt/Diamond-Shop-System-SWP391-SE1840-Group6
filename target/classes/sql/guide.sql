create table guide
(
    id        int auto_increment
        primary key,
    title     varchar(100) null,
    steps     json         null,
    images    json         null,
    is_active int          null
);

INSERT INTO diamond_shop.guide (id, title, steps, images, is_active) VALUES (1, 'HƯỚNG DẪN CÁCH ĐO NI NHẪN', '{"desc_1": "Để biết rõ hơn về cách chọn nhẫn sao cho phù hợp nhất, bạn cần phải biết chu vi ngón tay của mình. So sánh thông số dưới bảng để biết được kích thước của nhẫn so với ngón tay. Dưới đây là bảng size nhẫn (Đường kính trong mm) phổ biến để bạn dễ dàng tham khảo", "desc_2": "Hiện nay có hai bảng size nhẫn phổ biến: Đó là bảng size được dùng tại Việt Nam và bảng size được dùng tại Mỹ. Tùy theo quốc gia đang cư trú mà bạn chọn bảng size phù hợp. Nhiều thương hiệu trang sức cũng thiết kế riêng bảng size nhẫn. Vì thế khi mua nhẫn, bạn nên nhờ nhân viên tư vấn cụ thể để chọn đúng size nhẫn vừa tay"}', null, 1);
