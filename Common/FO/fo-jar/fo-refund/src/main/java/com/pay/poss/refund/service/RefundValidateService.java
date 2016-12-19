/**
 *  <p>File: RefundValidateService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.service;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.refund.model.RechargeRecordDto;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.WebQueryRefundDTO;

public interface RefundValidateService {

	/**
	 * 
	 * @param dto
	 * @return
	 */
	String validateRefundData(WebQueryRefundDTO dto);

	/**
	 * 设置和验证会员信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	String validateMember(WebQueryRefundDTO dto, Page<RechargeRecordDto> page,
			Map<String, Object> model);
	
	/**
	 * 获取会员相关信息
	 * 
	 * @param dto
	 * @param model
	 * @return
	 */
	String setMemberInfo(RefundOrderM mDto);
	
	/**
	 * 验证充值银行是否有充退渠道
	 * 
	 * @param mDto
	 * @param string
	 * @return
	 */
	String validateBankCode(RefundOrderM mDto);
	
	/**余额判断
	 * @param mDto
	 * @return
	 */
	public boolean validateAmount(RefundOrderM mDto);
	
	/**可充退金额判断
	 * @param mDto
	 * @param dDto
	 * @return
	 */
	public boolean validateApplyMax(RefundOrderM mDto,RefundOrderD dDto);
}
