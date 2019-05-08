package com.nagarro.nagptrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NagpTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagpTrackingSystemApplication.class, args);
	}

}
