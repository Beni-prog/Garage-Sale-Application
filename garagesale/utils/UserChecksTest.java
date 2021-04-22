package com.endava.garagesale.utils;

import com.endava.garagesale.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.endava.garagesale.entity.User.AccountStatus.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserChecksTest {
    private UserChecks userChecks;

    private static String VALID_EMAIL = "test@test.com";
    private static String INVALID_EMAIL = "test.com";
    private static User.AccountStatus VALID_ACCOUNT = ACTIVE;
    private static User.AccountStatus INVALID_ACCOUNT = User.AccountStatus.BLOCKED;

    @Before
    public void setUp() {
        userChecks = new UserChecks();
    }

    @Test
    public void testUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setName("Beni");
        user.setAccountStatus(ACTIVE);


        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("Beni", user.getName());
        assertEquals(ACTIVE, user.getAccountStatus());
    }

    @Test
    public void emailIsValid() {
        assertTrue(userChecks.isEmailValid(VALID_EMAIL));
    }

    @Test
    public void emailIsNotValid() {
        assertFalse(userChecks.isEmailValid(INVALID_EMAIL));
    }

    @Test
    public void accountIsValid() {
        assertTrue(userChecks.isAccountActive(VALID_ACCOUNT));
    }

    @Test
    public void accountIsNotValid() {
        assertFalse(userChecks.isAccountActive(INVALID_ACCOUNT));
    }

}
