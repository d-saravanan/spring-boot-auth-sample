package com.example.demo.auth.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/*
 * @author dsaravanan
 */
public class TrustedClientAuthToken extends AbstractAuthenticationToken {
    public TrustedClientAuthToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
