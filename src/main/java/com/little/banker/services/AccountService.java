package com.little.banker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.little.banker.domain.Account;
import com.little.banker.repositories.AccountRepository;
import com.little.banker.services.exceptions.InsufficientFundsException;
import com.little.banker.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;
	
	public Account find (Integer id) {
		Optional<Account> op = accountRepo.findById(id);
		return op.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Account.class.getSimpleName() + "]"));
	}
	
	public Account insert(Account obj) {
		obj.setId(null);		
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
