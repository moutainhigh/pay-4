package com.pay.txncore.service.refund.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.commons.RefundConfirmStatusEnum;
import com.pay.txncore.dao.RefundOrderConfirmDAO;
import com.pay.txncore.dto.refund.RefundOrderConfirmDTO;
import com.pay.txncore.model.RefundOrderConfirm;
import com.pay.txncore.service.refund.RefundOrderConfirmService;
import com.pay.util.BeanConvertUtil;

/**
 * @author LIBO 退款确认订单请求
 */
public class RefundOrderConfirmServiceImpl implements RefundOrderConfirmService {

	private final Log logger = LogFactory.getLog(getClass());
	private RefundOrderConfirmDAO refundOrderConfirmDAO;
	
	public void setRefundOrderConfirmDAO(RefundOrderConfirmDAO refundOrderConfirmDAO) {
		this.refundOrderConfirmDAO = refundOrderConfirmDAO;
	}
	
	@Override
	public boolean updateRefundOrderState(Long refundConfirmId, 
			RefundConfirmStatusEnum state) {
		return refundOrderConfirmDAO.updateRefundOrderConfirmState(refundConfirmId,
				String.valueOf( state.getCode()));
	}
	@Override
	public boolean updateRefundOrderStateAfter(Long refundConfirmId, 
			RefundConfirmStatusEnum state, Date preStartTime) {
		return refundOrderConfirmDAO.updateRefundOrderConfirmStateAfter(refundConfirmId,
				String.valueOf( state.getCode()), preStartTime);
	}
	@Override
	public Long create(RefundOrderConfirmDTO refundOrderConfirmDTO) {
		RefundOrderConfirm refundConfirmModel = 
				BeanConvertUtil.convert(RefundOrderConfirm.class,
				refundOrderConfirmDTO);
		return (Long) refundOrderConfirmDAO.create(refundConfirmModel);
	}
	@Override
	public RefundOrderConfirmDTO queryByPk(Long id) {
		RefundOrderConfirm refundOrderConfirm = 
				(RefundOrderConfirm) refundOrderConfirmDAO.findById(id);
		return BeanConvertUtil.convert(RefundOrderConfirmDTO.class, refundOrderConfirm);
	}
	@Override
	public boolean updateRefundOrderConfirmByPk(
			RefundOrderConfirmDTO refundOrderConfirmDTO) {
		RefundOrderConfirm refundOrderConfirm = null;
		if (refundOrderConfirmDTO == null)
			return false;
		refundOrderConfirm = BeanConvertUtil
				.convert(RefundOrderConfirm.class, refundOrderConfirmDTO);
		return refundOrderConfirmDAO.update(refundOrderConfirm);
	}
	@Override
	public List<RefundOrderConfirmDTO> queryConfirmRefundList(Date currentDate) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("preEndTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate));
		return (List<RefundOrderConfirmDTO>) BeanConvertUtil.convert(
				RefundOrderConfirmDTO.class,
				refundOrderConfirmDAO.queryRefundConfirmList(param));
	}
	@Override
	public List<RefundOrderConfirmDTO> findByRefundOrderNo(Long refundOrderNo) {
		return (List<RefundOrderConfirmDTO>) BeanConvertUtil.convert(
				RefundOrderConfirmDTO.class,
				refundOrderConfirmDAO.findByRefundOrderNo(refundOrderNo));
	}

}
