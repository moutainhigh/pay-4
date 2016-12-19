/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.paychain;

import java.util.List;

import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.model.PayChain;

/**
 * @author fjl
 * @date 2011-9-21
 */
public interface PayChainPayInfoService {
	
	/**
	 * 得到支付链支付信息。
	 * @param memberCode
	 * @return
	 */
	public PayChainPayInfo get(PayChain payChain);
	
	/**
	 * 得到不需要登录就能支付的mcc 集合
	 * @return
	 */
	public List<String> getMccList();
	
	/**
	 * 根据mcc 判断是否需要登录
	 * @param mcc
	 * @return
	 */
	public boolean isNeedLogin(String mcc);

}
