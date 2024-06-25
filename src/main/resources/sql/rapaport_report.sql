create table rapaport_report
(
    id         binary(255) not null
        primary key,
    cara_from  int         null,
    cara_to    int         null,
    clarity_id binary(255) null,
    color_id   binary(255) null,
    create_at  datetime    null,
    is_active  bit         null,
    update_at  datetime    null,
    percent    double      null
);

