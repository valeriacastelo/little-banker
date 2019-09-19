package com.valeria.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.valeria.domain.Account;
import com.valeria.domain.Transaction;

public class TransactionByAccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String DEBIT = "DEBIT";
	private static final String CREDIT = "CREDIT";
	
	private AccountDTO accountFromOrTo; //Account that sent or received the amount
	private Double amount;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date date;
	
	private String type;
	
	public TransactionByAccountDTO() {
		
	}
	
	public TransactionByAccountDTO(Transaction transaction, Account account) { 
		if (transaction.getAccountFrom().equals(account)) {
			this.accountFromOrTo = new AccountDTO(transaction.getAccountTo());
			this.type = DEBIT;
		} else {
			this.accountFromOrTo = new AccountDTO(transaction.getAccountFrom());
			this.type = CREDIT;
		}
		
		this.date = transaction.getDate();
		this.amount = transaction.getAmount();
	}
	
	public AccountDTO getAccountFromOrTo() {
		return accountFromOrTo;
	}

	public void setAccountFromOrTo(AccountDTO accountFromOrTo) {
		this.accountFromOrTo = accountFromOrTo;
	}

	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
