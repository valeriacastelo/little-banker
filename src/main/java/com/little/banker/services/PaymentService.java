package com.little.banker.services;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.repositories.PaymentRepository;
import com.little.banker.services.exceptions.SameAccountException;

@Service
public class PaymentService {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private AccountService accountService;
	
	
	public List<Payment> findByAccountAndDate (Account account, LocalDate dateFrom, LocalDate dateTo) {
		LOG.debug("Finding payments by the account id [" + account.getId() + "] DateFrom [" + dateFrom + "] DateTo [" + dateTo + "]");
		
		return paymentRepo.findByAccountAndDate(account, dateFrom, dateTo);
	}
	
	@Transactional
	public Payment makePayment (Payment payment) {
		LOG.debug("Inserting the payment [" + payment + "]");
		
		//making sure it will be a insert and not a update
		payment.setId(null);
		
		//check if the the account are the same
		if (payment.getAccountFrom().equals(payment.getAccountTo())) {
			throw new SameAccountException("Account from and account to cannot be the same");
		}
		
		//performs the operations related to the payment
		accountService.debit(payment.getAccountFrom(), payment.getAmount());
		accountService.credit(payment.getAccountTo(), payment.getAmount());
		
		return paymentRepo.save(payment);
	}
}
