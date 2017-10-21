package org.webcrawler.mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Crunchify.com
 *
 */

public class Mailer {

    private String host = "smtp.gmail.com";

    private String username = ""; //provide your gmail username

    private String password =  ""; //provide your gmail password

    private String protocol = "smtp";

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