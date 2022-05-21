package com.wildcodeschool.patent.controller;

import com.wildcodeschool.patent.DTO.PasswordDTO;
import com.wildcodeschool.patent.DTO.ResetPasswordDTO;
import com.wildcodeschool.patent.entity.ERole;
import com.wildcodeschool.patent.entity.Role;
import com.wildcodeschool.patent.entity.User;
import com.wildcodeschool.patent.payload.request.LoginRequest;
import com.wildcodeschool.patent.payload.request.SignUpRequest;
import com.wildcodeschool.patent.payload.response.JwtResponse;
import com.wildcodeschool.patent.security.UserDetailsImpl;
import com.wildcodeschool.patent.security.jwt.SecurityServiceImpl;
import com.wildcodeschool.patent.service.*;
import com.wildcodeschool.patent.payload.response.MessageResponse;
import com.wildcodeschool.patent.repository.RoleRepository;
import com.wildcodeschool.patent.repository.UserRepository;
import com.wildcodeschool.patent.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private UserServiceImpl userService;


    /**
     * login of a user
     * @param email
     * @param password
     * @return
     */
    public ResponseEntity<?> login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * authenticate of User
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        return login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    /**
     * registration of a new user
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {


         // error message if mail is already in database
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le mail est deja utilisé"));
        }

         // error message if password size is -6 characters
        if (signUpRequest.getPassword().length() < 6) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le mot de passe doit comporter au moins 6 caractères"));
        }


        // create a new user
        User user = new User(signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Set user's roles
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        // add role to user
        if(strRoles ==null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur: Le role est indisponible."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le role est indisponible."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le role est indisponible."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

         // login after registration
        return login(signUpRequest.getEmail(), signUpRequest.getPassword());
    }


    /**
     * reset password and send the mail
     * @param resetPasswordDTO
     */
    @PostMapping("/user/resetPassword")
    public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) throws Exception {

        User user = userRepository.findByEmail(resetPasswordDTO.getEmail()).orElse(null);

        if (user != null) {
            userServiceImpl.createPasswordResetTokenForUser(user);
        }
    }


    /**
     * update password
     * @param passwordDto
     * @return
     */
    @PostMapping("/user/saveResetPassword")
    public ResponseEntity<?> saveResetPassword(@Valid @RequestBody PasswordDTO passwordDto) {
        String result = securityService.validatePasswordResetToken(passwordDto.getToken());

        // error message if password size is -6 characters
        if (passwordDto.getPassword().length() < 6) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le mot de passe doit comporter au moins 6 caractères"));
        }

        // error message if token is invalid
        if(result != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: " + result));
        }

        // messages when token is valid
        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getPassword());
            return  ResponseEntity.ok(new MessageResponse("Mot de passe modifié avec succès"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Une erreur c'est produit"));
        }
    }
}
