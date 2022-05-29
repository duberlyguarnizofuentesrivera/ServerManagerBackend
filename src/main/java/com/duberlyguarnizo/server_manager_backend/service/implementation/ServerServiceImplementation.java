package com.duberlyguarnizo.server_manager_backend.service.implementation;

import com.duberlyguarnizo.server_manager_backend.enumeration.Status;
import com.duberlyguarnizo.server_manager_backend.model.Server;
import com.duberlyguarnizo.server_manager_backend.repository.ServerRepository;
import com.duberlyguarnizo.server_manager_backend.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImplementation implements ServerService {
    private final ServerRepository serverRepository;

    @Override
    public Server create(Server server) {
        log.info("Created new server: {}", server.getHostname());
        server.setImgUrl(setServerImageURL());
        return serverRepository.save(server);
    }

    private String setServerImageURL() {
        return "https://picsum.photos/150?random=1";
    }

    @Override
    public Collection<Server> list(int limit) {
        //return only the first "limit" servers
        return serverRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        return serverRepository.findById(id).orElse(null);
    }

    @Override
    public Server update(Server server) {
        log.info("Updated server: {}", server.getHostname());
        return serverRepository.save(server);
    }

    @Override
    public boolean delete(Long id) {
        try {
            serverRepository.deleteById(id);
            log.info("Deleted server with id: {}", id);
            return true;
        } catch (Exception e) {
            log.warn("Failed to delete server with id: {}", id);
            return false;
        }
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server: {}", ipAddress);
        InetAddress inetAddress = InetAddress.getByName(ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        server.setStatus(inetAddress.isReachable(5000) ? Status.ONLINE : Status.OFFLINE);
        serverRepository.save(server);
        return server;
    }
}
