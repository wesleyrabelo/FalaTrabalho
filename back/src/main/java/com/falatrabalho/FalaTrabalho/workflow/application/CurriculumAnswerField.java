package com.falatrabalho.FalaTrabalho.workflow.application;

import java.util.Arrays;

public enum CurriculumAnswerField {

	PERSONAL_INFO_NAME("personalInfo.name"),
	PERSONAL_INFO_CITY("personalInfo.city"),
	PERSONAL_INFO_STATE("personalInfo.state"),
	PERSONAL_INFO_MARITAL_STATUS("personalInfo.maritalStatus"),
	PERSONAL_INFO_AGE("personalInfo.age"),
	PERSONAL_INFO_PHONE_NUMBER("personalInfo.phoneNumber"),
	PROFESSIONAL_GOAL("professionalGoal"),
	PROFESSIONAL_SUMMARY("professionalSummary"),
	EDUCATION_TITLE("education.title"),
	EDUCATION_INSTITUTION("education.institution"),
	EDUCATION_PERIOD("education.period"),
	WORK_EXPERIENCE_COMPANY("workExperience.company"),
	WORK_EXPERIENCE_POSITION("workExperience.position"),
	WORK_EXPERIENCE_PERIOD("workExperience.period"),
	WORK_EXPERIENCE_DESCRIPTION("workExperience.description"),
	COMPLEMENTARY_COURSE_TITLE("complementaryCourse.title"),
	COMPLEMENTARY_COURSE_INSTITUTION("complementaryCourse.institution"),
	COMPLEMENTARY_COURSE_PERIOD("complementaryCourse.period"),
	COMPLEMENTARY_COURSE_DESCRIPTION("complementaryCourse.description"),
	QUALITIES("qualities");

	private final String value;

	CurriculumAnswerField(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static CurriculumAnswerField fromValue(String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Field must not be blank");
		}

		return Arrays.stream(values())
				.filter(field -> field.value.equals(value))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Unsupported curriculum answer field: " + value));
	}
}
