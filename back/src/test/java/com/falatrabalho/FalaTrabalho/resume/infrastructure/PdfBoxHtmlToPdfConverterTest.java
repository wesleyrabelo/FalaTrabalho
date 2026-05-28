package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import com.falatrabalho.FalaTrabalho.resume.application.PdfGenerationException;
import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;

import static org.junit.jupiter.api.Assertions.*;

class PdfBoxHtmlToPdfConverterTest {

	private final HtmlDocument htmlDocument = new HtmlDocument("""
			<html>
				<body>
					<h1>João da Silva</h1>
					<p>E-mail: joao.silva@email.com</p>
					<p>Telefone: +55 (11) 99999-9999</p>
					<p>Localização: São Paulo / SP</p>
					<h2>Experiência profissional</h2>
					<ul>
						<li>Atendente de loja em São Paulo</li>
						<li>Operador de caixa</li>
					</ul>
					<h2>Educação</h2>
					<p>Ensino médio completo</p>
					<p>Competências: comunicação, organização e atenção.</p>
				</body>
			</html>
			""");

	private final HtmlDocument longHtmlDocument = new HtmlDocument("""
			<html>
				<body>
					<h1>Curriculo longo</h1>
					<p>Inicio do curriculo com mais de uma pagina.</p>
					<div style="height: 1200px;">Experiencias profissionais detalhadas.</div>
					<p>Fim do curriculo na segunda pagina.</p>
				</body>
			</html>
			""");

	private final PdfDocument pdfDocument = new PdfBoxHtmlToPdfConverter().convert(htmlDocument);
	private final PdfDocument longPdfDocument = new PdfBoxHtmlToPdfConverter().convert(longHtmlDocument);

	@Test
	void shouldUseDefaultResumeFileName() {
		assertEquals("curriculo.pdf", pdfDocument.fileName());
	}

	@Test
	void shouldGeneratePdfWithOnePage() throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			assertEquals(1, document.getNumberOfPages());
		}
	}

	@Test
	void shouldRenderPortugueseTextCorrectly() throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);

			assertTrue(text.contains("João da Silva"));
			assertTrue(text.contains("joao.silva@email.com"));
			assertTrue(text.contains("+55 (11) 99999-9999"));
			assertTrue(text.contains("São Paulo / SP"));
			assertTrue(text.contains("Experiência profissional"));
			assertTrue(text.contains("São Paulo"));
			assertTrue(text.contains("Educação"));
			assertTrue(text.contains("Ensino médio completo"));
			assertTrue(text.contains("Competências"));
			assertTrue(text.contains("atenção"));
		}
	}

	@Test
	void shouldGeneratePdfWithMoreThanOnePage() throws IOException {
		try (PDDocument document = Loader.loadPDF(longPdfDocument.content())) {
			String text = new PDFTextStripper().getText(document);

			assertTrue(document.getNumberOfPages() > 1);
			assertTrue(text.contains("Inicio do curriculo com mais de uma pagina."));
			assertTrue(text.contains("Fim do curriculo na segunda pagina."));
		}
	}

	@Test
	void shouldThrowPdfGenerationExceptionWhenHtmlDocumentIsNull() {
		PdfBoxHtmlToPdfConverter converter = new PdfBoxHtmlToPdfConverter();

		assertThrows(PdfGenerationException.class, () -> converter.convert(null));
	}
}
