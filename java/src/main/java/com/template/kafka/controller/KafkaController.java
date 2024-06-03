package com.template.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.template.kafka.exception.MissingConfigurationException;
import com.template.kafka.service.KafkaService;

@RestController
@RequestMapping("/api")
public class KafkaController {

	private final KafkaService kafkaService;

	public KafkaController(KafkaService kafkaService) {
		this.kafkaService = kafkaService;
	}

	@PostMapping("/message")
	public ResponseEntity<?> publishDataToKafka(
			@RequestParam(name = "configId") String configId,
			@RequestParam(required = false, defaultValue = "false") boolean useKey,
			@RequestBody String requestBody) {

		try {
			kafkaService.runKafkaService(configId, requestBody, useKey);
		} catch (JsonProcessingException | MissingConfigurationException e) {
			//TODO: some error logging and response returning here
		}

		return ResponseEntity.ok("Message processed successfully");
	}
}
