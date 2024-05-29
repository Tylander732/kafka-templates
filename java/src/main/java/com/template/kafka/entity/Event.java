package com.template.kafka.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Event {
	public Metadata metadata;
	public String data;
}
