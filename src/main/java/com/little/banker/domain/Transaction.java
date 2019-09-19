package com.little.banker.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.little.banker.dto.AccountDTO;

public class Transaction implements Serializable, Comparable<Transaction> {
	private static final long serialVersionUID = 1L;
	
	private AccountDTO account;
	private Double amount;
	private TransactionType type;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dateTime;
	
	@JsonIgnore
	private Integer paymentId;

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
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	public Integer getPaymentId() {
		return this.paymentId;
	}
	
	@Override
	public int compareTo(Transaction otherTransaction) {
		int compareToDate = this.dateTime == null? -1 : this.dateTime.compareTo(otherTransaction.getDateTime());
		if (compareToDate == 0) {
			compareToDate = this.equals(otherTransaction) ? 0 : 1;
		}
		
		return compareToDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [account=");
		builder.append(account);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", type=");
		builder.append(type);
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append(", paymentId=");
		builder.append(paymentId);
		builder.append("]");
		return builder.toString();
	}
	
}
