package com.template.kafka.service;

public interface EventService<T> {
	void handleEvent(T record, String kafkaTopic);

	void handleEventWithKey(String key, T record, String kafkaTopic);
}
