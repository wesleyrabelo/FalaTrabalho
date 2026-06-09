package com.falatrabalho.FalaTrabalho.workflow.infrastructure;

import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerResponse;
import com.falatrabalho.FalaTrabalho.workflow.application.CurriculumAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/workflow/answers")
@Tag(name = "Workflow", description = "Fluxos de processamento de respostas em audio")
public class CurriculumAnswerController {

	private static final String ACCEPTED_FIELDS = """
			personalInfo.name, personalInfo.city, personalInfo.state, personalInfo.maritalStatus, \
			personalInfo.age, personalInfo.phoneNumber, professionalGoal, professionalSummary, \
			education.title, education.institution, education.period, workExperience.company, \
			workExperience.position, workExperience.period, workExperience.description, \
			complementaryCourse.title, complementaryCourse.institution, complementaryCourse.period, \
			complementaryCourse.description, qualities\
			""";

	private final CurriculumAnswerService curriculumAnswerService;

	public CurriculumAnswerController(CurriculumAnswerService curriculumAnswerService) {
		this.curriculumAnswerService = curriculumAnswerService;
	}

	@PostMapping(value = "/{field}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(
			summary = "Processa uma resposta em audio para um campo do curriculo",
			description = "Recebe um arquivo WAV, transcreve o audio, melhora o texto com IA e retorna o texto bruto e tratado."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Resposta processada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Campo ou arquivo de audio invalido",
					content = @Content(schema = @Schema(implementation = WorkflowErrorResponse.class))),
			@ApiResponse(responseCode = "502", description = "Falha ao processar audio ou melhorar texto",
					content = @Content(schema = @Schema(implementation = WorkflowErrorResponse.class)))
	})
	public CurriculumAnswerResponse processAnswer(
			@Parameter(description = "Campo do curriculo. Aceitos: " + ACCEPTED_FIELDS, required = true)
			@PathVariable String field,
			@Parameter(description = "Arquivo de audio WAV da resposta", required = true)
			@RequestPart("file") MultipartFile file
	) {
		return curriculumAnswerService.processAnswer(field, file);
	}
}
