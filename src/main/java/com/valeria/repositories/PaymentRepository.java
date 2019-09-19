package com.valeria.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.valeria.domain.Account;
import com.valeria.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	@Transactional(readOnly = true)	
	@Query("select p from Payment p "
			+"join p.accountFrom ac "
			+"join p.accountTo at "
			+ "where (ac = :account or at = :account) "
			+ "and (trunc(p.date) between trunc(:dateFrom) and trunc(:dateTo))"
			)
	List<Payment> findByAccountAndDate(@Param("account") Account account, 
			@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

}
