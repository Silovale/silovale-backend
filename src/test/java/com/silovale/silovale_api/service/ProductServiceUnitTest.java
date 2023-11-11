package com.silovale.silovale_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import com.silovale.silovale_api.domain.Product;
import com.silovale.silovale_api.model.ProductDTO;
import com.silovale.silovale_api.repos.ProductRepository;
import com.silovale.silovale_api.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductServiceUnitTest {
    
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }


    @Test
    void testFindAll(){
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop Asus", "Muy bueno", 4800.90, 10));
        products.add(new Product("Play Station 5", "Bueno de buenos", 3500.00, 20));
        when(productRepository.findAll(Sort.by("id"))).thenReturn(products);

        // When
        List<ProductDTO> productDTOs = productService.findAll();

        // Then
        assertThat(productDTOs).isNotEmpty();
        assertThat(productDTOs).hasSize(products.size());
        // You can add more specific assertions for the mapping logic if needed
    }


    @Test
    void testCreate(){
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Ps4 Pro");
        productDTO.setDescription("En buen estado");
        productDTO.setPrice(1840.50);
        productDTO.setStock(2);
        Product productToReturnFromRepository = new Product();
        productToReturnFromRepository.setId((long) 10000);

        when(productRepository.save(any(Product.class))).thenReturn(productToReturnFromRepository);


        // When 
        Long result = productService.create(productDTO);


        // Then
        assertInstanceOf(Long.class, result);
    }


    @Test
    public void testUpdate(){
        // Datos de prueba
        Long productId = (long) 10000;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Monitor Asus");
        productDTO.setDescription("Full hd 1080p 144hz");
        productDTO.setPrice(1010.50);
        productDTO.setStock(12);

        // Mock del repositorio
        Product existingProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Llamada al método a probar
        productService.update(productId, productDTO);

        // Verificaipnes
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(existingProduct);

        // Verifica que el método mapToEntity haya sido llamado con los argumentos correctos
        assertEquals("Monitor Asus", existingProduct.getName());
        assertEquals("Full hd 1080p 144hz", existingProduct.getDescription());
    }


    @Test
    public void testDelete(){
        // Datos de prueba
        Long productId = (long) 10000;

        // Llamar al método a probar
        productService.delete(productId);

        // Verificación
        verify(productRepository, times(1)).deleteById(productId);
    }


    @Test
    public void testGet(){
        // Datos de prueba
        Long productId = (long) 10000;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Mouse Razer Viper Ultimate");
        productDTO.setDescription("El mas rapido");
        productDTO.setPrice(450.99);
        productDTO.setStock(6);

        //Covertir ProductDTO a Producto
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());

        when (productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Llamar metodo probar
        ProductDTO result = productService.get(productId);

        // Verificación
        verify(productRepository, times(1)).findById(productId);

        //Verifica que el resultado sea el esperado
        assertEquals("Mouse Razer Viper Ultimate", result.getName());
        assertEquals("El mas rapido", result.getDescription());
    }

    @Test
    public void testGetNotFound(){
        // Datos de prueba
        Long productId = (long) 10000;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // LLamada al método a probar que debería lanzar NotfoundExeption
        assertThrows(NotFoundException.class, () -> productService.get(productId));
    }
}