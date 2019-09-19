package com.little.banker;

import java.util.Date;
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
import com.little.banker.utils.DateUtils;

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
		Payment payment1 = new Payment(null, account1, account2, new Date(), 50.0);
		payment1 = paymentService.makePayment(payment1);
		
		Payment payment2 = new Payment(null, account2, account1, new Date(), 25.0);
		payment2 = paymentService.makePayment(payment2);
		
		Payment payment3 = new Payment(null, account2, account1, new Date(), 60.0);
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
		Payment payment1 = new Payment(null, account1, account2, new Date(), 1000.0);
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
		Payment payment1 = new Payment(null, account1, account2, new Date(), 80.0);
		payment1 = paymentService.makePayment(payment1);
		
		try {
			Payment payment2 = new Payment(null, account2, account1, new Date(), 1000.0);
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
		
		String date1 = "12/01/2019 15:09:00";
		String date2 = "15/01/2019 23:29:00";
		String date3 = "25/03/2019 02:50:01";
		
		String dateFrom = "12/01/2019";
		String dateTo = "16/01/2019";
		
		//WHEN		
		Payment payment1 = new Payment(null, account1, account2, DateUtils.getDateTime(date1), 50.0);
		payment1 = paymentService.makePayment(payment1);
		
		Payment payment2 = new Payment(null, account2, account1, DateUtils.getDateTime(date2), 25.0);
		payment2 = paymentService.makePayment(payment2);
		
		Payment payment3 = new Payment(null, account2, account1, DateUtils.getDateTime(date3), 60.0);
		payment3 = paymentService.makePayment(payment3);
		
		//THEN
		Transaction account1Transaction1 = transactionService.getTransactionOfAccountFromPayment(payment1, account1);
		Transaction account1Transaction2 = transactionService.getTransactionOfAccountFromPayment(payment2, account1);
		Transaction account1Transaction3 = transactionService.getTransactionOfAccountFromPayment(payment3, account1);

		TreeSet<Transaction> transactionsOfAccountByDate = transactionService.findTransactionsOfAccountByDate(account1, DateUtils.getDate(dateFrom), DateUtils.getDate(dateTo));
		
		Assert.assertEquals(2, transactionsOfAccountByDate.size());
		Assert.assertEquals(true, transactionsOfAccountByDate.contains(account1Transaction1));
		Assert.assertEquals(true, transactionsOfAccountByDate.contains(account1Transaction2));
		Assert.assertEquals(false, transactionsOfAccountByDate.contains(account1Transaction3));
	}

}
