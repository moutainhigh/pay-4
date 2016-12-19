package com.pay.pe.service.order.common;

import java.io.Serializable;
import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

/**
 * 
 */
public interface COSResponse extends Serializable {
	/**
	 * 处理的订单号.
	 * 此订单号为银行网关系统的订单号.
	 * 如果要得到应用层的订单号，需要根据getDealId得到PaymentOrder中的sequenceId.
	 * @return String
	 */
	String getOrderNo();
	
	void setOrderNo(String orderNo);
	
	/**
	 * 处理的交易号.
	 * @return String
	 */
	String getDealId();
	
	void setDealId(String  dealId);
	
	
	/**
	 * 处理后的order的 SeqId
	 * @return
	 */
	public Long getOrderSeqId();

	public void setOrderSeqId(Long orderSeqId);	
	
	
	
	
	/**
	 * 处理的类型.
	 * @return int
	 * 
	 */
	int getCosActionType();
	
	/**
	 * 设置Deal处理的类型.
	 *
	 */
	void setCosActionType(int actionType);
	
	
	
	/**
	 * 处理的结果.
	 * @return int
	 */
	int getCosActionStatus();
	
	/**
	 * 设置Deal处理的结果.
	 *
	 */
	void setCosActionStatus(int actionStatus);
	

	
	
	
	
	List<AccountEntryDTO> getDealEntries();
	
	void setDealEntries(List<AccountEntryDTO> entries);
	
	
	
	/**
	 * 应用层接收分录号.
	 */
	 long getVoucherCode();
	 
	 void setVoucherCode(long voucherCode);
	
//	
//	ErrorCodeType getErrorCodeType();
//	void setErrorCodeType(ErrorCodeType errorCodeType);
}
