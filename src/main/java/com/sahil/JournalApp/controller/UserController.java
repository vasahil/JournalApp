package com.sahil.JournalApp.controller;

//it is a component that contains all classes/methods for http request...

import com.sahil.JournalApp.entity.JournalEntry;
import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.repository.UserRepository;
import com.sahil.JournalApp.service.JournalEntryService;
import com.sahil.JournalApp.service.UserService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public List<User> getAllUsers (){
//        return userService.getAll();
//    }



//   @PutMapping("/{username}")
//  
//    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
//        User byUserName = userService.findByUserName(username);
//        if(byUserName!=null){
//            byUserName.setUsername(user.getUsername());
//            byUserName.setPassword(user.getPassword());
//             userService.saveEntry(byUserName);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }


    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User byUserName = userService.findByUserName(username);
        byUserName.setUsername(user.getUsername());
        byUserName.setPassword(user.getPassword());
        userService.saveNewUser(byUserName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}


// ? -> generic type