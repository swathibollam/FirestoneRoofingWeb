package com.vavv.web;

import com.vavv.web.model.User;
import com.vavv.web.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * * Created by sbollam on 11/9/17.
 */
@RestController
@RequestMapping("/testcontrol")
public class Control1 {

    private final UserRepository userRepository;

    public Control1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/hello")
    public String sayHello() {
        return "ello";
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting getGreeting(@RequestParam(value = "name", defaultValue = "globe") String name) {
        System.out.println("Name sent: " + name);
        return new Greeting(1, "hello " + name);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return null;
    }
}

