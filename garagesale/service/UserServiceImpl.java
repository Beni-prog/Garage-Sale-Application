package com.endava.garagesale.service;

import com.endava.garagesale.entity.User;
import com.endava.garagesale.repository.UserRepository;
import com.endava.garagesale.utils.UserChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Operations on users in service
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserChecks userChecks;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserChecks userChecks) {
        this.userRepository = userRepository;
        this.userChecks = userChecks;
    }

    /**
     * @param user: User
     * @return: a new added user
     */
    @Override
    public User createUser(User user) {
        if (userChecks.isEmailValid(user.getEmail())) {
            user.setAccountStatus(User.AccountStatus.ACTIVE);
            return userRepository.save(user);
        }
        return null;
    }
}
