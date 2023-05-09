package com.example.projeto.Views;

import com.example.projeto.Utils.ScreenSize;
import com.example.projeto.Utils.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class EmailScreen extends JFrame {
    private JLabel mailServerLabel = new JLabel("Mail Server:");
    private JLabel fromLabel = new JLabel("De:");
    private JLabel toLabel = new JLabel("Para:");
    private JLabel ccLabel = new JLabel("Cc:");
    private JLabel bccLabel = new JLabel("Bcc:");
    private JLabel subjectLabel = new JLabel("Assunto:");
    private JLabel messageLabel = new JLabel("Mensagem:");
    private JLabel attachmentLabel = new JLabel("Anexo:");
    private JTextField mailServerField = new JTextField(20);
    private JTextField fromField = new JTextField(20);
    private JTextField toField = new JTextField(20);
    private JTextField ccField = new JTextField(20);
    private JTextField bccField = new JTextField(20);
    private JTextField subjectField = new JTextField(20);
    private JTextArea messageArea = new JTextArea(5, 20);
    private JButton sendButton = new JButton("Enviar");
    private JButton clearButton = new JButton("Limpar");
    private JButton uploadButton = new JButton("Selecionar arquivo");
    private JTextField attachmentField = new JTextField(20);

    public EmailScreen() {
        super("Tela de Envio de Email");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        WindowUtils.centerOnScreen(this);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(9, 2));
        fieldsPanel.add(mailServerLabel);
        fieldsPanel.add(mailServerField);
        fieldsPanel.add(fromLabel);
        fieldsPanel.add(fromField);
        fieldsPanel.add(toLabel);
        fieldsPanel.add(toField);
//        fieldsPanel.add(ccLabel);
//        fieldsPanel.add(ccField);
//        fieldsPanel.add(bccLabel);
//        fieldsPanel.add(bccField);
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
        setVisible(false);
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public String getFromText() {
        return fromField.getText();
    }

    public String  getMailServerFieldText() {
        return mailServerField.getText();
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
        mailServerField.setText("");
        fromField.setText("");
        toField.setText("");
        ccField.setText("");
        bccField.setText("");
        subjectField.setText("");
        messageArea.setText("");
        attachmentField.setText("");
    }
}
