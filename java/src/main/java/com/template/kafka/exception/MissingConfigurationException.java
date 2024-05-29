package com.template.kafka.exception;

public class MissingConfigurationException extends Exception {
	public MissingConfigurationException(String errorMessage) {
		super(errorMessage);
	}
}
