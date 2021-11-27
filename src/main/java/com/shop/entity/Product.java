package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @EqualsAndHashCode.Include
    private long id;
    private String name;
    private double price;
    private LocalDate createDate;

    @Override
    public String toString() {
        return "#" + id + " " + name + " for $" + price + " created at " + createDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }
}

