package com.example.demo.auth.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
 * @author dsaravanan
 */
public class UserPasswordAuthToken extends UsernamePasswordAuthenticationToken {

    public UserPasswordAuthToken(String userName, Object credentials) {
        super(userName, credentials);
    }

    public UserPasswordAuthToken(Object userName, Object password, Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, authorities);
    }
}

