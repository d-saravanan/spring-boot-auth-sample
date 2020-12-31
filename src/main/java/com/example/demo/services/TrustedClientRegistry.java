package com.example.demo.services;

import org.springframework.stereotype.Service;

/*
 * @author dsaravanan
 */
@Service
public class TrustedClientRegistry implements ITrustedClientRegistry {

    @Override
    public String getClientSecret(String clientId) {
        return (null == clientId || clientId.isEmpty())
                ? null
                : TrustedClientsStore.getClientSecret(clientId);
    }
}

