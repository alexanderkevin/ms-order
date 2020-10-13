package com.showcase.interview.msorder.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.showcase.interview.msorder.MsOrderApplication;
import com.showcase.interview.msorder.model.ReservedInventory;
import com.showcase.interview.msorder.model.Order;
import com.showcase.interview.msorder.utils.SuccessTemplateMessage;

@RestController
@RequestMapping("/api/v1/microservice/")
public class HelloController {

	@Value("${message:Hello default}")
	private String message;

	@Autowired
	private final RabbitTemplate rabbitTemplate;

	HelloController() {
		this.rabbitTemplate = new RabbitTemplate();
	}

	@PutMapping("/{id}/send-updated-data")
	public ResponseEntity<Object> SendMessage(@RequestBody ReservedInventory newInventory, @PathVariable Long id) {
		this.rabbitTemplate.convertSendAndReceive("reserve-inventory", "update", newInventory);
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

	@GetMapping("/hello")
	public String hello() {
		return message;
	}

	@Value("${timezone:Asia/Jakarta}")
	private String timezone;

	@GetMapping("/timezone")
	public String tz() {
		return timezone;
	}

}