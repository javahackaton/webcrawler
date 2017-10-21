package org.webcrawler.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class Mailer {

    @Value("${mail.sender.host}")
    private String host;

    @Value("${mail.sender.protocol}")
    private String protocol;

    @Value("${mail.sender.username}")
    private String username;

    @Value("${mail.sender.password}")
    private String password;

    private  Session getMailSession;

    private  MimeMessage generateMailMessage;

    public Mailer() {

        Properties mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
    }

    public void mail(String receiver, String title, String body) throws MessagingException {
        generateEmail( receiver,  title,  body);
        sendEmail();
    }

    private void generateEmail(String receiver, String title, String body) throws MessagingException {
        InternetAddress email = new InternetAddress(receiver);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, email);
        generateMailMessage.setSubject(title);
        generateMailMessage.setContent(body, "text/html");
    }

    private void sendEmail() throws MessagingException {
        Transport transport = getMailSession.getTransport(protocol);
        transport.connect(host, username, password);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}