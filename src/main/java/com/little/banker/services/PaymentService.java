package com.little.banker.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.little.banker.domain.Account;
import com.little.banker.domain.Payment;
import com.little.banker.repositories.PaymentRepository;
import com.little.banker.services.exceptions.ObjectNotFoundException;
import com.little.banker.services.exceptions.SameAccountException;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private AccountService accountService;
	
	public Payment find (Integer id) {
		Optional<Payment> op = paymentRepo.findById(id);
		return op.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id:[" + id + "] Type:[" + Payment.class.getSimpleName() + "]"));
	}
	
	public List<Payment> findByAccountAndDate (Account account, Date dateFrom, Date dateTo) {
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