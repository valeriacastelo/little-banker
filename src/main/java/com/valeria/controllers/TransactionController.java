package com.valeria.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valeria.controllers.utils.ControllerUtils;
import com.valeria.domain.Account;
import com.valeria.domain.Transaction;
import com.valeria.services.AccountService;
import com.valeria.services.TransactionService;

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
							@RequestParam(value="dateFrom", defaultValue="") String dateFrom,
							@RequestParam(value="dateTo", defaultValue="") String dateTo) {
		
		Account account = accountService.find(accountId);
		
		Set<Transaction> list = transactionService.findTransactionsOfAccountByDate(account, 
				ControllerUtils.getDateFromParam(dateFrom), 
				ControllerUtils.getDateFromParam(dateTo));
		
		return ResponseEntity.ok(list);
	}
}
