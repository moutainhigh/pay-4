package com.pay.txncore.service.chargeback;

import java.util.List;

import com.pay.txncore.model.BouncedFlowVO;

/**
 * 
 * 拒付操作记录表查询
 * @author delin.dong
 * @date  2016年5月28日11:26:49
 */
public interface BouncedFlowService {

	List<BouncedFlowVO> queryBouncedOrders(BouncedFlowVO bouncedFlowVO);

	void batchAddBouncedFlowOrder(List<BouncedFlowVO> bouncedFlowVOs);

}
