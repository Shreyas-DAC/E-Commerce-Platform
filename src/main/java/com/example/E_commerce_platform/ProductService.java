package com.example.E_commerce_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Bean
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findByNameAndDescription(keyword);
    }

    public List<Product> filterProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
