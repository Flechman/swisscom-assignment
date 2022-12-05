package ch.swisscom.prototype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.swisscom.controller.ProductController;
import ch.swisscom.entity.Product;

@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController controller;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
    
    @Test
    public void testAddproduct() throws JsonProcessingException, Exception {
        Product product = new Product("T", "Chocolate", "Frey");
        Product idProduct = (Product)mockMvc.perform(MockMvcRequestBuilders.put("/manage/")
                .content(new ObjectMapper().writeValueAsBytes(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getAsyncResult();
        assert(idProduct.getId() != null);
    }
    
}
