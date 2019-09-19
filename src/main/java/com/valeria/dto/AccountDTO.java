package com.valeria.dto;

import java.io.Serializable;

import com.valeria.domain.Account;

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
	
}
