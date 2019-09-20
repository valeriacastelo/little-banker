package com.little.banker.controllers;

import java.time.LocalDate;
import java.util.List;

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

@RestController
@RequestMapping(value="transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;	
	
	/**
	 * GET transactions/account/{accountId} : 
	 * Show all transactions in one account with filtering dateFrom and dateTo
	 * 
	 * @param accountId
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@RequestMapping(value = "/account/{accountId}", method = RequestMethod.GET)
	public ResponseEntity<List<Transaction>> find (
			@PathVariable Integer accountId,
			@RequestParam(value="dateFrom") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
			@RequestParam(value="dateTo") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo) {
		
		Account account = accountService.find(accountId);
		List<Transaction> list = transactionService.findTransactionsOfAccountByDate(account, dateFrom, dateTo);
		
		return ResponseEntity.ok(list);
	}
}
