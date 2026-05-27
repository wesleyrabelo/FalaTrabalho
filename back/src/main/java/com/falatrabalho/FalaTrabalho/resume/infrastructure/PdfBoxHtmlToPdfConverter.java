package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.falatrabalho.FalaTrabalho.resume.application.HtmlToPdfConverter;
import com.falatrabalho.FalaTrabalho.resume.application.PdfGenerationException;
import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfBoxHtmlToPdfConverter implements HtmlToPdfConverter {

	private static final String DEFAULT_FILE_NAME = "curriculo.pdf";

	@Override
	public PdfDocument convert(HtmlDocument htmlDocument) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.withHtmlContent(htmlDocument.content(), null);
			builder.toStream(outputStream);
			builder.run();

			return new PdfDocument(outputStream.toByteArray(), DEFAULT_FILE_NAME);
		}
		catch (IOException exception) {
			throw new PdfGenerationException("Failed to create PDF output stream", exception);
		}
		catch (Exception exception) {
			throw new PdfGenerationException("Failed to convert HTML to PDF", exception);
		}
	}
}
