package com.showcase.interview.msorder.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.msorder.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query(value = "SELECT * FROM orders  o left join order_detail od on o.id = od.order_id WHERE currency LIKE %:currency% AND (od.base_price BETWEEN coalesce(:priceMin,0) AND coalesce(:priceMax,999999)) AND (od.qty BETWEEN coalesce(:qtyMin,0) AND coalesce(:qtyMax,100))",nativeQuery = true)
	Iterable<Order> filterCustomOrder(String currency, BigDecimal priceMin, BigDecimal priceMax, int qtyMin, int qtyMax);
	
}
