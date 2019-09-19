package com.little.banker.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "required")
	private Double balance;
	
	@JsonIgnore
	@OneToMany(mappedBy = "accountFrom")
	private List<Payment> madePayments = new ArrayList<Payment>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "accountTo")
	private List<Payment> receivedPayments = new ArrayList<Payment>();
	
	public Account() {
		
	}

	public Account(Integer id, Double balance) {
		super();
		this.id = id;
		this.balance = balance;
	}

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

	public List<Payment> getMadePayments() {
		return madePayments;
	}

	public void setMadePayments(List<Payment> madePayments) {
		this.madePayments = madePayments;
	}

	public List<Payment> getReceivedPayments() {
		return receivedPayments;
	}

	public void setReceivedPayments(List<Payment> receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [id=");
		builder.append(id);
		builder.append(", balance=");
		builder.append(balance);
		builder.append("]");
		return builder.toString();
	}	
}
