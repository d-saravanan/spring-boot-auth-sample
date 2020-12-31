package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author dsaravanan
 */
@Service
public class UserRolesService implements IUserRolesService {

    @Override
    public List<Long> getUserRoles(long userId) {
        return UserRolesStore.GetAllUserRoles().get(userId);
    }
}
