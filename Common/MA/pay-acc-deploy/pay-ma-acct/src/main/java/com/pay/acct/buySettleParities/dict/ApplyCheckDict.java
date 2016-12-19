package com.pay.acct.buySettleParities.dict;

public enum ApplyCheckDict {
	WAIT_CHECK("0","待审核"),
	SUCCESS_CHECK("1","审核通过"),
	FAIL_CHECK("2","审核拒绝");
	
	private String status;
	private String des;
	ApplyCheckDict(String status,String des){
		this.status=status;
		this.des=des;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
}
