package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.support.CurriculumDataFixture;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThymeleafResumeHtmlGeneratorTest {

	private final ThymeleafResumeHtmlGenerator generator = new ThymeleafResumeHtmlGenerator(templateEngine());

	@Test
	void shouldGenerateHtmlDocumentFromCurriculumData() {
		HtmlDocument htmlDocument = generator.generate(CurriculumDataFixture.complete());

		assertFalse(htmlDocument.content().isBlank());
		assertTrue(htmlDocument.content().contains("João da Silva"));
		assertTrue(htmlDocument.content().contains("Resumo Profissional"));
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

