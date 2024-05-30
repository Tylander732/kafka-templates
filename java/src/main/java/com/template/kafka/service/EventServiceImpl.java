package com.template.kafka.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;


public class EventServiceImpl<T> implements EventService<T> {

	private final KafkaTemplate<String, T> kafkaTemplate;

	public EventServiceImpl(KafkaTemplate<String, T> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public void handleEvent(T record, String kafkaTopic) {
		CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(kafkaTopic, record);
		// Do something with the returned future result
	}

	@Override
	public void handleEventWithKey(String key, T record, String kafkaTopic) {
		CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(kafkaTopic, key, record);
		// Do something with the returned future result
	}
}
