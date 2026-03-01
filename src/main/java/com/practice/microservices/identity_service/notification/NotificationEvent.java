package com.practice.microservices.identity_service.notification;

import java.time.Instant;
import java.util.Map;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class NotificationEvent {
	
	
	private String eventId;
	private String traceId;
	private EventType eventType;
	private NotificationChannel channel;
	private String recipient;
	private String templateId;
	private  Map<String, Object> payload;
	private Instant createdAt;
	private  String version;
}
