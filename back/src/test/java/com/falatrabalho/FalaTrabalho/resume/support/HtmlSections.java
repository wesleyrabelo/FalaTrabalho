package com.falatrabalho.FalaTrabalho.resume.support;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class HtmlSections {

	private HtmlSections() {
	}

	public static String personalInfo(String html) {
		return sectionBetween(html, "<h1 class=\"name\">", "<h2 class=\"professional-goal\">");
	}

	public static String professionalGoal(String html) {
		return sectionBetween(html, "<h2 class=\"professional-goal\">", "<h2 class=\"professional-summary\">");
	}

	public static String professionalSummary(String html) {
		return sectionBetween(html, "<h2 class=\"professional-summary\">", "<h2 class=\"educational-background\">");
	}

	public static String education(String html) {
		return sectionBetween(html, "<h2 class=\"educational-background\">", "<h2 class=\"work-experience\">");
	}

	public static String workExperience(String html) {
		return sectionBetween(html, "<h2 class=\"work-experience\">", "<h2 class=\"complementary\">");
	}

	public static String complementaryCourses(String html) {
		return sectionBetween(html, "<h2 class=\"complementary\">", "<h2 class=\"qualities\">");
	}

	public static String qualities(String html) {
		String normalizedHtml = normalizeLineBreaks(html);
		String startMarker = "<h2 class=\"qualities\">";
		String endMarker = "</ul>";
		int start = normalizedHtml.indexOf(startMarker);
		assertTrue(start >= 0, () -> "Start marker not found: " + startMarker);

		int end = normalizedHtml.indexOf(endMarker, start);
		assertTrue(end >= 0, () -> "End marker not found: " + endMarker);

		return normalizedHtml.substring(start, end + endMarker.length()).stripTrailing();
	}

	private static String sectionBetween(String html, String startMarker, String endMarker) {
		String normalizedHtml = normalizeLineBreaks(html);
		int start = normalizedHtml.indexOf(startMarker);
		assertTrue(start >= 0, () -> "Start marker not found: " + startMarker);

		int end = normalizedHtml.indexOf(endMarker, start);
		assertTrue(end >= 0, () -> "End marker not found: " + endMarker);

		return normalizedHtml.substring(start, end).stripTrailing();
	}

	private static String normalizeLineBreaks(String html) {
		return html.replace("\r\n", "\n");
	}
}
