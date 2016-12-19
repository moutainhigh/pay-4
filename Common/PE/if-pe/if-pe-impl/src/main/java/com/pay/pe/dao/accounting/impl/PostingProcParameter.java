
package com.pay.pe.dao.accounting.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public final class PostingProcParameter implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6794997428098766268L;
	
	/**
	 * 修改账户余额.
	 */
	private List <PostingProcAcctParameter> acctParameters = new ArrayList <PostingProcAcctParameter>();
	
	/**
	 * 增加的分录.
	 */
	private List <PostingProcEntryParameter> entryParameters = new ArrayList <PostingProcEntryParameter>();

	/**
	 * @return Returns the acctParameters.
	 */
	public List<PostingProcAcctParameter> getAcctParameters() {
		return acctParameters;
	}

	/**
	 * @param acctParameters The acctParameters to set.
	 */
	public void setAcctParameters(List<PostingProcAcctParameter> acctParameters) {
		this.acctParameters = acctParameters;
	}

	/**
	 * @return Returns the entryParameters.
	 */
	public List<PostingProcEntryParameter> getEntryParameters() {
		return entryParameters;
	}

	/**
	 * @param entryParameters The entryParameters to set.
	 */
	public void setEntryParameters(List<PostingProcEntryParameter> entryParameters) {
		this.entryParameters = entryParameters;
	}
	
	public void addAcctPara(final PostingProcAcctParameter acctPara) {
		acctParameters.add(acctPara);
	}
	
	public void addEntryPara(final PostingProcEntryParameter entryPara) {
		entryParameters.add(entryPara);
	}
}
