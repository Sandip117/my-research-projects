package com.example.demo.service;

import java.util.List;
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
 * This class represents a mail generator
 * 
 * @author team211
 * 
 */
public class MailGenerator {
	private static final Logger logger = LoggerFactory.getLogger(MailGenerator.class);

	/**
	 * User name and password declared here
	 */
	final String USERNAME = "plagiarismnotifications@gmail.com";// change accordingly
	final String MYP = "notify211";// change accordingly

	/**
	 * Sends a mail from the system
	 * 
	 * @param to
	 *            : the email id of the TO recipient
	 * @param ccList
	 *            : a list of email ids for the CC recipients
	 * @param link
	 *            : a string that should be in the body of the mail
	 */
	public void sendMail(String to, List<String> ccList, String link, String asnName, String courseName) {

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			for (String cc : ccList)
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));

			// Set Subject: header field
			message.setSubject("Plagiarism Alerts");

			// Now set the actual message
			message.setText(
					"Hi Instructor,\n\n The plagiarism system has detected plagiarism for Assignment: "+asnName+" in Course: "+courseName+". One or more students might have plagiarised in this assignment.\n Kindly view this link :"
							+ link + " after logging in to the system.\n\n\nBest,\nTeam Sherlock [211]");

			// Send message
			Transport.send(message);

		} catch (MessagingException e) {
			logger.info("Messaging Exception");
		}
	}
}
