package com.banking.service;

import com.banking.dto.UserDTO;
import com.banking.model.User;
import com.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to add a new user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    // Method to update an existing user
    public String registerUser(UserDTO userDTO) {
        // Check if the email is already registered
        List<User> users = searchbyemail(userDTO.getEmail());
        if (!users.isEmpty()) {
            throw new RuntimeException("Email already registered");
        }

        // Convert UserDTO to User entity
        User user = new User();
        user.setUsername(userDTO.getusername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        // Save the new user
        userRepository.save(user);
        return "Register success";
    }

    public List<User> searchbyname(String name) {
        return userRepository.findByUsernameContaining(name);
    }

    public List<User> searchbyemail(String email) {
        return userRepository.findByEmailContaining(email);
    }

    public String login(UserDTO user) {
        List<User> users = searchbyname(user.getusername());
        if (users.isEmpty()) {
            return "user doesn't exist";
        }


        User u = users.get(0);
        if (!u.getPassword().equals(user.getPassword())) {
            return "Password incorrect";
        }
        u.setActive(true);
        userRepository.save(u);
        return "Login Success!";
    }

    //-------------
    public String changepassword(UserDTO user) {
        List<User> users = searchbyemail(user.getEmail());
        if (users.isEmpty()) {
            return "user doesn't exist";
        }
        if (user.getPassword() == null) {
            return "Field cannot be empty";
        }

        User u = users.get(0);
        if (user.getPassword().equals(u.getPassword())) {
            return "Create new password";
        }
        u.setPassword(user.getPassword());
        userRepository.save(u);
        return "Password changed successfully";
    }

    //---------
    public List<User> getallUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getbyid(Long id) {
        return userRepository.findById(id);
    }

    public String logout(UserDTO user) {
        List<User> users = searchbyemail(user.getEmail());
        if (users.isEmpty()) {
            return "Email not found";
        }
        User u = users.get(0);
        u.setActive(false);
        userRepository.save(u);
        return "Logout Success!";
    }

    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    // Delete user by ID
    public String deleteUser(Long id) {
        // Check if the user exists
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {return "User not found";}// Delete the user

        userRepository.deleteById(id);
        return id+" deleted successfully";

    }
}

