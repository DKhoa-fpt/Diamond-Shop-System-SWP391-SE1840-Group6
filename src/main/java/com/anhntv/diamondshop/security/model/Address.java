package com.anhntv.diamondshop.security.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ADDRESS")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "province")
    private String province;
    @Column(name = "district")
    private String district;
    @Column(name = "city")
    private String city;
    @Column(name = "ward")
    private String ward;
    @Column(name = "extra")
    private String extra;
    @Column(name = "create_at")
    private String createAt;
    @Column(name = "update_at")
    private String updateAt;
}
