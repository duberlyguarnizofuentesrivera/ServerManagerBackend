package com.duberlyguarnizo.server_manager_backend.service;

import com.duberlyguarnizo.server_manager_backend.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server server);

    Collection<Server> list(int limit);

    Server get(Long id);

    Server update(Server server);

    boolean delete(Long id);

    Server ping(String ipAddress) throws IOException;
}
