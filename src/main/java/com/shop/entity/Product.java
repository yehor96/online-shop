package com.shop.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Product {
    private long id;
    private String name;
    private double price;
    private LocalDate createdDate;
}