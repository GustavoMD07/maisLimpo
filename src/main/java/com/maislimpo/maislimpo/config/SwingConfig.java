package com.maislimpo.maislimpo.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.maislimpo.maislimpo.MaislimpoApplication;

@Configuration
public class SwingConfig {
	 /**
     * Configura a aplicação Spring para funcionar com o Swing
     */
    @Bean
    public ApplicationContextInitializer<ConfigurableApplicationContext> swingInitializer() {
        return applicationContext -> {
            // Garantir que a aplicação não é headless
            System.setProperty("java.awt.headless", "false");
        };
    }
    
    /**
     * Método auxiliar para iniciar a aplicação com interface gráfica
     * @param args argumentos da linha de comando
     * @return o contexto da aplicação Spring
     */
    public static ConfigurableApplicationContext startSwingApplication(String[] args) {
        return new SpringApplicationBuilder(MaislimpoApplication.class)
                .headless(false)
                .run(args);
    }
}
