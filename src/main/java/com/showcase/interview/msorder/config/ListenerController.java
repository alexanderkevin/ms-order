package com.showcase.interview.msorder.config;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.interview.msorder.controller.OrderController;
import com.showcase.interview.msorder.model.Order;

@Component
public class ListenerController {
	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	private OrderController orderController;

	@RabbitListener(queues = "ms-order-queue")
	public void receiveMessage(Order newOrder) throws IOException {
		if (newOrder == null) {
			throw new AmqpRejectAndDontRequeueException("Received message is null");
		}
		orderController.updateStatus(newOrder, newOrder.getId());
	}

}

