package com.banking.controller;

import com.banking.service.UserService;
import com.banking.dto.UserDTO;
import com.banking.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        String registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String logUser = userService.login(userDTO);
        return new ResponseEntity<>(logUser, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getall() {
        List<User> users = userService.getallUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getbyid(@PathVariable Long id) {
        Optional<User> users = userService.getbyid(id);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users.get(), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody UserDTO userDTO) {
        String logUser = userService.logout(userDTO);
        return new ResponseEntity<>(logUser, HttpStatus.OK);
    }
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody UserDTO userDTO) {
        String logUser = userService.changepassword(userDTO);
        return new ResponseEntity<>(logUser, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String del=userService.deleteUser(id);
        return new ResponseEntity<>(del,HttpStatus.CREATED);
    }

}