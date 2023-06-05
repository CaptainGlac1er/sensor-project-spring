package com.gwcprojects.sensorproject.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TemperatureController {
    @MessageMapping("/temperature")
    @SendTo("/topic/app")
    public String temperature(String message) {
        return message;
    }
}
