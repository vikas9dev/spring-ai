package com.knowprogram.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BedRockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BedRockApplication.class, args);
	}

    @Autowired
    public void debugBeans(ApplicationContext ctx) {
        System.out.println("OpenAI Bean: " + ctx.containsBean("openAiChatModel"));
        System.out.println("Bedrock Bean: " + ctx.containsBean("bedrockChatModel"));
    }

}
