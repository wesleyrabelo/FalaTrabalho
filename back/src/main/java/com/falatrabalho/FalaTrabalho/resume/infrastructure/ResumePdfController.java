package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.application.ResumePdfService;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Curriculo", description = "Geracao de curriculos")
public class ResumePdfController {

	private final ResumePdfService resumePdfService;

	public ResumePdfController(ResumePdfService resumePdfService) {
		this.resumePdfService = resumePdfService;
	}

	@PostMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	@Operation(
			summary = "Gera o PDF do curriculo",
			description = "Recebe os dados estruturados do curriculo e retorna o arquivo PDF final."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "PDF gerado com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE,
							schema = @Schema(type = "string", format = "binary"))),
			@ApiResponse(responseCode = "400", description = "Dados do curriculo invalidos",
					content = @Content(schema = @Schema(implementation = ResumePdfErrorResponse.class))),
			@ApiResponse(responseCode = "500", description = "Falha ao gerar PDF",
					content = @Content(schema = @Schema(implementation = ResumePdfErrorResponse.class)))
	})
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
