package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

public record Education(
		String title,
		String institution,
		String period,
		String description) {

	public Education {
		title = requireNonBlank(title, "title");
		institution = requireNonBlank(institution, "institution");
		period = requireNonBlank(period, "period");
		description = requireNonBlank(description, "description");
	}

	private static String requireNonBlank(String value, String fieldName) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(fieldName + " e obrigatorio");
		}

		return value;
	}
}
