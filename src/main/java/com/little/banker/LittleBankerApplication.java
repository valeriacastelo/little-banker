package com.little.banker;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.repositories.AccountRepository;
import com.little.banker.repositories.PaymentRepository;

@SpringBootApplication
public class LittleBankerApplication implements CommandLineRunner {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PaymentRepository transactionRepo;

	public static void main(String[] args) {
		SpringApplication.run(LittleBankerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		LocalDateTime date1 = LocalDateTime.of(2019, Month.SEPTEMBER, 19, 23, 29, 0);
		LocalDateTime date2 = LocalDateTime.of(2019, Month.SEPTEMBER, 18, 2, 50, 1);

		Account a1 = new Account(null, 100.0);		
		Account a2 = new Account(null, 200.0);
		Account a3 = new Account(null, 500.0);
		
		Payment t1 = new Payment(null, a1, a2, date2, 50.0);
		Payment t2 = new Payment(null, a2, a1, date1, 10.0);
		
		a1.getMadePayments().addAll(Arrays.asList(t1));
		a1.getReceivedPayments().addAll(Arrays.asList(t2));
		
		a2.getMadePayments().addAll(Arrays.asList(t2));
		a2.getReceivedPayments().addAll(Arrays.asList(t1));
		
		accountRepo.saveAll(Arrays.asList(a1,a2,a3));
		transactionRepo.saveAll(Arrays.asList(t1, t2));
		
	}

}
