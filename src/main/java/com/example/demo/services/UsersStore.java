package com.example.demo.services;

import com.example.demo.domain.Users;

import java.util.ArrayList;
import java.util.List;

/*
 * @author dsaravanan
 */
public class UsersStore {

    public static List<Users> AllUsers() {

        List<Users> randomUsers = new ArrayList<>();
        for (long i = 0; i < 15; i++) {
            randomUsers.add(new Users(i, getRandomString(i), "Abcd@" + i, getRandomString(i) + "@company.com", true));
        }
        return randomUsers;
    }

    private static String getRandomString(long i) {
        return names[(int) (i % 14)];
    }

    private static String[] names = new String[]{"John", "Smith", "Steve", "William", "Bill", "Legolas", "Kate", "Jack", "Sawyer", "Jin", "Tom", "Annie", "Agustus", "Tim", "Ron", "Sam"};
}

