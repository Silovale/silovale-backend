package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.User;
import com.silovale.silovale_api.model.UserDTO;
import com.silovale.silovale_api.repos.UserRepository;
import com.silovale.silovale_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
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

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setDni(user.getDni());
        userDTO.setRuc(user.getRuc());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setBusinessName(user.getBusinessName());
        userDTO.setAddress(user.getAddress());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setUserRol(user.getUserRol());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setDni(userDTO.getDni());
        user.setRuc(userDTO.getRuc());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setBusinessName(userDTO.getBusinessName());
        user.setAddress(userDTO.getAddress());
        user.setRegistrationDate(userDTO.getRegistrationDate());
        user.setUserRol(userDTO.getUserRol());
        return user;
    }

}
