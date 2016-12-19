package com.pay.poss.notify.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.notify.dao.IOrderEmailNotifyDAO;
import com.pay.poss.notify.model.OrderEmailNotify;
import com.pay.poss.notify.model.OrderEmailNotifyCriteria;
import com.pay.poss.notify.service.IOrderEmailNotifyService;
/***
 * 
 * 商户下单邮件通知ServiceImpl
 * @author davis.guo at 2016-09-02
 *
 */
public class OrderEmailNotifyServiceImpl implements
		IOrderEmailNotifyService {

	private final Log log = LogFactory.getLog(getClass());
	
	private IOrderEmailNotifyDAO orderEmailNotifyDao;

	public void setOrderEmailNotifyDao(IOrderEmailNotifyDAO orderEmailNotifyDao) {
		this.orderEmailNotifyDao = orderEmailNotifyDao;
	}

	@Override
	public OrderEmailNotify findById(Long id) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.findById is running...");
			return orderEmailNotifyDao.findById(id);
		}
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.deleteById is running...");
			return orderEmailNotifyDao.delete(id);
		}
		return false;
	}

	@Override
	public Long saveEntry(OrderEmailNotify orderEmailNotify) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.saveEntry is running...");
			return (Long)orderEmailNotifyDao.create(orderEmailNotify);
		}
		return 0L;
	}

	@Override
	public boolean updateEntry(OrderEmailNotify orderEmailNotify) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.updateEntry is running...");
			return orderEmailNotifyDao.update(orderEmailNotify);
		}
		return false;
	}

	@Override
	public boolean updateFlag(OrderEmailNotify orderEmailNotify) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.updateFlag is running...");
			int result = orderEmailNotifyDao.updateFlag(orderEmailNotify);
			return (result>0?true:false);
		}
		return false;
	}

	@Override
	public List<OrderEmailNotify> findByCriteria(
			OrderEmailNotifyCriteria oenCriteria) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.findByCriteria is running...");
			return orderEmailNotifyDao.findByCriteria(oenCriteria);
		}
		return null;
	}

	@Override
	public List<OrderEmailNotify> queryOrderEmailNotify(
			OrderEmailNotifyCriteria oenCriteria, Page page) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.findByCriteria(page)  is running...");
			return orderEmailNotifyDao.findByCriteria(oenCriteria, page);
		}
		return null;
	}

	@Override
	public List<OrderEmailNotify> queryOrderEmailNotify(
			OrderEmailNotifyCriteria oenCriteria) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.queryOrderEmailNotify is running...");
			return orderEmailNotifyDao.queryOrderEmailNotify(oenCriteria);
		}
		return null;
	}

	@Override
	public Integer queryCountOrderEmailNotify(
			OrderEmailNotifyCriteria oenCriteria) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.queryCountOrderEmailNotify is running...");
			return orderEmailNotifyDao.queryCountOrderEmailNotify(oenCriteria);
		}
		return null;
	}


}