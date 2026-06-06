package com.falatrabalho.FalaTrabalho.transcription.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import org.junit.jupiter.api.Test;

class WavAudioSamplesExtractorTest {

	private final WavAudioSamplesExtractor samplesExtractor = new WavAudioSamplesExtractor();

	@Test
	void shouldExtractSamplesFromValidWavAudio() {
		byte[] wavContent = loadTestResource("audio/teste-curto.wav");
		AudioFile audioFile = new AudioFile(wavContent, "teste-curto.wav", "audio/wav");

		float[] samples = samplesExtractor.extractSamples(audioFile);

		assertThat(samples).isNotEmpty();
		for (float sample : samples) {
			assertThat(sample).isBetween(-1.0f, 1.0f);
		}
	}

	@Test
	void shouldRejectInvalidAudioContent() {
		AudioFile audioFile = new AudioFile(new byte[] { 1, 2, 3 }, "resposta.wav", "audio/wav");

		assertThatThrownBy(() -> samplesExtractor.extractSamples(audioFile))
				.isInstanceOf(TranscriptionException.class)
				.hasMessage("Unsupported audio format");
	}

	private byte[] loadTestResource(String resourcePath) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
			assertThat(inputStream).isNotNull();
			return inputStream.readAllBytes();
		}
		catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}
}
