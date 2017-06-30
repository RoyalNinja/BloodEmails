package com.royalninja.bloodemails.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

	public static void sendEmail(String smtp, String port, String email, String password, String sendEmail, String subject, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtp);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("[U]"+email+"[/U]",password);
				//[U]Username[/U] is the [U]username[/U] to your actual email...
				//Password is the password you use to log into that email address
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("[U]"+email+"[/U]"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(sendEmail));
			//to[U]@email.com[/U] is the address you are sending this email to
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		}catch(MessagingException e)  {
			throw new RuntimeException(e);
		}
	}
	
}
