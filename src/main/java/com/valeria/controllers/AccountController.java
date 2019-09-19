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

import com.valeria.domain.Account;
import com.valeria.services.AccountService;

@RestController
@RequestMapping(value="accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Account> find (@PathVariable Integer id) {
		
		Account obj = accountService.find(id);
		return ResponseEntity.ok(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@RequestBody Account obj) {
		
		Account inserted = accountService.insert(obj);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}

}
