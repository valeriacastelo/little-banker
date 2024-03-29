package com.little.banker.dto;

import java.io.Serializable;

import com.little.banker.domain.Account;

public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Integer id;
	
	public AccountDTO () {
		
	}
	
	public AccountDTO(Account account) {
		this.id = account.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		AccountDTO other = (AccountDTO) obj;
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
		builder.append("AccountDTO [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
}
