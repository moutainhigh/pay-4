package com.pay.poss.refund.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkorder;
import com.pay.poss.refund.service.RefundOrderService;

public class RefundOrderServiceImpl implements RefundOrderService {

	private final Log logger = LogFactory.getLog(getClass());
	private transient BaseDAO baseDao; // 处理数据库操作的基础DAO

	@Override
	public Long saveRefundMRnTx(RefundOrderM mDto) {
		long acceptKy = (Long) baseDao.create(
				RefundConstants.CREATE_REFUNDORDERM, mDto);
		return acceptKy;
	}

	@Override
	public Long saveRefundDRnTx(RefundOrderD dDto) {
		long detailKy = (Long) baseDao.create(
				RefundConstants.CREATE_REFUNDORDERD, dDto);
		return detailKy;
	}

	@Override
	public Long saveRefundWorkorderRnTx(RefundWorkorder workOrder) {
		Long workorderKy = (Long) baseDao.create(
				RefundConstants.CREATE_REFUNDWORKORDER, workOrder);
		return workorderKy;
	}

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public boolean updateRefundMStatusRnTx(Long orderKy, Integer status) {
		RefundOrderM mDto = new RefundOrderM();
		mDto.setOrderKy(orderKy);
		mDto.setStatus(status);
		return baseDao.update(RefundConstants.UPDATE_REFUND_ORDER_M_STATUS,
				mDto);
	}

	@Override
	public boolean updateRefundDStatusRnTx(Long orderKy, Integer status) {
		RefundOrderD dDto = new RefundOrderD();
		dDto.setDetailKy(orderKy);
		dDto.setStatus(status);
		return baseDao.update(RefundConstants.UPDATE_REFUND_ORDER_D_STATUS,
				dDto);
	}

	@Override
	public boolean updateWorkorderStatusRnTx(Long workorderKy, Integer status) {

		Map paraMap = new HashMap();
		paraMap.put("workOrderKy", workorderKy);
		paraMap.put("status", status);
		return baseDao.update(RefundConstants.UPDATE_REFUND_WORK_ORDER_STATUS,
				paraMap);
	}
}
