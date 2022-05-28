package com.duberlyguarnizo.server_manager_backend.service.implementation;

import com.duberlyguarnizo.server_manager_backend.model.Server;
import com.duberlyguarnizo.server_manager_backend.repository.ServerRepository;
import com.duberlyguarnizo.server_manager_backend.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImplementation implements ServerService {
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        Server saved = serverRepository.save(server);
        return saved;
    }

    @Override
    public Collection<Server> list(int limit) {
        return serverRepository.findAll();
    }

    @Override
    public Server get(Long id) {
        return serverRepository.findById(id).orElse(null);
    }

    @Override
    public Server update(Server server) {
        return serverRepository.save(server);
    }

    @Override
    public boolean delete(Long id) {
        try {
            serverRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Server ping(String ipAddress) {
        return null;
    }
}
