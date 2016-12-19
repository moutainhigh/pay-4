package com.pay.pe.exception;

/**
 * g
 * ErrorCode枚举
 * 
 */
public enum ErrorCodeType {
	ASYNACCOUNT_REJECTED(100, "不能进行异步过账"),
	ASYNACCOUNT_MUSTASYN(101, "必须异步过账"),
	POSTIRNRULE_IS_EXISTS(102, "记帐规则已经存在"),
	SANDMQ_FAIL(103, "发送MQ失败"),
	ORDERPAYSERVICE_IS_NULL(104, "回调类为空"),
	BALANCE_NOT_ENOUGH(105,"账户余额不足"),
	SUBJECT_IS_NULL(106,"账户对应的科目为空"),
	ACCT_IS_NULL(107,"账户为空"),
	MEMBERACCT_IS_NULL(108,"账户属性为空"),
	DRCR_NOT_BALANCE(109,"借贷不平衡"),
	ACCT_FROZENED(110,"用户账户被冻结或不存在"),
	PAYMENTSERVICES_NOT_FOUND(111,"没有找到相应的支付服务"),
	DEALID_IS_NULL(112, "dealId为空"),
	DEAL_IS_NULL(113,"DEAL为空"),
	DEALSTATUS_IS_WRONG(114, "DEAL的状态不对"),
	DEAL_NOT_FOUND(115,"没有找到DEAL"),
	DEAL_IS_LOCKED(116,"DEAL已经被锁住"),
	GENERATEACCTCODE_IS_WRONG(117,"产生帐号时错误"),
	TRANSACTIONDATE_IS_NULL(118,"DEAL已经算过费,会计时间不能为空"),
	TRANSACTIONDATE_IS_CLOSED(119, "该会计日期已经轧账，不能再进行过帐"),
    SAVE_DEAL_ERROR(120,"保存paymentOrder与deal失败"),
    DRTOTAL_CRTOTAL_ERROR(121,"借贷发生额不相等,产生的分录有错")

    ;	
	
	private String desc;

	private Integer value;

	ErrorCodeType(final Integer value, final String desc) {
		this.desc = desc;
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.getDesc();
	}

}
