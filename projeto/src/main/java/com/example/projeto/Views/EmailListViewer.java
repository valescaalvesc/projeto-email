package com.example.projeto.Views;

import com.example.projeto.Models.Email;
import com.example.projeto.Utils.ScreenSize;
import com.example.projeto.Utils.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailListViewer extends JFrame {

    private JList<Email> emailList;
    private DefaultListModel<Email> emailListModel;

    public EmailListViewer(ArrayList<Email> emails) {
        super("Inbox");

        // Create DefaultListModel to hold email data
        emailListModel = new DefaultListModel<>();
        for (Email email : emails) {
            emailListModel.addElement(email);
        }

        // Create JList to display email data
        emailList = new JList<>(emailListModel);
        emailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emailList.setCellRenderer(new EmailListRenderer());
        emailList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Double-click detected
                    Email selectedEmail = emailList.getSelectedValue();
                    displayEmail(selectedEmail);
                }
            }
        });

        // Create JScrollPane to provide scrolling functionality
        JScrollPane scrollPane = new JScrollPane(emailList);

        // Create JPanel for button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sendEmailButton = new JButton("Enviar email");
        sendEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmailScreen emailScreen = new EmailScreen(EmailListViewer.this);
                emailScreen.setVisible(true);
            }
        });
        buttonPanel.add(sendEmailButton);

        // Add components to JFrame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Set JFrame properties
        setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        WindowUtils.centerOnScreen(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }


    private void displayEmail(Email email) {
        // Create new dialog box to display email content
        JDialog emailDialog = new JDialog(this, email.getSubject(), true);
        JTextArea emailContent = new JTextArea(10, 30);
        emailContent.setEditable(false);
        emailContent.setText(email.getContent());

        // Create JScrollPane to wrap emailContent
        JScrollPane scrollPane = new JScrollPane(emailContent);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        emailContent.setCaretPosition(0);

        // Create "Return to List" button
        JButton returnButton = new JButton("Voltar para o inbox");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailDialog.dispose();
            }
        });

        // Create JPanel to hold button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(returnButton);

        // Add components to dialog box
        emailDialog.add(scrollPane, BorderLayout.CENTER);
        emailDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog box properties
        emailDialog.setSize(ScreenSize.SCREEN_WIDTH, ScreenSize.SCREEN_HEIGHT);
        emailDialog.setLocationRelativeTo(this);
        emailDialog.setVisible(true);
    }

    private class EmailListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Email) {
                Email email = (Email) value;
                String text = String.format("<html><b>%s</b><br>%s<br>%s</html>", email.getFrom(), email.getSubject(), email.getReceiveDate());
                return super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            } else {
                return super.getListCellRendererComponent
                        (list, value, index, isSelected, cellHasFocus);
            }
        }
    }
}