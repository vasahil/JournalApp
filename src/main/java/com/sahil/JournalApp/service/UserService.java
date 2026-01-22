package com.sahil.JournalApp.service;

import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList("USER"));
            user.setUsername(null);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            logger.info("Sahil vashisht");
            return false;
        }

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById( ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
       return userRepository.findByUsername(username);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }
}



// controller ---> service ---> repository