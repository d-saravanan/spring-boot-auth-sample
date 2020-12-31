package com.example.demo.auth.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.demo.auth.domain.UserIdentity.fromJWT;
import static com.example.demo.auth.misc.Helpers.isNullOrEmptyString;
import static com.example.demo.auth.misc.SecurityConstants.*;

/*
 * @author: dsaravanan
 */
public class UsernamePasswordAuthorizationFilter extends BasicAuthenticationFilter {

    public UsernamePasswordAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(END_USER_AUTH_HEADER_KEY);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(END_USER_AUTH_HEADER_KEY);

        if (isNullOrEmptyString(token)) return null;

        // parse the token.
        DecodedJWT authJWT = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));

        return (authJWT != null)
                ? new UsernamePasswordAuthenticationToken(fromJWT(authJWT), null, new ArrayList<>())
                : null;
    }
}