package com.valeria.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	@Transactional(readOnly = true)	
	@Query("select t from Transaction t "
			+ "where (t.accountFrom.id = :accountId or t.accountTo.id = :accountId) "
			+ "and (trunc(t.date) between trunc(:dateFrom) and trunc(:dateTo))"
			)
	List<Transaction> findByAccountAndDate(@Param("accountId") Integer accountId, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
