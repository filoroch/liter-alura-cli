package com.alura.liter_alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class LiterAluraCLI {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraCLI.class, args);
	}
}
