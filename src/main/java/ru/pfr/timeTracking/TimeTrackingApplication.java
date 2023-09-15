package ru.pfr.timeTracking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.pfr.timeTracking.controller.uploadingfiles.StorageProperties;
import ru.pfr.timeTracking.controller.uploadingfiles.StorageService;

@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class TimeTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackingApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
