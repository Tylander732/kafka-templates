package com.tylander.template.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("configuration")
@Data
public class ConfigProperties {
	private Map<String, Config> configs;

	@Data
	public static class Config {
		private String kafkaTopic;
	}
}
