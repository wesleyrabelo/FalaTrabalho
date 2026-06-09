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

import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerResponse;
import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerService;

class CurriculumAnswerControllerTest {

	private CurriculumAnswerService curriculumAnswerService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		curriculumAnswerService = mock(CurriculumAnswerService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new CurriculumAnswerController(curriculumAnswerService)).build();
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
}
