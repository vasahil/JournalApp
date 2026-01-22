package com.sahil.JournalApp.Service;

import com.sahil.JournalApp.repository.UserRepository;
import com.sahil.JournalApp.service.UserDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class UserDetailsServiceTests {

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void loadUserByUsernameTest() {

        User entityUser = User.builder()
                .username("Ram")
                .password("inrinck")
                .roles(String.valueOf(List.of("ROLE_USER")))
                .build();

        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(entityUser);

        UserDetails userDetails = userDetailService.loadUserByUsername("Ram");

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("Ram", userDetails.getUsername());
    }

}
