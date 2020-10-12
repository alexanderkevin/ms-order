package com.showcase.interview.msorder.repository;

import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.msorder.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
