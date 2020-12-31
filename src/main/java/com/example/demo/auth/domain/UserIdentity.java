package com.example.demo.auth.domain;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.example.demo.auth.misc.SecurityConstants.*;

/*
 * @author dsaravanan
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentity {
    private long userId;
    private String username;
    private String email;
    private List<Long> roles;

    public static UserIdentity fromJWT(DecodedJWT jwt) {
        long userId = jwt.getClaim(ID_CLAIM_NAME).asLong();
        List<Long> roles = jwt.getClaim(ROLES_CLAIM_NAME).asList(Long.class);
        return new UserIdentity(userId, jwt.getSubject(), jwt.getClaim(EMAIL_CLAIM_NAME).asString(), roles);
    }
}

