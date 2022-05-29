package com.duberlyguarnizo.server_manager_backend;

import com.duberlyguarnizo.server_manager_backend.model.Server;
import com.duberlyguarnizo.server_manager_backend.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.duberlyguarnizo.server_manager_backend.enumeration.Status.OFFLINE;
import static com.duberlyguarnizo.server_manager_backend.enumeration.Status.ONLINE;

@SpringBootApplication
public class ServerManagerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagerBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner run(ServerRepository repo){
        return args -> {
            repo.save(new Server(null,"192.168.10.1","Casa",
                    "Windows 11","PC",null, ONLINE));
            repo.save(new Server(null,"192.168.12.5","Oficina",
                    "Linux","Laptop",null, OFFLINE));
        };
    }

}
