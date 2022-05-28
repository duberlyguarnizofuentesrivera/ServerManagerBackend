package com.duberlyguarnizo.server_manager_backend.enumeration;

import java.security.PrivateKey;

public enum Status {
    ONLINE("SERVER_UP"),
    OFFLINE("SERVER_DOWN"),
    UNKNOWN("SERVER_UNKNOWN");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
