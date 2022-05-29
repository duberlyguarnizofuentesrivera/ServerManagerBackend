package com.duberlyguarnizo.server_manager_backend.controller;

import com.duberlyguarnizo.server_manager_backend.model.HttpResponse;
import com.duberlyguarnizo.server_manager_backend.model.Server;
import com.duberlyguarnizo.server_manager_backend.service.implementation.ServerServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static com.duberlyguarnizo.server_manager_backend.enumeration.Status.ONLINE;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController {

    private final ServerServiceImplementation service;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getServers() {
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now())
                .data(Map.of("servers", service.list(30)))
                .message("Got list of servers!")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<HttpResponse> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now())
                .data(Map.of("server", server))
                .message(server.getStatus() == ONLINE ? "Ping complete, server online" : "Ping failure, server may be offline")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<HttpResponse> saveServer(@RequestBody @Valid Server server) {

        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now())
                .data(Map.of("server", service.create(server)))
                .message("Server created with IP: " + server.getIpAddress())
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now())
                .data(Map.of("server", service.get(id)))
                .message("Server retrieved")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    //TODO: Fix deleting when id is not found
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteServer(@PathVariable("id") Long id) {
        String address = service.get(id).getIpAddress();
        return ResponseEntity.ok(HttpResponse.builder()
                .timeStamp(now())
                .data(Map.of("deleted", service.delete(id)))
                .message("Server " + address + " deleted")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
    //TODO: Add method to manage update of server

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "Downloads/images" + fileName));
    }
}
