package com.falatrabalho.FalaTrabalho.resume.domain;

public record PdfDocument(byte[] content, String fileName) {

	public PdfDocument {
		if (content == null || content.length == 0) {
			throw new IllegalArgumentException("PDF content must not be empty");
		}
		if (fileName == null || fileName.isBlank()) {
			throw new IllegalArgumentException("PDF file name must not be blank");
		}
	}
}
