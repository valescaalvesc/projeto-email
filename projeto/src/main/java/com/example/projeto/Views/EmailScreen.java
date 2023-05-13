package com.example.projeto.Views;

import com.example.projeto.Configs.AuthConfig;
import com.example.projeto.Configs.SessionConfig;
import com.example.projeto.Utils.EmailUtils;
import com.example.projeto.Utils.ScreenSize;
import com.example.projeto.Utils.WindowUtils;

import javax.mail.Session;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Properties;

public class EmailScreen extends JDialog {
    private JLabel fromLabel = new JLabel("De:");
    private JLabel toLabel = new JLabel("Para:");
    private JLabel subjectLabel = new JLabel("Assunto:");
    private JLabel messageLabel = new JLabel("Mensagem:");
    private JLabel attachmentLabel = new JLabel("Anexo:");
    private JTextField fromField = new JTextField(20);
    private JTextField toField = new JTextField(20);
    private JTextField subjectField = new JTextField(20);
    private JTextArea messageArea = new JTextArea(5, 20);
    private JButton sendButton = new JButton("Enviar");
    private JButton clearButton = new JButton("Limpar");
    private JButton uploadButton = new JButton("Selecionar arquivo");
    private JTextField attachmentField = new JTextField(20);

    public EmailScreen(JFrame parent) {
        super(parent, "Enviar email", true);
        setLayout(new BorderLayout());

        setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(9, 2));
        fieldsPanel.add(fromLabel);
        fieldsPanel.add(fromField);
        fieldsPanel.add(toLabel);
        fieldsPanel.add(toField);
        fieldsPanel.add(subjectLabel);
        fieldsPanel.add(subjectField);
        fieldsPanel.add(messageLabel);
        fieldsPanel.add(new JScrollPane(messageArea));
        fieldsPanel.add(attachmentLabel);
        JPanel attachmentPanel = new JPanel(new BorderLayout());
        attachmentPanel.add(attachmentField, BorderLayout.CENTER);
        attachmentPanel.add(uploadButton, BorderLayout.EAST);
        fieldsPanel.add(attachmentPanel);
        panel.add(fieldsPanel, BorderLayout.CENTER);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Properties props = SessionConfig.getProps();
                String toEmail = getToText();
                String fromEmail = getFromText();
                String subject = getSubjectText();
                String body = getMessageAreaText();
                String attachment = getAttachmentText();

                Session session = Session.getInstance(EmailUtils.getPropertiesToSendEmails(), AuthConfig.authenticator);

                if (attachment.equals("")){
                    EmailUtils.sendEmail(session, fromEmail, toEmail, subject, body);
                }else {
                    EmailUtils.sendAttachmentEmail(session, fromEmail, toEmail, subject, body, attachment);
                }
                JOptionPane.showMessageDialog(EmailScreen.this, "Email enviado com sucesso");
                setVisible(false);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clean();
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(EmailScreen.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    attachmentField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.add(sendButton);
        buttonsPanel.add(clearButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public String getFromText() {
        return fromField.getText();
    }

    public String getToText() {
        return toField.getText();
    }

    public String getSubjectText() {
        return subjectField.getText();
    }

    public String getMessageAreaText() {
        return messageArea.getText();
    }

    public String getAttachmentText() {
        return attachmentField.getText();
    }

    public void clean() {
        // redefinindo os campos do formulário
        fromField.setText("");
        toField.setText("");
        subjectField.setText("");
        messageArea.setText("");
        attachmentField.setText("");
    }
}
