package com.pay.txncore.crosspay.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.IOrderEmailNotifyDAO;
import com.pay.txncore.crosspay.model.OrderEmailNotify;
import com.pay.txncore.crosspay.model.OrderEmailNotifyCriteria;
import com.pay.txncore.crosspay.service.IOrderEmailNotifyService;
/***
 * 
 * 商户下单邮件通知ServiceImpl
 * @author davis.guo at 2016-08-31
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
	public boolean updateFlag(Map<String,Object> paraMap) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.updateFlag is running...");
			int result = orderEmailNotifyDao.updateFlag(paraMap);
			return (result>0?true:false);
		}
		return false;
	}

	@Override
	public boolean updateStatus(Map<String,Object> paraMap) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.updateStatus is running...");
			int result = orderEmailNotifyDao.updateStatus(paraMap);
			return (result>0?true:false);
		}
		return false;
	}

	@Override
	public boolean updateMaxOrderNo(Map<String,Object> paraMap) {
		if(orderEmailNotifyDao != null)
		{
			log.debug("OrderEmailNotifyServiceImpl.updateMaxOrderNo is running...");
			int result = orderEmailNotifyDao.updateMaxOrderNo(paraMap);
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