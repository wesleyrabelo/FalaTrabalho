package com.falatrabalho.FalaTrabalho.resume.domain.curriculum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PersonalInfoTest {

	@Test
	void shouldCreatePersonalInfo() {
		PersonalInfo personalInfo = new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", 28,
				"(11) 99999-9999");

		assertEquals("Joao da Silva", personalInfo.name());
		assertEquals("Sao Paulo", personalInfo.city());
		assertEquals("SP", personalInfo.state());
		assertEquals("Solteiro", personalInfo.maritalStatus());
		assertEquals(28, personalInfo.age());
		assertEquals("(11) 99999-9999", personalInfo.phoneNumber());
	}

	@Test
	void shouldNotAcceptNullName() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo(null, "Sao Paulo", "SP", "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptBlankName() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("   ", "Sao Paulo", "SP", "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptNullCity() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", null, "SP", "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptBlankCity() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "   ", "SP", "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptNullState() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", null, "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptBlankState() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "   ", "Solteiro", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptNullMaritalStatus() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", null, 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptBlankMaritalStatus() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "   ", 28, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptNullAge() {
		assertThrows(NullPointerException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", null, "(11) 99999-9999"));
	}

	@Test
	void shouldNotAcceptNullPhoneNumber() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", 28, null));
	}

	@Test
	void shouldNotAcceptBlankPhoneNumber() {
		assertThrows(IllegalArgumentException.class,
				() -> new PersonalInfo("Joao da Silva", "Sao Paulo", "SP", "Solteiro", 28, "   "));
	}
}
