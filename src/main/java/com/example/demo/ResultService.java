package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResultService {

	@Autowired
	RestTemplate restTemplate;

	@Async
	public CompletableFuture<String> callMsgService() {
		final String msgServiceUrl = "http://localhost:8080/test";

		final String response = restTemplate.getForObject(msgServiceUrl, String.class);

		return CompletableFuture.completedFuture(response);
	}

	@Async
	public CompletableFuture<Response> allData() throws InterruptedException, ExecutionException {
		List<CompletableFuture<String>> allFutures = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			allFutures.add(callMsgService());
		}
		CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

		return CompletableFuture.completedFuture(formateResponse(allFutures));
	}

	Response formateResponse(List<CompletableFuture<String>> allFutures)
			throws InterruptedException, ExecutionException {
		Response res = new Response();
		CompletableFuture<String> completableFuture = allFutures.get(0);
		res.setOne(completableFuture.get());
		completableFuture = allFutures.get(1);
		res.setTwo(completableFuture.get());
		completableFuture = allFutures.get(2);
		res.setThree(completableFuture.get());
		return res;
	}
}
