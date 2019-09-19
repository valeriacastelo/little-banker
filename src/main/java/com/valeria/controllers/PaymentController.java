package com.valeria.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valeria.domain.Payment;
import com.valeria.services.PaymentService;

@RestController
@RequestMapping(value="payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
	public ResponseEntity<Payment> find (@PathVariable Integer paymentId) {
		
		Payment obj = paymentService.find(paymentId);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Payment obj) {
		
		Payment inserted = paymentService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
