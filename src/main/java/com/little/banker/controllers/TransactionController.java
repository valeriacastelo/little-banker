package com.little.banker.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.little.banker.domain.Account;
import com.little.banker.domain.Transaction;
import com.little.banker.services.AccountService;
import com.little.banker.services.TransactionService;
import com.little.banker.utils.DateUtils;

@RestController
@RequestMapping(value="transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;	
	
	@RequestMapping(value = "/account/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<Set<Transaction>> find (
			@PathVariable Integer accountId,
			@RequestParam(value="dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") String dateFrom,
			@RequestParam(value="dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") String dateTo) {
		
		Account account = accountService.find(accountId);
		
		Set<Transaction> list = transactionService.findTransactionsOfAccountByDate(account, 
				DateUtils.getDate(dateFrom), 
				DateUtils.getDate(dateTo));
		
		return ResponseEntity.ok(list);
	}
}