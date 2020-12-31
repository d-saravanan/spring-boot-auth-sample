package com.example.demo.services;

import com.example.demo.auth.domain.TrustedClientsAuthenticationToken;
import org.springframework.security.core.Authentication;

/*
 * @author: dsaravanan
 */
public interface IAuthenticationService {

    Authentication isAuthenticationValid(Authentication authentication);

    TrustedClientsAuthenticationToken isTrustedClientAuthenticationValid(Authentication authentication);

}

