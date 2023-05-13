package com.example.projeto;

import com.example.projeto.Configs.AuthConfig;
import com.example.projeto.Configs.SessionConfig;
import com.example.projeto.Models.Email;
import com.example.projeto.Utils.EmailUtils;
import com.example.projeto.Utils.EmailValidator;
import com.example.projeto.Views.EmailListViewer;
import com.example.projeto.Views.EmailScreen;
import com.example.projeto.Views.LoginScreen;

import javax.mail.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();

        loginScreen.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = loginScreen.getEmailField().getText();
                String password = String.valueOf(loginScreen.getPasswordField().getPassword());

                // verificar se o email e senha são válidos
                if (EmailValidator.isValidEmail(email)) {
                    try {
                        Authenticator auth = EmailUtils.authenticator(email, password);
                        Properties props = EmailUtils.getPropertiesToReadEmails(email);
                        Session session = Session.getDefaultInstance(props, auth);
                        SessionConfig.session = session;
                        AuthConfig.authenticator = auth;

                        Store store = session.getStore("pop3");
                        store.connect("pop.gmail.com", email, password);

                        ArrayList<Email> emails = EmailUtils.readAndFormatEmails(store);
                        EmailListViewer emailListViewer = new EmailListViewer(emails);
                        loginScreen.setVisible(false);
                        emailListViewer.setVisible(true);
                    } catch (AuthenticationFailedException exception){
                        JOptionPane.showMessageDialog(loginScreen, "Email ou senha incorretos");
                    } catch (Exception exception ){
                        JOptionPane.showMessageDialog(loginScreen, "Emails não encontrados");
                    }
                } else {
                    JOptionPane.showMessageDialog(loginScreen, "Email inválido");
                }
            }
        });
    }
}
