package com.example.E_commerce_platform;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerWithMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void test_Get_All_Products() throws Exception {
        List<Product> productList = Arrays.asList(
                new Product("Product A","description", "Category A", 100.0),
                new Product("Product B", "description","Category B", 200.0)
        );

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/Products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product B"));
    }

    @Test
    public void test_Get_Product_By_Id() throws Exception {
        Product product = new Product("Product A", "description","Category A", 100.0);

        when(productService.getProductById(1L)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/Products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product A"));
    }

    @Test
    public void test_Create_Product() throws Exception {
        Product productToCreate = new Product("New Product","description", "New Category", 50.0);
        Product createdProduct = new Product( "New Product", "description","New Category", 50.0);

        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/Products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Product\", \"category\": \"New Category\", \"price\": 50.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productToCreate.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(productToCreate.getCategory()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productToCreate.getPrice()));
    }

    @Test
    public void test_Update_Product() throws Exception {
        Long productId = 1L;
        Product updatedProduct = new Product("Updated Product","description", "Updated Category", 75.0);

        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/Products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Product\", \"category\": \"Updated Category\", \"price\": 75.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Updated Category"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(75.0));
    }


    @Test
    public void test_Delete_Product() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/Products/{id}", productId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void test_Search_Product() throws Exception {
        List<Product> searchResults = Arrays.asList(
                new Product("Product A", "description","Category A", 100.0),
                new Product("Product B","description", "Category B", 200.0)
        );

        String keyword = "Product";

        when(productService.searchProduct(keyword)).thenReturn(searchResults);

        mockMvc.perform(MockMvcRequestBuilders.get("/Products/search")
                        .param("keyword", keyword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product B"));
    }

    @Test
    public void test_Filter_Product_By_Category() throws Exception {
        List<Product> filteredProducts = Arrays.asList(
                new Product("Product A","description", "Category A", 100.0),
                new Product("Product B", "description","Category A", 200.0)
        );

        String category = "Category A";

        when(productService.filterProductsByCategory(category)).thenReturn(filteredProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/Products/filter")
                        .param("category", category)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product B"));
    }
}
