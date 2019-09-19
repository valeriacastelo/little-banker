package com.little.banker.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.little.banker.domain.Account;
import com.little.banker.services.AccountService;

@RestController
@RequestMapping(value="accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * GET accounts/{accountId} : Show balance per account
	 * 
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<Account> find (@PathVariable Integer accountId) {
		Account obj = accountService.find(accountId);
		return ResponseEntity.ok(obj);
	}
	
	
	/**
	 * POST accounts/ : Create account with an initial balance
	 * @param account
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Account account) {
		
		Account inserted = accountService.insert(account);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(inserted.getId())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}

}
