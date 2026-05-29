package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.io.IOException;
import java.util.Locale;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
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
		String text = generatePdfText(CurriculumDataFixture.withNullProfessionalGoal());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("objetivo profissional"));
	}

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWhenEducationIsNull() throws IOException {
		String text = generatePdfText(CurriculumDataFixture.withNullEducation());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("formação"));
	}

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWhenEducationIsEmpty() throws IOException {
		String text = generatePdfText(CurriculumDataFixture.withEmptyEducation());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("formação"));
	}

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWhenComplementaryCoursesIsNull() throws IOException {
		String text = generatePdfText(CurriculumDataFixture.withNullComplementaryCourses());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("qualificações e cursos complementares"));
	}

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWhenComplementaryCoursesIsEmpty() throws IOException {
		String text = generatePdfText(CurriculumDataFixture.withEmptyComplementaryCourses());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("qualificações e cursos complementares"));
	}

	@Test
	void shouldGeneratePdfFromRenderedCurriculumHtmlWithoutOptionalFields() throws IOException {
		String text = generatePdfText(CurriculumDataFixture.withoutOptionalFields());
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertContainsEssentialResumeContent(text);
		assertFalse(normalizedText.contains("objetivo profissional"));
		assertFalse(normalizedText.contains("formação"));
		assertFalse(normalizedText.contains("qualificações e cursos complementares"));
	}

	private String generatePdfText(CurriculumData curriculumData) throws IOException {
		HtmlDocument htmlDocument = htmlGenerator.generate(curriculumData);
		PdfDocument pdfDocument = pdfConverter.convert(htmlDocument);

		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			assertTrue(document.getNumberOfPages() >= 1);
			return new PDFTextStripper().getText(document);
		}
	}

	private static void assertContainsEssentialResumeContent(String text) {
		String normalizedText = text.toLowerCase(Locale.ROOT);

		assertTrue(text.contains("da Silva"), text);
		assertTrue(normalizedText.contains("resumo profissional"), text);
		assertTrue(text.contains("Mercado"), text);
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
