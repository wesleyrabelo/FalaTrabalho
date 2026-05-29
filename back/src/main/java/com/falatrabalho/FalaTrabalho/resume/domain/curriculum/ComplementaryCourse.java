package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

public record ComplementaryCourse(
		String title,
		String institution,
		String workload,
		String period,
		String description) {

	public ComplementaryCourse {
		title = requireNonBlank(title, "title");
		institution = requireNonBlank(institution, "institution");
		workload = requireNonBlank(workload, "workload");
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
