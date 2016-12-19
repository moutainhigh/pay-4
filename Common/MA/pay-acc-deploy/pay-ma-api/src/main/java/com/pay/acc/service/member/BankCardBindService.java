/**
 * 
 */
package com.pay.acc.service.member;

import java.util.List;

import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.service.member.dto.BankCardBindBO;


/**
 * 银行卡绑定
 * 
 * @author jeffrey_teng 
 * 
 * @date 2010-9-26
 */
public interface BankCardBindService {

	/**
	 * 查询银行卡绑定信息
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 返回所以银行卡绑定信息
	 * @throws MaBankCardBindException
	 *             银行卡查询异常
	 */
	public List<BankCardBindBO> doQueryBankCardBindInfoForVerifyNsTx(long memberCode) throws MaBankCardBindException;

}
