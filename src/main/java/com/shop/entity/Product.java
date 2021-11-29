package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ofPattern;

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

    @Override
    public String toString() {
        return "id #%d %s for $%s created at %s"
                .formatted(id, name, price, createdDate.format(ofPattern("dd MMM yyyy")));
    }
}