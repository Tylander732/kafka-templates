package com.template.kafka.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Metadata {
	public Metadata(){
		this.trackingId = getUUID();
	}

	// Setup any Metadata fields you need here.
	
	private UUID trackingId;

	// Always create a random UUID to attach to data going into Kafka topic for tracking
	static UUID getUUID() {
		return UUID.randomUUID();
	}
}
