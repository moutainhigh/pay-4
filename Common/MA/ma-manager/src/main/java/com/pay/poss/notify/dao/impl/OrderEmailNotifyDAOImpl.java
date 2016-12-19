package com.pay.poss.notify.dao.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.notify.dao.IOrderEmailNotifyDAO;
import com.pay.poss.notify.model.OrderEmailNotify;
import com.pay.poss.notify.model.OrderEmailNotifyCriteria;

/***
 * 
 * 商户下单邮件通知DAOImpl
 * @author davis.guo at 2016-09-02
 *
 */
public class OrderEmailNotifyDAOImpl extends
		BaseDAOImpl<OrderEmailNotify> implements IOrderEmailNotifyDAO {

	@Override
	public List<OrderEmailNotify> queryOrderEmailNotify(
			final OrderEmailNotifyCriteria oenCriteria) {
		List<OrderEmailNotify> enterpriseList = 
				this.getSqlMapClientTemplate().queryForList(
						"notifyByEmail.queryOrderEmailNotify", oenCriteria);
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
				.queryForObject("notifyByEmail.queryCountOrderEmailNotify",
						oenCriteria);
		return count;
	}

	@Override
	public Integer updateFlag(OrderEmailNotify orderEmailNotify) {
		Integer result = this.getSqlMapClientTemplate().update(
				"notifyByEmail.updateFlag", orderEmailNotify);
		return result;
	}

}
