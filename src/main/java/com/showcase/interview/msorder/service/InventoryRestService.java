package com.showcase.interview.msorder.service;

import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.showcase.interview.msorder.model.ReservedInventory;

@Service
public class InventoryRestService {
	
	@Value("${request_time_out:500}")
	private int timeoutInSecond;
	
	@Value("${ms_inventory_url:http://localhost:8001/api/v1/ms-inventory/inventory/}")
	private String baseURL;
	
    private RestTemplate restTemplate;

    public InventoryRestService(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(timeoutInSecond))
                .setReadTimeout(Duration.ofSeconds(timeoutInSecond))
                .build();
    }
    
    public ReservedInventory[] getPostsAsObject() {
        String url = baseURL;
        return this.restTemplate.getForObject(url, ReservedInventory[].class);
    }
    
    public ReservedInventory getPostWithUrlParameters(Long id) {
        String url = baseURL+"/"+id+"/detail";
        return this.restTemplate.getForObject(url, ReservedInventory.class);
    }
    
    public ReservedInventory getPostWithResponseHandling() {
        String url = baseURL+"/{id}";
        ResponseEntity<ReservedInventory> response = this.restTemplate.getForEntity(url, ReservedInventory.class, 1);
        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

}