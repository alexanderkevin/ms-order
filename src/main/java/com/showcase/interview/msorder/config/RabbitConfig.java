package com.showcase.interview.msorder.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.showcase.interview.msorder.model.Order;


@Configuration
public class RabbitConfig {

	public static String queueName = "ms-inventory-queue";
    
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("reserve-inventory");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("update");
    }
    
}

@Component
class Listener {

    @RabbitListener(queues = "ms-order-queue")
    public Order process(Order ro) {
        return new Order();
    }

}
