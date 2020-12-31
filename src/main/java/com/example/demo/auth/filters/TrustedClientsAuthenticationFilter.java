package com.example.demo.auth.filters;

import com.example.demo.auth.domain.TrustedClientsAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Locale;

import static com.example.demo.auth.misc.Helpers.isNullOrEmptyString;
import static com.example.demo.auth.misc.SecurityConstants.*;

/*
 * @author: dsaravanan
 */
@Slf4j
public class TrustedClientsAuthenticationFilter extends BasicAuthenticationFilter {

    public TrustedClientsAuthenticationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(TRUSTED_CLIENTS_AUTH_HEADER_KEY);

        if (isNullOrEmptyString(header)) {
            chain.doFilter(req, res);
            return;
        }

        TrustedClientsAuthenticationToken authentication = getAuthentication(req, res);
        if (null == authentication) return;
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private TrustedClientsAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse res) throws IOException {

        String token = request.getHeader(TRUSTED_CLIENTS_AUTH_HEADER_KEY);

        if (isNullOrEmptyString(token)) return null;

        StringBuilder canonicalReq = constructRequestHash(request, res);
        TrustedClientsAuthenticationToken authToken = new TrustedClientsAuthenticationToken(request.getHeader(TRUSTED_CLIENTS_REQUESTOR),
                request.getHeader(TRUSTED_CLIENTS_CLIENTID), canonicalReq, token);

        return authToken;
    }

    private StringBuilder constructRequestHash(HttpServletRequest request, HttpServletResponse res) throws IOException {

        String requestHost = request.getRemoteHost(), method = request.getMethod(), path = request.getRequestURI(),
                clientId = request.getHeader(TRUSTED_CLIENTS_CLIENTID),
                requestedBy = request.getHeader(TRUSTED_CLIENTS_REQUESTOR);

        long requestedAt = Long.parseLong(request.getHeader(TRUSTED_CLIENTS_REQUEST_DATE));

        if (isNullOrEmptyString(clientId)) {
            log.error("Invalid or missing client id");
            return null;
        }

        if (isNullOrEmptyString(requestedBy)) {
            log.error("invalid or missing requestedBy");
            return null;
        }

        if (0 == requestedAt) {
            log.error("Invalid or missing requested at header");
            return null;
        }

        if (requestedAt == 0 || requestedAt > (getCurrentTime() + 900)) {
            log.error("requested time instant pre-dated or post-dated");
            return null;
        }

        StringBuilder canonicalRequest = new StringBuilder(method);
        canonicalRequest.append(System.lineSeparator());
        canonicalRequest.append(path);
        canonicalRequest.append(System.lineSeparator());
        canonicalRequest.append(requestHost);
        canonicalRequest.append(System.lineSeparator());
        canonicalRequest.append(requestedBy.toLowerCase(Locale.ROOT));
        canonicalRequest.append(System.lineSeparator());
        canonicalRequest.append(requestedAt);
        canonicalRequest.append(System.lineSeparator());
        canonicalRequest.append(clientId);
        canonicalRequest.append(System.lineSeparator());

        return canonicalRequest;
    }

    private static long getCurrentTime() {
        return Instant.now().toEpochMilli() / 1000;
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(401, failed.getMessage());
    }
}