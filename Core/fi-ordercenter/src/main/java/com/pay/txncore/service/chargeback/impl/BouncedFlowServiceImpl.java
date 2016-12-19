package com.pay.txncore.service.chargeback.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.BouncedFlowVO;
import com.pay.txncore.service.chargeback.BouncedFlowService;


/**
 * 
 * 拒付操作记录表查询
 * @author delin.dong
 * @date  2016年5月28日11:26:49
 */
public class BouncedFlowServiceImpl  implements BouncedFlowService{
	
	private BaseDAO bouncedFlowDao;
	
	public void setBouncedFlowDao(BaseDAO bouncedFlowDao) {
		this.bouncedFlowDao = bouncedFlowDao;
	}

	@Override
	public List<BouncedFlowVO> queryBouncedOrders(BouncedFlowVO bouncedFlowVO) {
		return bouncedFlowDao.findByCriteria(bouncedFlowVO);
	}
	/**
	 * 
	 * 拒付操作记录表添加
	 * @author delin.dong
	 * @date  2016年5月28日11:26:49
	 */
	@Override
	public void batchAddBouncedFlowOrder(List<BouncedFlowVO> bouncedFlowVOs) {
	List<Object> ids = bouncedFlowDao.batchCreate("batchAddBouncedFlowOrder",bouncedFlowVOs);
//		Assert.isTrue(bouncedFlowVOs.size() == ids.size());
	}

}
