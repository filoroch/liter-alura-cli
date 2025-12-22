package com.alura.liter_alura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.annotation.CommandScan;

import java.net.http.HttpClient;
import java.time.Duration;

@SpringBootApplication
@CommandScan
public class LiterAluraCLI {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraCLI.class, args);
	}

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }
}
