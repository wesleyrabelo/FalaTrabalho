package com.falatrabalho.FalaTrabalho.textimprovement.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import com.falatrabalho.FalaTrabalho.textimprovement.application.TextEnhancer;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LlamaCppTextEnhancerRealModelTest {

	@Autowired
	private TextEnhancer textEnhancer;

	@Value("${llama.cpp.real-model-test.enabled:false}")
	private boolean realModelTestEnabled;

	@ParameterizedTest
	@MethodSource("textsToEnhance")
	void shouldEnhanceTextUsingRealLocalModel(String originalText) {
		Assumptions.assumeTrue(
				realModelTestEnabled,
				"Real LLM test disabled. Set llama.cpp.real-model-test.enabled=true to run it."
		);

		String enhancedText = textEnhancer.enhance(originalText);

		System.out.println("Texto original:");
		System.out.println(originalText);
		System.out.println("Texto melhorado:");
		System.out.println(enhancedText);

		assertThat(enhancedText).isNotBlank();
		assertThat(enhancedText).isNotEqualTo(originalText);
	}

	private static Stream<String> textsToEnhance() {
		return Stream.of(
				"""
				eu trabalhei de atendente numa loja fazia atendimento aos cliente
				e tambem mexia no caixa e organizava produtos nas prateleira
				""",
				"""
				estudei ensino medio completo na escola joao silva terminei em 2021
				tambem fiz curso de informatica basica
				""",
				"""
				sei trabalhar em equipe sou pontual aprendo rapido
				tenho conhecimento em excel atendimento ao publico e organizacao
				"""
		);
	}
}
