package com.falatrabalho.FalaTrabalho.workflow.infrastructure;

import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerResponse;
import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/workflow/answers")
public class CurriculumAnswerController {

	private final CurriculumAnswerService curriculumAnswerService;

	public CurriculumAnswerController(CurriculumAnswerService curriculumAnswerService) {
		this.curriculumAnswerService = curriculumAnswerService;
	}

	@PostMapping("/{field}")
	public CurriculumAnswerResponse processAnswer(
			@PathVariable String field,
			@RequestPart("file") MultipartFile file
	) {
		return curriculumAnswerService.processAnswer(field, file);
	}
}
