package com.valeria.services;

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
import com.valeria.repositories.AccountRepository;
import com.valeria.services.exceptions.InsufficientFundsException;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
	
	@Autowired
	private AccountService accountService;

	@MockBean
	private AccountRepository accountRepo;

	@TestConfiguration
	static class AccountServiceTestContextConfiguration {

		@Bean
		public AccountService accountService() {
			return new AccountService();
		}
	}
	
	@Test
	public void creditTest() {
		
		//GIVEN
		Account account = new Account(1, 100.0);
		Double amount = 50.0;
		
	    Mockito.doReturn(Optional.of(account)).when(accountRepo).findById(1);
	    Mockito.doReturn(account).when(accountRepo).save(Mockito.any(Account.class));
		
		//WHEN
		accountService.credit(account, amount);
		
		//THEN
		Assert.assertEquals(150.0, account.getBalance(), 0);
	}
	
	@Test
	public void debitTest() {
		
		//GIVEN
		Account account = new Account(1, 100.0);
		Double amount = 50.0;
		
	    Mockito.doReturn(Optional.of(account)).when(accountRepo).findById(1);
	    Mockito.doReturn(account).when(accountRepo).save(Mockito.any(Account.class));
		
		//WHEN
		accountService.debit(account, amount);
		
		//THEN
		Assert.assertEquals(50.0, account.getBalance(), 0);
	}
	
	@Test(expected = InsufficientFundsException.class)
	public void debitWithExceptionTest() {
		
		//GIVEN
		Account account = new Account(1, 100.0);
		Double amount = 150.0;
		
	    Mockito.doReturn(Optional.of(account)).when(accountRepo).findById(1);
	    Mockito.doReturn(account).when(accountRepo).save(Mockito.any(Account.class));
		
		//WHEN
		accountService.debit(account, amount);
		
		//THEN
		//EXCEPTION
	}

}
