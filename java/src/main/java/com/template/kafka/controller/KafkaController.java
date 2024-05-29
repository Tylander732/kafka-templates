package com.template.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KafkaController {

	@GetMapping("response")
	public ResponseEntity<?> publishDataToKafka() {
		return ResponseEntity.ok("hello");
	}
}
