package com.little.banker.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.little.banker.domain.Account;
import com.little.banker.repositories.AccountRepository;
import com.little.banker.services.exceptions.InsufficientFundsException;
import com.little.banker.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {
	private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private AccountRepository accountRepo;
	
	
	public Account find (Integer id) {
		LOG.debug("Finding an account by the id [" + id + "]");
		
		Optional<Account> op = accountRepo.findById(id);
		return op.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Account.class.getSimpleName() + "]"));
	}
	
	public Account insert(Account account) {
		LOG.debug("Inserting an new account [" + account + "]");
		
		//making sure it will be a insert and not a update
		account.setId(null);		
		
		return accountRepo.save(account);
	}
	
	public Account credit(Account obj, Double amount) {
		LOG.debug("Credit for the account id [" + obj.getId() + "]. Amount [" + amount + "]");
		
		Account account = find(obj.getId());
		account.setBalance(account.getBalance() + amount);
		
		return accountRepo.save(account);
	}
	
	public Account debit(Account obj, Double amount) {
		LOG.debug("Debit for the account id [" + obj.getId() + "]. Amount [" + amount + "]");
		
		Account account = find(obj.getId());
		
		//Check if the account has enough balance
		if (account.getBalance() < amount) {
			throw new InsufficientFundsException("Insufficient Funds for the account id [" + account.getId() + "]");
		}
		
		account.setBalance(account.getBalance() - amount);
		
		return accountRepo.save(account);
	}

}
