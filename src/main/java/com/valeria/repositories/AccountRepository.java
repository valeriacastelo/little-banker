package com.valeria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valeria.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
