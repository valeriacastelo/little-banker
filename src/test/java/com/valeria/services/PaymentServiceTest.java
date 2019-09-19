package com.valeria.services;

import java.util.Date;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.valeria.domain.Account;
import com.valeria.domain.Payment;
import com.valeria.repositories.AccountRepository;
import com.valeria.repositories.PaymentRepository;
import com.valeria.services.exceptions.SameAccountException;

@RunWith(SpringRunner.class)
public class PaymentServiceTest {
	
	@Autowired
	private PaymentService paymentService;

	@MockBean
	private PaymentRepository paymentRepo;
	
	@MockBean
	private AccountRepository accountRepo;

	@TestConfiguration
	static class PaymentServiceTestContextConfiguration {

		@Bean
		public PaymentService paymentService() {
			return new PaymentService();
		}
		
		@Bean
		public AccountService accountService() {
			return new AccountService();
		}
	}
	
	@Test
	public void makePaymentTest() {
		
		//GIVEN
		Account accountFrom = new Account(1, 350.0);
		Account accountTo = new Account(2, 50.0);
		
		Payment payment = new Payment(1, accountFrom, accountTo, new Date(), 50.0);
		
	    Mockito.doReturn(Optional.of(accountFrom)).when(accountRepo).findById(1);
	    Mockito.doReturn(Optional.of(accountTo)).when(accountRepo).findById(2);
	    Mockito.doReturn(accountFrom).when(accountRepo).save(Mockito.any(Account.class));
	    Mockito.doReturn(payment).when(paymentRepo).save(Mockito.any(Payment.class));
		
		//WHEN
	    paymentService.makePayment(payment);
		
		//THEN
		Assert.assertEquals(300.0, accountFrom.getBalance(), 0);
		Assert.assertEquals(100.0, accountTo.getBalance(), 0);
	}
	
	@Test(expected = SameAccountException.class)
	public void makePaymentWithExceptionTest() {
		
		//GIVEN
		Account accountFrom = new Account(1, 350.0);
		Account accountTo = new Account(1, 350.0);
		
		Payment payment = new Payment(1, accountFrom, accountTo, new Date(), 50.0);
		
	    Mockito.doReturn(Optional.of(accountFrom)).when(accountRepo).findById(1);
	    Mockito.doReturn(Optional.of(accountTo)).when(accountRepo).findById(2);
	    Mockito.doReturn(accountFrom).when(accountRepo).save(Mockito.any(Account.class));
	    Mockito.doReturn(payment).when(paymentRepo).save(Mockito.any(Payment.class));
		
		//WHEN
	    paymentService.makePayment(payment);
		
		//THEN
		//EXCEPTION
	}

}
