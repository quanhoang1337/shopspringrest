package com.shop.sukuna.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String supplierName;

    @NotBlank(message = "SĐT nhà cung cấp không được để trống")
    @Size(min = 10, message = "Số điện thoại phải có tối thiểu 10 ký tự")
    private String phone;

    @NotBlank(message = "Địa chỉ nhà cung cấp không được để trống")
    private String address;

    @NotBlank(message = "Email nhà cung cấp không được để trống")
    private String email;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;

}
