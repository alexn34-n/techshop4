package com.example.techshop4;

import com.example.techshop4.entity.User;
import com.example.techshop4.entity.repository.UserRepository;
import com.example.techshop4.exception.NotFoundException;
import com.example.techshop4.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        var uuid = UUID.randomUUID();

        User userFromDb = new User();
        userFromDb.setId(uuid);
        userFromDb.setRole("ADMIN");
        userFromDb.setLogin("123");

        Mockito.doReturn(Optional.of(userFromDb))
                .when(userRepository)
                .findById(uuid);

        Optional<User> user = userRepository.findById(uuid);
        Assertions.assertNotNull(user.get());
        System.out.println(user.get().getLogin());
    }

    @Test
    public void checkThrow() {
        var uuid = UUID.randomUUID();

        User userFromDb = new User();
        userFromDb.setId(uuid);
        userFromDb.setRole("ADMIN");
        userFromDb.setLogin("123");

        Mockito.doReturn(Optional.of(userFromDb))
                .when(userRepository)
                .findById(uuid);

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(uuid));;
    }
}