package com.falatrabalho.FalaTrabalho.workflow.infrastructure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.falatrabalho.FalaTrabalho.textimprovement.infrastructure.LlamaCppException;
import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerResponse;
import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerService;

class CurriculumAnswerControllerTest {

	private CurriculumAnswerService curriculumAnswerService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		curriculumAnswerService = mock(CurriculumAnswerService.class);
		mockMvc = MockMvcBuilders
				.standaloneSetup(new CurriculumAnswerController(curriculumAnswerService))
				.setControllerAdvice(new WorkflowExceptionHandler())
				.build();
	}

	@Test
	void shouldProcessCurriculumAnswerAudio() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);
		when(curriculumAnswerService.processAnswer(eq("personalInfo.name"), any(MultipartFile.class)))
				.thenReturn(new CurriculumAnswerResponse(
						"personalInfo.name",
						"teste transcrição",
						"teste mellhora"
				));

		mockMvc.perform(multipart("/api/workflow/answers/{field}", "personalInfo.name").file(file))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.field").value("personalInfo.name"))
				.andExpect(jsonPath("$.transcription").value("teste transcrição"))
				.andExpect(jsonPath("$.improvedText").value("teste mellhora"));

		verify(curriculumAnswerService).processAnswer("personalInfo.name", file);
	}

	@Test
	void shouldReturnBadRequestWhenServiceRejectsInput() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);
		when(curriculumAnswerService.processAnswer(eq("personalInfo.name"), any(MultipartFile.class)))
				.thenThrow(new IllegalArgumentException("Audio file must not be empty"));

		mockMvc.perform(multipart("/api/workflow/answers/{field}", "personalInfo.name").file(file))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Audio file must not be empty"));
	}

	@Test
	void shouldReturnBadRequestWhenFilePartIsMissing() throws Exception {
		mockMvc.perform(multipart("/api/workflow/answers/{field}", "personalInfo.name"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Required part 'file' is not present."));
	}

	@Test
	void shouldReturnBadGatewayWhenTranscriptionFails() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);
		when(curriculumAnswerService.processAnswer(eq("personalInfo.name"), any(MultipartFile.class)))
				.thenThrow(new TranscriptionException("Failed to transcribe audio with whisper.cpp"));

		mockMvc.perform(multipart("/api/workflow/answers/{field}", "personalInfo.name").file(file))
				.andExpect(status().isBadGateway())
				.andExpect(jsonPath("$.message").value("Failed to transcribe audio with whisper.cpp"));
	}

	@Test
	void shouldReturnBadGatewayWhenTextEnhancementFails() throws Exception {
		MockMultipartFile file = new MockMultipartFile(
				"file",
				"resposta.wav",
				"audio/wav",
				new byte[] { 1, 2, 3 }
		);
		when(curriculumAnswerService.processAnswer(eq("personalInfo.name"), any(MultipartFile.class)))
				.thenThrow(new LlamaCppException("Failed to communicate with llama.cpp server"));

		mockMvc.perform(multipart("/api/workflow/answers/{field}", "personalInfo.name").file(file))
				.andExpect(status().isBadGateway())
				.andExpect(jsonPath("$.message").value("Failed to communicate with llama.cpp server"));
	}
}
