package com.valeria.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Transaction;
import com.valeria.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private AccountService accountService;
	
	public Transaction find (Integer id) {
		Optional<Transaction> op = transactionRepo.findById(id);
		return op.orElseThrow(() -> new IllegalArgumentException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Transaction.class.getName() + "]"));
	}
	
	public List<Transaction> findByAccountAndDate (Integer accountId, Date dateFrom, Date dateTo) {
		return transactionRepo.findByAccountAndDate(accountId, dateFrom, dateTo);
	}
	
	
	@Transactional
	public Transaction insert (Transaction transaction) {
		transaction.setId(null);
		transaction.setDate(new Date());
		
		accountService.debit(transaction.getAccountFrom(), transaction.getAmount());
		accountService.credit(transaction.getAccountTo(), transaction.getAmount());
		
		return transactionRepo.save(transaction);
	}

}
