package com.showcase.interview.msorder;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.showcase.interview.msorder.model.Order;
import com.showcase.interview.msorder.repository.OrderRepository;
import com.showcase.interview.msorder.service.OrderService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private OrderRepository orderRepository;

	private String baseUrl = "/api/v1/ms-order/order/";
	
	@Test
	public void formOrderCRUDSeq() throws Exception {
		Order baseOrder = new Order();
		baseOrder.setTotalAmount(BigDecimal.valueOf(500000));
		baseOrder.setCurrency("IDR");
		baseOrder.setStatus("WAITING_FOR_PAYMENT");
		
		
//		Create Order
		Order insertedOrder = createOrderShouldSuccess(baseOrder);
		
//		Read Order
		queryShouldReturnSpecificOrder(insertedOrder, baseOrder.getCurrency());
		
//		Update Order
		insertedOrder.setCurrency("USD");
		updateOrderProductNameShouldSuccess(insertedOrder, insertedOrder.getId());
		
		queryShouldReturnSpecificOrder(insertedOrder, insertedOrder.getCurrency());
		
//		Delete Order
		deleteOrderShouldSuccess(insertedOrder);
		
		
	}
	
	public Order createOrderShouldSuccess(Order newOrder) throws Exception {

		Order newOrderResult = orderRepository.save(newOrder);
		assertThat(newOrderResult).isEqualToIgnoringGivenFields(newOrder, "id", "created_at", "updated_at");
		
		return newOrderResult;

	}
	
	public void queryShouldReturnSpecificOrder(Order form, String currency) throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + baseUrl + form.getId() + "/detail",
				String.class)).contains(currency);
	}
	
	public void updateOrderProductNameShouldSuccess(Order updatedOrder, Long insertedId) throws Exception {
		String url = "http://localhost:" + port + baseUrl + insertedId + "/update-data";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(updatedOrder);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		assertThat(response.getBody()).contains(updatedOrder.getCurrency());
	}
	
	public void deleteOrderShouldSuccess(Order inventory) throws Exception {

		String url = "http://localhost:" + port + baseUrl + inventory.getId() + "/delete-data";
		HttpEntity<?> requestEntity = null;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

		assertThat(response.getBody()).contains("Success");

	}
	
	@Test
	public void queryShouldReturnListOrder() throws Exception {
		assertThat(
				this.restTemplate.getForObject("http://localhost:" + port + baseUrl +"all", String.class))
						.contains(Integer.toString(10));
	}
	
}