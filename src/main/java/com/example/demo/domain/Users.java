package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @author dsaravanan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    private long id;
    private String name;
    private String password;
    private String email;
    private boolean active;
}

