package com.project.brka_media.service;

import com.project.brka_media.DTO.UserDTO;
import com.project.brka_media.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    @Transactional
    UserDTO updateUser(UserDTO userDTO, Long id);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);
}
