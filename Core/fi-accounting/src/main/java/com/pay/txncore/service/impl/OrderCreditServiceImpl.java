/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppException;
import com.pay.txncore.dao.OrderCreditDAO;
import com.pay.txncore.dto.OrderCreditDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;
import com.pay.txncore.service.OrderCreditService;

/**
 * @author Jiangbo.Peng
 *
 */
@SuppressWarnings({"rawtypes"})
public class OrderCreditServiceImpl implements OrderCreditService {

	private static final Log logger = LogFactory.getLog(OrderCreditServiceImpl.class) ;
	
	//注入
	private OrderCreditDAO orderCreditDAO ;
	
	/**
	 * @param orderCreditDAO the orderCreditDAO to set
	 */
	public void setOrderCreditDAO(OrderCreditDAO orderCreditDAO) {
		this.orderCreditDAO = orderCreditDAO;
	}

	@Override
	public Long createOrderCreidtDetail(OrderCreditDTO orderCreditDTO) {
		return this.orderCreditDAO.createOrderCreidtDetail(orderCreditDTO) ;
	}

	@Override
	public List<Object> createOrderCreditDetailBatch(List<OrderCreditDetailDTO> ocs) {
		return this.orderCreditDAO.createOrderCreditDetailBatch(ocs) ;
	}

	@Override
	public void createOrder(OrderCreditDTO oc,
			List<OrderCreditDetailDTO> ocds) {
		try {
			//创建融资订单批次
			Long orderCreditId = this.orderCreditDAO.createOrderCredit(oc) ;
			//融资订单详情
			if(null != orderCreditId){
				for(OrderCreditDetailDTO ocd : ocds){
					ocd.setCreditId(orderCreditId);
				}
				List<Object> createOrderCreditDetailBatch = this.orderCreditDAO.createOrderCreditDetailBatch(ocds) ;
				logger.info("融资订单详情保存，所有详情id:" + createOrderCreditDetailBatch.toString());
			}
		} catch (Exception e) {
			//logger.info("创建融资订单及融资订单详情失败。。" + e);
			e.printStackTrace();
			//事务回滚
			throw new AppException("创建融资订单及融资订单详情失败") ;
		}
		
		
	}

	@Override
	public Long createOrderCredit(OrderCreditDTO oc) {
		return this.orderCreditDAO.createOrderCredit(oc) ;
	}

	@Override
	public List<?> findByCriteria(String sqlId, Object criteria) {
		return this.orderCreditDAO.findByCriteria(sqlId, criteria) ;
	}

	@Override
	public List<?> findByCriteria(String sqlId, Object criteria, Page page) {
		return this.orderCreditDAO.findByCriteria(sqlId, criteria, page) ;
	}

	@Override
	public boolean updateDetail(OrderCreditDetailDTO orderCreditDetailDTO) {
		return this.orderCreditDAO.update("updateDetail", orderCreditDetailDTO);
		
	}


}
