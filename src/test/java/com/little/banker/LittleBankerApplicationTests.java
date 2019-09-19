package com.little.banker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.domain.Transaction;
import com.little.banker.repositories.AccountRepository;
import com.little.banker.services.PaymentService;
import com.little.banker.services.TransactionService;
import com.little.banker.services.exceptions.InsufficientFundsException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LittleBankerApplicationTests {
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	@Test
	public void makePaymentIntegrationTest() {
		
		//GIVEN		
		Account account1 = new Account(null, 600.0);
		Account account2 = new Account(null, 700.0);
		
		account1 = accountRepo.save(account1);
		account2 = accountRepo.save(account2);
		
		//WHEN		
		Payment payment1 = new Payment(null, account1, account2, LocalDateTime.now(), 50.0);
		payment1 = paymentService.makePayment(payment1);
		
		Payment payment2 = new Payment(null, account2, account1, LocalDateTime.now(), 25.0);
		payment2 = paymentService.makePayment(payment2);
		
		Payment payment3 = new Payment(null, account2, account1, LocalDateTime.now(), 60.0);
		payment3 = paymentService.makePayment(payment3);
		
		//THEN		
		account1 = accountRepo.findById(account1.getId()).get();
		account2 = accountRepo.findById(account2.getId()).get();
		
		Assert.assertEquals(635.0, account1.getBalance(), 0);
		Assert.assertEquals(665.0, account2.getBalance(), 0);		
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void makePaymentIntegrationWithExceptionTest() {
		
		//GIVEN
		Account account1 = new Account(null, 600.0);
		Account account2 = new Account(null, 700.0);
		
		account1 = accountRepo.save(account1);
		account2 = accountRepo.save(account2);
		
		//WHEN
		Payment payment1 = new Payment(null, account1, account2, LocalDateTime.now(), 1000.0);
		payment1 = paymentService.makePayment(payment1);
		
		//THEN
		//EXCEPTION
	}
	
	@Test
	public void makePaymentIntegrationWithStateAfterExceptionTest() {
		
		//GIVEN
		Account account1 = new Account(null, 600.0);
		Account account2 = new Account(null, 700.0);
		
		account1 = accountRepo.save(account1);
		account2 = accountRepo.save(account2);
		
		//WHEN
		Payment payment1 = new Payment(null, account1, account2, LocalDateTime.now(), 80.0);
		payment1 = paymentService.makePayment(payment1);
		
		try {
			Payment payment2 = new Payment(null, account2, account1, LocalDateTime.now(), 1000.0);
			payment2 = paymentService.makePayment(payment2);
			
		} catch(InsufficientFundsException ex) {
			//do nothing. Not testing exception
		}
		
		//THEN
		account1 = accountRepo.findById(account1.getId()).get();
		account2 = accountRepo.findById(account2.getId()).get();
		
		Assert.assertEquals(520.0, account1.getBalance(), 0);
		Assert.assertEquals(780.0, account2.getBalance(), 0);		
	}
	
	@Test
	public void findTransactionsOfAccountByDateTest() {
		
		//GIVEN
		Account account1 = new Account(null, 600.0);
		Account account2 = new Account(null, 700.0);
		
		account1 = accountRepo.save(account1);
		account2 = accountRepo.save(account2);
		
		//Date 12/01/2019 15:09:00
		LocalDateTime date1 = LocalDateTime.of(2019, Month.JANUARY, 12, 15, 9, 1);
		
		//Date 15/01/2019 23:29:00
		LocalDateTime date2 = LocalDateTime.of(2019, Month.JANUARY, 15, 23, 29, 0);
		
		//Date 25/03/2019 02:50:01
		LocalDateTime date3 = LocalDateTime.of(2019, Month.JANUARY, 25, 2, 50, 1);
		
		//Date 12/01/2019
		LocalDate dateFrom = LocalDate.of(2019, Month.JANUARY, 12);
		
		//Date 16/01/2019
		LocalDate dateTo = LocalDate.of(2019, Month.JANUARY, 16);
		
		
		//WHEN		
		Payment payment1 = new Payment(null, account1, account2, date1, 50.0);
		payment1 = paymentService.makePayment(payment1);
		
		Payment payment2 = new Payment(null, account2, account1, date2, 25.0);
		payment2 = paymentService.makePayment(payment2);
		
		Payment payment3 = new Payment(null, account2, account1, date3, 60.0);
		payment3 = paymentService.makePayment(payment3);
		
		//THEN
		Transaction account1Transaction1 = transactionService.getTransactionOfAccountFromPayment(payment1, account1);
		Transaction account1Transaction2 = transactionService.getTransactionOfAccountFromPayment(payment2, account1);
		Transaction account1Transaction3 = transactionService.getTransactionOfAccountFromPayment(payment3, account1);

		TreeSet<Transaction> transactionsOfAccountByDate = transactionService.findTransactionsOfAccountByDate(account1, dateFrom, dateTo);
		
		Assert.assertEquals(2, transactionsOfAccountByDate.size());
		Assert.assertEquals(true, transactionsOfAccountByDate.contains(account1Transaction1));
		Assert.assertEquals(true, transactionsOfAccountByDate.contains(account1Transaction2));
		Assert.assertEquals(false, transactionsOfAccountByDate.contains(account1Transaction3));
	}

}
