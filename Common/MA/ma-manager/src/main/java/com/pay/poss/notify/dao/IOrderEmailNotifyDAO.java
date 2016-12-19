package com.pay.poss.notify.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.notify.model.OrderEmailNotify;
import com.pay.poss.notify.model.OrderEmailNotifyCriteria;

/**
 * 商户下单邮件通知DAO
 * @author davis.guo at 2016-09-02
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
	 * @param oenCriteria
	 * @return 
	 */
	public Integer updateFlag(OrderEmailNotify orderEmailNotify);
}