package com.endava.garagesale.service;

import com.endava.garagesale.entity.User;
import com.endava.garagesale.repository.UserRepository;
import com.endava.garagesale.utils.UserChecks;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.endava.garagesale.entity.User.AccountStatus.ACTIVE;
import static com.endava.garagesale.entity.User.AccountStatus.BLOCKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private UserRepository userRepository;
    private UserChecks userChecks;
    private UserService userService;

    @Before
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userChecks = new UserChecks();
        userService = new UserServiceImpl(userRepository, userChecks);
    }

    @Test
    public void createUserTest() {
        User user = new User("User1", "test@test.com", BLOCKED);
        when(userRepository.save(any())).thenReturn(user);
        User myUser = userService.createUser(user);

        assertEquals("User1", myUser.getName());
        assertEquals("test@test.com", myUser.getEmail());
        assertEquals(ACTIVE, myUser.getAccountStatus());
    }
}
