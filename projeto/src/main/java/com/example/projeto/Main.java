package com.example.projeto;

import com.example.projeto.Configs.SessionConfig;
import com.example.projeto.Utils.EmailUtils;
import com.example.projeto.Utils.EmailValidator;
import com.example.projeto.Views.EmailScreen;
import com.example.projeto.Views.LoginScreen;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        EmailScreen emailScreen = new EmailScreen();

        loginScreen.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginScreen.getEmailField().getText();
                String password = String.valueOf(loginScreen.getPasswordField().getPassword());

                // verificar se o email e senha são válidos
                if (EmailValidator.isValidEmail(email)) {
                    try {
                        Authenticator auth = EmailUtils.authenticator(email, password);
                        Properties props = new Properties();
                        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
                        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
                        props.put("mail.smtp.socketFactory.class",
                                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
                        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
                        props.put("mail.smtp.port", "465"); //SMTP Port

                        Session session = Session.getDefaultInstance(props, auth);
                        SessionConfig.session = session;

                        loginScreen.setVisible(false);
                        emailScreen.setVisible(true);
                    }catch (RuntimeException exception){
                        JOptionPane.showMessageDialog(loginScreen, "Email ou senha incorretos");
                    }

                } else {
                    JOptionPane.showMessageDialog(loginScreen, "Email ou senha incorretos");
                }
            }
        });

        emailScreen.getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Properties props = SessionConfig.getProps();
                String mailHost = emailScreen.getMailServerFieldText();
                String toEmail = emailScreen.getToText();
                String fromEmail = emailScreen.getFromText();
                String subject = emailScreen.getSubjectText();
                String body = emailScreen.getMessageAreaText();
                String attachment = emailScreen.getAttachmentText();

                if (attachment.equals("")){
                    EmailUtils.sendEmail(SessionConfig.session, mailHost, fromEmail, toEmail, subject, body);
                }else {
                    EmailUtils.sendAttachmentEmail(SessionConfig.session, mailHost, fromEmail, toEmail, subject, body, attachment);
                }
                JOptionPane.showMessageDialog(emailScreen, "Email enviado com sucesso");
                emailScreen.clean();
            }
        });
    }
}
