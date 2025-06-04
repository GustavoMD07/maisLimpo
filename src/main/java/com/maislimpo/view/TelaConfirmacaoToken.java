package com.maislimpo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.maislimpo.controller.UsuarioController;

@Component
public class TelaConfirmacaoToken extends JFrame {

    private static final long serialVersionUID = 1L;

    private final UsuarioController usuarioController;
    private final ApplicationContext applicationContext; 

    private JTextField txtToken;
    private JButton btnConfirmar;
    private JButton btnCancelar; 

//  private String emailUsuario;

    @Autowired
    public TelaConfirmacaoToken(UsuarioController usuarioController, ApplicationContext applicationContext) {
        this.usuarioController = usuarioController;
        this.applicationContext = applicationContext;
        
        setTitle("Confirmar Cadastro - Projeto +Limpo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null); 
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); 


        JPanel painelConteudo = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInstrucao = new JLabel("Cole o Token de Confirmação recebido por e-mail:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        painelConteudo.add(lblInstrucao, gbc);

        txtToken = new JTextField(20); 
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painelConteudo.add(txtToken, gbc);

       
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        

        btnConfirmar = new JButton("Confirmar Token");
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarToken();
            }
        });
        painelBotoes.add(btnConfirmar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
                telaLogin.setVisible(true);
                dispose(); 
            }
        });
        painelBotoes.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER;
        painelConteudo.add(painelBotoes, gbc);

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
                
                TelaLogin telaLogin = applicationContext.getBean(TelaLogin.class);
                telaLogin.setVisible(true);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Token inválido, expirado ou já utilizado.\nPor favor, verifique o token ou tente se cadastrar novamente para receber um novo.", 
                    "Falha na Confirmação", 
                    JOptionPane.ERROR_MESSAGE);
                txtToken.setText("");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Ocorreu um erro ao processar a confirmação: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}