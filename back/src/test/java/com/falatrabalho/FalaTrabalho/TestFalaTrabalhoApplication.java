package com.falatrabalho.FalaTrabalho;

import org.springframework.boot.SpringApplication;

public class TestFalaTrabalhoApplication {

	public static void main(String[] args) {
		SpringApplication.from(FalaTrabalhoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
