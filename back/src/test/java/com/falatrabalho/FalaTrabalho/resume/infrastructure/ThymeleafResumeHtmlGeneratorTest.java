package com.falatrabalho.FalaTrabalho.resume.infrastructure;

import com.falatrabalho.FalaTrabalho.resume.support.CurriculumDataFixture;
import com.falatrabalho.FalaTrabalho.resume.support.HtmlSections;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThymeleafResumeHtmlGeneratorTest {

	private final ThymeleafResumeHtmlGenerator generator = new ThymeleafResumeHtmlGenerator(templateEngine());
	private final String completeHtml = generator.generate(CurriculumDataFixture.complete()).content();

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
				</div>
				""".stripTrailing(), HtmlSections.personalInfo(completeHtml).stripTrailing());
	}

	@Test
	void shouldGenerateProfessionalGoalHtmlStructure() {
		assertEquals("""
				<h2 class="professional-goal">Objetivo Profissional</h2>
				<p>Atuar como auxiliar administrativo</p>
				""".stripTrailing(), HtmlSections.professionalGoal(completeHtml).stripTrailing());
	}

	@Test
	void shouldGenerateProfessionalSummaryHtmlStructure() {
		assertEquals("""
				<h2 class="professional-summary">Resumo Profissional</h2>
				<p>Profissional com experiência em atendimento e organização de documentos.</p>
				""".stripTrailing(), HtmlSections.professionalSummary(completeHtml).stripTrailing());
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
				""".stripTrailing(), HtmlSections.education(completeHtml).stripTrailing());
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
				""".stripTrailing(), HtmlSections.workExperience(completeHtml).stripTrailing());
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
				""".stripTrailing(), HtmlSections.complementaryCourses(completeHtml).stripTrailing());
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
				""".stripTrailing(), HtmlSections.qualities(completeHtml).stripTrailing());
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

}


