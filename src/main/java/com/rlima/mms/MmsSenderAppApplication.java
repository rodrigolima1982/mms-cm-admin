package com.rlima.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MmsSenderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmsSenderAppApplication.class, args);
	}	
}
