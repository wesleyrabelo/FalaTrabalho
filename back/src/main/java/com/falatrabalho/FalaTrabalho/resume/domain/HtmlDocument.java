package com.falatrabalho.FalaTrabalho.resume.domain;

public record HtmlDocument(String content) {

	public HtmlDocument {
		if (content == null || content.isBlank()) {
			throw new IllegalArgumentException("HTML content must not be blank");
		}
	}
}
