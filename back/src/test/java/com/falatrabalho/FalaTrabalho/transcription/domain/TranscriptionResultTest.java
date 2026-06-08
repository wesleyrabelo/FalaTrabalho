package com.falatrabalho.FalaTrabalho.transcription.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TranscriptionResultTest {

	@Test
	void shouldCreateTranscriptionResultWithValidText() {
		TranscriptionResult result = new TranscriptionResult("Experiencia com atendimento ao publico");

		assertThat(result.text()).isEqualTo("Experiencia com atendimento ao publico");
	}

	@Test
	void shouldTrimTranscriptionText() {
		TranscriptionResult result = new TranscriptionResult("  Experiencia com atendimento ao publico  ");

		assertThat(result.text()).isEqualTo("Experiencia com atendimento ao publico");
	}

	@Test
	void shouldRejectNullText() {
		assertThatThrownBy(() -> new TranscriptionResult(null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Transcription text must not be blank");
	}

	@Test
	void shouldRejectBlankText() {
		assertThatThrownBy(() -> new TranscriptionResult("   "))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Transcription text must not be blank");
	}
}
