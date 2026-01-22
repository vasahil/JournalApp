package com.sahil.JournalApp.Service;

import com.sahil.JournalApp.entity.User;
import com.sahil.JournalApp.repository.UserRepository;
import com.sahil.JournalApp.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.SimpleTriggerContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    //@Test
    @ParameterizedTest
//    @CsvSource({
//            "Ram",
//            "shym"
//    })

//    @ValueSource(strings = {
//            "Ram"
//    })

    @ArgumentsSource(UserArgumentProvider.class)
    public void testSaveNewUser(User user){
        assertTrue(userService.saveNewUser(user));
 //       assertNotNull(userRepository.findByUsername(name));
//        User user = userRepository.findByUsername("Ram");
//        assertTrue(!user.getJournalEntry().isEmpty());

    }

//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "2,2,4",
//            "4,3,7"
//    })
//    public void test( int a, int b,int expected){
//        assertEquals(expected, a+b);
//    }
}
