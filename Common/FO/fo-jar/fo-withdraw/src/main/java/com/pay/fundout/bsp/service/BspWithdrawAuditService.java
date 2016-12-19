package com.pay.fundout.bsp.service;

import java.util.Map;

import com.pay.fundout.withdraw.dto.bsp.BspWithdrawAuditResultDTO;
import com.pay.inf.dao.Page;

/**
 * 提现审核
 * <p>
 * </p>
 * 
 * @author wucan
 * @since 2011-6-28
 * @see
 */
public interface BspWithdrawAuditService {

	/**
	 * 提现审核列表
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Map<String, Object>> queryResultList(Map<String, Object> map, Integer pageNo, Integer pageSize);

	/**
	 * 提现审核查看
	 * 
	 * @param map
	 * @return
	 */
	BspWithdrawAuditResultDTO view(Map<String, Object> map);

	/**
	 * 提现审核通过
	 * 
	 * @return
	 */
	boolean approved(Map<String, Object> map);

	/**
	 * 提现审核拒绝
	 * 
	 * @return
	 */
	boolean refuse(Map<String, Object> map);

}
