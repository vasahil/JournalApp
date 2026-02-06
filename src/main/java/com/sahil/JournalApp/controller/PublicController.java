package com.sahil.JournalApp.controller;

import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.repository.UserRepository;
import com.sahil.JournalApp.service.UserDetailServiceImpl;
import com.sahil.JournalApp.service.UserService;
import com.sahil.JournalApp.utils.JwtUtli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    JwtUtli jwtUtli;

    @PostMapping("/signup")
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());

            String jwt = jwtUtli.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthentication");
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
