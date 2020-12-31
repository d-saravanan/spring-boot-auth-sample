package com.example.demo.auth.filters;

import com.auth0.jwt.JWT;
import com.example.demo.auth.domain.UserAuthentication;
import com.example.demo.auth.domain.UserIdentity;
import com.example.demo.auth.misc.SecurityConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.demo.auth.misc.SecurityConstants.*;

/*
 * @author dsaravanan
 */
public class UserNamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public UserNamePasswordAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse) throws AuthenticationException {
        try {
            UserAuthentication creds = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), UserAuthentication.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserIdentity userIdentity = (UserIdentity) authResult.getPrincipal();

        if (null == userIdentity) return;

        String token = JWT.create()
                .withSubject(userIdentity.getUsername())
                .withClaim(ID_CLAIM_NAME, userIdentity.getUserId())
                .withArrayClaim(ROLES_CLAIM_NAME, userIdentity.getRoles().toArray(new Long[0]))
                .withClaim(EMAIL_CLAIM_NAME, userIdentity.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        response.addHeader(END_USER_AUTH_HEADER_KEY, SecurityConstants.TOKEN_PREFIX + token);
    }
}
