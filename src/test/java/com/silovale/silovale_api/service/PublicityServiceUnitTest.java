package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.Publicity;
import com.silovale.silovale_api.model.PublicityDTO;
import com.silovale.silovale_api.repos.PublicityRepository;
import com.silovale.silovale_api.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublicityServiceUnitTest {
    
    @Mock
    private PublicityRepository publicityRepository;

    private PublicityService publicityService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        publicityService = new PublicityService((publicityRepository));
    }

    @Test
    void testFindAll(){
        // Given
        List<Publicity> publicities = new ArrayList<>();
        publicities.add(new Publicity("Full descuento", "Todo a descuento", "6 de junio", "6 de julio"));
        publicities.add(new Publicity("Descuento en Monitores", "Todo en monitores", "14 de septiembre", "14 de octubre"));
        when(publicityRepository.findAll(Sort.by("id"))).thenReturn(publicities);

        // When
        List<PublicityDTO> publicityDTOs = publicityService.findAll();

        // Then
        assertThat(publicityDTOs).isNotEmpty();
        assertThat(publicityDTOs).hasSize(publicities.size());
        // You can add more specific assertions for the mapping logic if needed
    }

    @Test
    void testCreate(){
        PublicityDTO publicityDTO = new PublicityDTO();
        publicityDTO.setNamePublicity("Laptops");
        publicityDTO.setDescriptionPublicity("Descuento del 50%");
        publicityDTO.setStartDate("15 de septiembre");
        publicityDTO.setEndDate("15 de octubre");
        Publicity publicityToReturnFromRepository = new Publicity();
        publicityToReturnFromRepository.setId((long) 10000);

        when(publicityRepository.save(any(Publicity.class))).thenReturn(publicityToReturnFromRepository);

        // When
        Long result = publicityService.create(publicityDTO);

        // Then
        assertInstanceOf(Long.class, result);
    }

    @Test
    public void testUpdate(){
        // Datos de prueba
        Long publicityId = (long) 10000;
        PublicityDTO publicityDTO = new PublicityDTO();
        publicityDTO.setNamePublicity("Teclado Razer TKL");
        publicityDTO.setDescriptionPublicity("Descuento Razer Hustman tkl");
        publicityDTO.setStartDate("6 de junio");
        publicityDTO.setEndDate("6 de julio");

        // Mock del repositorio
        Publicity exisPublicity = new Publicity();
        when(publicityRepository.findById(publicityId)).thenReturn(Optional.of(exisPublicity));


        // Llamada al método a probar
        publicityService.update(publicityId, publicityDTO);

        // Verificaiones
        verify(publicityRepository, times(1)).findById(publicityId);
        verify(publicityRepository, times(1)).save(exisPublicity);

        // Verifica que el método mapToEntity haya sido llamado con los argumentos correctos
        assertEquals("Teclado Razer TKL", exisPublicity.getNamePublicity());
        assertEquals("Descuento Razer Hustman tkl", exisPublicity.getDescriptionPublicity());
    }

    @Test
    public void testDelete(){
        // Datos de prueba
        Long publicityId = (long) 10000;

        // Llamada al método a probar
        publicityService.delete(publicityId);

        // Verificación
        verify(publicityRepository, times(1)).deleteById(publicityId);
    }

    @Test
    public void testGet() {
        // Datos de prueba
        Long publicityId = (long) 10000;
        PublicityDTO publicityDTO = new PublicityDTO();
        publicityDTO.setNamePublicity("Todo en Ps4");
        publicityDTO.setDescriptionPublicity("Ps4 pro en descuento");
        publicityDTO.setStartDate("17 de abril");
        publicityDTO.setEndDate("17 de mayo");

        // Convertir PublicityDTO a Publicity
        Publicity publicity = new Publicity();
        publicity.setNamePublicity(publicityDTO.getNamePublicity());
        publicity.setDescriptionPublicity(publicityDTO.getDescriptionPublicity());

        when(publicityRepository.findById(publicityId)).thenReturn(Optional.of(publicity));

        // Llamada al método a probar
        PublicityDTO result = publicityService.get(publicityId);

        // Verificación
        verify(publicityRepository, times(1)).findById(publicityId);

        // Verifica que el resultado sea el esperado
        assertEquals("Todo en Ps4", result.getNamePublicity());
        assertEquals("Ps4 pro en descuento", result.getDescriptionPublicity());
    }

    @Test
    public void testGetNotFound(){
        // Datos de prueba
        Long publicityId = (long) 10000;
        when(publicityRepository.findById(publicityId)).thenReturn(Optional.empty());

        // Llamada al método a probar que debería lanzar NotFoundException
        assertThrows(NotFoundException.class, () -> publicityService.get(publicityId));
    }
}