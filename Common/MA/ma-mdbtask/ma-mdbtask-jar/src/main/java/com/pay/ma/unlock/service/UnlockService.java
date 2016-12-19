package com.pay.ma.unlock.service;


/**
 * @author Administrator
 *
 */
public interface UnlockService {
	
	/**
	 * @param unfreezeDate 解冻时间
	 * @param status 解冻更新状态
	 * @return
	 */
	public boolean unlockMemberWithStatus(Integer status);

}
