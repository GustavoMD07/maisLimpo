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
                "Token de Confirmação: %s\n\n" + 
                "Se você não se cadastrou, por favor, ignore este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe +Limpo",
                nomeUsuario, token 
            );
            
            mensagem.setText(textoMensagem);
            mailSender.send(mensagem);
            System.out.println("LOG: Email de confirmação (com token para copiar) enviado para: " + destinatario);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao enviar email de confirmação para " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
             throw new EmailNaoConfirmadoException("Não foi possível enviar o email de confirmação.");
        }
    }

  public void enviarEmailRedefinicaoSenha(String destinatario, String token) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom(fromEmail);
            mensagem.setTo(destinatario);
            mensagem.setSubject("Projeto +Limpo - Token para Redefinir sua Senha");

            String textoMensagem = String.format(
                "Olá %s,\n\n" +
                "Recebemos uma solicitação para redefinir a senha da sua conta no Projeto +Limpo.\n\n" +
                "Copie o token abaixo e cole na tela de validação para criar uma nova senha:\n\n" +
                "Seu Token: %s\n\n" + 
                "Este token irá expirar em 1 hora.\n\n" +
                "Se você não solicitou essa alteração, pode ignorar este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe +Limpo",
                destinatario, token 
            );

            mensagem.setText(textoMensagem);
            mailSender.send(mensagem);
            System.out.println("LOG: Email de redefinição de senha (com TOKEN) enviado para: " + destinatario);

        } catch (Exception e) {
            System.err.println("Erro ao enviar email de redefinição para " + destinatario + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Não foi possível enviar o email de redefinição de senha.");
        }
    }
}