package com.example.demo.services;

import com.example.demo.domain.Users;

import java.util.List;

/*
 * @author dsaravanan
 */
public interface IUsersService {

    Users GetUserByName(String userName);

    List<Users> GetAllUsers();

    Users IsUserCredentialsValid(String userName, String password);
}

