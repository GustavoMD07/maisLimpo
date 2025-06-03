package com.maislimpo.maislimpo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.maislimpo.maislimpo.controller.UsuarioController;

@Component
public class TelaConfirmacaoToken extends JFrame {

    private static final long serialVersionUID = 1L;

    private final UsuarioController usuarioController;
    private final ApplicationContext applicationContext; 

    private JTextField txtToken;
    private JButton btnConfirmar;
    private JButton btnCancelar; 

//    private String emailUsuario;

    @Autowired
    public TelaConfirmacaoToken(UsuarioController usuarioController, ApplicationContext applicationContext) {
        this.usuarioController = usuarioController;
        this.applicationContext = applicationContext;
        
        setTitle("Confirmar Cadastro - Projeto +Limpo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha só esta janela, não a aplicação
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);
        initComponents();
    }

    // Construtor que pode receber o email do usuário que está confirmando (opcional)
    // Se usar este, a anotação @Autowired não se aplicaria diretamente nele se o Spring
    // for instanciar via construtor sem args (o que não é o caso se for @Component e só tiver o de cima)
    // Para usar este e ainda ter injeção, a TelaCadastro precisaria pegar o bean e depois chamar um método setEmmail.
    // Por simplicidade, vamos focar no construtor com injeção e não passar o email por enquanto,
    // já que o token é único.
    /*
    public TelaConfirmacaoToken(UsuarioController usuarioController, ApplicationContext applicationContext, String email) {
        this(usuarioController, applicationContext); // Chama o construtor principal
        this.emailUsuario = email;
        // Se quiser exibir o email na tela:
        // JLabel lblInfoEmail = new JLabel("Confirmando para o email: " + email);
        // add(lblInfoEmail, BorderLayout.NORTH); // Exemplo
    }
    */

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); // Layout principal da janela

        // Painel para o conteúdo
        JPanel painelConteudo = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento interno
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Rótulo de instrução
        JLabel lblInstrucao = new JLabel("Cole o Token de Confirmação recebido por e-mail:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa duas colunas
        painelConteudo.add(lblInstrucao, gbc);

        // Campo de texto para o token
        txtToken = new JTextField(20); // 20 colunas de largura preferida
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painelConteudo.add(txtToken, gbc);

        // Painel para os botões (para que fiquem juntos)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        // Botão Confirmar
        btnConfirmar = new JButton("Confirmar Token");
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarToken();
            }
        });
        painelBotoes.add(btnConfirmar);

        // Botão Cancelar (opcional, pode fechar a tela ou voltar para login)
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Poderia perguntar se quer voltar para a tela de login
                // ou simplesmente fechar esta janela de confirmação.
                TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
                telaLogin.setVisible(true);
                dispose(); // Fecha esta janela
            }
        });
        painelBotoes.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; // Para não esticar os botões
        gbc.anchor = GridBagConstraints.CENTER;
        painelConteudo.add(painelBotoes, gbc);

        // Adiciona um preenchimento nas bordas do painel de conteúdo
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        add(painelConteudo, BorderLayout.CENTER);
    }

    private void confirmarToken() {
        String token = txtToken.getText().trim();
        if (token.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o token.", "Token Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean sucesso = usuarioController.processarConfirmacaoDeEmail(token);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "E-mail confirmado com sucesso!\nSeu cadastro está completo. Você será redirecionado para a tela de login.", 
                    "Cadastro Confirmado!", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Abrir a tela de login
                TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
                telaLogin.setVisible(true);
                dispose(); // Fecha esta janela de confirmação
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Token inválido, expirado ou já utilizado.\nPor favor, verifique o token ou tente se cadastrar novamente para receber um novo.", 
                    "Falha na Confirmação", 
                    JOptionPane.ERROR_MESSAGE);
                txtToken.setText(""); // Limpa o campo para nova tentativa
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Ocorreu um erro ao processar a confirmação: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Método main para testar esta tela individualmente (opcional, mas útil para design)
    // Para rodar este main, as dependências injetadas (@Autowired) não funcionarão
    // a menos que você configure um contexto Spring manualmente aqui, o que é complexo para um simples teste de UI.
    /*
    public static void main(String[] args) {
        // Este main é apenas para visualização rápida do design da tela.
        // A lógica de confirmação não funcionará sem o contexto Spring.
        SwingUtilities.invokeLater(() -> {
            // Para testar, precisaríamos mockar ou passar null para os controllers/context,
            // o que limitaria o teste funcional.
            // Exemplo SIMPLIFICADO (NÃO FUNCIONAL PARA LÓGICA REAL):
            // TelaConfirmacaoToken tela = new TelaConfirmacaoToken(null, null);
            // tela.setVisible(true);
            System.out.println("Para testar a UI da TelaConfirmacaoToken, rode a aplicação principal e siga o fluxo de cadastro.");
        });
    }
    */
}