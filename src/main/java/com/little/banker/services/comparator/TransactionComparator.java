package com.little.banker.services.comparator;

import java.util.Comparator;

import com.little.banker.domain.Transaction;

public class TransactionComparator implements Comparator<Transaction> {

	@Override
	public int compare(Transaction o1, Transaction o2) {
		return o1.compareTo(o2);
	}

}
