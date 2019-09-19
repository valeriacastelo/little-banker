package com.little.banker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.little.banker.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
