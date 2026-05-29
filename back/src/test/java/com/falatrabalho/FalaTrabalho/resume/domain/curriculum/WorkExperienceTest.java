package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class WorkExperienceTest {

	@Test
	void shouldCreateWorkExperience() {
		WorkExperience workExperience = new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "2010 - 2013",
				"Atendimento ao cliente e fechamento de caixa.");

		assertEquals("Mercado Bom Preco", workExperience.company());
		assertEquals("Operadora de caixa", workExperience.position());
		assertEquals("2010 - 2013", workExperience.period());
		assertEquals("Atendimento ao cliente e fechamento de caixa.", workExperience.description());
	}

	@Test
	void shouldNotAcceptNullCompany() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience(null, "Operadora de caixa", "2010 - 2013",
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptBlankCompany() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("   ", "Operadora de caixa", "2010 - 2013",
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptNullPosition() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", null, "2010 - 2013",
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptBlankPosition() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", "   ", "2010 - 2013",
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptNullPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", "Operadora de caixa", null,
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptBlankPeriod() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "   ",
						"Atendimento ao cliente e fechamento de caixa."));
	}

	@Test
	void shouldNotAcceptNullDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "2010 - 2013", null));
	}

	@Test
	void shouldNotAcceptBlankDescription() {
		assertThrows(IllegalArgumentException.class,
				() -> new WorkExperience("Mercado Bom Preco", "Operadora de caixa", "2010 - 2013", "   "));
	}
}
