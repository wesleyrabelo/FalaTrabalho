package com.falatrabalho.FalaTrabalho.resume.support;

import java.util.List;

import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.ComplementaryCourse;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.Education;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.PersonalInfo;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.WorkExperience;

public final class CurriculumDataFixture {

	private CurriculumDataFixture() {
	}

	public static CurriculumData complete() {
		return curriculumData(education(), workExperience(), complementaryCourses());
	}

	public static CurriculumData withNullProfessionalGoal() {
		return new CurriculumData(
				personalInfo(),
				null,
				professionalSummary(),
				education(),
				workExperience(),
				complementaryCourses(),
				qualities());
	}

	public static CurriculumData withNullEducation() {
		return new CurriculumData(
				personalInfo(),
				professionalGoal(),
				professionalSummary(),
				null,
				workExperience(),
				complementaryCourses(),
				qualities());
	}

	public static CurriculumData withNullComplementaryCourses() {
		return new CurriculumData(
				personalInfo(),
				professionalGoal(),
				professionalSummary(),
				education(),
				workExperience(),
				null,
				qualities());
	}

	private static CurriculumData curriculumData(
			List<Education> education,
			List<WorkExperience> workExperience,
			List<ComplementaryCourse> complementaryCourses) {

		return new CurriculumData(
				personalInfo(),
				professionalGoal(),
				professionalSummary(),
				education,
				workExperience,
				complementaryCourses,
				qualities());
	}

	private static PersonalInfo personalInfo() {
		return new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", 28, "(11) 99999-9999");
	}

	private static String professionalGoal() {
		return "Atuar como auxiliar administrativo";
	}

	private static String professionalSummary() {
		return "Profissional com experiencia em atendimento e organizacao de documentos.";
	}

	private static List<Education> education() {
		return List.of(education1(), education2(), education3(), education4());
	}

	private static Education education1() {
		return new Education("Ensino medio completo", "Escola Estadual Central", "2006 - 2008",
				"Formacao regular");
	}

	private static Education education2() {
		return new Education("Tecnico em Administracao", "ETEC", "2009 - 2010",
				"Rotinas administrativas e financeiras");
	}

	private static Education education3() {
		return new Education("Graduacao em Gestao Comercial", "Faculdade Metropolitana", "2012 - 2015",
				"Formacao superior");
	}

	private static Education education4() {
		return new Education("Pos-graduacao em Gestao de Pessoas", "Instituto Saber", "2018 - 2019",
				"Especializacao em lideranca");
	}

	private static List<WorkExperience> workExperience() {
		return List.of(workExperience1(), workExperience2(), workExperience3(), workExperience4());
	}

	private static WorkExperience workExperience1() {
		return new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "2010 - 2013",
				"Atendimento ao cliente e fechamento de caixa.");
	}

	private static WorkExperience workExperience2() {
		return new WorkExperience("Loja Central", "Auxiliar administrativo", "2014 - 2017",
				"Organizacao de documentos e apoio ao setor financeiro.");
	}

	private static WorkExperience workExperience3() {
		return new WorkExperience("Clinica Vida", "Recepcionista", "2018 - 2021",
				"Agendamento de consultas e atendimento ao publico.");
	}

	private static WorkExperience workExperience4() {
		return new WorkExperience("Servicos Alfa", "Assistente administrativa", "2022 - atual",
				"Controle de planilhas, relatorios e atendimento interno.");
	}

	private static List<ComplementaryCourse> complementaryCourses() {
		return List.of(complementaryCourse1(), complementaryCourse2(), complementaryCourse3(),
				complementaryCourse4());
	}

	private static ComplementaryCourse complementaryCourse1() {
		return new ComplementaryCourse("Informatica basica", "SENAI", "40 horas", "2011",
				"Curso de pacote office e internet.");
	}

	private static ComplementaryCourse complementaryCourse2() {
		return new ComplementaryCourse("Atendimento ao cliente", "SEBRAE", "20 horas", "2014",
				"Tecnicas de comunicacao e relacionamento.");
	}

	private static ComplementaryCourse complementaryCourse3() {
		return new ComplementaryCourse("Excel intermediario", "Fundacao Bradesco", "30 horas", "2019",
				"Planilhas, formulas e graficos.");
	}

	private static ComplementaryCourse complementaryCourse4() {
		return new ComplementaryCourse("Rotinas administrativas", "SENAC", "60 horas", "2023",
				"Documentos, arquivos e processos administrativos.");
	}

	private static List<String> qualities() {
		return List.of("Comunicacao", "Pontualidade", "Atencao", "Aprendizado rapido");
	}
}
