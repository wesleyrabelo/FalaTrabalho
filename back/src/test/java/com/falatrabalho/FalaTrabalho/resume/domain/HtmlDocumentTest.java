package com.falatrabalho.FalaTrabalho.resume.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class HtmlDocumentTest {

	@Test
	void shouldCreateHtmlDocument() {
		HtmlDocument document = new HtmlDocument("<html><body>Curriculo</body></html>");

		assertEquals("<html><body>Curriculo</body></html>", document.content());
	}

	@Test
	void shouldNotAcceptNullContent() {
		assertThrows(IllegalArgumentException.class, () -> new HtmlDocument(null));
	}

	@Test
	void shouldNotAcceptBlankContent() {
		assertThrows(IllegalArgumentException.class, () -> new HtmlDocument("   "));
	}
}
