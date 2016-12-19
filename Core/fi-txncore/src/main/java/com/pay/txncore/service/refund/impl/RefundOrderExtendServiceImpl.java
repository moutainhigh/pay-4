package com.pay.txncore.service.refund.impl;


import com.pay.txncore.dto.refund.RefundOrderExtendDTO;
import com.pay.txncore.model.RefundOrderExtend;
import com.pay.txncore.dao.RefundOrderExtendDAO;
import com.pay.txncore.dao.RefundFeeOrderDAO;
import com.pay.txncore.dto.refund.RefundOrderStatusChangeLogDTO;
import com.pay.txncore.model.RefundOrderStatusChangeLog;
import com.pay.txncore.dao.RefundOrderStatusChangeLogDAO;
import com.pay.txncore.service.refund.RefundOrderExtendService;
import com.pay.txncore.model.RefundFeeOrder;
import com.pay.inf.dao.BaseDAO;

import com.pay.util.BeanConvertUtil;


public class RefundOrderExtendServiceImpl implements RefundOrderExtendService{
	
	private RefundOrderExtendDAO refundOrderExtendDAO;
	private RefundOrderStatusChangeLogDAO refundOrderStatusChangeLogDAO;
	private RefundFeeOrderDAO refundFeeOrderDAO;
	
	public void setRefundOrderExtendDAO(RefundOrderExtendDAO refundOrderExtendDAO) {
		this.refundOrderExtendDAO = refundOrderExtendDAO;
	}

	public void setRefundOrderStatusChangeLogDAO(RefundOrderStatusChangeLogDAO refundOrderStatusChangeLogDAO) {
		this.refundOrderStatusChangeLogDAO = refundOrderStatusChangeLogDAO;
	}
	
	public void setRefundFeeOrderDAO(RefundFeeOrderDAO refundFeeOrderDAO){
		this.refundFeeOrderDAO = refundFeeOrderDAO;
	}
	
	/**
	 * 插入附属表TradeExtends
	 */
	public Long createRefundOrderExtend(RefundOrderExtendDTO refundOrderExtendDTO){
		RefundOrderExtend refundOrderExtend = BeanConvertUtil.convert(RefundOrderExtend.class,
				refundOrderExtendDTO);
		
		//System.out.println(refundOrderExtend.toString());
		return (Long) refundOrderExtendDAO.create(refundOrderExtend);
	}

	/**
	 * 查询附属表TradeExtends
	 */
	public RefundOrderExtendDTO findByRefundOrderNo(Long refundOrderNo){
		RefundOrderExtend refundOrderExtend = refundOrderExtendDAO
				.findByRefundOrderNo(refundOrderNo);

		return BeanConvertUtil.convert(RefundOrderExtendDTO.class, refundOrderExtend);
	}
	
	public boolean updateRefundOrderExtendCount(RefundOrderExtendDTO refundOrderExtendDTO){
		Long refundExtendNo = refundOrderExtendDTO.getRefundExtendNo();
		Long refundOrderNo = refundOrderExtendDTO.getRefundOrderNo();
		return refundOrderExtendDAO.updateReturnCount(refundExtendNo, refundOrderNo);
	}
	/*
	 * 插入日志记录 
	 */
	public Long createRefundOrderStatusChangeLog(RefundOrderStatusChangeLogDTO refundOrderStatusChangeLogDTO){
		RefundOrderStatusChangeLog refundOrderStatusChangeLog = BeanConvertUtil.convert(RefundOrderStatusChangeLog.class,
				refundOrderStatusChangeLogDTO);
		
		//System.out.println(refundOrderExtend.toString());
		return (Long) refundOrderStatusChangeLogDAO.create(refundOrderStatusChangeLog);
	}
	
	/*
	 * 插入退款手续费订单 
	 */
	public void createRefundFeeOrder(RefundFeeOrder refundFeeOrder){	
		//System.out.println(refundOrderExtend.toString());
		refundFeeOrderDAO.create(refundFeeOrder);
	}
}
