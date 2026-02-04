package com.plm.pdmservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic productEventsTopic() {
        return TopicBuilder.name("product-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic bomEventsTopic() {
        return TopicBuilder.name("bom-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}