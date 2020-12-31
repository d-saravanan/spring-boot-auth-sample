package com.example.demo.auth.misc;

/*
 * @author dsaravanan
 */
public class SecurityConstants {
    public static final String SECRET = "Awxed$edf6yhujcvd%6@#edghtbnodwsdcbujjj=0cde4tbhukoinvxaqs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String END_USER_AUTH_HEADER_KEY = "Authorization";
    /*
     * The trusted clients authz flow
     */
    public static final String TRUSTED_CLIENTS_AUTH_HEADER_KEY = "X-TC-TOKEN";
    public static final String TRUSTED_CLIENTS_CLIENTID = "X-TC-CLIENTID";
    public static final String TRUSTED_CLIENTS_REQUESTOR = "X-TC-REQUESTOR";
    public static final String TRUSTED_CLIENTS_REQUEST_DATE = "X-TC-DATE";

    public static final String ID_CLAIM_NAME = "id",
            ROLES_CLAIM_NAME = "roles", EMAIL_CLAIM_NAME = "email";
}

