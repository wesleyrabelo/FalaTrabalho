package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

public record WorkExperience(
		String company,
		String position,
		String period,
		String description) {

	public WorkExperience {
		company = requireNonBlank(company, "company");
		position = requireNonBlank(position, "position");
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
