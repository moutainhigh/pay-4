package com.pay.fundout.withdraw.service.autocheck;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;

/**
 * 风控自动审核接口
 * @author meng.li
 *
 */
public interface AutoCheckService {
	
	/**
	 * 自动审核订单，每个都作为一个新的事务执行
	 * @param order 待审核订单数据
	 * @return 自动审核通过成功返回1，其他情况返回0
	 * @throws Exception
	 */
	public int autocheckRnTx(WithdrawAuditDTO order) throws Exception;
	
}
