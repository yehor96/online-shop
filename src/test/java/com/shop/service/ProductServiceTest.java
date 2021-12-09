package com.shop.service;

import com.shop.dao.product.JdbcProductDaoImpl;
import com.shop.dao.product.ProductDao;
import com.shop.entity.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private static ProductService productService;
    private static ProductDao mockDao;

    @BeforeAll
    static void beforeAll() {
        mockDao = mock(JdbcProductDaoImpl.class);
        productService = new ProductService(mockDao);
    }

    @Test
    void testFindAllInvokesRequiredMethods() {
        productService.findAll();

        verify(mockDao, times(1))
                .findAll();
    }

    @Test
    void testSaveInvokesRequiredMethods() {
        Product product = Product.builder()
                .id(1L)
                .name("test_name")
                .price(10.5)
                .build();

        productService.save(product);

        verify(mockDao, times(1))
                .save(product);
    }

    @Test
    void testUpdateInvokesRequiredMethods() {
        Product product = Product.builder()
                .id(1L)
                .name("test_name")
                .price(10.5)
                .build();

        productService.update(product);

        verify(mockDao, times(1))
                .update(product);
    }

    @Test
    void testDeleteInvokesRequiredMethods() {
        Long testId = 1L;

        productService.delete(testId);

        verify(mockDao, times(1))
                .delete(testId);
    }
}