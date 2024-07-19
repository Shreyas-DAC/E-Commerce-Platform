package com.example.E_commerce_platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/Products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/Products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/Products/create")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/Products/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/Products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/Products/search")
    public List<Product> searchProduct(@RequestParam String keyword) {
        return productService.searchProduct(keyword);
    }

    @GetMapping("/Products/filter")
    public List<Product> filterProductByCategory(@RequestParam String category) {
        return productService.filterProductsByCategory(category);
    }
}
