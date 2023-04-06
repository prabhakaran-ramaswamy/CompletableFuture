package com.example.demo;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	@Autowired
	private ResultService resultService;

	@GetMapping("/result")
	CompletableFuture<Response> getResult() throws InterruptedException, ExecutionException {
		Instant start = Instant.now();
		CompletableFuture<Response> allData = resultService.allData();
		Instant finish = Instant.now();
		long timeElapsed = Duration.between(start, finish).toMillis();
		System.out.println("Total time: " + timeElapsed + " ms");
		return allData;
	}

	@GetMapping("/test")
	String test() {
		return "test";
	}
}
