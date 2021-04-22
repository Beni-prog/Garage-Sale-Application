package com.endava.garagesale.utils;

import com.endava.garagesale.entity.User;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * This class checks whether an email is valid or not
 */
@Component
public class UserChecks {
    /**
     * @param email: String
     * @return true, if email is valid, false otherwise
     */
    public boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public boolean isAccountActive(User.AccountStatus accountStatus) {
        return accountStatus.equals(User.AccountStatus.ACTIVE);
    }
}
