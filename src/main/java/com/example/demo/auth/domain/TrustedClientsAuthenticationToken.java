package com.example.demo.auth.domain;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/*
 * @author dsaravanan
 */
@Getter
public class TrustedClientsAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final Object credentials;
    private final StringBuilder canonicalRequest;
    private final String requestToken;

    public TrustedClientsAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        canonicalRequest = null;
        requestToken = null;
    }

    public TrustedClientsAuthenticationToken(Object principal, Object credentials, StringBuilder canonicalRequest, String incomingToken) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.requestToken = incomingToken;
        this.canonicalRequest = canonicalRequest;
    }
}
