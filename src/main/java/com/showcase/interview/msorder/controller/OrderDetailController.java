package com.showcase.interview.msorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.showcase.interview.msorder.exception.CommitFailedException;
import com.showcase.interview.msorder.exception.DataNotFoundException;
import com.showcase.interview.msorder.exception.UndefinedException;
import com.showcase.interview.msorder.model.OrderDetail;
import com.showcase.interview.msorder.service.OrderDetailService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/microservice/order-detail", produces = { "application/json" })
public class OrderDetailController {
	@Autowired
	private OrderDetailService orderDetailService;

	@PostMapping("/create")
	public @ResponseBody OrderDetail createNew(@RequestBody OrderDetail newOrderDetail) {
		try {
			return orderDetailService.createNew(newOrderDetail);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@GetMapping("/{id}/detail")
	public @ResponseBody OrderDetail findById(@PathVariable Long id) {
		try {
			return orderDetailService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody OrderDetail updateData(@RequestBody OrderDetail newOrderDetail, @PathVariable Long id) {
		return orderDetailService.updateById(newOrderDetail, id);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<OrderDetail> getAll() {
		return orderDetailService.getAll();

	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return orderDetailService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

}