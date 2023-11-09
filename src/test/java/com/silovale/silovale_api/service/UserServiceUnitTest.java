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
import com.silovale.silovale_api.domain.User;
import com.silovale.silovale_api.model.UserDTO;
import com.silovale.silovale_api.repos.UserRepository;
import com.silovale.silovale_api.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testFindAll() {
        // Given
        List<User> users = new ArrayList<>();
        users.add(new User("user1.com", "123456789","1234567", null, "Alexa", "Mendez", "Mybusiness", "Lima", 1));
        users.add(new User("user2@example.com", "1234567888","1234657", null, "Diana", "Chanta", "Mybusiness2", "Trujillo", 1));
        when(userRepository.findAll(Sort.by("id"))).thenReturn(users);

        // When
        List<UserDTO> userDTOs = userService.findAll();
        
        // Then
        assertThat(userDTOs).isNotEmpty();
        assertThat(userDTOs).hasSize(users.size());
        // You can add more specific assertions for the mapping logic if needed
    }

    @Test
    void testCreate(){
        //Given
        UserDTO userDTO = new UserDTO(); 
        userDTO.setAddress("Lima");
        userDTO.setBusinessName("Negocio1");
        userDTO.setDni("1234567");
        userDTO.setName("Patricia");
        userDTO.setEmail("user2@example.com");
        userDTO.setPassword("PerroGato");
        userDTO.setRuc("12345");
        userDTO.setUserRol(1);
        User userToReturnFromRepository = new User();
        userToReturnFromRepository.setId((long) 10000);

     
        when(userRepository.save(any(User.class))).thenReturn(userToReturnFromRepository);


        //When
        Long result = userService.create(userDTO);

        //Then
        assertInstanceOf(Long.class, result);
    }


   
    @Test
    public void testUpdate() {
        // Datos de prueba
        Long userId = (long) 10000;
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lima");
        userDTO.setBusinessName("Negocio1");
        userDTO.setDni("1234567");
        userDTO.setName("NuevoNombre");
        userDTO.setEmail("nuevo@email.com");
        userDTO.setPassword("PerroGato");
        userDTO.setRuc("12345");
        userDTO.setUserRol(1);

        // Mock del repositorio
        User existingUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Llamada al método a probar
        userService.update(userId, userDTO);

        // Verificaciones
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);

        // Verifica que el método mapToEntity haya sido llamado con los argumentos correctos
         assertEquals("NuevoNombre", existingUser.getName());
        assertEquals("nuevo@email.com", existingUser.getEmail());
    }

    @Test
    public void testDelete() {
        // Datos de prueba
        Long userId = (long) 10000;

        // Llamada al método a probar
        userService.delete(userId);

        // Verificación
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGet() {
        // Datos de prueba
        Long userId = (long) 10000;
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lima");
        userDTO.setBusinessName("Negocio1");
        userDTO.setDni("1234567");
        userDTO.setName("Carmen");
        userDTO.setEmail("nuevo@email.com");
        userDTO.setPassword("PerroGato");
        userDTO.setRuc("12345");
        userDTO.setUserRol(1);

        // Convertir UserDTO a User 
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Llamada al método a probar
        UserDTO result = userService.get(userId);

        // Verificación
        verify(userRepository, times(1)).findById(userId);
       

        // Verifica que el resultado sea el esperado
        assertEquals("Carmen", result.getName());
        assertEquals("nuevo@email.com", result.getEmail());
    }

    @Test
    public void testGetNotFound() {
        // Datos de prueba
        Long userId = (long) 10000;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Llamada al método a probar que debería lanzar NotFoundException
        assertThrows(NotFoundException.class, () -> userService.get(userId));
    }
}






