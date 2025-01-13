package br.com.cotiinformatica.components;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProducerComponent {

	@Autowired RabbitTemplate rabbitTemplate;
	@Autowired Queue queue;
	
	public void send(String content) throws Exception {		
		rabbitTemplate.convertAndSend(queue.getName(), content);
	}
}
