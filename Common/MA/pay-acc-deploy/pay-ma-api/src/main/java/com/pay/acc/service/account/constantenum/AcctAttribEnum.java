package com.pay.acc.service.account.constantenum;

public enum AcctAttribEnum {
	
	/**
	 * 允许充值  0:不允许  1:是正常
	 */
	ALLOW_DEPOSIT("allowDeposit", 1),
	
	/**
	 * 允许提现  0:不允许  1:是正常
	 */
	ALLOW_WITHDRAWAL("allowWithdrawal",1),
	
	/**
	 * 允许转账入  0:不允许  1:是正常
	 */
	ALLOW_TRANSFER_IN("allowTransferIn",1),

	/**
	 * 允许转账出  0:不允许  1:是正常
	 */
	ALLOW_TRANSFER_OUT("allowTransferOut",1),

	/**
	 * 是否止入  0:不允许  1:是正常
	 */
	ALLOW_IN("allowIn",1),

	/**
	 * 是否止出  0:不允许  1:是正常
	 */
	ALLOW_OUT("allowOut",1),

	/**
	 * 允许支付  0:不允许  1:是正常
	 */
	PAY_ABLE("payAble",1),
	/**
	 * 允许透支  0:不允许  1:是正常
	 */
	ALLOW_OVERDRAFT("allowOverdraft",1),
	/**
	 * 密码保护  0:不允许  1:是正常
	 */
	NEED_PROTECT("needProtect",1),

	/**
	 * 允许会员管理  0:不允许  1:是正常
	 */
	MANAGERABLE("managerable",1),
	
	/**
	 * 是否垫资 0 否；1是
	 */
	ALLOW_ADVANCE_MONEY("allowAdvanceMoney",1)

	;

	private String code;

	private Integer vale;

	private AcctAttribEnum(String code, Integer value) {
		this.code = code;
		this.vale = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getVale() {
		return vale;
	}

	public void setVale(Integer vale) {
		this.vale = vale;
	}

}
