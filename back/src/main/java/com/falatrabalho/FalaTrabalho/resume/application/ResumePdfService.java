package com.falatrabalho.FalaTrabalho.resume.application;

import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import org.springframework.stereotype.Service;

@Service
public class ResumePdfService {

	private final ResumeHtmlGenerator resumeHtmlGenerator;
	private final HtmlToPdfConverter htmlToPdfConverter;

	public ResumePdfService(ResumeHtmlGenerator resumeHtmlGenerator, HtmlToPdfConverter htmlToPdfConverter) {
		this.resumeHtmlGenerator = resumeHtmlGenerator;
		this.htmlToPdfConverter = htmlToPdfConverter;
	}

	public PdfDocument generate(CurriculumData curriculumData) {
		return htmlToPdfConverter.convert(resumeHtmlGenerator.generate(curriculumData));
	}
}
