package com.gwcprojects.sensorproject.listener;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.util.Strings;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.util.Arrays;
import java.util.Objects;
import com.google.gson.JsonParser;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@MessageEndpoint
public class TcpConsumer {
    SimpMessagingTemplate simpMessagingTemplate;
    public TcpConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @ServiceActivator(inputChannel = "sensor-data")
    public void consume(byte[] bytes) {
        String line = new String(bytes);
        this.simpMessagingTemplate.convertAndSend("/topic/temperature", line);
        JsonObject data = JsonParser.parseString(line).getAsJsonObject();
        if(data.has("TYPE") && Objects.equals(data.get("TYPE").getAsString(), "BME680")) {
            String[] dataStrings = {
                    data.get("timestamp").toString(),
                    data.get("temperature").toString(),
                    data.get("gas").toString(),
                    data.get("humidity").toString(),
                    data.get("pressure").toString(),
                    data.get("altitude").toString()
            };
            System.out.println(Strings.join(Arrays.asList(dataStrings), ','));
        } else {
            System.out.println(line);
        }
    }
}
