package com.valeria.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Account;
import com.valeria.domain.Transaction;
import com.valeria.dto.TransactionByAccountDTO;
import com.valeria.repositories.TransactionRepository;
import com.valeria.services.exceptions.ObjectNotFoundException;
import com.valeria.services.exceptions.SameAccountException;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private AccountService accountService;
	
	public Transaction find (Integer id) {
		Optional<Transaction> op = transactionRepo.findById(id);
		return op.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Transaction.class.getName() + "]"));
	}
	
	public List<TransactionByAccountDTO> findByAccountAndDate (Integer accountId, Date dateFrom, Date dateTo) {
		Account account = accountService.find(accountId);
		List<Transaction> list = transactionRepo.findByAccountAndDate(account, dateFrom, dateTo);
		
		return fromTransactionList(list, account);
	}
	
	
	@Transactional
	public Transaction insert (Transaction transaction) {
		transaction.setId(null);
		transaction.setDate(new Date());
		
		if (transaction.getAccountFrom().getId().equals(transaction.getAccountTo().getId())) {
			throw new SameAccountException("Account from and account to cannot be the same");
		}
		
		accountService.debit(transaction.getAccountFrom(), transaction.getAmount());
		accountService.credit(transaction.getAccountTo(), transaction.getAmount());
		
		return transactionRepo.save(transaction);
	}
	
	public List<TransactionByAccountDTO> fromTransactionList (List<Transaction> transactions, Account account) {
		
		List<TransactionByAccountDTO> dtos = new ArrayList<TransactionByAccountDTO>();
		for (Transaction t : transactions) {
			dtos.add(new TransactionByAccountDTO(t, account));
		}
		
		return dtos;
	}

}
