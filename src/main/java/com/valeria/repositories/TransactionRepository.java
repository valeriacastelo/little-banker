package com.valeria.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Account;
import com.valeria.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	@Transactional(readOnly = true)
	List<Transaction> findByAccountFromOrAccountToAndDateBetween(Account accountFrom, Account accountTo, Date dateFrom , Date dateTo);

}
