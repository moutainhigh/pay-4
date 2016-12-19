package com.pay.fundout.withdraw.model.paytoaccount;

import java.io.Serializable;
import java.util.Date;

public class Pay2AcctResponse implements Serializable {
	private static final long serialVersionUID = 8661501990827713331L;

	private Long sequenceId;
	private Date tradeDate;
	private Integer status;
	private String errotTip;


	public Long getSequenceId() {
		return sequenceId;
	}


	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}


	public Date getTradeDate() {
		return tradeDate;
	}


	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getErrotTip() {
		return errotTip;
	}


	public void setErrotTip(String errotTip) {
		this.errotTip = errotTip;
	}


	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("PAY RESPONSE [").append("sequence=").append(sequenceId).append(", ");
		result.append("status=").append(status).append(", ");
		result.append("errorTip=").append(errotTip).append(", ");
		result.append("tradeDate=").append(tradeDate).append("]");
		return result.toString();
	}
}
