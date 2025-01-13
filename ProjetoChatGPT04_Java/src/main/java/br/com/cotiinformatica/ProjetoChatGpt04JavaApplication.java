package br.com.cotiinformatica;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ProjetoChatGpt04JavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoChatGpt04JavaApplication.class, args);
	}

}
