package com.little.banker.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.repositories.PaymentRepository;
import com.little.banker.services.exceptions.SameAccountException;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private AccountService accountService;
	
	
	public List<Payment> findByAccountAndDate (Account account, LocalDate dateFrom, LocalDate dateTo) {
		return paymentRepo.findByAccountAndDate(account, dateFrom, dateTo);
	}
	
	@Transactional
	public Payment makePayment (Payment payment) {
		payment.setId(null);
		
		if (payment.getAccountFrom().equals(payment.getAccountTo())) {
			throw new SameAccountException("Account from and account to cannot be the same");
		}
		
		accountService.debit(payment.getAccountFrom(), payment.getAmount());
		accountService.credit(payment.getAccountTo(), payment.getAmount());
		
		return paymentRepo.save(payment);
	}
}
