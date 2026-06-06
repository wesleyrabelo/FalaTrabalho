package com.falatrabalho.FalaTrabalho.textimprovement.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class LlamaCppRestClientConfig {

	@Bean
	public RestClient llamaCppRestClient(@Value("${llama.cpp.base-url}") String baseUrl) {
		return RestClient.builder().baseUrl(baseUrl).build();
	}
}
