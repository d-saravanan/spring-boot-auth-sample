package com.example.demo.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @author dsaravanan
 */
@Getter
@Setter
@NoArgsConstructor
public class UserAuthentication {
    private String username;
    private String password;
}
