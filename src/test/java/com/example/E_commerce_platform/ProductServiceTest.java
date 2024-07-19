package com.example.E_commerce_platform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void test_get_all_products(){
        List<Product> products = Arrays.asList(
                new Product("Product 1", "Description 1", "Category 1", 2500),
                new Product("Product 2", "Description 2", "Category 2", 4700)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();
        assertEquals(2,result.size());
    }

    @Test
    public void test_get_product_by_id(){
        Product product = new Product("Product 1", "Description 1", "Category 1", 2500);

        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(id);
        assertEquals(result,product);
    }

    @Test
    public void test_create_product(){
        Product product = new Product("Product 1", "Description 1", "Category 1", 2500);

        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);
        assertEquals(product,result);
    }

    @Test
    public void test_update_product(){
        Product product = new Product("Product 1", "Description 1", "Category 1", 2500);
        Product updatedProduct = new Product("Product 2", "Description 2", "Category 2", 5000);

        Long id=1L;
        when(productRepository.existsById(id)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(id,updatedProduct);
        assertEquals(result,updatedProduct);
    }

    @Test
    public void test_delete_product(){
        Long id=1L;
        productService.deleteProduct(id);
        verify(productRepository,times(1)).deleteById(id);
    }

    @Test
    public void test_search_product(){
        List<Product> products = Arrays.asList(
                new Product("Product 1", "Description 1", "Category 1", 2500),
                new Product("Product 2", "Description 2", "Category 2", 4700)
        );

        String keyword = "Product";

        when(productRepository.findByNameAndDescription(keyword)).thenReturn(products);

        List<Product> resultSet = productService.searchProduct(keyword);
        assertEquals(resultSet, products);
    }

    @Test
    public void test_filter_products(){
        List<Product> products = Arrays.asList(
                new Product("Product 1", "Description 1", "Category 1", 2500),
                new Product("Product 2", "Description 2", "Category 1", 4700)
        );

        String keyword = "Category1";
        when(productRepository.findByCategory(keyword)).thenReturn(products);

        List<Product> resultSet = productService.filterProductsByCategory(keyword);
        assertEquals(resultSet, products);
    }
}
