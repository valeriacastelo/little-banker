package com.valeria.services;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeria.domain.Account;
import com.valeria.domain.Payment;
import com.valeria.domain.Transaction;
import com.valeria.domain.TransactionType;
import com.valeria.dto.AccountDTO;

@Service
public class TransactionService {
	
	@Autowired
	private PaymentService paymentService;
	
	public TreeSet<Transaction> findTransactionsOfAccountByDate (Account account, Date dateFrom, Date dateTo) {
		List<Payment> payments = paymentService.findByAccountAndDate(account, dateFrom, dateTo);
		
		TreeSet<Transaction> transactions = new TreeSet<Transaction>();
		for (Payment p : payments) {
			transactions.add(getTransactionOfAccountFromPayment(p, account));
		}
		
		return transactions;
	}
	
	public Transaction getTransactionOfAccountFromPayment (Payment payment, Account account) {
		Transaction transaction = new Transaction();
		if (payment.getAccountFrom().equals(account)) {
			transaction.setAccount(new AccountDTO(payment.getAccountTo()));
			transaction.setType(TransactionType.DEBIT);
		} else {
			transaction.setAccount(new AccountDTO(payment.getAccountFrom()));
			transaction.setType(TransactionType.CREDIT);
		}
		
		transaction.setDate(payment.getDate());
		transaction.setAmount(payment.getAmount());
		
		return transaction;
	}
}
