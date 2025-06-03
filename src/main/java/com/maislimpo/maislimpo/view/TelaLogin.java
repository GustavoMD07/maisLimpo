package com.maislimpo.maislimpo.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.maislimpo.maislimpo.controller.UsuarioController;
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;

// Presumindo que TelaDescricao exista no mesmo pacote ou seja importada corretamente
// import com.maislimpo.maislimpo.view.TelaDescricao; 

@Component
public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	private final UsuarioController usuarioController;
	private final ApplicationContext applicationContext;

	// Seus campos existentes
	private JTextField textEmail;
	private JPasswordField textSenha;
	private JButton botaoEntrar;
	private JButton botaoCadastro;
	private JLabel IconLogin;
	private JLabel Titulo;
	private JLabel Email;
	private JLabel Senha;
	private JLabel textCadastro;
	private JLabel ConhecaNossoProjeto;
	private JButton botaoMais;

	@Autowired
	public TelaLogin(UsuarioController usuarioController, ApplicationContext applicationContext) {
		this.usuarioController = usuarioController;
		this.applicationContext = applicationContext;
		initComponents();
		configureFrame();
	}

	public TelaLogin() {
		this(null, null);
		System.out.println("TelaLogin instanciada via construtor padrão. Dependências podem ser nulas.");
		if (this.usuarioController == null || this.applicationContext == null) {
			System.err.println(
					"ALERTA: TelaLogin instanciada sem injeção de dependência Spring. Funcionalidades podem falhar.");
		}
	}

	// Seu configureFrame (mantido)
	private void configureFrame() {
		setTitle("Projeto Mais Limpo - Login");
		setSize(422, 480); // Ajustei a altura para acomodar os novos botões, similar ao do seu amigo.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
	}

	private void initComponents() {

		// Suas inicializações existentes
		IconLogin = new JLabel();
		Titulo = new JLabel();
		Email = new JLabel();
		Senha = new JLabel();
		textEmail = new JTextField();
		textSenha = new JPasswordField();
		botaoEntrar = new JButton();
		botaoCadastro = new JButton();
		textCadastro = new JLabel();

		// Novas inicializações
		ConhecaNossoProjeto = new JLabel();
		botaoMais = new JButton();

		IconLogin.setHorizontalAlignment(SwingConstants.CENTER);
		IconLogin.setVerticalAlignment(SwingConstants.CENTER);
		try {
			java.net.URL imgUrl = getClass().getResource("/imagens/CadeadoAzul136.png");
			if (imgUrl != null) {
				ImageIcon originalIcon = new ImageIcon(imgUrl);
				Image scaledImage = originalIcon.getImage().getScaledInstance(-1, 100, Image.SCALE_SMOOTH);
				IconLogin.setIcon(new ImageIcon(scaledImage));
			} else {
				IconLogin.setText("Icon não encontrado");
				System.err.println("Erro: Imagem /imagens/CadeadoAzul136.png não encontrada no classpath.");
			}
		} catch (Exception e) {
			IconLogin.setText("Erro ao carregar icon");
			e.printStackTrace();
		}

		Titulo.setFont(new Font("Arial Black", 1, 16));
		Titulo.setForeground(new Color(51, 153, 255));
		Titulo.setHorizontalAlignment(SwingConstants.CENTER);
		Titulo.setText("Bem Vindo ao Projeto +Limpo!");

		Email.setFont(new Font("Arial Black", 1, 12));
		Email.setForeground(new Color(51, 153, 255));
		Email.setText("E-mail");

		Senha.setFont(new Font("Arial Black", 1, 12));
		Senha.setForeground(new Color(51, 153, 255));
		Senha.setText("Senha");

		textEmail.setBackground(new Color(200, 225, 255));
		textEmail.setForeground(new Color(0, 0, 0));
		textEmail.setPreferredSize(new Dimension(300, 25));

		textSenha.setBackground(new Color(200, 225, 255));
		textSenha.setForeground(new Color(0, 0, 0));
		textSenha.setPreferredSize(new Dimension(300, 25));

		// Configuração dos botões (mantida a sua base, ajustando tamanhos se necessário)
		Dimension buttonSize = new Dimension(145, 30); // Seu tamanho padrão
        Dimension buttonMaisSize = new Dimension(106, 30); // Tamanho para o botão "MAIS", como no do amigo

		botaoEntrar.setBackground(new Color(102, 204, 255));
		botaoEntrar.setFont(new Font("Arial Black", 1, 12));
		botaoEntrar.setForeground(new Color(0, 0, 255));
		botaoEntrar.setText("ENTRAR");
		botaoEntrar.setPreferredSize(buttonSize);
		botaoEntrar.addActionListener(evt -> botaoEntrarActionPerformed(evt));

		botaoCadastro.setBackground(new Color(102, 204, 255));
		botaoCadastro.setFont(new Font("Arial Black", 1, 12));
		botaoCadastro.setForeground(new Color(0, 0, 255));
		botaoCadastro.setText("CADASTRO");
		botaoCadastro.setPreferredSize(buttonSize); 
		botaoCadastro.addActionListener(evt -> botaoCadastroActionPerformed(evt));

		textCadastro.setFont(new Font("Arial", 0, 10));
		textCadastro.setForeground(new Color(51, 153, 255));
		textCadastro.setText("Não possui um login? Cadastre-se! \u2192"); 

		ConhecaNossoProjeto.setFont(new java.awt.Font("Arial", 0, 10));
		ConhecaNossoProjeto.setForeground(new java.awt.Color(51, 153, 255));
		ConhecaNossoProjeto.setText("Conheça mais sobre o nosso projeto! \u2192"); 
        ConhecaNossoProjeto.setPreferredSize(new Dimension(186, 23)); 

		botaoMais.setBackground(new java.awt.Color(102, 204, 255));
		botaoMais.setFont(new java.awt.Font("Arial Black", 1, 12));
		botaoMais.setForeground(new java.awt.Color(0, 0, 255));
		botaoMais.setText("MAIS");
        botaoMais.setPreferredSize(buttonMaisSize); 
		botaoMais.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				botaoMaisActionPerformed(evt);
			}
		});

		// Usando GroupLayout (baseado no seu, com adições)
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// Definição dos grupos horizontais
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
		    .addComponent(Titulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addComponent(IconLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		    .addGroup(layout.createSequentialGroup()
		        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Margem flexível esquerda
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addComponent(Email)
		            .addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            .addComponent(Senha)
		            .addComponent(textSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING) // Agrupa os botões para alinhá-los à direita
		                .addComponent(botaoEntrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addGroup(layout.createSequentialGroup()
		                    .addComponent(textCadastro)
		                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		                    .addComponent(botaoCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		                .addGroup(layout.createSequentialGroup()
		                    .addComponent(ConhecaNossoProjeto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) // Ajuste aqui para garantir que o botão MAIS fique colado ao texto
		                    .addComponent(botaoMais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Margem flexível direita
		);

		// Definição dos grupos verticais
		layout.setVerticalGroup(layout.createSequentialGroup()
		    .addComponent(Titulo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		    .addComponent(IconLogin, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		    .addComponent(Email)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		    .addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		    .addComponent(Senha)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
		    .addComponent(textSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		    .addComponent(botaoEntrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // Mantém o espaçamento
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(textCadastro)
		        .addComponent(botaoCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // Mantém o espaçamento
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(ConhecaNossoProjeto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		        .addComponent(botaoMais, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		pack();
	}

	// Seu botaoEntrarActionPerformed (mantido)
	private void botaoEntrarActionPerformed(java.awt.event.ActionEvent evt) {
		String email = textEmail.getText().trim();
		String senha = new String(textSenha.getPassword());

		if (email.isEmpty() || senha.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Campos Vazios",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (usuarioController == null || applicationContext == null) {
			JOptionPane.showMessageDialog(this,
					"Erro crítico de configuração: Componentes Spring não foram injetados corretamente.\nPor favor, reinicie a aplicação ou contate o suporte.",
					"Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
			System.err.println("Falha na injeção em TelaLogin: usuarioController=" + usuarioController
					+ ", applicationContext=" + applicationContext);
			return;
		}
		try {
			Usuario usuarioLogado = usuarioController.loginUsuario(email, senha);
			if (usuarioLogado != null) {
				System.out.println("LOG: Usuário logado com sucesso: " + usuarioLogado.getEmail() + " (ID: "
						+ usuarioLogado.getId() + ", Tipo: " + usuarioLogado.getTipoUsuario() + ")");
				TelaPrincipal telaPrincipal = applicationContext.getBean(TelaPrincipal.class);
				telaPrincipal.setUsuarioLogado(usuarioLogado);
				telaPrincipal.setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.", "Falha no Login",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (EmailNaoConfirmadoException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage()
					+ "\nPor favor, verifique seu e-mail (incluindo a caixa de spam) e siga as instruções para confirmar.",
					"Confirmação de E-mail Pendente", JOptionPane.WARNING_MESSAGE);
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Login", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao tentar fazer login: " + e.getMessage(),
					"Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Seu botaoCadastroActionPerformed (mantido)
	private void botaoCadastroActionPerformed(java.awt.event.ActionEvent evt) {
		if (applicationContext == null) {
			JOptionPane.showMessageDialog(this,
					"Erro crítico de configuração: ApplicationContext não injetado.\nNão é possível abrir a tela de cadastro.",
					"Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
			return;
		}
		TelaCadastro telaCadastro = applicationContext.getBean(TelaCadastro.class);
		telaCadastro.setVisible(true);
		this.dispose();
	}

	// Novo método para o botaoMais
	private void botaoMaisActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO: Certifique-se de que a classe TelaDescricao exista e esteja acessível.
		// Se TelaDescricao for uma tela simples que não precisa de injeção do Spring,
		// esta forma de instanciar é aceitável.
		// Se TelaDescricao também for um @Component gerenciado pelo Spring,
		// você faria:
		// TelaDescricao telaDescricao = applicationContext.getBean(TelaDescricao.class);
		// telaDescricao.setTelaAnterior(this); // Se ela precisar de referência
		// telaDescricao.setVisible(true);

		System.out.println("Botão MAIS clicado. Tentando abrir TelaDescricao..."); // Log para debug
		try {
			// Supondo que TelaDescricao tenha um construtor que aceita JFrame (a tela de login) como parâmetro.
			// Se não existir, você precisará criar ou ajustar TelaDescricao.
			TelaDescricao telaDescricao = new TelaDescricao(this); // Como no código do seu amigo
			telaDescricao.setVisible(true);
			this.setVisible(false); // Esconde a tela de login
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao abrir a tela de descrição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}
	@SuppressWarnings("unused")
	private void textSenhaActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

}