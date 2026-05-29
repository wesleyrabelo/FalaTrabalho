package com.falatrabalho.FalaTrabalho.resume.application;

import com.falatrabalho.FalaTrabalho.resume.domain.HtmlDocument;
import com.falatrabalho.FalaTrabalho.resume.domain.PdfDocument;

public interface HtmlToPdfConverter {

	PdfDocument convert(HtmlDocument htmlDocument);
}
