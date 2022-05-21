package com.wildcodeschool.patent.service;

import com.wildcodeschool.patent.DTO.UserDTO;
import com.wildcodeschool.patent.entity.PasswordResetToken;
import com.wildcodeschool.patent.repository.PasswordResetTokenRepository;
import com.wildcodeschool.patent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wildcodeschool.patent.entity.User;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@Service @Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExpireDateService expireDateService;

    @Value("${password.rest.token.expiration}")
    private String passwordReserTokenExpiration;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SendMailService sendMailService;


    /**
     * Get connected user
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    /**
     * Update a user from the front into the database using the UserDTO
     * @param userDTO
     * @param authentication
     * @return
     * @throws Exception
     */
    @Override
    public User updateUser(UserDTO userDTO, Authentication authentication) throws Exception {
        String email = authentication.getName();
        User userToUpdate = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
        userToUpdate.setFirstname(userDTO.getFirstname());
        userToUpdate.setLastname(userDTO.getLastname());
        userToUpdate.setCompanyName(userDTO.getCompanyName());
        userToUpdate.setCompanyDomain(userDTO.isCompanyDomain());
        userToUpdate.setCreatedCompany(userDTO.isCreatedCompany());
        userToUpdate.setEmployed(userDTO.isEmployed());

        return userToUpdate;
    }

    /**
     * get user by Id
     * @param id
     * @return
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    /**
     * delete user by id
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {

        try {
            userRepository.deleteById(id);
        }catch(DataAccessException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     *
     * @param user
     */
    @Override
    public void createPasswordResetTokenForUser(User user) throws Exception {
        String token = UUID.randomUUID().toString();
        Date expiryDate = expireDateService.calculateExpiryDate(parseInt(passwordReserTokenExpiration));
        PasswordResetToken myToken = new PasswordResetToken(token, user, expiryDate);
        passwordTokenRepository.save(myToken);
        mailSender.send(sendMailService.constructResetTokenEmail(token, user));
    }

    /**
     * Find user by token
     * @param token
     * @return
     */
    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    /**
     * Update user password
     * @param user
     * @param password
     */
    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
