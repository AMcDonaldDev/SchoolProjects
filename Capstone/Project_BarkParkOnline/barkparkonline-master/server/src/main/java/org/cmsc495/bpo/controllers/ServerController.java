package org.cmsc495.bpo.controllers;

import org.cmsc495.bpo.controllers.response.ServerStatus;
import org.cmsc495.bpo.controllers.response.ServerStatus.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    
    @GetMapping("/status")
    public ResponseEntity<ServerStatus> getStatus() {
        return ResponseEntity.ok().body(new ServerStatus().status(Status.UP_AND_RUNNING));
    }
}
