package com.falatrabalho.FalaTrabalho.resume.domain;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PdfDocumentTest {

	@Test
	void shouldCreatePdfDocument() {
		byte[] content = "%PDF".getBytes();

		PdfDocument document = new PdfDocument(content, "curriculo.pdf");

		assertArrayEquals(content, document.content());
		assertEquals("curriculo.pdf", document.fileName());
	}

	@Test
	void shouldNotAcceptNullContent() {
		assertThrows(IllegalArgumentException.class, () -> new PdfDocument(null, "curriculo.pdf"));
	}

	@Test
	void shouldNotAcceptEmptyContent() {
		assertThrows(IllegalArgumentException.class, () -> new PdfDocument(new byte[0], "curriculo.pdf"));
	}

	@Test
	void shouldNotAcceptNullFileName() {
		assertThrows(IllegalArgumentException.class, () -> new PdfDocument("%PDF".getBytes(), null));
	}

	@Test
	void shouldNotAcceptBlankFileName() {
		assertThrows(IllegalArgumentException.class, () -> new PdfDocument("%PDF".getBytes(), "   "));
	}
}
