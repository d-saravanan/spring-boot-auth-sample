package com.example.demo.services;

import java.util.List;

/*
 * @author dsaravanan
 */
public interface IUserRolesService {
    List<Long> getUserRoles(long userId);
}
