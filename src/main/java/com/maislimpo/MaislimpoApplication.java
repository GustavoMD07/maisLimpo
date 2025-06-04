package com.maislimpo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.SwingUtilities;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.maislimpo.view.TelaLogin;

@SpringBootApplication
public class MaislimpoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MaislimpoApplication.class)
				.headless(false)
				.run(args);

		SwingUtilities.invokeLater(() -> {
			
			TelaLogin telaLogin = context.getBean(TelaLogin.class); 
			telaLogin.setVisible(true);
		});
	}
}
