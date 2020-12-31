package com.example.demo.api;

import com.example.demo.domain.Users;
import com.example.demo.services.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class UsersController {

    @Autowired
    IUsersService usersService;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Users>> GetAllUsers() {
        return ok(usersService.GetAllUsers());
    }
}
