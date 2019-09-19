package com.little.banker.controllers;

import java.net.URI;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.little.banker.domain.Payment;
import com.little.banker.services.PaymentService;

@RestController
@RequestMapping(value="payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	
	/**
	 * POST payments/ : Make payment from one account to another
	 * 
	 * @param payment
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Payment payment) {
		
		payment.setDateTime(LocalDateTime.now());
		Payment inserted = paymentService.makePayment(payment);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
