package com.falatrabalho.FalaTrabalho.workflow.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.falatrabalho.FalaTrabalho.textimprovement.application.TextEnhancer;
import com.falatrabalho.FalaTrabalho.transcription.application.AudioTranscriber;
import com.falatrabalho.FalaTrabalho.transcription.domain.AudioFile;
import com.falatrabalho.FalaTrabalho.transcription.domain.TranscriptionResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class CurriculumAnswerServiceTest {

	@Mock
	private AudioTranscriber audioTranscriber;

	@Mock
	private TextEnhancer textEnhancer;

	@InjectMocks
	private CurriculumAnswerService curriculumAnswerService;

	@Test
	void shouldProcessCurriculumAnswerAudio() {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);
		when(audioTranscriber.transcribe(any(AudioFile.class)))
				.thenReturn(new TranscriptionResult("teste transcrição"));
		when(textEnhancer.enhance("teste transcrição"))
				.thenReturn("teste melhora");

		CurriculumAnswerResponse response = curriculumAnswerService.processAnswer("personalInfo.name", file);

		assertThat(response.field()).isEqualTo("personalInfo.name");
		assertThat(response.transcription()).isEqualTo("teste transcrição");
		assertThat(response.improvedText()).isEqualTo("teste melhora");

		ArgumentCaptor<AudioFile> audioFileCaptor = ArgumentCaptor.forClass(AudioFile.class);
		verify(audioTranscriber).transcribe(audioFileCaptor.capture());
		AudioFile audioFile = audioFileCaptor.getValue();
		assertThat(audioFile.content()).containsExactly(1, 2, 3);
		assertThat(audioFile.originalFilename()).isEqualTo("resposta.wav");
		assertThat(audioFile.contentType()).isEqualTo("audio/wav");

		verify(textEnhancer).enhance("teste transcrição");
	}

	@Test
	void shouldRejectBlankField() {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);

		assertThatThrownBy(() -> curriculumAnswerService.processAnswer("   ", file))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Field must not be blank");
	}

	@Test
	void shouldRejectUnsupportedField() {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);

		assertThatThrownBy(() -> curriculumAnswerService.processAnswer("unknownField", file))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unsupported curriculum answer field: unknownField");
	}

	@Test
	void shouldRejectEmptyAudioFile() {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[0]
		);

		assertThatThrownBy(() -> curriculumAnswerService.processAnswer("personalInfo.name", file))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Audio file must not be empty");
	}
}
