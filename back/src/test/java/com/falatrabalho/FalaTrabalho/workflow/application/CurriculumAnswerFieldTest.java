package com.falatrabalho.FalaTrabalho.workflow.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CurriculumAnswerFieldTest {

	@Test
	void shouldFindFieldByValue() {
		CurriculumAnswerField field = CurriculumAnswerField.fromValue("personalInfo.name");

		assertThat(field).isEqualTo(CurriculumAnswerField.PERSONAL_INFO_NAME);
		assertThat(field.value()).isEqualTo("personalInfo.name");
	}

	@Test
	void shouldRejectBlankValue() {
		assertThatThrownBy(() -> CurriculumAnswerField.fromValue("   "))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Field must not be blank");
	}

	@Test
	void shouldRejectUnsupportedValue() {
		assertThatThrownBy(() -> CurriculumAnswerField.fromValue("unknownField"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unsupported curriculum answer field: unknownField");
	}
}
