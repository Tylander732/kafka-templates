package com.template.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value(value = "${spring.kafka.properties.security.protocol}")
	private String securityProtocol;

	@Value(value = "${spring.kafka.properties.ssl.truststore.location}")
	private String truststoreLocation;

	@Value(value = "${spring.kafka.properties.ssl.truststore.password}")
	private String truststorePassword;

	@Value(value = "${spring.kafka.properties.ssl.key.password}")
	private String sslKeyPassword;

	@Value(value = "${spring.kafka.properties.ssl.keystore.location}")
	private String keystoreLocation;
	
	@Value(value = "${spring.kafka.properties.ssl.keystore.password}")
	private String keystorePassword;

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		if(this.securityProtocol.equalsIgnoreCase("SSL")) {
			config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol.toUpperCase());
			config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation);
			config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);

			config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystoreLocation);
			config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
			config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, sslKeyPassword);
		}

		return new DefaultKafkaProducerFactory<>(config);
	}
}
