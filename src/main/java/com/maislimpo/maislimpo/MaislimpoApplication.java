package com.maislimpo.maislimpo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.SwingUtilities;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import com.maislimpo.maislimpo.view.TelaLogin;

@SpringBootApplication
public class MaislimpoApplication {

	public static void main(String[] args) {
		// inicializando o spring pra ser compatível com swing
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MaislimpoApplication.class)
				.headless(false)
				.run(args);

		// garante que a UI seja criada na Event Dispatch Thread (EDT)
		SwingUtilities.invokeLater(() -> {
			
			TelaLogin telaLogin = context.getBean(TelaLogin.class); 
			telaLogin.setVisible(true);
		});
	}
}
