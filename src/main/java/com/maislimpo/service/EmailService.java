package com.maislimpo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.maislimpo.exception.EmailNaoConfirmadoException;


@Service
public class EmailService {

    private final JavaMailSender mailSender;

    //injetando o email do properties
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailConfirmacaoCadastro(String destinatario, String nomeUsuario, String token) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(destinatario);
            mensagem.setSubject("Bem-vindo ao +Limpo! Confirme seu Cadastro");
            
            String textoMensagem = String.format(
                "Olá %s,\n\n" +
                "Obrigado por se cadastrar no Projeto +Limpo!\n\n" +
                "Para confirmar seu endereço de e-mail e ativar sua conta, por favor, copie o seguinte Token de Confirmação e cole-o na janela de confirmação da aplicação:\n\n" +
                "Token de Confirmação: %s\n\n" + // O token será exibido aqui
                "Se você não se cadastrou, por favor, ignore este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe +Limpo",
                nomeUsuario, token //%s sendo um ""placeholder"" 
            );
            
            mensagem.setText(textoMensagem);
            mailSender.send(mensagem);
            System.out.println("LOG: Email de confirmação (com token para copiar) enviado para: " + destinatario);

        } catch (Exception e) {
            System.err.println("Erro ao enviar email de confirmação para " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
             throw new EmailNaoConfirmadoException("Não foi possível enviar o email de confirmação.");
        }
    }

    public void enviarEmailRedefinicaoSenha(String destinatario, String token) {
    try {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(fromEmail); // Você já tem essa variável na classe
        mensagem.setTo(destinatario);
        mensagem.setSubject("Projeto +Limpo - Instruções para Redefinir sua Senha");

        // Monta a URL que o usuário vai usar. MUITO IMPORTANTE!
        // Altere a parte "http://127.0.0.1:5500" se o seu frontend rodar em outra porta/endereço
        String urlRedefinicao = "http://127.0.0.1:5500/redefinir-senha.html?token=" + token;

        String textoMensagem = String.format(
            "Olá,\n\n" +
            "Recebemos uma solicitação para redefinir a senha da sua conta no Projeto +Limpo.\n\n" +
            "Clique no link abaixo ou copie e cole no seu navegador para criar uma nova senha:\n\n" +
            "%s\n\n" + // Placeholder para a URL
            "Este link irá expirar em 1 hora.\n\n" +
            "Se você não solicitou essa alteração, pode ignorar este e-mail com segurança.\n\n" +
            "Atenciosamente,\n" +
            "Equipe +Limpo",
            urlRedefinicao
        );

        mensagem.setText(textoMensagem);
        mailSender.send(mensagem);
        System.out.println("LOG: Email de redefinição de senha enviado para: " + destinatario);

    } catch (Exception e) {
        System.err.println("Erro ao enviar email de redefinição para " + destinatario + ": " + e.getMessage());
        e.printStackTrace();
        // Você pode querer criar uma exceção específica pra isso depois, mas por enquanto tá ótimo.
        throw new RuntimeException("Não foi possível enviar o email de redefinição de senha.");
    }
}
}