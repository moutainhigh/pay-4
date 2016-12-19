package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.OrderEmailNotify;
import com.pay.txncore.crosspay.model.OrderEmailNotifyCriteria;

/**
 * 商户下单邮件通知DAO
 * @author davis.guo at 2016-08-31
 */
public interface IOrderEmailNotifyDAO extends BaseDAO<OrderEmailNotify> {
	
	/***
	 * 根据查询条件查询记录
	 * @param oenCriteria
	 * @return List<OrderEmailNotify>
	 */
	public List<OrderEmailNotify> queryOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria);
	/***
	 * 根据查询条件查询记录，分页显示
	 * @param oenCriteria
	 * @return List<OrderEmailNotify>
	 */
	public List<OrderEmailNotify> queryOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria, Page page);
	
	/***
	 * 根据查询条件查询记录数
	 * @param oenCriteria
	 * @return 记录数
	 */
	public Integer queryCountOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria);
	
	/***
	 * 更新开通状态
	 * @param paraMap: id,openFlag,updateDate
	 * @return 
	 */
	public Integer updateFlag(Map<String,Object> paraMap);

	/***
	 * 更新开通状态
	 * @param paraMap: openFlag, operateStatus
	 * @return 
	 */
	public Integer updateStatus(Map<String,Object> paraMap);

	/***
	 * 更新最大网关订单号
	 * @param paraMap: id, maxTradeOrderNo
	 * @return 
	 */
	public Integer updateMaxOrderNo(Map<String,Object> paraMap);
}