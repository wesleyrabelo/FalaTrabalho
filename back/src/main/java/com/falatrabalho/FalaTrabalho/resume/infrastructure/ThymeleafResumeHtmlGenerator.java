package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.application.ResumeHtmlGenerator;
import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class ThymeleafResumeHtmlGenerator implements ResumeHtmlGenerator {

	private static final String CURRICULUM_TEMPLATE = "curriculum";
	private static final String CURRICULUM_DATA_VARIABLE = "curriculumData";

	private final SpringTemplateEngine templateEngine;

	public ThymeleafResumeHtmlGenerator(SpringTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@Override
	public HtmlDocument generate(CurriculumData curriculumData) {
		if (curriculumData == null) {
			throw new IllegalArgumentException("Curriculum data must not be null");
		}

		Context context = new Context();
		context.setVariable(CURRICULUM_DATA_VARIABLE, curriculumData);

		return new HtmlDocument(templateEngine.process(CURRICULUM_TEMPLATE, context));
	}
}
