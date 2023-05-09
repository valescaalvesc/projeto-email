package com.example.projeto.Views;

import com.example.projeto.Utils.ScreenSize;
import com.example.projeto.Utils.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Senha:");
    private JTextField emailField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton loginButton = new JButton("Entrar");

    public LoginScreen() {
        super("Tela de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        WindowUtils.centerOnScreen(this);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        fieldsPanel.add(emailPanel);
        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        fieldsPanel.add(passwordPanel);
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(loginButton, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
