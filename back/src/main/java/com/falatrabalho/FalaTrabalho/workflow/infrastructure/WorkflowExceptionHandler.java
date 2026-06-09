package com.falatrabalho.FalaTrabalho.workflow.infrastructure;

import com.falatrabalho.FalaTrabalho.textimprovement.infrastructure.LlamaCppException;
import com.falatrabalho.FalaTrabalho.transcription.application.TranscriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice(assignableTypes = CurriculumAnswerController.class)
public class WorkflowExceptionHandler {

	@ExceptionHandler({
			IllegalArgumentException.class,
			MissingServletRequestPartException.class
	})
	public ResponseEntity<WorkflowErrorResponse> handleBadRequest(Exception exception) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new WorkflowErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler({
			TranscriptionException.class,
			LlamaCppException.class
	})
	public ResponseEntity<WorkflowErrorResponse> handleProcessingFailure(Exception exception) {
		return ResponseEntity
				.status(HttpStatus.BAD_GATEWAY)
				.body(new WorkflowErrorResponse(exception.getMessage()));
	}
}
