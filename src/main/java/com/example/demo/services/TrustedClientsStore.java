package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

/*
 * @author dsaravanan
 */
public class TrustedClientsStore {
    private static final Map<String, String> clientSecrets = new HashMap<>();

    private static void Init() {
        if (clientSecrets.isEmpty()) {
            //This normally loads from a data store
            clientSecrets.putIfAbsent("9bd93a2405384bfcbfea6daf32f5b652", "$2b$12$CuD0CV8oKClqZUIKHQOkeO");
        }
    }

    public static String getClientSecret(String clientId) {
        if (null == clientId || clientId.isEmpty()) return null;
        Init();
        return clientSecrets.get(clientId);
    }

    public static Map<String, String> getTrustedClients() {
        return clientSecrets;
    }
}
