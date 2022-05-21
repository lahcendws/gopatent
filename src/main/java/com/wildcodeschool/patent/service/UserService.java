package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.DTO.UserDTO;
import com.wildcodeschool.patent.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(Long id);

    void deleteUserById(Long id);

    List<User> getAllUsers();

    void createPasswordResetTokenForUser(User user) throws Exception;

    Optional<User> getUserByPasswordResetToken(String token);

    void changeUserPassword(User user, String password);

    User getUserByEmail(String email);

    User updateUser(UserDTO userDTO, Authentication authentication) throws Exception;
}
