package com.falatrabalho.FalaTrabalho.resume.application;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;

public interface ResumeHtmlGenerator {

	HtmlDocument generate(CurriculumData curriculumData);
}
