package com.maislimpo.maislimpo.view;

import java.awt.Color;
import java.awt.Dimension; // Import para Dimension
import java.awt.FlowLayout; // Import para FlowLayout
import java.awt.Font; // Import para Font
import java.awt.Image; // Import para Image
import javax.swing.GroupLayout;
import javax.swing.ImageIcon; // Import para ImageIcon
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; // Import para JPanel
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.maislimpo.maislimpo.controller.UsuarioController;
import com.maislimpo.maislimpo.exception.EmailNaoConfirmadoException;

@Component
public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;

    private final UsuarioController usuarioController;
    private final ApplicationContext applicationContext;

    private JTextField textEmail;
    private JPasswordField textSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastro;
    private JLabel IconLogin;
    private JLabel Titulo;
    private JLabel Email;
    private JLabel Senha;
    private JLabel textCadastro;

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
            System.err.println("ALERTA: TelaLogin instanciada sem injeção de dependência Spring. Funcionalidades podem falhar.");
        }
    }

    private void configureFrame() {
        setTitle("Projeto Mais Limpo - Login");
        setSize(420, 450); // Aumentei um pouco a altura para dar mais espaço à imagem
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        IconLogin = new JLabel();
        Titulo = new JLabel();
        Email = new JLabel();
        Senha = new JLabel();
        textEmail = new JTextField();
        textSenha = new JPasswordField();
        botaoEntrar = new JButton();
        botaoCadastro = new JButton();
        textCadastro = new JLabel();

        // Configurações do Ícone/Imagem
        IconLogin.setHorizontalAlignment(SwingConstants.CENTER);
        IconLogin.setVerticalAlignment(SwingConstants.CENTER); // Centralizar verticalmente também
        try {
            java.net.URL imgUrl = getClass().getResource("/imagens/CadeadoAzul136.png");
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                // Redimensionar a imagem para um tamanho maior, mantendo a proporção
                // Você pode ajustar os valores 100, 100 para o tamanho desejado
                // Se quiser que ocupe mais altura, aumente o segundo parâmetro de scaleImage
                Image scaledImage = originalIcon.getImage().getScaledInstance(-1, 100, Image.SCALE_SMOOTH); // Largura automática, altura 100
                IconLogin.setIcon(new ImageIcon(scaledImage));
            } else {
                IconLogin.setText("Icon não encontrado");
                System.err.println("Erro: Imagem /imagens/CadeadoAzul136.png não encontrada no classpath.");
            }
        } catch (Exception e) {
            IconLogin.setText("Erro ao carregar icon");
            e.printStackTrace();
        }

        Titulo.setFont(new Font("Arial Black", 1, 16)); // Aumentei um pouco a fonte
        Titulo.setForeground(new Color(51, 153, 255));
        Titulo.setHorizontalAlignment(SwingConstants.CENTER);
        Titulo.setText("Bem Vindo ao Projeto +Limpo!");

        Email.setFont(new Font("Arial Black", 1, 12));
        Email.setForeground(new Color(51, 153, 255));
        Email.setText("E-mail");

        Senha.setFont(new Font("Arial Black", 1, 12));
        Senha.setForeground(new Color(51, 153, 255));
        Senha.setText("Senha");

        textEmail.setBackground(new Color(200, 225, 255)); // Cor de fundo mais suave
        textEmail.setForeground(new Color(0, 0, 0));
        textEmail.setPreferredSize(new Dimension(300, 25)); // Definindo tamanho preferido

        textSenha.setBackground(new Color(200, 225, 255)); // Cor de fundo mais suave
        textSenha.setForeground(new Color(0, 0, 0));
        textSenha.setPreferredSize(new Dimension(300, 25)); // Definindo tamanho preferido

        // Configuração dos botões
        Dimension buttonSize = new Dimension(145, 30); // Tamanho padrão para os botões

        botaoEntrar.setBackground(new Color(102, 204, 255));
        botaoEntrar.setFont(new Font("Arial Black", 1, 12));
        botaoEntrar.setForeground(new Color(0, 0, 255));
        botaoEntrar.setText("ENTRAR");
        botaoEntrar.setPreferredSize(buttonSize); // Aplicando tamanho padrão
        botaoEntrar.addActionListener(evt -> botaoEntrarActionPerformed(evt));

        botaoCadastro.setBackground(new Color(102, 204, 255));
        botaoCadastro.setFont(new Font("Arial Black", 1, 12));
        botaoCadastro.setForeground(new Color(0, 0, 255));
        botaoCadastro.setText("CADASTRO");
        botaoCadastro.setPreferredSize(buttonSize); // Aplicando tamanho padrão
        botaoCadastro.addActionListener(evt -> botaoCadastroActionPerformed(evt));
        
        textCadastro.setFont(new Font("Arial", 0, 10));
        textCadastro.setForeground(new Color(51, 153, 255));
        textCadastro.setText("Não possui um login? Cadastre-se! →");

        // Usando GroupLayout (como estava antes, mas com ajustes)
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Definição dos grupos horizontais
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER) // Centralizar todo o conteúdo
            .addComponent(Titulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(IconLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Adiciona espaço flexível nas laterais
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Email)
                    .addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(Senha)
                    .addComponent(textSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoEntrar, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textCadastro)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) // Espaço entre o texto e o botão
                        .addComponent(botaoCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) // Adiciona espaço flexível nas laterais
        );

        // Definição dos grupos verticais
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(Titulo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE) // Altura para o título
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // Espaço
            .addComponent(IconLogin, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE) // Altura definida para o ícone
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // Espaço
            .addComponent(Email)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(Senha)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(textSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(botaoEntrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(textCadastro)
                .addComponent(botaoCadastro, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Espaço flexível no final
        );
        pack(); // Ajusta o tamanho da janela aos componentes se GroupLayout não definir tamanho fixo.
                // Como definimos setSize em configureFrame, este pack pode ser opcional ou ajustar ligeiramente.
    }
    
    // ... (métodos botaoEntrarActionPerformed, botaoCadastroActionPerformed, etc. permanecem os mesmos)
    // Cole aqui o restante dos seus métodos de ação e variáveis que eu não incluí acima para focar no layout.
    // Certifique-se de que os action listeners dos botões estão corretos:
    // botaoEntrar.addActionListener(evt -> botaoEntrarActionPerformed(evt));
    // botaoCadastro.addActionListener(evt -> botaoCadastroActionPerformed(evt));

    private void botaoEntrarActionPerformed(java.awt.event.ActionEvent evt) {
        String email = textEmail.getText().trim();          
        String senha = new String(textSenha.getPassword()); 

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        if (usuarioController == null || applicationContext == null) {
            JOptionPane.showMessageDialog(this, 
                "Erro crítico de configuração: Componentes Spring não foram injetados corretamente.\nPor favor, reinicie a aplicação ou contate o suporte.", 
                "Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
             System.err.println("Falha na injeção em TelaLogin: usuarioController=" + usuarioController + ", applicationContext=" + applicationContext);
            return;
        }
        
        try {
            if (usuarioController.loginUsuario(email, senha)) {
                TelaPrincipal telaPrincipal = applicationContext.getBean(TelaPrincipal.class);
                telaPrincipal.setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "E-mail ou senha inválidos.", "Falha no Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (EmailNaoConfirmadoException ex) { 
            JOptionPane.showMessageDialog(this, 
                ex.getMessage() + "\nPor favor, verifique seu e-mail (incluindo a caixa de spam) e siga as instruções para confirmar.", 
                "Confirmação de E-mail Pendente", 
                JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) { 
             JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Login", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao tentar fazer login: " + e.getMessage(), "Erro Desconhecido", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

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

    private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void textSenhaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

}