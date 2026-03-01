package com.practice.microservices.identity_service.config;

	import org.apache.kafka.clients.admin.NewTopic;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	@Configuration
	public class KafkaTopicConfig {

	    @Bean
	    public NewTopic notificationEventsTopic() {
	        return new NewTopic("notification.events", 3, (short) 1);
	    }
	}
