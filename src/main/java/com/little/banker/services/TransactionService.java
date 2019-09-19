package com.little.banker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.domain.Transaction;
import com.little.banker.domain.TransactionType;
import com.little.banker.dto.AccountDTO;

@Service
public class TransactionService {
	
	@Autowired
	private PaymentService paymentService;
	
	
	public TreeSet<Transaction> findTransactionsOfAccountByDate (Account account, LocalDate dateFrom, LocalDate dateTo) {
		
		//Get the payments of the account by the date
		List<Payment> payments = paymentService.findByAccountAndDate(account, dateFrom, dateTo);
		
		//Build the sorted Set with information related to the transaction of the account
		TreeSet<Transaction> transactions = new TreeSet<Transaction>();
		for (Payment p : payments) {
			transactions.add(getTransactionOfAccountFromPayment(p, account));
		}
		
		return transactions;
	}
	
	
	public Transaction getTransactionOfAccountFromPayment (Payment payment, Account account) {
		
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
