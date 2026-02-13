package com.kkdevportal.SpringKafkaBridge.sender.controller;

import com.kkdevportal.SpringKafkaBridge.sender.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/send")
@Slf4j
public class SenderController {
    @Autowired
    private SenderService senderService;


    @PostMapping("/location")
    public ResponseEntity<?> updateLocation() {

        log.info("Received request to update location : {}",getRandomLocation());
        boolean updatedLocation = senderService.updateLocation(getRandomLocation());
        return  ResponseEntity.ok().body("Updated location :" + updatedLocation);
    }

    private String getRandomLocation() {
        double latitude = ThreadLocalRandom.current().nextDouble(-90.0, 90.0);
        double longitude = ThreadLocalRandom.current().nextDouble(-180.0, 180.0);

        return latitude + "," + longitude;
    }
}
