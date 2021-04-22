package com.endava.garagesale.service;

import com.endava.garagesale.entity.User;
import com.endava.garagesale.exceptions.NonExistentUserException;
import org.springframework.stereotype.Service;

@Service
public class UserChecksService {
    public void executeUserChecks(User user) throws NonExistentUserException {
        if (user == null) {
            throw new NonExistentUserException();
        }
    }
}
