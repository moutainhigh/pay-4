package com.pay.pe.model;

import java.sql.Timestamp;
import java.util.List;

import com.pay.inf.model.Model;



public class AccountReference implements Model{

	private Timestamp closedacctperioddate;
	private Long id;

	public AccountReference() {
		super();
	}

	public Timestamp getClosedacctperioddate() {
		return this.closedacctperioddate;
	}

	public Long getId() {
		return this.id;
	}

	public void setClosedacctperioddate(Timestamp closedacctperioddate) {
		this.closedacctperioddate = closedacctperioddate;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
