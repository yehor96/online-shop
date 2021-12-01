package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Product {

    @EqualsAndHashCode.Include
    private long id;
    private String name;
    private double price;
    private LocalDate createdDate;
}