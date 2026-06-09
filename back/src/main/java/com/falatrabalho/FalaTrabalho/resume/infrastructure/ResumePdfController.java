package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.application.ResumePdfService;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes")
public class ResumePdfController {

	private final ResumePdfService resumePdfService;

	public ResumePdfController(ResumePdfService resumePdfService) {
		this.resumePdfService = resumePdfService;
	}

	@PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> generatePdf(@RequestBody CurriculumData curriculumData) {
		PdfDocument pdfDocument = resumePdfService.generate(curriculumData);

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
						.filename(pdfDocument.fileName())
						.build()
						.toString())
				.body(pdfDocument.content());
	}
}
