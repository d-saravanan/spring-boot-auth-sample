package com.example.demo.auth.providers;

import com.example.demo.auth.domain.TrustedClientsAuthenticationToken;
import com.example.demo.services.IAuthenticationService;
import com.example.demo.services.ITrustedClientRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static com.example.demo.auth.misc.Helpers.isNullOrEmptyString;

@Service
public class TrustedClientsAuthProvider implements AuthenticationProvider {

    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    ITrustedClientRegistry trustedClientRegistry;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        TrustedClientsAuthenticationToken authToken = (TrustedClientsAuthenticationToken) authentication;

        if (null == authToken) return null;

        StringBuilder canonicalRequest = authToken.getCanonicalRequest();
        String requestToken = authToken.getRequestToken();

        if (null == canonicalRequest || isNullOrEmptyString(requestToken)) return null;

        String clientSecret = trustedClientRegistry.getClientSecret(authToken.getCredentials().toString());
        if (isNullOrEmptyString(clientSecret)) return null;

        canonicalRequest.append(clientSecret);

        String encodedCanonicalRequest = new String(canonicalRequest.toString().getBytes(StandardCharsets.UTF_8));

        String hash = BCrypt.hashpw(encodedCanonicalRequest, new String(clientSecret.getBytes(StandardCharsets.UTF_8)));

        if (!requestToken.equals(hash)) return null;

        return authenticationService.isTrustedClientAuthenticationValid(authentication);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == TrustedClientsAuthenticationToken.class;
    }
}