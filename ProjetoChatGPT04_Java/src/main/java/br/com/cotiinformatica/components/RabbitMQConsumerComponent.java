package br.com.cotiinformatica.components;

import java.time.Instant;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumerComponent {
	
	@RabbitListener(queues = { "relatorios" })
	public void proccess(@Payload String message) throws Exception {
		
		var host = "localhost";
		var port = 1026;
		var from = "noreply@example.com";
		var to = "user@example.com";
		
		var properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		
		var session = Session.getInstance(properties);
		
		try {
			
			var mimeMessage = new MimeMessage(session);
			
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject("Relatório de atendimentos gerado em: " + Instant.now());
			mimeMessage.setText(message);
			
			Transport.send(mimeMessage);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
