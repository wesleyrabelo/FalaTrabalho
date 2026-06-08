package com.falatrabalho.FalaTrabalho.transcription.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.falatrabalho.FalaTrabalho.transcription.application.AudioTranscriber;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import com.falatrabalho.FalaTrabalho.transcription.domain.TranscriptionResult;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WhisperCppAudioTranscriberRealModelTest {

	@Autowired
	private AudioTranscriber audioTranscriber;

	@Value("${whisper.cpp.real-model-test.enabled:true}")
	private boolean realModelTestEnabled;

	@ParameterizedTest
	@MethodSource("audioFiles")
	void shouldTranscribeWavAudioUsingRealLocalModel(Path audioPath) throws IOException {
		Assumptions.assumeTrue(
				realModelTestEnabled,
				"Real Whisper test disabled. Set whisper.cpp.real-model-test.enabled=true to run it."
		);

		assertThat(audioPath)
				.as("Test audio file must exist at %s", audioPath)
				.exists();

		AudioFile audioFile = new AudioFile(
				Files.readAllBytes(audioPath),
				audioPath.getFileName().toString(),
				"audio/wav"
		);

		TranscriptionResult result = audioTranscriber.transcribe(audioFile);

		System.out.println("Texto transcrito:");
		System.out.println(result.text());

		assertThat(result.text()).isNotBlank();
	}

	private static Stream<Path> audioFiles() {
		return Stream.of(
				Path.of("src/test/resources/audio/teste-curto.wav"),
				Path.of("src/test/resources/audio/teste-longo.wav")
		);
	}
}
