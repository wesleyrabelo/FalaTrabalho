package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import java.util.Objects;

public record PersonalInfo(
		String name,
		String city,
		String state,
		String maritalStatus,
		Integer age,
		String phoneNumber) {

	public PersonalInfo {
		name = requireNonBlank(name, "name");
		city = requireNonBlank(city, "city");
		state = requireNonBlank(state, "state");
		maritalStatus = requireNonBlank(maritalStatus, "maritalStatus");
		Objects.requireNonNull(age, "age e obrigatorio");
		phoneNumber = requireNonBlank(phoneNumber, "phoneNumber");
	}

	private static String requireNonBlank(String value, String fieldName) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(fieldName + " e obrigatorio");
		}

		return value;
	}
}
