package com.kkdevportal.SpringKafkaBridge.receiver.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class ReceiverService {
    @PostMapping("/location")
    public ResponseEntity<?> updateLocation() {

        return ResponseEntity.ok().build();
    }
}
