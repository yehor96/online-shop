package com.shop.dao.product;

import com.shop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    void save(Product product);

    List<Product> findAll();

    Optional<Product> findOne(Long id);

    void update(Product product);

    void delete(Long id);

}
