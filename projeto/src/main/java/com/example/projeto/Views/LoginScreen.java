package com.example.projeto.Views;

import com.example.projeto.Utils.ScreenSize;
import com.example.projeto.Utils.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Senha:");
    private JTextField emailField = new JTextField(5);
    private JPasswordField passwordField = new JPasswordField(5);
    private JButton loginButton = new JButton("Entrar");

    public LoginScreen() {
        super("Tela de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        WindowUtils.centerOnScreen(this);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        add(panel);

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
