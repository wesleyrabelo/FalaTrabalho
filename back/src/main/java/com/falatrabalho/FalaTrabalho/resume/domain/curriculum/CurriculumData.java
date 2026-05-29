package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import java.util.List;
import java.util.Objects;

public record CurriculumData(
		PersonalInfo personalInfo,
		String professionalGoal,
		String professionalSummary,
		List<Education> education,
		List<WorkExperience> workExperience,
		List<ComplementaryCourse> complementaryCourses,
		List<String> qualities) {

	public CurriculumData {
		personalInfo = Objects.requireNonNull(personalInfo, "personalInfo é obrigatório");
		professionalSummary = requireNonBlank(professionalSummary, "professionalSummary");
		education = education == null ? List.of() : List.copyOf(education);
		workExperience = requireNonEmpty(workExperience, "workExperience");
		complementaryCourses = complementaryCourses == null ? List.of() : List.copyOf(complementaryCourses);
		qualities = requireNonEmpty(qualities, "qualities");
	}

	private static String requireNonBlank(String value, String fieldName) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(fieldName + " e obrigatorio");
		}

		return value;
	}

	private static <T> List<T> requireNonEmpty(List<T> values, String fieldName) {
		if (values == null || values.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " é obrigatorio");
		}

		return List.copyOf(values);
	}
}
