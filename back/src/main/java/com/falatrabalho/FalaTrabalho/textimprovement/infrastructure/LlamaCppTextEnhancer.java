package com.falatrabalho.FalaTrabalho.textimprovement.infrastructure;

import java.util.List;
import java.util.Map;

import com.falatrabalho.FalaTrabalho.textimprovement.application.TextEnhancer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import tools.jackson.databind.JsonNode;

@Service
public class LlamaCppTextEnhancer implements TextEnhancer {

	private static final String CHAT_ENDPOINT = "/v1/chat/completions";
	private static final String SYSTEM_MESSAGE = """
			Corrija erros de escrita, pontuacao e concordancia do texto recebido.
			Melhore a clareza e organize as frases mantendo fidelidade total as informacoes fornecidas.
			Retorne somente o texto corrigido, sem explicacoes, comentarios ou marcadores.
			""";

	private final RestClient restClient;
	private final String model;
	private final double temperature;
	private final int maxTokens;

	public LlamaCppTextEnhancer(
			@Qualifier("llamaCppRestClient") RestClient restClient,
			@Value("${llama.cpp.model}") String model,
			@Value("${llama.cpp.temperature}") double temperature,
			@Value("${llama.cpp.max-tokens}") int maxTokens
	) {
		this.restClient = restClient;
		this.model = model;
		this.temperature = temperature;
		this.maxTokens = maxTokens;
	}

	@Override
	public String enhance(String text) {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("Text must not be blank");
		}

		try {
			JsonNode response = restClient.post()
					.uri(CHAT_ENDPOINT)
					.body(Map.of(
							"model", model,
							"temperature", temperature,
							"stream", false,
							"max_tokens", maxTokens,
							"messages", List.of(
									Map.of("role", "system", "content", SYSTEM_MESSAGE),
									Map.of("role", "user", "content", text.trim())
							)
					))
					.retrieve()
					.body(JsonNode.class);

			return extractContent(response);
		}
		catch (RestClientException exception) {
			throw new LlamaCppException("Failed to communicate with llama.cpp server", exception);
		}
	}

	private String extractContent(JsonNode response) {
		if (response == null) {
			throw new LlamaCppException("llama.cpp response does not contain generated content");
		}

		String content = response.path("choices").path(0).path("message").path("content").asString();
		if (content == null || content.isBlank()) {
			content = response.path("message").path("content").asString();
		}
		if (content.isBlank()) {
			throw new LlamaCppException("llama.cpp returned empty generated content");
		}

		return content.trim();
	}
}
