package com.pay.fundout.withdraw.service.autocheck;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;

/**
 * 风控自动审核判断标准接口
 * @author meng.li
 *
 */
public interface JudgeService {

	/**
	 * 规则A，判断工单是否处于滞留状态
	 * @return true：是，false：不是
	 */
	public boolean methodA(WithdrawAuditDTO dto);
	
	/**
	 * 规则B，判断账户是否处于"止出"或"冻结"状态
	 * @return true：是，false：不是
	 */
	public boolean methodB(WithdrawAuditDTO dto) throws Exception;
	
	/**
	 * 规则C，判断当前时间点是否已满足该交易结算周期要求
	 * @return true：满足，false：不满足
	 */
	public boolean methodC(WithdrawAuditDTO dto);
	
}
