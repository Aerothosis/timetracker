
import java.util.*;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class EmailVerify {
	
	private String from;
	private String to;
	private String subject;
	private String text;
	
	public EmailVerify(String from, String to, String subject, String text){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}
	
	public void send(){
		
		String username = "no-reply@mfurtado.com";
		String password = "HALOrvb12#$!@34";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "mail.mfurtado.com");
		props.put("mail.smtp.port", "587");
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);
		
		Session mailSession = Session.getInstance(props, 
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				}
				);
		Message simpleMessage = new MimeMessage(mailSession);
		
		try{
			simpleMessage.setFrom(new InternetAddress(from));
			simpleMessage.setRecipient(RecipientType.TO, new InternetAddress(to));
			simpleMessage.setSubject(subject);
			simpleMessage.setText(text);
			Transport.send(simpleMessage);
			
		}catch(MessagingException e){
			e.printStackTrace();
		}
	}

}
