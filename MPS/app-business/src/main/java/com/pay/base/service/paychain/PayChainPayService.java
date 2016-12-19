/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.paychain;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import com.pay.base.dto.PayChainPayInfo;

/**
 * @author fjl
 * @date 2011-9-22
 */
public interface PayChainPayService {

	/**
	 * 创建有序的付款的参数
	 * @param payeeMemberCode 收款方memberCode
	 * @param orderNo 流水号
	 * @param amount 支付金额
	 * @param payerEmail 付款方Email
	 * @param payChainCode 支付链编号
	 * @param getReceiptDesc 收款项目描述
	 * @return LinkedHashMap<String,String> 参数列表
	 */
	public LinkedHashMap<String,String> createPayMap(PayChainPayInfo payChainPayInfo);
	
	/**
	 * 验证付款通知的参数是否正确，一般是验证签名是否正确
	 * @param request http的request 网关的请求
	 * @return true/false   验证通过失败或是成功
	 */
	public boolean validateNoticeMap(HttpServletRequest request);
	
	
	

}
