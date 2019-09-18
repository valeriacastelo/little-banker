package com.valeria.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.valeria.controllers.utils.ControllerUtils;
import com.valeria.domain.Transaction;
import com.valeria.services.TransactionService;

@RestController
@RequestMapping(value="transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Transaction> find (@PathVariable Integer id) {
		
		Transaction obj = transactionService.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(value = "/account/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> find (
							@PathVariable Integer accountId,
							@RequestParam(value="dateFrom", defaultValue="") String dateFrom,
							@RequestParam(value="dateTo", defaultValue="") String dateTo) {
		
		List<Transaction> list = transactionService.findByAccountAndDate(accountId, 
				ControllerUtils.getDateFromParam(dateFrom), 
				ControllerUtils.getDateFromParam(dateTo));
		
		return ResponseEntity.ok(list);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Transaction obj) {
		
		Transaction inserted = transactionService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
