package com.example.demo.services;

import com.example.demo.auth.domain.TrustedClientsAuthenticationToken;
import com.example.demo.auth.domain.UserIdentity;
import com.example.demo.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author: dsaravanan
 */
@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    IUsersService usersService;

    @Autowired
    IUserRolesService userRolesService;

    @Override
    public UsernamePasswordAuthenticationToken isAuthenticationValid(Authentication authentication) {
        if (null == authentication) return null;

        Users matchedUser = usersService.GetUserByName(authentication.getName());

        if (null == matchedUser) return null;

        if (!matchedUser.isActive()) return null;

        if (!matchedUser.getPassword().equals(authentication.getCredentials())) return null;

        return (UsernamePasswordAuthenticationToken) constructIdentityForValidUser(matchedUser, true);
    }

    @Override
    public TrustedClientsAuthenticationToken isTrustedClientAuthenticationValid(Authentication authentication) {
        if (null == authentication) return null;
        Users requestor = usersService.GetUserByName(authentication.getName());

        if (null == requestor || !requestor.isActive()) {
            return null;
        }

        return (TrustedClientsAuthenticationToken) constructIdentityForValidUser(requestor, false);
    }

    private AbstractAuthenticationToken constructIdentityForValidUser(Users matchedUser, boolean userAuth) {
        List<Long> userRoles = userRolesService.getUserRoles(matchedUser.getId());

        if (null == userRoles || userRoles.isEmpty()) return null;

        var userIdentity = new UserIdentity(matchedUser.getId(), matchedUser.getName(), matchedUser.getEmail(), userRoles);

        return userAuth
                ? new UsernamePasswordAuthenticationToken(userIdentity, null)
                : new TrustedClientsAuthenticationToken(userIdentity, null);
    }
}
