package br.com.macrosxtreme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MacrosXtremeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacrosXtremeApplication.class, args);
	}

}
