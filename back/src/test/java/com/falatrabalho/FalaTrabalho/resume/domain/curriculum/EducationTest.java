package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class EducationTest {

	@Test
	void shouldCreateEducation() {
		Education education = new Education("Ensino medio completo", "Escola Estadual Central", "2006 - 2008",
				"Formacao regular");

		assertEquals("Ensino medio completo", education.title());
		assertEquals("Escola Estadual Central", education.institution());
		assertEquals("2006 - 2008", education.period());
		assertEquals("Formacao regular", education.description());
	}

	@Test
	void shouldNotAcceptNullTitle() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education(null, "Escola Estadual Central", "2006 - 2008", "Formacao regular"));
	}

	@Test
	void shouldNotAcceptBlankTitle() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("   ", "Escola Estadual Central", "2006 - 2008", "Formacao regular"));
	}

	@Test
	void shouldNotAcceptNullInstitution() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", null, "2006 - 2008", "Formacao regular"));
	}

	@Test
	void shouldNotAcceptBlankInstitution() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", "   ", "2006 - 2008", "Formacao regular"));
	}

	@Test
	void shouldNotAcceptNullPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", "Escola Estadual Central", null, "Formacao regular"));
	}

	@Test
	void shouldNotAcceptBlankPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", "Escola Estadual Central", "   ", "Formacao regular"));
	}

	@Test
	void shouldNotAcceptNullDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", "Escola Estadual Central", "2006 - 2008", null));
	}

	@Test
	void shouldNotAcceptBlankDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new Education("Ensino medio completo", "Escola Estadual Central", "2006 - 2008", "   "));
	}
}
