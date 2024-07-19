package com.example.E_commerce_platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    public void test_to_get_all_products() {

        Product product1 = new Product("Product 1", "Description 1", "Category 1", 10.0);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", 20.0);
        productRepository.save(product1);
        productRepository.save(product2);

        List<Product> products = productController.getAllProducts();

        assertThat(products.size()).isEqualTo(2);
        assertThat(products).contains(product1, product2);
    }


    @Test
    public void test_to_get_product_by_Id() {

        Product product = new Product("Product 1", "Description 1", "Category 1", 10.0);
        productRepository.save(product);

        Product result_Product = productController.getProductById(product.getId());

        assertThat(result_Product).isNotNull();
        assertThat(result_Product.getId()).isEqualTo(product.getId());
    }

    @Test
    public void test_get_product_by_id_not_found(){
        Product product = productController.getProductById(56L);

        assertThat(product).isNull();
    }

    @Test
    public void test_create_product(){
        Product product = new Product("Product 1", "Description 1", "Category 1", 10.0);
        Product newProduct = productController.createProduct(product);

        assertThat(newProduct.getName()).isEqualTo("Product 1");
    }

    @Test
    public void test_update_product(){
        Product product = new Product("test","Test_desc","test_cat",100.00);
        productRepository.save(product);

        assertThat(product.getName()).isEqualTo("test");

        Product newProduct = new Product("Cat","For playing","Toy",900.00);
        Product afterUpdate = productController.updateProduct(product.getId(),newProduct);

        assertThat(product.getId()).isEqualTo(afterUpdate.getId());
        assertThat(product.getName()).isEqualTo("Cat");
        assertThat(afterUpdate.getName()).isEqualTo("Cat");
    }

    @Test
    public void test_update_not_found(){
        Product product = new Product("Cat","For playing","Toy",900.00);
        Product newProduct = productController.updateProduct(25L,product);

        assertThat(newProduct).isNull();
    }

    @Test
    public void test_delete_product(){
        Product product1 = new Product("Product 1", "Description 1", "Category 1", 10.0);
        Product product = productController.createProduct(product1);

        productController.deleteProduct(product.getId());

        assertThat(productRepository.findById(product.getId()).isEmpty());
    }

    @Test
    public void test_search_products() {

        Product product1 = new Product("Product 1", "Description 1", "Category 1", 10.0);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", 20.0);
        Product product3 = new Product("dummy", "test_desc", "Category 3", 20.0);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products_1 = productController.searchProduct("desc");

        List<Product> products_2 = productController.searchProduct("dummy");

        assertThat(products_1.size()).isEqualTo(1);   //search by description
        assertThat(products_2).contains(product3);   // search by name
    }

    @Test
    public void test_search_products_which_is_not_present() {

        Product product1 = new Product("Product 1", "Description 1", "Category 1", 10.0);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", 20.0);
        Product product3 = new Product("dummy", "Description 3", "Category 3", 20.0);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products_2 = productController.searchProduct("Dummy");
        assertThat(products_2).isEmpty();
    }

    @Test
    public void test_filter_products() {

        Product product1 = new Product("Product 1", "Description 1", "toy", 10.0);
        Product product2 = new Product("Product 2", "Description 2", "sample", 20.0);
        Product product3 = new Product("dummy", "Description 3", "toy", 20.0);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products_1 = productService.filterProductsByCategory("toy");
        assertThat(products_1.size()).isEqualTo(2);
    }

    @Test
    public void test_filter_products_not_found() {

        Product product1 = new Product("Product 1", "Description 1", "toy", 10.0);
        Product product2 = new Product("Product 2", "Description 2", "sample", 20.0);
        Product product3 = new Product("dummy", "Description 3", "toy", 20.0);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> products = productService.filterProductsByCategory("doll");
        assertThat(products).isEmpty();
    }
}
