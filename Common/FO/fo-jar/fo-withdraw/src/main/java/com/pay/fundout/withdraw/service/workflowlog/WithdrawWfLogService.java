package com.pay.fundout.withdraw.service.workflowlog;

import java.util.List;

import com.pay.fundout.withdraw.dto.workflowlog.WithdrawWfLogDTO;

public interface WithdrawWfLogService {
	
	
	/**
	 * 保存日志信息
	 * @param dto
	 * @return
	 */
	public Long saveWithdrawWfLog(WithdrawWfLogDTO dto);
	
	/**
	 * 查询历史信息
	 * @param workflowId
	 * @return
	 */
	public List<WithdrawWfLogDTO> queryWithdrawWfLogList(String workflowId);
	
	/**
	 * 根据工单信息查询历史
	 * @param workOrderKy
	 * @return
	 */
	public List<WithdrawWfLogDTO> queryWithDrawWfLogByWorkOrderKy(Long workOrderKy);
	
	/**
	 * 根据订单信息查询历史
	 * @param OrderKy
	 * @return
	 */
	public List<WithdrawWfLogDTO> queryWithDrawWfLogByOrderKy(Long OrderKy);

}
