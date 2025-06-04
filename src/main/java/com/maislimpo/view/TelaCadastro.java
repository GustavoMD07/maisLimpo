package com.maislimpo.view;

import javax.swing.*;
import java.awt.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.maislimpo.controller.UsuarioController;
import com.maislimpo.entity.Usuario;
import com.maislimpo.exception.EmailNaoEnviadoException;

@Component //bean
public class TelaCadastro extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private final UsuarioController usuarioController;
	private final ApplicationContext applicationContext; // pra buscar outros beans

	@Autowired
	public TelaCadastro(UsuarioController usuarioController, ApplicationContext applicationContext) {
		this.usuarioController = usuarioController;
		this.applicationContext = applicationContext;
		initComponents();
		configureFrame();
	}

	public TelaCadastro() { //didi, esse aqui é só pq o Spring tava bugando e reclamando que n tinha um
		this(null, null);
		System.out.println(
				"TelaCadastro instanciada via construtor padrão. Dependências (UsuarioController, ApplicationContext) podem ser nulas.");
		if (this.usuarioController == null || this.applicationContext == null) {
			System.err.println(
					"ALERTA: TelaCadastro instanciada sem injeção de dependência Spring. Funcionalidades podem falhar.");
		}
	}

	private void configureFrame() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Projeto Mais Limpo - Cadastro");
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void initComponents() {

		textCadastro = new javax.swing.JLabel();
		LogoCadastro = new javax.swing.JLabel();
		Email = new javax.swing.JLabel();
		textEmail = new javax.swing.JTextField();
		Senha1 = new javax.swing.JLabel();
		Senha2 = new javax.swing.JLabel();
		textSenha1 = new javax.swing.JPasswordField();
		textSenha2 = new javax.swing.JPasswordField();
		Cadastrar = new javax.swing.JButton();
		opcaoCadastro = new javax.swing.JComboBox<>();
		Senha3 = new javax.swing.JLabel();
		botaoVoltar = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Projeto Mais Limpo - Cadastro");
		setBackground(new java.awt.Color(255, 255, 255));
		setResizable(false);

		textCadastro.setFont(new java.awt.Font("Arial", 1, 24)); 
		textCadastro.setForeground(new java.awt.Color(51, 153, 255));
		textCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		textCadastro.setText("Cadastro");

		LogoCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		try {
			java.net.URL imgUrl = getClass().getResource("/imagens/CadastroNovo80x80.png");
			if (imgUrl != null) {
				LogoCadastro.setIcon(new javax.swing.ImageIcon(imgUrl));
			} else {
				LogoCadastro.setText("Logo não encontrada");
				System.err.println("Erro: Imagem /imagens/CadastroNovo80x80.png não encontrada no classpath.");
			}
		} catch (Exception e) {
			LogoCadastro.setText("Erro ao carregar logo");
			e.printStackTrace();
		} //aqui, em vez de pegar o caminho do pc de cada um, eu to colocando pra pegar direto do campo "resources" do projeto
		//no repository git, tem a pasta com todas as imagens q a gente usou, e esse try-catch
		//é pra caso a imagem n seja encontrada, o sistema n bugar e fechar

		Email.setFont(new java.awt.Font("Arial", 1, 12)); 
		Email.setForeground(new java.awt.Color(51, 153, 255));
		Email.setText("Digite o seu E-mail");

		textEmail.setBackground(new java.awt.Color(102, 204, 255));
		textEmail.setForeground(new java.awt.Color(0, 0, 0));
		textEmail.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textEmailActionPerformed(evt);
			}
		});

		Senha1.setFont(new java.awt.Font("Arial", 1, 12)); 
		Senha1.setForeground(new java.awt.Color(51, 153, 255));
		Senha1.setText("Digite a sua Senha");

		Senha2.setFont(new java.awt.Font("Arial", 1, 12)); 
		Senha2.setForeground(new java.awt.Color(51, 153, 255));
		Senha2.setText("Confirme a sua Senha");

		textSenha1.setBackground(new java.awt.Color(102, 204, 255));
		textSenha1.setForeground(new java.awt.Color(0, 0, 0));
		textSenha1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textSenha1ActionPerformed(evt);
			}
		});

		textSenha2.setBackground(new java.awt.Color(102, 204, 255));
		textSenha2.setForeground(new java.awt.Color(0, 0, 0));
		textSenha2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				textSenha2ActionPerformed(evt);
			}
		});

		Cadastrar.setBackground(new java.awt.Color(102, 204, 255));
		Cadastrar.setFont(new java.awt.Font("Arial", 1, 12)); 
		Cadastrar.setForeground(new java.awt.Color(0, 0, 255));
		Cadastrar.setText("CADASTRAR");
		Cadastrar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CadastrarActionPerformed(evt);
			}
		});

		opcaoCadastro.setBackground(new java.awt.Color(102, 204, 255));
		opcaoCadastro.setFont(new java.awt.Font("Arial", 1, 12)); 
		opcaoCadastro.setForeground(new java.awt.Color(0, 0, 255));
		opcaoCadastro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Voluntário", "ONG" }));
		opcaoCadastro.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				opcaoCadastroActionPerformed(evt);
			}
		});

		Senha3.setFont(new java.awt.Font("Arial", 1, 12)); 
		Senha3.setForeground(new java.awt.Color(51, 153, 255));
		Senha3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		Senha3.setText("Como você deseja ser cadastrado?");

		botaoVoltar.setBackground(new java.awt.Color(102, 204, 255));
		botaoVoltar.setFont(new java.awt.Font("Arial", 1, 12)); 
		botaoVoltar.setForeground(new java.awt.Color(0, 0, 255));
		botaoVoltar.setText("VOLTAR");
		botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoVoltarActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addComponent(textCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addComponent(LogoCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addComponent(Senha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addGroup(layout.createSequentialGroup()
		        .addGap(60, 60, 60)
		        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		            .addComponent(Email)
		            .addComponent(textEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		            .addComponent(Senha1)
		            .addComponent(Senha2)
		            .addComponent(textSenha1)
		            .addComponent(textSenha2))
		        .addContainerGap(60, Short.MAX_VALUE))
		    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		        .addComponent(opcaoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addGap(165, 165, 165))
		    .addGroup(layout.createSequentialGroup()
		        .addGap(40, 40, 40) 
		        .addComponent(botaoVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE) // Define um tamanho pro VOLTAR
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Empurra o CADASTRAR pra direita
		        .addComponent(Cadastrar) 
		        .addGap(40, 40, 40)) 
		);
		layout.setVerticalGroup(
		    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		    .addGroup(layout.createSequentialGroup()
		        .addGap(18, 18, 18)
		        .addComponent(textCadastro)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(LogoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addGap(18, 18, 18)
		        .addComponent(Email)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(Senha1)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(textSenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(Senha2)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(textSenha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		        .addComponent(Senha3)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		        .addComponent(opcaoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED) 
		        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		            .addComponent(botaoVoltar)
		            .addComponent(Cadastrar))
		        .addContainerGap(30, Short.MAX_VALUE)) 
		);

		pack();
	}

	private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {
		
	}

	private void CadastrarActionPerformed(java.awt.event.ActionEvent evt) {
		String email = textEmail.getText().trim(); // trim remove espaços extras
		String senha1 = new String(textSenha1.getPassword());
		String senha2 = new String(textSenha2.getPassword());

		String tipoSelecionadoOriginal = "";
		if (opcaoCadastro.getSelectedItem() != null) {
			tipoSelecionadoOriginal = opcaoCadastro.getSelectedItem().toString();
		}
		String tipoUsuarioFinal = tipoSelecionadoOriginal;

		if ("gustavomatachun.domingues@gmail.com".equalsIgnoreCase(email)) {
			tipoUsuarioFinal = "Admin";
			System.out.println("LOG: Email de administrador detectado. Tipo de usuário definido como 'Admin'.");
		}

		if ("joyce_sfernandes@hotmail.com".equalsIgnoreCase(email)) {
			tipoUsuarioFinal = "Admin";
			System.out.println("LOG: Email de administrador detectado. Tipo de usuário definido como 'Admin'.");
		}

		if ("munizdiego12@gmail.com".equalsIgnoreCase(email)) {
			tipoUsuarioFinal = "Admin";
			System.out.println("LOG: Email de administrador detectado. Tipo de usuário definido como 'Admin'.");
		}

		if ("claudiohenriqueoliveiralp@gmail.com".equalsIgnoreCase(email)) {
			tipoUsuarioFinal = "Admin";
			System.out.println("LOG: Email de administrador detectado. Tipo de usuário definido como 'Admin'.");
		}

		if (email.isEmpty() || senha1.isEmpty() || senha2.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Campos Vazios",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!senha1.equals(senha2)) {
			JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro de Validação",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// validar formato do email
		if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			JOptionPane.showMessageDialog(this, "Formato de e-mail inválido.", "Erro de Validação",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// validações da senha usando regex (letra maiscula, minuscula e caracter especial)
		if (senha1.length() < 6) {
			JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres.", "Erro de Validação",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!senha1.matches(".*[A-Z].*")) {
		    JOptionPane.showMessageDialog(this, "A senha deve conter pelo menos uma letra maiúscula.", "Erro de Validação",
		            JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		if (!senha1.matches(".*[a-z].*")) {
			JOptionPane.showMessageDialog(this, "A senha deve conter pelo menos uma letra minúscula.",
					"Erro de Validação", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!senha1.matches(".*[!@#%_\\-].*")) {
		    JOptionPane.showMessageDialog(this, "A senha deve conter pelo menos um caractere especial (ex: !@#%_-).", "Erro de Validação",
		            JOptionPane.ERROR_MESSAGE);
		    return;
		}

		Usuario novoUsuario = new Usuario();
		novoUsuario.setEmail(email);
		novoUsuario.setSenha(senha1);
		novoUsuario.setTipoUsuario(tipoUsuarioFinal);

		if (usuarioController == null || applicationContext == null) {
			JOptionPane.showMessageDialog(this,
					"Erro crítico de configuração: Componentes Spring não foram injetados corretamente.\nPor favor, reinicie a aplicação ou contate o suporte.",
					"Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
			System.err.println("Falha na injeção: usuarioController=" + usuarioController + ", applicationContext="
					+ applicationContext);
			return;
		}

		try {
			usuarioController.iniciarCadastroUsuario(novoUsuario);
			JOptionPane.showMessageDialog(this,
					"Um e-mail de confirmação foi enviado para " + email + " (como " + tipoUsuarioFinal + ").\n" + 
							"Por favor, verifique sua caixa de entrada (e spam!), copie o token recebido\n"
							+ "e cole-o na próxima janela para ativar sua conta.",
					"Confirmação Pendente", JOptionPane.INFORMATION_MESSAGE);

			this.dispose();

			TelaConfirmacaoToken telaConfirmacao = applicationContext.getBean(TelaConfirmacaoToken.class);
			telaConfirmacao.setVisible(true);

		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao Cadastrar", JOptionPane.ERROR_MESSAGE);
		} catch (EmailNaoEnviadoException e) { 
			JOptionPane.showMessageDialog(this,
					"Não foi possível enviar o e-mail de confirmação no momento.\n"
							+ "Por favor, tente novamente mais tarde ou verifique suas configurações de rede.\n"
							+ "Detalhe: " + e.getMessage(),
					"Falha no Envio de E-mail", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(this,
							"Ocorreu um erro inesperado durante o processo de cadastro:\n" + e.getMessage()
									+ "\nVerifique o console para mais detalhes.",
							"Erro Crítico", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}// GEN-LAST:event_CadastrarActionPerformed

	private void opcaoCadastroActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_opcaoCadastroActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_opcaoCadastroActionPerformed

	private void textSenha2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textSenha2ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_textSenha2ActionPerformed

	private void textSenha1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_textSenha1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_textSenha1ActionPerformed

	private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {
		if (applicationContext == null) {
			JOptionPane.showMessageDialog(this,
		"Erro crítico de configuração: ApplicationContext não injetado.\nNão é possível voltar para a tela de login.",
		"Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
			return;
		}
		TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
		telaLogin.setVisible(true);
		this.dispose();
	}

	private javax.swing.JButton Cadastrar;
	private javax.swing.JLabel Email;
	private javax.swing.JLabel LogoCadastro;
	private javax.swing.JLabel Senha1;
	private javax.swing.JLabel Senha2;
	private javax.swing.JLabel Senha3;
	private javax.swing.JComboBox<String> opcaoCadastro;
	private javax.swing.JLabel textCadastro;
	private javax.swing.JTextField textEmail;
	private javax.swing.JPasswordField textSenha1;
	private javax.swing.JPasswordField textSenha2;
	private javax.swing.JButton botaoVoltar;
}