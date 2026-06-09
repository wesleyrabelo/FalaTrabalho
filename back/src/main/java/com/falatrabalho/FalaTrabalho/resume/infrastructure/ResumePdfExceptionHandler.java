package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.application.PdfGenerationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ResumePdfController.class)
public class ResumePdfExceptionHandler {

	@ExceptionHandler({
			IllegalArgumentException.class,
			NullPointerException.class,
			HttpMessageNotReadableException.class
	})
	public ResponseEntity<ResumePdfErrorResponse> handleBadRequest(Exception exception) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ResumePdfErrorResponse(exception.getMessage()));
	}

	@ExceptionHandler(PdfGenerationException.class)
	public ResponseEntity<ResumePdfErrorResponse> handlePdfGenerationFailure(PdfGenerationException exception) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ResumePdfErrorResponse(exception.getMessage()));
	}
}
