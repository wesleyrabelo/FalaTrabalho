package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CurriculumDataTest {

	@Test
	void shouldNormalizeOptionalNullListsToEmptyLists() {
		CurriculumData curriculumData = new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				null,
				List.of(workExperience()),
				null,
				List.of("Comunicacao"));

		assertEquals(List.of(), curriculumData.education());
		assertEquals(List.of(), curriculumData.complementaryCourses());
	}

	@Test
	void shouldCopyLists() {
		List<Education> education = new ArrayList<>();
		education.add(education());
		List<WorkExperience> workExperience = new ArrayList<>();
		workExperience.add(workExperience());
		List<ComplementaryCourse> complementaryCourses = new ArrayList<>();
		complementaryCourses.add(complementaryCourse());
		List<String> qualities = new ArrayList<>();
		qualities.add("Comunicacao");

		CurriculumData curriculumData = new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				education,
				workExperience,
				complementaryCourses,
				qualities);

		education.clear();
		workExperience.clear();
		complementaryCourses.clear();
		qualities.clear();

		assertEquals(List.of(education()), curriculumData.education());
		assertEquals(List.of(workExperience()), curriculumData.workExperience());
		assertEquals(List.of(complementaryCourse()), curriculumData.complementaryCourses());
		assertEquals(List.of("Comunicacao"), curriculumData.qualities());
		assertThrows(UnsupportedOperationException.class, () -> curriculumData.education().add(education()));
		assertThrows(UnsupportedOperationException.class, () -> curriculumData.workExperience().add(workExperience()));
		assertThrows(UnsupportedOperationException.class, () -> curriculumData.complementaryCourses().add(complementaryCourse()));
		assertThrows(UnsupportedOperationException.class, () -> curriculumData.qualities().add("Pontualidade"));
	}

	@Test
	void shouldNotAcceptNullPersonalInfo() {
		assertThrows(NullPointerException.class, () -> new CurriculumData(
				null,
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				List.of(),
				List.of(workExperience()),
				List.of(),
				List.of("Comunicacao")));
	}

	@Test
	void shouldNotAcceptNullProfessionalSummary() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				null,
				List.of(),
				List.of(workExperience()),
				List.of(),
				List.of("Comunicacao")));
	}

	@Test
	void shouldNotAcceptBlankProfessionalSummary() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"   ",
				List.of(),
				List.of(workExperience()),
				List.of(),
				List.of("Comunicacao")));
	}

	@Test
	void shouldNotAcceptNullWorkExperience() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				List.of(),
				null,
				List.of(),
				List.of("Comunicacao")));
	}

	@Test
	void shouldNotAcceptEmptyWorkExperience() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				List.of(),
				List.of(),
				List.of(),
				List.of("Comunicacao")));
	}

	@Test
	void shouldNotAcceptNullQualities() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				List.of(),
				List.of(workExperience()),
				List.of(),
				null));
	}

	@Test
	void shouldNotAcceptEmptyQualities() {
		assertThrows(IllegalArgumentException.class, () -> new CurriculumData(
				personalInfo(),
				"Atuar como auxiliar administrativo",
				"Profissional com experiencia em atendimento.",
				List.of(),
				List.of(workExperience()),
				List.of(),
				List.of()));
	}

	private static PersonalInfo personalInfo() {
		return new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", 28, "(11) 99999-9999");
	}

	private static Education education() {
		return new Education("Ensino medio completo", "Escola Estadual Central", "2006 - 2008", "Formacao regular");
	}

	private static WorkExperience workExperience() {
		return new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "2010 - 2013",
				"Atendimento ao cliente e fechamento de caixa.");
	}

	private static ComplementaryCourse complementaryCourse() {
		return new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "2011",
				"Curso de pacote office e internet.");
	}
}
