package me.tapumandal.ovirupa;

import me.tapumandal.ovirupa.service.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableWebSecurity
public class OvirupaApplication {
	public static void main(String[] args) {
		SpringApplication.run(OvirupaApplication.class, args);
	}

}