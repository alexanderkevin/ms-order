package com.showcase.interview.msorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.showcase.interview.msorder.exception.CommitFailedException;
import com.showcase.interview.msorder.exception.DataNotFoundException;
import com.showcase.interview.msorder.exception.UndefinedException;
import com.showcase.interview.msorder.model.OrderDetail;
import com.showcase.interview.msorder.repository.OrderDetailRepository;
import com.showcase.interview.msorder.utils.SuccessTemplateMessage;
import com.showcase.interview.msorder.utils.Util;

@Service
public class OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private Util util;

	public Iterable<OrderDetail> getAll() {
		return orderDetailRepository.findAll();
	}

	public OrderDetail createNew(OrderDetail newData) throws CommitFailedException, UndefinedException {
		try {
			newData.setCreated_at(util.getTimeNow());
			newData.setUpdated_at(util.getTimeNow());
			return orderDetailRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public OrderDetail getById(long id) throws DataNotFoundException {
		return orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public OrderDetail updateById(OrderDetail updatedData, Long id) {

		return orderDetailRepository.findById(id).map(data -> {
			updatedData.setId(id);
			updatedData.setCreated_at(data.getCreated_at());
			data = updatedData;

			data.setUpdated_at(util.getTimeNow());
			return orderDetailRepository.save(data);
		}).orElseGet(() -> {
			updatedData.setCreated_at(util.getTimeNow());
			updatedData.setUpdated_at(util.getTimeNow());
			return orderDetailRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (orderDetailRepository.findById(id) != null) {
				orderDetailRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

}