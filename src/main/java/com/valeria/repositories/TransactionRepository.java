package com.valeria.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Account;
import com.valeria.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	@Transactional(readOnly = true)	
	@Query("select t from Transaction t "
			+"join t.accountFrom ac "
			+"join t.accountTo at "
			+ "where (ac = :account or at = :account) "
			+ "and (trunc(t.date) between trunc(:dateFrom) and trunc(:dateTo))"
			)
	List<Transaction> findByAccountAndDate(@Param("account") Account account, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
