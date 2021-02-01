package com.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@EnableAsync
public class MmsSenderAppApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(MmsSenderAppApplication.class, args);
	}	
	
}
