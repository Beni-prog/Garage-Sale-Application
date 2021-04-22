package com.endava.garagesale.controller;

import com.endava.garagesale.entity.User;
import com.endava.garagesale.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the user controller
 */
@RestController
@RequestMapping("users/")
public class UserController {
    private final UserService userService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param user: User
     * @return: a new user
     */
    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("\nRequest received for adding a user {}", user);
        return ResponseEntity.ok(userService.createUser(user));
    }
}
