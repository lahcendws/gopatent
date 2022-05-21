package com.wildcodeschool.patent.controller;

import com.wildcodeschool.patent.DTO.UserDTO;
import com.wildcodeschool.patent.entity.User;
import com.wildcodeschool.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Thuy: Get connected user
     * @param authentication
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity<User> getConnectedUser(Authentication authentication) throws Exception {
        try {
            User user = userService.getUserByEmail(authentication.getName());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Thuy: Update a user from the front  into the database using the UserDTO
     * @param userDTO
     * @param authentication
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO, Authentication authentication) throws Exception {
        try {
            User user = userService.updateUser(userDTO, authentication);

            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * display all users
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers()) ;
    }

    /**
     * delete user
     * @param id
     * @return
     */
    @RequestMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        }catch(RuntimeException ex){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }
}

