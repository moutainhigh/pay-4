package com.pay.txncore.crosspay.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.OrderEmailNotify;
import com.pay.txncore.crosspay.model.OrderEmailNotifyCriteria;
/***
 * 
 * 商户下单邮件通知Service
 * @author davis.guo at 2016-08-31
 *
 */
public interface IOrderEmailNotifyService {

	/***
	 * 通过Id查询指定记录
	 * @param id
	 * @return
	 */
	public OrderEmailNotify findById(Long id);

	/***
	 * 通过Id删除指定记录
	 * @param id
	 * @return
	 */
	public boolean deleteById(Long id);
	
	/***
	 * 保存记录
	 * @param orderEmailNotify
	 * @return
	 */
	public Long saveEntry(OrderEmailNotify orderEmailNotify);

	/***
	 * 更新记录
	 * @param orderEmailNotify
	 * @return
	 */
	public boolean updateEntry(OrderEmailNotify orderEmailNotify);

	/***
	 * 更新开通状态
	 * @param paraMap: id,openFlag,updateDate
	 * @return
	 */
	public boolean updateFlag(Map<String,Object> paraMap);

	/***
	 * 更新开通状态
	 * @param paraMap: openFlag, operateStatus
	 * @return 
	 */
	public boolean updateStatus(Map<String,Object> paraMap);

	/***
	 * 更新最大网关订单号
	 * @param paraMap: id, maxTradeOrderNo
	 * @return 
	 */
	public boolean updateMaxOrderNo(Map<String,Object> paraMap);
	
	/***
	 * 根据条件查询满足条件的记录集
	 * @param oenCriteria
	 * @return
	 */
	public List<OrderEmailNotify> findByCriteria(OrderEmailNotifyCriteria oenCriteria);
	
	/***
	 * 根据条件查询满足条件的记录集
	 * @param oenCriteria
	 * @return
	 */
	public List<OrderEmailNotify> queryOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria);
	
	/***
	 * 根据条件查询满足条件的记录集
	 * @param oenCriteria
	 * @return
	 */
	public List<OrderEmailNotify> queryOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria, Page page);
	
	/***
	 * 根据条件查询满足条件的记录数
	 * @param oenCriteria
	 * @return
	 */
	public Integer queryCountOrderEmailNotify(OrderEmailNotifyCriteria oenCriteria);
}