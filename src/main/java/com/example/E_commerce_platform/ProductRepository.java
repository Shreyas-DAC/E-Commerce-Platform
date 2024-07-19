package com.example.E_commerce_platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR description LIKE %:keyword%" )
    List<Product> findByNameAndDescription(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCategory(@Param("category") String category);
}

