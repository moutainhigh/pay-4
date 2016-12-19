package com.pay.poss.controller.fi.commons;

public enum WithdrawalsApprovalStatusEnum {

	APPR_WAIT(1,"待审核"),
	APPR_PASS(3,"审核通过"),
	APPR_COMP(4,"完成");
	
	private int value;
	private String des;
	WithdrawalsApprovalStatusEnum(int val,String des){
		this.value = val;
		this.des = des;
	}
	
	public int getValue(){
		return this.value;
	}
}
