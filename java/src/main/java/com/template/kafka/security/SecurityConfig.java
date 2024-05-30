package com.template.kafka.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Value("${audience}")
	private String audience;

	@Value("${issuer}")
	private String issuer;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("").permitAll() // Add any end points within the matchers that you want to be accessible by all
				.requestMatchers("").hasAuthority("") // Add endpoints that you want only accessible by a certain authority
				.anyRequest().authenticated())
			.oauth2ResourceServer(authConfigurer -> authConfigurer.jwt(Customizer.withDefaults()))
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	JwtDecoder jwtDecoders() {

		NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);

		OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience);
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
		OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

		jwtDecoder.setJwtValidator(withAudience);

		return jwtDecoder;
	}
}
