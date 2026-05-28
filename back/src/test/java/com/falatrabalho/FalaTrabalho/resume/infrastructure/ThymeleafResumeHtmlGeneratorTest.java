package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import java.util.List;

import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.ComplementaryCourse;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.CurriculumData;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.Education;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.PersonalInfo;
import com.falatrabalho.FalaTrabalho.resume.domain.curriculum.WorkExperience;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThymeleafResumeHtmlGeneratorTest {

	private final ThymeleafResumeHtmlGenerator generator = new ThymeleafResumeHtmlGenerator(templateEngine());
	private final String completeHtml = generator.generate(completeCurriculumData()).content();

	@Test
	void shouldGeneratePersonalInfoHtmlStructure() {
		assertEquals("""
				<h1 class="name">João da Silva</h1>

				<div class="contact">
				    <p>
				        <span class="city">São Paulo</span><span>/</span><span class="state">SP</span>
				    </p>
				    <p>
				        <span class="marital-status">Solteiro</span><span>, </span><span class="age">28 anos</span>
				    </p>
				    <p class="phone-number">Telefone: (11) 99999-9999</p>
				    <p class="email">E-mail: joao@email.com</p>
				</div>
				""".stripTrailing(), personalInfoHtml().stripTrailing());
	}

	@Test
	void shouldGenerateProfessionalGoalHtmlStructure() {
		assertEquals("""
				<h2 class="professional-goal">Objetivo Profissional</h2>
				<p>Atuar como auxiliar administrativo</p>
				""".stripTrailing(), professionalGoalHtml().stripTrailing());
	}

	@Test
	void shouldGenerateProfessionalSummaryHtmlStructure() {
		assertEquals("""
				<h2 class="professional-summary">Resumo Profissional</h2>
				<p>Profissional com experiência em atendimento e organização de documentos.</p>
				""".stripTrailing(), professionalSummaryHtml().stripTrailing());
	}

	@Test
	void shouldGenerateEducationListHtmlStructure() {
		assertEquals("""
				<h2 class="educational-background">Forma&ccedil;&atilde;o</h2>
				<div class="education">
				    <p class="title">Ensino médio completo</p>
				    <p>Escola Estadual Central</p>
				    <p>Formação regular</p>
				    <p class="period">2006 - 2008</p>
				</div>
				<div class="education">
				    <p class="title">Técnico em Administração</p>
				    <p>ETEC</p>
				    <p>Rotinas administrativas e financeiras</p>
				    <p class="period">2009 - 2010</p>
				</div>
				<div class="education">
				    <p class="title">Graduação em Gestão Comercial</p>
				    <p>Faculdade Metropolitana</p>
				    <p>Formação superior</p>
				    <p class="period">2012 - 2015</p>
				</div>
				<div class="education">
				    <p class="title">Pós-graduação em Gestão de Pessoas</p>
				    <p>Instituto Saber</p>
				    <p>Especialização em liderança</p>
				    <p class="period">2018 - 2019</p>
				</div>
				""".stripTrailing(), educationHtml().stripTrailing());
	}

	@Test
	void shouldGenerateWorkExperienceListHtmlStructure() {
		assertEquals("""
				<h2 class="work-experience">Experi&ecirc;ncias Profissionais</h2>

				<div class="job">
				    <p class="title">Mercado Bom Preço</p>
				    <p class="position">Operadora de caixa</p>
				    <p class="period">2010 - 2013</p>
				    <p class="description">Atendimento ao cliente e fechamento de caixa.</p>
				</div>

				<div class="job">
				    <p class="title">Loja Central</p>
				    <p class="position">Auxiliar administrativo</p>
				    <p class="period">2014 - 2017</p>
				    <p class="description">Organização de documentos e apoio ao setor financeiro.</p>
				</div>

				<div class="job">
				    <p class="title">Clínica Vida</p>
				    <p class="position">Recepcionista</p>
				    <p class="period">2018 - 2021</p>
				    <p class="description">Agendamento de consultas e atendimento ao público.</p>
				</div>

				<div class="job">
				    <p class="title">Serviços Alfa</p>
				    <p class="position">Assistente administrativa</p>
				    <p class="period">2022 - atual</p>
				    <p class="description">Controle de planilhas, relatórios e atendimento interno.</p>
				</div>
				""".stripTrailing(), workExperienceHtml().stripTrailing());
	}

	@Test
	void shouldGenerateComplementaryCourseListHtmlStructure() {
		assertEquals("""
				<h2 class="complementary">Qualifica&ccedil;&otilde;es e Cursos Complementares</h2>

				<div class="course">
				    <p class="title">Informática básica</p>
				    <p>SENAI</p>
				    <p class="period">2011</p>
				    <p>40 horas</p>
				    <p class="description">Curso de pacote office e internet.</p>
				</div>

				<div class="course">
				    <p class="title">Atendimento ao cliente</p>
				    <p>SEBRAE</p>
				    <p class="period">2014</p>
				    <p>20 horas</p>
				    <p class="description">Técnicas de comunicação e relacionamento.</p>
				</div>

				<div class="course">
				    <p class="title">Excel intermediário</p>
				    <p>Fundação Bradesco</p>
				    <p class="period">2019</p>
				    <p>30 horas</p>
				    <p class="description">Planilhas, fórmulas e gráficos.</p>
				</div>

				<div class="course">
				    <p class="title">Rotinas administrativas</p>
				    <p>SENAC</p>
				    <p class="period">2023</p>
				    <p>60 horas</p>
				    <p class="description">Documentos, arquivos e processos administrativos.</p>
				</div>
				""".stripTrailing(), complementaryCoursesHtml().stripTrailing());
	}

	@Test
	void shouldGenerateQualitiesListHtmlStructure() {
		assertEquals("""
				<h2 class="qualities">Principais Qualidades / Caracter&iacute;sticas</h2>
				<ul>
				    <li>Comunicação</li>
				    <li>Pontualidade</li>
				    <li>Atenção</li>
				    <li>Aprendizado rápido</li>
				</ul>
				""".stripTrailing(), qualitiesHtml().stripTrailing());
	}

	private static SpringTemplateEngine templateEngine() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}

	private static CurriculumData completeCurriculumData() {
		return new CurriculumData(
				new PersonalInfo("João da Silva", "São Paulo", "SP", "Solteiro", 28, "(11) 99999-9999",
						"joao@email.com"),
				"Atuar como auxiliar administrativo",
				"Profissional com experiência em atendimento e organização de documentos.",
				List.of(
						new Education("Ensino médio completo", "Escola Estadual Central", "2006 - 2008",
								"Formação regular"),
						new Education("Técnico em Administração", "ETEC", "2009 - 2010",
								"Rotinas administrativas e financeiras"),
						new Education("Graduação em Gestão Comercial", "Faculdade Metropolitana", "2012 - 2015",
								"Formação superior"),
						new Education("Pós-graduação em Gestão de Pessoas", "Instituto Saber", "2018 - 2019",
								"Especialização em liderança")),
				List.of(
						new WorkExperience("Mercado Bom Preço", "Operadora de caixa", "2010 - 2013",
								"Atendimento ao cliente e fechamento de caixa."),
						new WorkExperience("Loja Central", "Auxiliar administrativo", "2014 - 2017",
								"Organização de documentos e apoio ao setor financeiro."),
						new WorkExperience("Clínica Vida", "Recepcionista", "2018 - 2021",
								"Agendamento de consultas e atendimento ao público."),
						new WorkExperience("Serviços Alfa", "Assistente administrativa", "2022 - atual",
								"Controle de planilhas, relatórios e atendimento interno.")),
				List.of(
						new ComplementaryCourse("Informática básica", "SENAI", "40 horas", "2011",
								"Curso de pacote office e internet."),
						new ComplementaryCourse("Atendimento ao cliente", "SEBRAE", "20 horas", "2014",
								"Técnicas de comunicação e relacionamento."),
						new ComplementaryCourse("Excel intermediário", "Fundação Bradesco", "30 horas", "2019",
								"Planilhas, fórmulas e gráficos."),
						new ComplementaryCourse("Rotinas administrativas", "SENAC", "60 horas", "2023",
								"Documentos, arquivos e processos administrativos.")),
				List.of("Comunicação", "Pontualidade", "Atenção", "Aprendizado rápido"));
	}

	private String personalInfoHtml() {
		return htmlBetween("<h1 class=\"name\">", "<h2 class=\"professional-goal\">");
	}

	private String professionalGoalHtml() {
		return htmlBetween("<h2 class=\"professional-goal\">", "<h2 class=\"professional-summary\">");
	}

	private String professionalSummaryHtml() {
		return htmlBetween("<h2 class=\"professional-summary\">", "<h2 class=\"educational-background\">");
	}

	private String educationHtml() {
		return htmlBetween("<h2 class=\"educational-background\">", "<h2 class=\"work-experience\">");
	}

	private String workExperienceHtml() {
		return htmlBetween("<h2 class=\"work-experience\">", "<h2 class=\"complementary\">");
	}

	private String complementaryCoursesHtml() {
		return htmlBetween("<h2 class=\"complementary\">", "<h2 class=\"qualities\">");
	}

	private String qualitiesHtml() {
		String normalizedHtml = completeHtml.replace("\r\n", "\n");
		String startMarker = "<h2 class=\"qualities\">";
		String endMarker = "</ul>";
		int start = normalizedHtml.indexOf(startMarker);
		assertTrue(start >= 0, () -> "Start marker not found: " + startMarker);

		int end = normalizedHtml.indexOf(endMarker, start);
		assertTrue(end >= 0, () -> "End marker not found: " + endMarker);

		end += endMarker.length();
		return normalizedHtml.substring(start, end).stripTrailing();
	}

	private String htmlBetween(String startMarker, String endMarker) {
		String normalizedHtml = completeHtml.replace("\r\n", "\n");
		int start = normalizedHtml.indexOf(startMarker);
		assertTrue(start >= 0, () -> "Start marker not found: " + startMarker);

		int end = normalizedHtml.indexOf(endMarker, start);
		assertTrue(end >= 0, () -> "End marker not found: " + endMarker);

		return normalizedHtml.substring(start, end).stripTrailing();
	}

}
