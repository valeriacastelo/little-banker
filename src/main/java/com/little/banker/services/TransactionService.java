package com.little.banker.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.domain.Transaction;
import com.little.banker.domain.TransactionType;
import com.little.banker.dto.AccountDTO;
import com.little.banker.services.comparator.TransactionComparator;

@Service
public class TransactionService {
	private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private PaymentService paymentService;
	
	
	public List<Transaction> findTransactionsOfAccountByDate (Account account, LocalDate dateFrom, LocalDate dateTo) {
		LOG.debug("Getting the transactions for the account id [" + account.getId() + "] DateFrom [" + dateFrom + "] DateTo [" + dateTo + "]");
		
		//Get the payments of the account by the date
		List<Payment> payments = paymentService.findByAccountAndDate(account, dateFrom, dateTo);
		
		//Build the List with information related to the transaction of the account
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (Payment p : payments) {
			transactions.add(getTransactionOfAccountFromPayment(p, account));
		}
		
		//Sorting the List with the Comparator
		//It was chosen sort a List instead of using sorted Collection (like TreeSet) because of two points:
		//This list will not be changed (add or removed elements), what it will be good for TreeSet
		//And this list will be sort only one time. So in those cases the performance is better with comparator
		Collections.sort(transactions, new TransactionComparator());
		
		LOG.debug("Transactions founded: " + transactions);
		
		return transactions;
	}
	
	
	public Transaction getTransactionOfAccountFromPayment (Payment payment, Account account) {
		LOG.debug("Get transaction for the account id [" + account.getId() + "]");
		
		Transaction transaction = new Transaction();
		
		//Check if the account was the accountFrom in the Payment. If it was it the operation was a DEBIT
		//If the account was the accountFrom in the Payment, that means the transaction it was a CREDIT to the account
		if (payment.getAccountFrom().equals(account)) {
			transaction.setAccount(new AccountDTO(payment.getAccountTo()));
			transaction.setType(TransactionType.DEBIT);
		} else {
			transaction.setAccount(new AccountDTO(payment.getAccountFrom()));
			transaction.setType(TransactionType.CREDIT);
		}
		
		transaction.setDateTime(payment.getDateTime());
		transaction.setAmount(payment.getAmount());
		transaction.setPaymentId(payment.getId());
		
		return transaction;
	}
}
