package com.falatrabalho.FalaTrabalho.resume.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import com.falatrabalho.FalaTrabalho.resume.support.CurriculumDataFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResumePdfServiceTest {

	@Mock
	private ResumeHtmlGenerator resumeHtmlGenerator;

	@Mock
	private HtmlToPdfConverter htmlToPdfConverter;

	@InjectMocks
	private ResumePdfService resumePdfService;

	@Test
	void shouldGeneratePdfFromCurriculumData() {
		CurriculumData curriculumData = CurriculumDataFixture.complete();
		HtmlDocument htmlDocument = new HtmlDocument("<html><body>Curriculo</body></html>");
		PdfDocument pdfDocument = new PdfDocument("%PDF".getBytes(), "curriculo.pdf");

		when(resumeHtmlGenerator.generate(curriculumData)).thenReturn(htmlDocument);
		when(htmlToPdfConverter.convert(htmlDocument)).thenReturn(pdfDocument);

		PdfDocument result = resumePdfService.generate(curriculumData);

		assertThat(result).isEqualTo(pdfDocument);
		verify(resumeHtmlGenerator).generate(curriculumData);
		verify(htmlToPdfConverter).convert(htmlDocument);
	}
}
