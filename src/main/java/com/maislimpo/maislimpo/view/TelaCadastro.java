package com.maislimpo.maislimpo.view;

import javax.swing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext; // Importar ApplicationContext
import org.springframework.stereotype.Component;
import com.maislimpo.maislimpo.controller.UsuarioController;
import com.maislimpo.maislimpo.entity.Usuario;

@Component
public class TelaCadastro extends javax.swing.JFrame {
	 
	private static final long serialVersionUID = 1L;
	
    private UsuarioController usuarioController;
    private ApplicationContext applicationContext; // Para buscar outros beans

    @Autowired
    public TelaCadastro(UsuarioController usuarioController, ApplicationContext applicationContext) { // Injetar ApplicationContext
        this.usuarioController = usuarioController;
        this.applicationContext = applicationContext; // Armazenar o contexto
        initComponents();
        configureFrame();
    }
    
    // Construtor padrão (se o NetBeans GUI Builder precisar)
    public TelaCadastro() {
        System.out.println("TelaCadastro instanciada sem UsuarioController ou ApplicationContext via Spring - pode não funcionar corretamente.");
        initComponents();
        configureFrame();
        // ATENÇÃO: Este construtor não terá as dependências injetadas pelo Spring.
        // Se o NetBeans o utilizar para o design, a lógica que depende do controller/context
        // não funcionará no modo design ou se instanciado diretamente sem o Spring.
        // Para obter as dependências aqui, seria necessário um lookup manual estático,
        // o que é um antipadrão (ex: ApplicationContextProvider.getContext().getBean(...)).
        // O ideal é que a IDE permita configurar qual construtor usar para preview,
        // ou que a lógica de negócio seja testada apenas quando a aplicação roda via Spring.
    }

    private void configureFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Projeto Mais Limpo - Cadastro");
        setBackground(new java.awt.Color(255, 255, 255));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projeto Mais Limpo - Cadastro");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        textCadastro.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        textCadastro.setForeground(new java.awt.Color(51, 153, 255));
        textCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textCadastro.setText("Cadastro");

        // Tentativa de carregar a imagem a partir do classpath
        try {
            java.net.URL imgUrl = getClass().getResource("/imagens/CadastroNovo80x80.png");
            if (imgUrl != null) {
                LogoCadastro.setIcon(new javax.swing.ImageIcon(imgUrl)); // NOI18N
            } else {
                LogoCadastro.setText("Logo não encontrada");
                System.err.println("Erro: Imagem /images/CadastroNovo80x80.png não encontrada no classpath.");
            }
        } catch (Exception e) {
            LogoCadastro.setText("Erro ao carregar logo");
            e.printStackTrace();
        }
        LogoCadastro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);


        Email.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Email.setForeground(new java.awt.Color(51, 153, 255));
        Email.setText("Digite o seu E-mail");

        textEmail.setBackground(new java.awt.Color(102, 204, 255));
        textEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEmailActionPerformed(evt);
            }
        });

        Senha1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Senha1.setForeground(new java.awt.Color(51, 153, 255));
        Senha1.setText("Digite a sua Senha");

        Senha2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Senha2.setForeground(new java.awt.Color(51, 153, 255));
        Senha2.setText("Confirme a sua Senha");

        textSenha1.setBackground(new java.awt.Color(102, 204, 255));
        textSenha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSenha1ActionPerformed(evt);
            }
        });

        textSenha2.setBackground(new java.awt.Color(102, 204, 255));
        textSenha2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSenha2ActionPerformed(evt);
            }
        });

        Cadastrar.setBackground(new java.awt.Color(102, 204, 255));
        Cadastrar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Cadastrar.setForeground(new java.awt.Color(0, 0, 255));
        Cadastrar.setText("CADASTRAR");
        Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastrarActionPerformed(evt);
            }
        });

        opcaoCadastro.setBackground(new java.awt.Color(102, 204, 255));
        opcaoCadastro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        opcaoCadastro.setForeground(new java.awt.Color(0, 0, 255));
        opcaoCadastro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Voluntário", "ONG" }));
        opcaoCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaoCadastroActionPerformed(evt);
            }
        });

        Senha3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Senha3.setForeground(new java.awt.Color(51, 153, 255));
        Senha3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Senha3.setText("Como você deseja ser cadastrado?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(LogoCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Email, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(Senha1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Senha2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSenha1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSenha2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Senha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(165, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(opcaoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Cadastrar)
                        .addGap(62, 62, 62))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(textCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LogoCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE) // Altura definida para o logo
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cadastrar)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailActionPerformed

    private void CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastrarActionPerformed
        String email = textEmail.getText();              
        String senha1 = new String(textSenha1.getPassword()); 
        String senha2 = new String(textSenha2.getPassword());

        if (email.isEmpty() || senha1.isEmpty() || senha2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        
        if (!senha1.equals(senha2)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha1);

        // Verifica se o controller e o applicationContext foram injetados
        // (Principalmente relevante se o construtor padrão for usado pelo NetBeans)
        if (usuarioController == null || applicationContext == null) {
            JOptionPane.showMessageDialog(this, 
                "Erro crítico: Dependências do Spring (UsuarioController ou ApplicationContext) não foram injetadas. A aplicação pode não estar configurada corretamente.", 
                "Erro de Configuração", JOptionPane.ERROR_MESSAGE);
            // Log para debug
            System.err.println("UsuarioController: " + usuarioController);
            System.err.println("ApplicationContext: " + applicationContext);
            return;
        }
        
        try {
            usuarioController.cadastrarUsuario(novoUsuario);
            JOptionPane.showMessageDialog(this,
                "Cadastro realizado com sucesso! Você será redirecionado para a tela de login.", 
                "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);

            // CORREÇÃO AQUI: Abrir a TelaLogin obtendo-a do contexto Spring
            TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
            telaLogin.setVisible(true);
            
            this.dispose(); // Fechar a tela atual (cadastro)

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao Cadastrar", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado durante o cadastro: " + e.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
        }
    }//GEN-LAST:event_CadastrarActionPerformed

    private void opcaoCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaoCadastroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_opcaoCadastroActionPerformed

    private void textSenha2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSenha2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSenha2ActionPerformed

    private void textSenha1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSenha1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSenha1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    // End of variables declaration//GEN-END:variables
}
