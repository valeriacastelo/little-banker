package com.valeria.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "from_account_id")
	private Account accountFrom;
	
	@ManyToOne
	@JoinColumn(name = "to_account_id")
	private Account accountTo;
	
	private Date date;
	
	private Double amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Account getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Account accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Account getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(Account accountTo) {
		this.accountTo = accountTo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
