package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author dsaravanan
 */
public class UserRolesStore {

    public static Map<Long, List<Long>> GetAllUserRoles() {
        Map<Long, List<Long>> userRoles = new HashMap<>();
        for (long i = 0; i < 15; i++) {
            userRoles.putIfAbsent(i, new ArrayList<>());
            userRoles.get(i).add(getRandomRoleId(i));
        }
        return userRoles;
    }

    private static long getRandomRoleId(long i) {
        return i % 4;
    }

    public static Long AdminstratorRole = 1L;
    public static Long Manager = 2L;
    public static Long Employee = 3L;
    public static Long User = 4L;

}
