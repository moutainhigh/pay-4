/**
 * 
 */
package com.pay.fundout.service.ma;

import java.util.List;

/**
 * @author NEW
 *
 */
public interface BankCardBindFacadeService {
	
	/**
	 * 获取已绑定银行卡列表
	 * @param memberCode
	 * @return
	 */
	List<BankCardBindInfo> getBankCardBindInfoList(long memberCode);

}
