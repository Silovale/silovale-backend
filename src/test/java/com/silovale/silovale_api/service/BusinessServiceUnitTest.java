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
import com.silovale.silovale_api.domain.Business;
import com.silovale.silovale_api.model.BusinessDTO;
import com.silovale.silovale_api.repos.BusinessRepository;
import com.silovale.silovale_api.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BusinessServiceUnitTest{

    @Mock
    private BusinessRepository businessRepository;

    private BusinessService businessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        businessService = new BusinessService(businessRepository);
    }


    @Test
    void testFindAll() {
        // Given
        List<Business> businesses = new ArrayList<>();
        businesses.add(new Business("Mi tienda", "Esto es una descripcion","Av. America Sur", "932217652"));
        businesses.add(new Business("Mi tienda 2", "Esto es otra descripcion", "Av. Izaguirre", "987654321"));
        when(businessRepository.findAll(Sort.by("id"))).thenReturn(businesses);

        // When
        List<BusinessDTO> businessDTOs = businessService.findAll();

        // Then
        assertThat(businessDTOs).isNotEmpty();
        assertThat(businessDTOs).hasSize(businesses.size());
        // You can add more specific assertions for the mapping logic if needed
    }


    @Test
    void testCreate(){
        // Given
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setBusinessName("Tienda");
        businessDTO.setDescription("Mi descripcion");
        businessDTO.setAddress("Av. Izaguirre");
        businessDTO.setPhone("987654321");
        Business businessToReturnFromRepository = new Business();
        businessToReturnFromRepository.setId((long) 10000);


        when(businessRepository.save(any(Business.class))).thenReturn(businessToReturnFromRepository);


        // When
        Long result = businessService.create(businessDTO);

        // Then
        assertInstanceOf(Long.class, result);
    }


    @Test
    public void testUpdate(){
        // Datos de prueba 
        Long businessId = (long) 10000;
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setBusinessName("Amazon");
        businessDTO.setDescription("Mi descripcion");
        businessDTO.setAddress("Jr. Alfonzo Ugarte");
        businessDTO.setPhone("966955830");

        // Mock del repositorio 
        Business existingBusiness = new Business();
        when(businessRepository.findById(businessId)).thenReturn(Optional.of(existingBusiness));

        // Llamada al método a probar
        businessService.update(businessId, businessDTO);

        // Verificaiones
        verify(businessRepository, times(1)).findById(businessId);
        verify(businessRepository, times(1)).save(existingBusiness);

        // Verifica que el método mapToEntity haya sido llamado con los argumentos correctos
        assertEquals("Amazon", existingBusiness.getBusinessName());
        assertEquals("Mi descripcion", existingBusiness.getDescription());
    }


    @Test
    public void testDelete(){
        // Datos de prueba
        Long businessId = (long) 10000;

        // Llamar al método a probar
        businessService.delete(businessId);

        // Verificacion
        verify(businessRepository, times(1)).deleteById(businessId);
    }


    @Test
    public void testGet(){
        // Datos de prueba
        Long businessId = (long) 10000;
        BusinessDTO businessDTO = new BusinessDTO();
        businessDTO.setBusinessName("Mercado libre");
        businessDTO.setDescription("Mi otra descripcion");
        businessDTO.setAddress("Jr. Huanchaco");
        businessDTO.setPhone("923335315");

        // Convertir BusinessDTO a Business
        Business business = new Business();
        business.setBusinessName(businessDTO.getBusinessName());
        business.setDescription(businessDTO.getDescription());

        when(businessRepository.findById(businessId)).thenReturn(Optional.of(business));

        // Llamada al método a probar 
        BusinessDTO result = businessService.get(businessId);

        // Verificación
        verify(businessRepository, times(1)).findById(businessId);

        // Verifica que el resultado sea el esperado
        assertEquals("Mercado libre", result.getBusinessName());
        assertEquals("Mi otra descripcion", result.getDescription());  
    }


    @Test
    public void testGetNotFound(){
        // Datos de prueba
        Long businessId = (long) 10000;
        when(businessRepository.findById(businessId)).thenReturn(Optional.empty());

        // LLamada al método a probar que debería lanzar NotfoundExeption
        assertThrows(NotFoundException.class, () -> businessService.get(businessId));
    }
       
}