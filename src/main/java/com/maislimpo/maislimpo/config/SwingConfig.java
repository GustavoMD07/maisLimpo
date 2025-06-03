package com.maislimpo.maislimpo.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maislimpo.maislimpo.MaislimpoApplication;

@Configuration
public class SwingConfig {

    @Bean
    public ApplicationContextInitializer<ConfigurableApplicationContext> swingInitializer() {
        return applicationContext -> {
            System.setProperty("java.awt.headless", "false");
        };
    }
    
    
    public static ConfigurableApplicationContext startSwingApplication(String[] args) {
        return new SpringApplicationBuilder(MaislimpoApplication.class)
                .headless(false)
                .run(args);
    }
}
