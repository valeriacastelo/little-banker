package com.little.banker.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Payment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "required")
	@ManyToOne
	@JoinColumn(name = "account_from_id")
	private Account accountFrom;
	
	@NotNull(message = "required")
	@ManyToOne
	@JoinColumn(name = "account_to_id")
	private Account accountTo;
	
	private LocalDateTime dateTime;
	
	@NotNull(message = "required")
	private Double amount;
	
	public Payment() {
		
	}
	
	public Payment(Integer id, Account accountFrom, Account accountTo, LocalDateTime dateTime, Double amount) {
		super();
		this.id = id;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.dateTime = dateTime;
		this.amount = amount;
	}
	
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
		Payment other = (Payment) obj;
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
		builder.append("Payment [id=");
		builder.append(id);
		builder.append(", accountFrom=");
		builder.append(accountFrom == null? "" : accountFrom.getId());
		builder.append(", accountTo=");
		builder.append(accountTo == null? "" : accountTo.getId());
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}	
}
