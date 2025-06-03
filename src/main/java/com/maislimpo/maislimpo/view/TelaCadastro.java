package com.maislimpo.maislimpo.view;

import javax.swing.*;
import java.awt.Color; // Import para Color
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext; // Importar ApplicationContext
import org.springframework.stereotype.Component;

import com.maislimpo.maislimpo.controller.UsuarioController;
import com.maislimpo.maislimpo.entity.Usuario;
import com.maislimpo.maislimpo.exception.EmailNaoEnviadoException;

@Component
public class TelaCadastro extends javax.swing.JFrame {
	 
	private static final long serialVersionUID = 1L;
	
    private final UsuarioController usuarioController;
    private final ApplicationContext applicationContext; // Para buscar outros beans

    @Autowired 
    public TelaCadastro(UsuarioController usuarioController, ApplicationContext applicationContext) {
        this.usuarioController = usuarioController;
        this.applicationContext = applicationContext;
        initComponents(); 
        configureFrame(); 
    }
    
    // Construtor padrão para compatibilidade com NetBeans GUI Builder (se necessário)
    // ATENÇÃO: Este construtor não terá as dependências injetadas pelo Spring.
    // Se o NetBeans o utilizar para o design, a lógica que depende do controller/context
    // não funcionará.
    public TelaCadastro() {
        // Esta é uma forma de obter o contexto se esta classe NÃO for gerenciada pelo Spring inicialmente,
        // mas se for @Component e instanciada pelo Spring, o construtor com @Autowired será usado.
        // Se precisar instanciar manualmente e ainda acessar beans, você teria que ter uma forma
        // estática de acessar o ApplicationContext, o que não é ideal.
        // O ideal é garantir que o Spring está instanciando esta tela.
        this(null, null); // Chama o construtor principal com nulos, o que pode causar NullPointerException
                           // se as dependências não forem tratadas.
                           // Melhor seria ter uma lógica de inicialização separada se este construtor for realmente usado.
        System.out.println("TelaCadastro instanciada via construtor padrão. Dependências (UsuarioController, ApplicationContext) podem ser nulas.");
        if (this.usuarioController == null || this.applicationContext == null) {
             // Tentar obter do contexto estático se existir um ApplicationContextProvider (não é o caso aqui)
             // ou lançar um erro/aviso. Para o TCC, assuma que o Spring está gerenciando.
             System.err.println("ALERTA: TelaCadastro instanciada sem injeção de dependência Spring. Funcionalidades podem falhar.");
        }
    }

    private void configureFrame() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        setTitle("Projeto Mais Limpo - Cadastro");
        getContentPane().setBackground(Color.WHITE); // Definir cor de fundo
        setResizable(false);
        setLocationRelativeTo(null); 
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        }


        Email.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        Email.setForeground(new java.awt.Color(51, 153, 255));
        Email.setText("Digite o seu E-mail");

        textEmail.setBackground(new java.awt.Color(102, 204, 255));
        textEmail.setForeground(new java.awt.Color(0,0,0)); // Definindo cor do texto
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
        textSenha1.setForeground(new java.awt.Color(0,0,0)); // Definindo cor do texto
        textSenha1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSenha1ActionPerformed(evt);
            }
        });

        textSenha2.setBackground(new java.awt.Color(102, 204, 255));
        textSenha2.setForeground(new java.awt.Color(0,0,0)); // Definindo cor do texto
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
                .addContainerGap(60, Short.MAX_VALUE)) // Ajustado para simetria
            .addComponent(Senha3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Alterado para centralizar melhor
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
        String email = textEmail.getText().trim(); // Adicionado trim() para remover espaços extras             
        String senha1 = new String(textSenha1.getPassword()); 
        String senha2 = new String(textSenha2.getPassword());

        if (email.isEmpty() || senha1.isEmpty() || senha2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        
        if (!senha1.equals(senha2)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar formato do email (simples)
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Formato de e-mail inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar força da senha (exemplo simples: mínimo 6 caracteres)
        if (senha1.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senha1); 
        // O tipo de usuário (Voluntário/ONG) pode ser pego de opcaoCadastro.getSelectedItem().toString()
        // e salvo em um campo apropriado na entidade Usuario, se necessário.

        // Verifica se as dependências Spring foram injetadas
        if (usuarioController == null || applicationContext == null) {
            JOptionPane.showMessageDialog(this, 
                "Erro crítico de configuração: Componentes Spring não foram injetados corretamente.\nPor favor, reinicie a aplicação ou contate o suporte.", 
                "Erro de Configuração Interna", JOptionPane.ERROR_MESSAGE);
            System.err.println("Falha na injeção: usuarioController=" + usuarioController + ", applicationContext=" + applicationContext);
            return;
        }
        
        try {
            // Chama o controller para iniciar o processo de cadastro (que envia o email)
            usuarioController.iniciarCadastroUsuario(novoUsuario); 
            
            // Se chegou aqui, o usuário foi salvo (não confirmado) e o email foi (ou tentou ser) enviado.
            JOptionPane.showMessageDialog(this,
                "Um e-mail de confirmação foi enviado para " + email + ".\n" +
                "Por favor, verifique sua caixa de entrada (e spam!), copie o token recebido\n" +
                "e cole-o na próxima janela para ativar sua conta.", 
                "Confirmação Pendente", 
                JOptionPane.INFORMATION_MESSAGE);

            // Fecha a tela de cadastro
            this.dispose(); 

            // Abre a tela de confirmação de token
            TelaConfirmacaoToken telaConfirmacao = applicationContext.getBean(TelaConfirmacaoToken.class);
            // Opcional: Passar o email para a tela de confirmação, se ela for adaptada para recebê-lo
            // telaConfirmacao.setEmailParaConfirmacao(email); // Precisaria de um método setter em TelaConfirmacaoToken
            telaConfirmacao.setVisible(true);

        } catch (IllegalArgumentException e) { 
            // Exceção específica do UsuarioService para "email já cadastrado e confirmado"
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro ao Cadastrar", JOptionPane.ERROR_MESSAGE);
        } catch (EmailNaoEnviadoException e) { // Supondo que você crie e use essa exceção no EmailService
             JOptionPane.showMessageDialog(this, 
                "Não foi possível enviar o e-mail de confirmação no momento.\n" +
                "Por favor, tente novamente mais tarde ou verifique suas configurações de rede.\n" +
                "Detalhe: " + e.getMessage(), 
                "Falha no Envio de E-mail", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception e) { 
            // Outras exceções inesperadas (problemas de rede, banco de dados, etc.)
            JOptionPane.showMessageDialog(this, 
                "Ocorreu um erro inesperado durante o processo de cadastro:\n" + e.getMessage() +
                "\nVerifique o console para mais detalhes.", "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Bom para debug no console
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