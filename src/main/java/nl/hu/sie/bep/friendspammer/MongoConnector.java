package nl.hu.sie.bep.friendspammer;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MongoConnector {
	private MongoConnector() {
		
	}
	
	public static Session getSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.mailtrap.io");
		props.put("mail.smtp.port", "2525");
		props.put("mail.smtp.auth", "true");
		
		String username = "acbbe8a16f5b9f";
		String password = "6e1a8d2b214b7c";
		
		return Session.getInstance(props,
				new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}
}
