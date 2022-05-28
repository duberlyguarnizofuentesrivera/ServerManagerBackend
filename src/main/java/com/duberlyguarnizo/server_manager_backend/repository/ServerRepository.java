package com.duberlyguarnizo.server_manager_backend.repository;

import com.duberlyguarnizo.server_manager_backend.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAddress);

}
