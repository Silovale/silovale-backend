package com.silovale.silovale_api.rest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.silovale.silovale_api.domain.Product;
import com.silovale.silovale_api.model.ProductDTO;
import com.silovale.silovale_api.repos.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class ProductResourceUnitTest {

    @InjectMocks
    ProductResource productResource;

    @Mock
    ProductRepository productRepository;

    @Test
public void testAddProduct(){

    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    
    Product product = new Product();
    product.setId((long) 10000);
    when(productRepository.save(any(Product.class))).thenReturn(product);
    
    ProductDTO productDTOToAdd = new ProductDTO();
    productDTOToAdd.setCategory("Ropa");
    productDTOToAdd.setDescription("Mi nuevo negocio");
    productDTOToAdd.setImage("Base64");
    productDTOToAdd.setPrice(120.00);
    ResponseEntity<Long> responseEntity = productResource.createProduct(productDTOToAdd);

 /*
    assertThat(((Object) responseEntity).getCategory("Ropa")).isEqualTo(201);
    assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    */

}
                                     } 
