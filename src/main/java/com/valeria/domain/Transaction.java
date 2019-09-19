package com.valeria.domain;

import java.io.Serializable;
import java.util.Date;

import com.valeria.dto.AccountDTO;

public class Transaction implements Serializable, Comparable<Transaction> {
	private static final long serialVersionUID = 1L;
	
	private AccountDTO account;
	private Double amount;
	private TransactionType type;
	private Date date;

	public AccountDTO getAccount() {
		return account;
	}
	public void setAccount(AccountDTO account) {
		this.account = account;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public TransactionType getType() {
		return type;
	}
	public void setType(TransactionType type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public int compareTo(Transaction otherTransaction) {
		int compareToDate = this.date == null? -1 : this.date.compareTo(otherTransaction.getDate());
		if (compareToDate == 0) {
			compareToDate = this.equals(otherTransaction) ? 0 : 1;
		}
		
		return compareToDate;
	}
	
}
