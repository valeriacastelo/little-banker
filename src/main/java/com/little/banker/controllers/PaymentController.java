package com.little.banker.controllers;

import java.net.URI;
import java.util.Date;

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
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Payment obj) {
		
		obj.setDate(new Date());
		Payment inserted = paymentService.makePayment(obj);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
