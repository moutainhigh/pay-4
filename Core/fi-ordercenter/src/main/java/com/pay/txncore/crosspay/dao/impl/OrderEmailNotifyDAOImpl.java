package com.pay.txncore.crosspay.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.crosspay.dao.IOrderEmailNotifyDAO;
import com.pay.txncore.crosspay.model.OrderEmailNotify;
import com.pay.txncore.crosspay.model.OrderEmailNotifyCriteria;

/***
 * 
 * 商户下单邮件通知DAOImpl
 * @author davis.guo at 2016-08-31
 *
 */
public class OrderEmailNotifyDAOImpl extends
		BaseDAOImpl<OrderEmailNotify> implements IOrderEmailNotifyDAO {

	@Override
	public List<OrderEmailNotify> queryOrderEmailNotify(
			final OrderEmailNotifyCriteria oenCriteria) {
		List<OrderEmailNotify> enterpriseList = 
				this.getSqlMapClientTemplate().queryForList(
						"ORDER_EMAIL_NOTIFY.queryOrderEmailNotify", oenCriteria);
		return null;
	}

	@Override
	public List<OrderEmailNotify> queryOrderEmailNotify(
			OrderEmailNotifyCriteria oenCriteria, Page page) {
		return 	super.findByCriteria(oenCriteria, page);
	}

	@Override
	public Integer queryCountOrderEmailNotify(
			OrderEmailNotifyCriteria oenCriteria) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("ORDER_EMAIL_NOTIFY.queryCountOrderEmailNotify",
						oenCriteria);
		return count;
	}

	@Override
	public Integer updateFlag(Map<String,Object> paraMap) {
		Integer result = this.getSqlMapClientTemplate().update(
				"ORDER_EMAIL_NOTIFY.updateFlag", paraMap);
		return result;
	}

	@Override
	public Integer updateStatus(Map<String,Object> paraMap) {
		Integer result = this.getSqlMapClientTemplate().update(
				"ORDER_EMAIL_NOTIFY.updateStatus", paraMap);
		return result;
	}

	@Override
	public Integer updateMaxOrderNo(Map<String,Object> paraMap) {
		Integer result = this.getSqlMapClientTemplate().update(
				"ORDER_EMAIL_NOTIFY.updateMaxOrderNo", paraMap);
		return result;
	}

}
