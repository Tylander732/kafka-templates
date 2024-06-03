package com.template.kafka.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.kafka.config.ConfigProperties;
import com.template.kafka.entity.Event;
import com.template.kafka.entity.Metadata;
import com.template.kafka.exception.MissingConfigurationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaService {
	private final ConfigProperties configProperties;
	private final EventService<String> eventService;

	ObjectMapper mapper = new ObjectMapper();

	public KafkaService(ConfigProperties configProperties, EventService<String> eventService) {
		this.configProperties = configProperties;
		this.eventService = eventService;
	}

	public void runKafkaService(String configId, String requestBody, boolean sendWithKey) throws MissingConfigurationException, JsonProcessingException {
		Event event = getEventData(configId, requestBody);
		processKafkaMessage(configId, event, sendWithKey);
	}

	private void processKafkaMessage(String configId, Event event, boolean sendWithKey) throws MissingConfigurationException, JsonProcessingException {
		ConfigProperties.Config currentConfig = gatherAndVerifyConfig(configId);
		String kafkaStringMessage = buildKafkaStringMessage(event);
		if (!sendWithKey) {
			eventService.handleEvent(kafkaStringMessage, currentConfig.getKafkaTopic());
		} else {
			eventService.handleEventWithKey(kafkaStringMessage, currentConfig.getKafkaTopic(), event.getMetadata().getTrackingId().toString());
		}
	}

	private String buildKafkaStringMessage(Event event) throws JsonProcessingException {
		return mapper.writeValueAsString(event);
	}

	ConfigProperties.Config gatherAndVerifyConfig(String configId) throws MissingConfigurationException {
		ConfigProperties.Config currentConfig = configProperties.getConfigs().get(configId.toLowerCase());

		if (currentConfig == null) {
			throw new MissingConfigurationException("ConfigId: " + configId + " not setup in application.properties file.");
		}

		if (currentConfig.getKafkaTopic().isEmpty()) {
			throw new MissingConfigurationException("Kafka Topic for: " + configId + " missing in application.properties file.");
		}

		return currentConfig;
	}

	private Event getEventData(String configId, String httpBody) {
		Event event = new Event();
		event.data = httpBody;

		Metadata metadata = new Metadata();

		event.metadata = metadata;

		return event;
	}
}
