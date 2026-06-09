package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import com.falatrabalho.FalaTrabalho.resume.application.PdfGenerationException;
import com.falatrabalho.FalaTrabalho.resume.application.ResumePdfService;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ResumePdfControllerTest {

	private ResumePdfService resumePdfService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		resumePdfService = mock(ResumePdfService.class);
		mockMvc = MockMvcBuilders
				.standaloneSetup(new ResumePdfController(resumePdfService))
				.setControllerAdvice(new ResumePdfExceptionHandler())
				.build();
	}

	@Test
	void shouldGenerateResumePdf() throws Exception {
		byte[] pdfContent = "%PDF conteudo".getBytes(StandardCharsets.UTF_8);
		when(resumePdfService.generate(any(CurriculumData.class)))
				.thenReturn(new PdfDocument(pdfContent, "curriculo.pdf"));

		mockMvc.perform(post("/api/resumes/pdf")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validCurriculumDataJson()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_PDF))
				.andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"curriculo.pdf\""))
				.andExpect(content().bytes(pdfContent));

		verify(resumePdfService).generate(any(CurriculumData.class));
	}

	@Test
	void shouldReturnBadRequestWhenRequestBodyIsInvalid() throws Exception {
		mockMvc.perform(post("/api/resumes/pdf")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").isNotEmpty());
	}

	@Test
	void shouldReturnInternalServerErrorWhenPdfGenerationFails() throws Exception {
		when(resumePdfService.generate(any(CurriculumData.class)))
				.thenThrow(new PdfGenerationException("Failed to generate PDF", new RuntimeException("PDF error")));

		mockMvc.perform(post("/api/resumes/pdf")
						.contentType(MediaType.APPLICATION_JSON)
						.content(validCurriculumDataJson()))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message").value("Failed to generate PDF"));
	}

	private String validCurriculumDataJson() {
		return """
				{
				  "personalInfo": {
				    "name": "Joao da Silva",
				    "city": "Sao Paulo",
				    "state": "SP",
				    "maritalStatus": "Solteiro",
				    "age": 28,
				    "phoneNumber": "(11) 99999-9999"
				  },
				  "professionalGoal": "Atuar como auxiliar administrativo.",
				  "professionalSummary": "Profissional com experiencia em atendimento ao publico.",
				  "education": [
				    {
				      "title": "Ensino medio completo",
				      "institution": "Escola Joao Silva",
				      "period": "2021",
				      "description": "Formacao basica completa."
				    }
				  ],
				  "workExperience": [
				    {
				      "company": "Loja Central",
				      "position": "Atendente",
				      "period": "2022 - 2024",
				      "description": "Atendimento ao cliente e organizacao de produtos."
				    }
				  ],
				  "complementaryCourses": [],
				  "qualities": ["Pontualidade", "Organizacao"]
				}
				""";
	}
}
