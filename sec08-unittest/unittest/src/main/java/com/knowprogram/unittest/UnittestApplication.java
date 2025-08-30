package com.knowprogram.unittest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class UnittestApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnittestApplication.class, args);
	}

}
