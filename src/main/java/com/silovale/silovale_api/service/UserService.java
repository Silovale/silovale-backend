package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.User;
import com.silovale.silovale_api.model.UserDTO;
import com.silovale.silovale_api.repos.UserRepository;
import com.silovale.silovale_api.util.NotFoundException;

import jakarta.transaction.Transactional;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Verificación de cuenta //
    private boolean isEmptyOrWhitespace(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    
    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    public User verifyAccount(String email, String password) {
        if (isEmptyOrWhitespace(email) || isEmptyOrWhitespace(password)) {
            throw new IllegalArgumentException("Ingresa tu correo y contraseña, son campos obligatorios");
        }

  List<User> existingUserByCount = userRepository.findByEmail(email);
        if (!existingUserByCount.isEmpty()) {
            User useremail = existingUserByCount.get(0);
            if (useremail.getPassword().equals(password)) {
                return useremail;
            } else {
                throw new IllegalStateException("Contraseña incorrecta");
            }

        } else {
            throw new IllegalArgumentException("Correo o contraseña incorrectos");
        }

    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setDni(user.getDni());
        userDTO.setRuc(user.getRuc());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setBusinessName(user.getBusinessName());
        userDTO.setAddress(user.getAddress());
        userDTO.setUserRol(user.getUserRol());
        return userDTO;
    }

    User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDni(userDTO.getDni());
        user.setRuc(userDTO.getRuc());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setBusinessName(userDTO.getBusinessName());
        user.setAddress(userDTO.getAddress());
        user.setUserRol(userDTO.getUserRol());
        return user;
    }

}
