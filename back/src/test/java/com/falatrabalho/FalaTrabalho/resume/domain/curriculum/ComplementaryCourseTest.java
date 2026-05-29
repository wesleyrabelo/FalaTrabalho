package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ComplementaryCourseTest {

	@Test
	void shouldCreateComplementaryCourse() {
		ComplementaryCourse course = new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "2011",
				"Curso de pacote office e internet.");

		assertEquals("Informatica basica", course.title());
		assertEquals("SENAI", course.institution());
		assertEquals("40 horas", course.workload());
		assertEquals("2011", course.period());
		assertEquals("Curso de pacote office e internet.", course.description());
	}

	@Test
	void shouldNotAcceptNullTitle() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse(null, "SENAI", "40 horas", "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptBlankTitle() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("   ", "SENAI", "40 horas", "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptNullInstitution() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", null, "40 horas", "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptBlankInstitution() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "   ", "40 horas", "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptNullWorkload() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", null, "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptBlankWorkload() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", "   ", "2011",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptNullPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", null,
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptBlankPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "   ",
						"Curso de pacote office e internet."));
	}

	@Test
	void shouldNotAcceptNullDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "2011", null));
	}

	@Test
	void shouldNotAcceptBlankDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "2011", "   "));
	}
}
