package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;

class PdfBoxHtmlToPdfConverterTest {

	private final PdfBoxHtmlToPdfConverter converter = new PdfBoxHtmlToPdfConverter();

	@Test
	void shouldConvertHtmlToPdf() {
		HtmlDocument htmlDocument = new HtmlDocument("""
				<html>
					<body>
						<h1>Curriculo</h1>
						<p>Joao Silva</p>
					</body>
				</html>
				""");

		PdfDocument pdfDocument = converter.convert(htmlDocument);

		assertEquals("curriculo.pdf", pdfDocument.fileName());
		assertFalse(pdfDocument.content().length == 0);
		assertTrue(startsWithPdfHeader(pdfDocument.content()));
	}

	private boolean startsWithPdfHeader(byte[] content) {
		String header = new String(content, 0, 4, StandardCharsets.US_ASCII);

		return "%PDF".equals(header);
	}
}
