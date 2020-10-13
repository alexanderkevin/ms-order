package com.showcase.interview.msorder;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.showcase.interview.msorder.model.OrderDetail;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderDetailTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String baseUrl = "/api/v1/ms-order/order-detail/";
	
	@Test
	public void formCustomerCRUDSeq() throws Exception {
		OrderDetail baseOrderDetail = new OrderDetail();
		baseOrderDetail.setBasePrice(BigDecimal.valueOf(50000));
		baseOrderDetail.setItem_id(new Long(1));
		baseOrderDetail.setQty(10);
		baseOrderDetail.setTotal();
		
//		Create OrderDetail
		OrderDetail insertedOrderDetail = createOrderDetailShouldSuccess(baseOrderDetail);
		
//		Read OrderDetail
		queryShouldReturnSpecificOrderDetail(insertedOrderDetail, baseOrderDetail.getQty());
		
//		Update OrderDetail
		insertedOrderDetail.setQty(20);
		updateOrderDetailProductNameShouldSuccess(insertedOrderDetail, insertedOrderDetail.getId());
		
		queryShouldReturnSpecificOrderDetail(insertedOrderDetail, insertedOrderDetail.getQty());
		
//		Delete OrderDetail
		deleteOrderDetailShouldSuccess(insertedOrderDetail);
		
		
	}
	
	public OrderDetail createOrderDetailShouldSuccess(OrderDetail newOrderDetail) throws Exception {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<OrderDetail> entity = new HttpEntity<>(newOrderDetail, headers);

		// send POST request
		OrderDetail newOrderDetailResult = this.restTemplate.postForObject("http://localhost:" + port + baseUrl + "create",
				entity, OrderDetail.class);
		assertThat(newOrderDetailResult).isEqualToIgnoringGivenFields(newOrderDetail, "id", "created_at", "updated_at");
		return newOrderDetailResult;

	}
	
	public void queryShouldReturnSpecificOrderDetail(OrderDetail form, int qty) throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + baseUrl + form.getId() + "/detail",
				String.class)).contains(Integer.toString(qty));
	}
	
	public void updateOrderDetailProductNameShouldSuccess(OrderDetail updatedOrderDetail, Long insertedId) throws Exception {
		String url = "http://localhost:" + port + baseUrl + insertedId + "/update-data";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(updatedOrderDetail);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		assertThat(response.getBody()).contains(Integer.toString(updatedOrderDetail.getQty()));
	}
	
	public void deleteOrderDetailShouldSuccess(OrderDetail inventory) throws Exception {

		String url = "http://localhost:" + port + baseUrl + inventory.getId() + "/delete-data";
		HttpEntity<?> requestEntity = null;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

		assertThat(response.getBody()).contains("Success");

	}
	
	@Test
	public void queryShouldReturnListOrderDetail() throws Exception {
		assertThat(
				this.restTemplate.getForObject("http://localhost:" + port + baseUrl +"all", String.class))
						.contains(Integer.toString(10));
	}
	
}