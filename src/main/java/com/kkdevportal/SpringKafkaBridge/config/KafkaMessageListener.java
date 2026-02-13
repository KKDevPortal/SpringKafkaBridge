package com.kkdevportal.SpringKafkaBridge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaMessageListener {


    @KafkaListener(topics = AppConstant.LOCATION_TOPIC_NAME, groupId = AppConstant.GROUP_ID)
    public void updateLocation(String location) {
        System.out.println("Message listener:: Received location: " + location);
    }
}
