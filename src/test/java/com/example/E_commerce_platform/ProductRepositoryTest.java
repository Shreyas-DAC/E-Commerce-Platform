package com.example.E_commerce_platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save_product_test(){
        Product product1 = new Product("test1","demo","toy",250.00);
        Product product2 = new Product("test2","demo","watch",290.00);
        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);

        assertThat(savedProduct2.getId()).isEqualTo(2);
        assertThat(savedProduct1.getCategory()).isEqualTo("toy");
    }
}