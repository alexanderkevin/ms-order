package com.showcase.interview.msorder.repository;

import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.msorder.model.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long>{

}
