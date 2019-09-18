package com.valeria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valeria.domain.Account;
import com.valeria.repositories.AccountRepository;
import com.valeria.services.exceptions.InsufficientFundsException;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	public Account find (Integer id) {
		Optional<Account> op = accountRepo.findById(id);
		return op.orElseThrow(() -> new IllegalArgumentException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Account.class.getName() + "]"));
	}
	
	public Account insert(Account obj) {
		obj.setId(null);
		
		if (obj.getBalance() == null) {
			obj.setBalance(0.0);
		}
		
		return accountRepo.save(obj);
	}
	
	public Account credit(Account obj, Double amount) {
		Account account = find(obj.getId());
		account.setBalance(account.getBalance() + amount);
		
		return accountRepo.save(account);
	}
	
	public Account debit(Account obj, Double amount) {
		Account account = find(obj.getId());
		
		if (account.getBalance() < amount) {
			throw new InsufficientFundsException("Insufficient Funds for the account id [" + account.getId() + "]");
		}
		
		account.setBalance(account.getBalance() - amount);
		
		return accountRepo.save(account);
	}


}
