package com.gwcprojects.sensorproject.listener;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ip.tcp.TcpReceivingChannelAdapter;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.ByteArraySingleTerminatorSerializer;

@Configuration
public class TcpConfiguration {
    @Bean
    public TcpReceivingChannelAdapter demoTcpReceivingChannelAdapter() {
        TcpReceivingChannelAdapter adapter = new TcpReceivingChannelAdapter();
        adapter.setConnectionFactory(prepareDemoTcpNetClientConnectionFactory());
        adapter.setClientMode(true);
        adapter.setOutputChannelName("sensor-data");
        return adapter;
    }

    private TcpNetClientConnectionFactory prepareDemoTcpNetClientConnectionFactory(){
        TcpNetClientConnectionFactory factory =
                new TcpNetClientConnectionFactory("raspberrypi.local", 2048);
        factory.setDeserializer(new ByteArraySingleTerminatorSerializer((byte) '\n'));
        return factory;
    }
}
