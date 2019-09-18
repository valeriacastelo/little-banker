package com.valeria;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.valeria.domain.Account;
import com.valeria.domain.Transaction;
import com.valeria.repositories.AccountRepository;
import com.valeria.repositories.TransactionRepository;

@SpringBootApplication
public class LittleBankerApplication implements CommandLineRunner {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private TransactionRepository transactionRepo;

	public static void main(String[] args) {
		SpringApplication.run(LittleBankerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		

		Account a1 = new Account();
		a1.setId(null);
		a1.setBalance(100.00);
		
		Account a2 = new Account();
		a2.setId(null);
		a2.setBalance(200.00);		
		
		Transaction t1 = new Transaction();
		t1.setId(null);
		t1.setDate(new Date());
		t1.setAccountFrom(a1);
		t1.setAccountTo(a2);
		t1.setAmount(50.00);
		
		a1.getDebitTransactions().add(t1);
		a2.getCreditTransactions().add(t1);
		
		accountRepo.saveAll(Arrays.asList(a1,a2));
		
		transactionRepo.saveAll(Arrays.asList(t1));
		
	}

}
