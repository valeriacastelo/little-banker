package com.valeria.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Double balance;
	
	@JsonIgnore
	@OneToMany(mappedBy = "accountFrom")
	private List<Transaction> debitTransactions = new ArrayList<Transaction>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "accountTo")
	private List<Transaction> creditTransactions = new ArrayList<Transaction>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Transaction> getDebitTransactions() {
		return debitTransactions;
	}

	public void setDebitTransactions(List<Transaction> debitTransactions) {
		this.debitTransactions = debitTransactions;
	}

	public List<Transaction> getCreditTransactions() {
		return creditTransactions;
	}

	public void setCreditTransactions(List<Transaction> creditTransactions) {
		this.creditTransactions = creditTransactions;
	}
	
}
