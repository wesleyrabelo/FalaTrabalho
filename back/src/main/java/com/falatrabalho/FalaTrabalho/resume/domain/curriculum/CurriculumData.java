package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import java.util.List;

public record CurriculumData(
		PersonalInfo personalInfo,
		String professionalGoal,
		String professionalSummary,
		List<Education> education,
		List<WorkExperience> workExperience,
		List<ComplementaryCourse> complementaryCourses,
		List<String> qualities) {

	public CurriculumData {
		education = education == null ? List.of() : List.copyOf(education);
		workExperience = workExperience == null ? List.of() : List.copyOf(workExperience);
		complementaryCourses = complementaryCourses == null ? List.of() : List.copyOf(complementaryCourses);
		qualities = qualities == null ? List.of() : List.copyOf(qualities);
	}
}
