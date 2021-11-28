package com.shop.dao;

import com.shop.entity.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockProductDaoImpl implements ProductDao {

    private static final List<Product> products;
    private static long counter = 4;

    static {
        Product product1 = Product.builder()
                .id(1).name("book")
                .price(2.25)
                .createdDate(LocalDate.of(2021, 10, 10))
                .build();
        Product product2 = Product.builder()
                .id(2).name("pencil")
                .price(0.75)
                .createdDate(LocalDate.of(2020, 9, 9))
                .build();
        Product product3 = Product.builder()
                .id(3).name("pen")
                .price(1.0)
                .createdDate(LocalDate.of(2019, 5, 5))
                .build();

        products = new ArrayList<>(List.of(product1, product2, product3));
    }

    @Override
    public void save(Product product) {
        product.setId(counter++);
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findOne(Long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public void update(Product newProduct) {
        findOne(newProduct.getId()).ifPresentOrElse(
                existingProduct -> products.set(products.indexOf(existingProduct), newProduct),
                () -> save(newProduct));
    }

    @Override
    public void delete(Long id) {
        Product product = Product.builder().id(id).build();
        products.remove(product);
    }

}
