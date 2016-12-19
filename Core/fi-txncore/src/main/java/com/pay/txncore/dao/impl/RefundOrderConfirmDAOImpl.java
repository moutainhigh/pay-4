package com.pay.txncore.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.RefundOrderConfirmDAO;
import com.pay.txncore.model.RefundOrderConfirm;

public class RefundOrderConfirmDAOImpl extends BaseDAOImpl implements RefundOrderConfirmDAO {

	/**
	 * 
	 * @param refundConfirmId
	 * @param status
	 * @return
	 */
	public boolean updateRefundOrderConfirmState(Long refundConfirmId, String status){
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("refundConfirmId", refundConfirmId.toString());
		queryParams.put("status", String.valueOf(status));
		return getSqlMapClientTemplate().update(
				"refundOrderConfirm.updateRefundOrderConfirmState", queryParams) == 1;
	}
	
	@Override
	public boolean updateRefundOrderConfirmStateAfter(Long refundConfirmId,
			String status, Date preStartTime) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("refundConfirmId", refundConfirmId.toString());
		queryParams.put("status", status);
		queryParams.put("preStartTime", preStartTime);
		return getSqlMapClientTemplate().update(
				"refundOrderConfirm.updateRefundOrderConfirmStateAfter", queryParams) == 1;
	}

	/**
	 * 根据退款订单号获取退款确认订单
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	public List<RefundOrderConfirm> findByRefundOrderNo(Long refundOrderNo){
		return super.findByCriteria("findByRefundOrderNo", refundOrderNo);
	}

	@Override
	public List<RefundOrderConfirm> queryRefundConfirmList(Map param) {
		List<RefundOrderConfirm> list = getSqlMapClientTemplate()
				.queryForList("refundOrderConfirm.getQueryRefundConfList", param);
		return list;
	}
	
}