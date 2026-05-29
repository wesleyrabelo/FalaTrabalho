package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.io.IOException;
import java.util.Locale;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.support.CurriculumDataFixture;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResumePdfGenerationIntegrationTest {

	private final ThymeleafResumeHtmlGenerator htmlGenerator = new ThymeleafResumeHtmlGenerator(templateEngine());
	private final PdfBoxHtmlToPdfConverter pdfConverter = new PdfBoxHtmlToPdfConverter();

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWithoutProfessionalGoal() throws IOException {
		HtmlDocument htmlDocument = htmlGenerator.generate(CurriculumDataFixture.withNullProfessionalGoal());
		PdfDocument pdfDocument = pdfConverter.convert(htmlDocument);

		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);
			String normalizedText = text.toLowerCase(Locale.ROOT);

			assertTrue(document.getNumberOfPages() >= 1);
			assertTrue(text.contains("da Silva"), text);
			assertTrue(normalizedText.contains("resumo profissional"), text);
			assertTrue(text.contains("Mercado"), text);
			assertFalse(normalizedText.contains("objetivo profissional"));
		}
	}

	private static SpringTemplateEngine templateEngine() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
}
