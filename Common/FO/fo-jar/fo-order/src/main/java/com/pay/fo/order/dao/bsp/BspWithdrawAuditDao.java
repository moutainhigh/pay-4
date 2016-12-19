package com.pay.fo.order.dao.bsp;

import java.util.Map;

import com.pay.inf.dao.Page;

/**
 * 提现审核Dao
 * <p>
 * </p>
 * 
 * @author wucan
 * @since 2011-6-29
 * @see
 */
public interface BspWithdrawAuditDao {

	/**
	 * 提现审核查看
	 * 
	 * @param map
	 * @return BspWithdrawAuditResultDTO
	 */
	Map<String, Object> view(Map<String, Object> map);

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
	 * 更新bsp工单状态
	 * 
	 * @param map
	 * @return
	 */
	public boolean updateBspWorkorder(Map<String, Object> map);
	
}
