package com.falatrabalho.FalaTrabalho.transcription.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class AudioFileTest {

	private static final byte[] VALID_CONTENT = new byte[] { 1, 2, 3 };

	@Test
	void shouldCreateAudioFileWithValidWavData() {
		AudioFile audioFile = new AudioFile(VALID_CONTENT, "resposta.wav", "audio/wav");

		assertThat(audioFile.content()).containsExactly(VALID_CONTENT);
		assertThat(audioFile.originalFilename()).isEqualTo("resposta.wav");
		assertThat(audioFile.contentType()).isEqualTo("audio/wav");
	}

	@Test
	void shouldRejectEmptyContent() {
		assertThatThrownBy(() -> new AudioFile(new byte[0], "resposta.wav", "audio/wav"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Audio content must not be empty");
	}

	@Test
	void shouldRejectFileWithoutWavExtension() {
		assertThatThrownBy(() -> new AudioFile(VALID_CONTENT, "resposta.mp3", "audio/wav"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Audio file must use .wav extension");
	}

	@Test
	void shouldRejectNonWavContentType() {
		assertThatThrownBy(() -> new AudioFile(VALID_CONTENT, "resposta.wav", "audio/mpeg"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Audio content type must be WAV");
	}

	@Test
	void shouldProtectContentFromExternalMutation() {
		byte[] content = new byte[] { 1, 2, 3 };
		AudioFile audioFile = new AudioFile(content, "resposta.wav", "audio/wav");

		content[0] = 9;
		byte[] returnedContent = audioFile.content();
		returnedContent[1] = 9;

		assertThat(audioFile.content()).containsExactly(1, 2, 3);
	}
}
