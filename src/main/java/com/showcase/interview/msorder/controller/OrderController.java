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
import com.showcase.interview.msorder.model.Order;
import com.showcase.interview.msorder.model.OrderFilter;
import com.showcase.interview.msorder.repository.OrderRepository;
import com.showcase.interview.msorder.service.OrderService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/ms-order/order", produces = { "application/json" })
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;
	
	@PostMapping("/create")
	public @ResponseBody Order createNew(@RequestBody Order newOrder) {
		try {
			return orderService.createNew(newOrder);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}
	
	@PostMapping("/customFilter")
	public @ResponseBody Iterable<Order> customFilter(@RequestBody OrderFilter orderFilter) {
			System.out.println(orderFilter.getCurrency());
			return orderRepository.filterCustomOrder(
					orderFilter.getCurrency(),
					orderFilter.getPriceMin(),
					orderFilter.getPriceMax(),
					orderFilter.getQtyMin(),
					orderFilter.getQtyMax());

	}


	@GetMapping("/{id}/detail")
	public @ResponseBody Order findById(@PathVariable Long id) {
		try {
			return orderService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Order updateData(@RequestBody Order newOrder, @PathVariable Long id) {
		return orderService.updateById(newOrder, id);
	}
	
	@PutMapping("/{id}/update-status")
	public @ResponseBody Order updateStatus(@RequestBody Order newOrder, @PathVariable Long id) {
		return orderService.updateStatusById(newOrder, id);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Order> getAll() {
		return orderService.getAll();

	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return orderService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

}