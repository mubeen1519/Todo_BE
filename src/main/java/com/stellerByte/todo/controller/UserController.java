package com.stellerByte.todo.controller;

import com.stellerByte.todo.entity.Users;
import com.stellerByte.todo.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @GetMapping("/check")
    public String check(){
        return "Running successfully";
    }


    @PostMapping("/signup")
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        try {
            userDetailsService.createUser(users);
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        String verify = userDetailsService.verify(users);
        return new ResponseEntity<>(verify,HttpStatus.OK);
    }
}

