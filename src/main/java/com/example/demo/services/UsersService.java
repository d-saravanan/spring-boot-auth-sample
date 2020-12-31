package com.example.demo.services;

import com.example.demo.domain.Users;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.services.UsersStore.AllUsers;


/*
 * @author dsaravanan
 */
@Service
public class UsersService implements IUsersService {
    @Override
    public Users GetUserByName(String userName) {
        return AllUsers().stream().filter(u -> u.getName().equalsIgnoreCase(userName)).findFirst().orElse(null);
    }

    @Override
    public List<Users> GetAllUsers() {
        return AllUsers();
    }

    @Override
    public Users IsUserCredentialsValid(String userName, String password) {
        return AllUsers().stream().anyMatch(u -> u.getName().equalsIgnoreCase(userName) && u.getPassword().equals(password))
                ? this.GetUserByName(userName)
                : null;
    }
}

