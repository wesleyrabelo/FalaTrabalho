package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotEquals(0, pdfDocument.content().length);
		assertTrue(startsWithPdfHeader(pdfDocument.content()));
	}

	@Test
	void shouldGenerateReadablePdfWithHtmlText() throws IOException {
		HtmlDocument htmlDocument = new HtmlDocument("""
				<html>
					<body>
						<h1>Curriculo</h1>
						<p>Joao Silva</p>
					</body>
				</html>
				""");

		PdfDocument pdfDocument = converter.convert(htmlDocument);

		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);

			assertEquals(1, document.getNumberOfPages());
			assertTrue(text.contains("Curriculo"));
			assertTrue(text.contains("Joao Silva"));
		}
	}

	@Test
	void shouldPreservePortugueseCharactersInPdfText() throws IOException {
		HtmlDocument htmlDocument = new HtmlDocument("""
				<html>
					<body>
						<h1>Curriculo</h1>
						<p>João da Silva</p>
						<p>Experiência em atendimento ao público em São Paulo.</p>
						<p>Educação: ensino médio completo.</p>
					</body>
				</html>
				""");

		PdfDocument pdfDocument = converter.convert(htmlDocument);

		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);

			assertTrue(text.contains("João da Silva"));
			assertTrue(text.contains("Experiência"));
			assertTrue(text.contains("São Paulo"));
			assertTrue(text.contains("Educação"));
		}
	}

	@Test
	void shouldRenderResumeSectionsAndListItems() throws IOException {
		HtmlDocument htmlDocument = new HtmlDocument("""
				<html>
					<body>
						<h1>Ana Souza</h1>
						<h2>Experiencia profissional</h2>
						<ul>
							<li>Atendente de loja</li>
							<li>Operadora de caixa</li>
						</ul>
						<h2>Formacao</h2>
						<p>Ensino medio completo</p>
					</body>
				</html>
				""");

		PdfDocument pdfDocument = converter.convert(htmlDocument);

		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);

			assertTrue(text.contains("Ana Souza"));
			assertTrue(text.contains("Experiencia profissional"));
			assertTrue(text.contains("Atendente de loja"));
			assertTrue(text.contains("Operadora de caixa"));
			assertTrue(text.contains("Formacao"));
			assertTrue(text.contains("Ensino medio completo"));
		}
	}

	@Test
	void shouldUseDefaultResumeFileName() {
		HtmlDocument htmlDocument = new HtmlDocument("""
				<html>
					<body>
						<p>Conteudo do curriculo</p>
					</body>
				</html>
				""");

		PdfDocument pdfDocument = converter.convert(htmlDocument);

		assertEquals("curriculo.pdf", pdfDocument.fileName());
	}

	private boolean startsWithPdfHeader(byte[] content) {
		String header = new String(content, 0, 4, StandardCharsets.US_ASCII);

		return "%PDF".equals(header);
	}
}
