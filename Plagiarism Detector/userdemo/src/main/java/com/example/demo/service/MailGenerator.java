package com.example.demo.service;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author team211
 * 
 */
public class MailGenerator {
	private static final Logger logger = LoggerFactory.getLogger(MailGenerator.class);

	
	final String USERNAME = "plagiarismnotifications@gmail.com";//change accordingly
	final String MYP = "notify211";//change accordingly
	
	public void sendMail(String to) {	
		
		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, MYP);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(USERNAME));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Plagiarism Alerts");

			// Now set the actual message
			message.setText("Hi Instructor,\n\n One or more recent submissions might have exceeded the plagiarism threshold limit.\n Kindly logged in to your system.\n\n\nBest,\nTeam-211");

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			logger.info("Messaging Exception");
		}
	}
}
