package com.example.projeto.Utils;
import com.example.projeto.Models.Email;
import com.example.projeto.ReadEmail;
import org.jsoup.Jsoup;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class EmailUtils {

    /**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String fromEmail, String toEmail, String subject, String body){
        try
        {
            System.out.println("SSLEmail Start");
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail));

            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void sendAttachmentEmail(Session session, String fromEmail, String toEmail, String subject, String body, String filename){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail));

            msg.setReplyTo(InternetAddress.parse(fromEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with attachment!!");
        }catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendImageEmail(Session session, String toEmail, String subject, String body){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "jesus2.png";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);

            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" +
                    "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            System.out.println("EMail Sent Successfully with image!!");
        }catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Authenticator authenticator(String email, String password) {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
    }


    public static Properties getPropertiesToReadEmails(String email){
        Properties props = new Properties();
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        props.put("mail.pop3.socketFactory.port", "995");
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.host", "pop.gmail.com");
        props.put("mail.pop3.user", email);
        props.put("mail.store.protocol", "pop3");
        props.put("mail.pop3.ssl.protocols", "TLSv1.2");

        return props;
    }

    public static Properties getPropertiesToSendEmails(){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        return props;
    }

    public static Properties setPropertiesToSendEmails(Properties props){
        props.setProperty("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.setProperty("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.setProperty("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.setProperty("mail.smtp.port", "465"); //SMTP Port

        return props;
    }

    public static ArrayList<Email> readAndFormatEmails(Store store) throws Exception {
        Folder inbox = store.getFolder("INBOX");
        ArrayList<Email> emails = new ArrayList<>();
        inbox.open(Folder.READ_ONLY);

        // 6. Retrieve the messages from the folder.
        int end = inbox.getMessageCount();
        int start = end-49;
        Message[] messages = inbox.getMessages(start, end);
        for (Message message : messages) {
            Email email = new Email();

            email.setFrom(message.getFrom()[0].toString());
            email.setReceiveDate(message.getSentDate());
            email.setSubject(message.getSubject());

            String contentType = message.getContentType();

            StringBuilder messageContent= new StringBuilder();

            if (contentType.contains("multipart")) {
                Multipart multiPart = (Multipart) message.getContent();
                int numberOfParts = multiPart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    String partContentType = part.getContentType();
                    if (partContentType.contains("text/plain")) {
                        messageContent = new StringBuilder(part.getContent().toString());
                        break;
                    } else if (partContentType.contains("text/html")) {
                        String html = part.getContent().toString();
                        messageContent = new StringBuilder(Jsoup.parse(html).text());
                    }
                }
            } else if (contentType.contains("text/plain")) {
                Object content = message.getContent();
                if (content != null) {
                    messageContent = new StringBuilder(content.toString());
                }
            } else if (contentType.contains("text/html")) {
                String html = message.getContent().toString();
                messageContent = new StringBuilder(Jsoup.parse(html).text());
            }

            email.setBody(messageContent.toString());
            emails.add(email);
        }

        inbox.close(false);
        store.close();
        return emails;
    }
}
