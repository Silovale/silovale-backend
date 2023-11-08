package com.silovale.silovale_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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


import java.util.ArrayList;
import java.util.List;

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
        users.add(new User("user1.com", "123456789","1234567", null, "Brayan", "Cruces", "Mybusiness", "Lima", 1));
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
        userDTO.setName("Brayan");
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

}

