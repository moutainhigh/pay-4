package com.pay.txncore.service.refund.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.commons.ResultCode;
import com.pay.txncore.dao.RefundApplyDAO;
import com.pay.txncore.dto.refund.RefundApplyDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.model.RefundApply;
import com.pay.txncore.service.refund.RefundApplyService;
import com.pay.txncore.service.refund.RefundHelperService;
import com.pay.util.DateUtil;
import com.pay.util.StringFormatUtil;
import com.pay.util.StringUtil;

public class RefundApplyServiceImpl implements RefundApplyService {

	private RefundApplyDAO refundApplyDAO;
	private RefundHelperService refundHelperService;

	private Log logger = LogFactory.getLog(RefundApplyServiceImpl.class);

	@Override
	public boolean auditRefundApply(RefundApplyDTO refundApplyDTO,
			String requestContext) {

		logger.info("@前台退款通过申请,开始执行退款。refundApplyDTO=>" + refundApplyDTO);
		RefundTransactionServiceParamDTO refundParamDTO = refundHelperService
				.constractRefundParam(refundApplyDTO.getOrderId(),
						refundApplyDTO.getPartnerId(),
						refundApplyDTO.getReason(),
						refundApplyDTO.getApplyAmount(),
						refundApplyDTO.getRefundType());
		RefundTransactionServiceResultDTO refundResultDTO = null;
		//refundResultDTO = refundService.refund(refundParamDTO);

		logger.info("@前台退款通过申请,退款结束。refundResultDTO=>" + refundResultDTO);

		String stateCode = refundResultDTO.getStateCode();

		if (refundResultDTO.getResultCode()
				.equals(ResultCode.SUCCESS.getCode())) {
			refundApplyDTO.setMessage(ResultCode.SUCCESS.getDescription());

			RefundApply applyModel = new RefundApply();
			if (!StringUtil.isEmpty(refundResultDTO.getRefundNo())) {
				applyModel.setRefundOrderNo(Long.valueOf(refundResultDTO
						.getRefundNo()));
			} else {
				applyModel.setRefundOrderNo(0L);
			}
			applyModel.setStatus(3);
			applyModel.setActualAmount(new BigDecimal(refundResultDTO
					.getRefundAmount()).divide(new BigDecimal(10))
					.setScale(2, BigDecimal.ROUND_HALF_UP).longValue());
			applyModel.setRefundApplyNo(refundApplyDTO.getRefundApplyNo());
			applyModel.setAuditOperator(refundApplyDTO.getAuditOperator());
			applyModel.setRemark2(refundApplyDTO.getRemark2());

			boolean bret = false;
			bret = refundApplyDAO.modifyApply(applyModel);

			logger.info("@前台退款通过申请,更新退款申请表结束。RESULT:" + bret);
			return bret;

		} else {
			if (refundResultDTO.getResultCode().equals(
					ResultCode.SUCCESS.getCode())
					&& stateCode.equals(String.valueOf(RefundStatusEnum.FAIL
							.getCode()))) {
				refundApplyDTO.setMessage("退款异常!");
				refundApplyDTO.setRemark2(String.valueOf(RefundStatusEnum.FAIL
						.getCode()));
			} else {
				refundApplyDTO.setStateCode(refundResultDTO.getResultCode());
				refundApplyDTO.setMessage(ResultCode
						.getDescription(refundResultDTO.getResultCode()));
				refundApplyDTO.setRemark2(refundResultDTO.getResultCode());
			}
			return false;
		}

	}

	@Override
	public boolean refusalRefundApply(RefundApplyDTO refundApplyDTO) {
		logger.info("@前台退款执行拒绝申请,refundApplyNo:"
				+ refundApplyDTO.getRefundApplyNo());

		RefundApply applyModel = new RefundApply();
		applyModel.setStatus(2);
		applyModel.setRefundApplyNo(refundApplyDTO.getRefundApplyNo());
		applyModel.setAuditOperator(refundApplyDTO.getAuditOperator());
		applyModel.setRemark2(refundApplyDTO.getRemark2());

		boolean bret = false;
		bret = refundApplyDAO.modifyApply(applyModel);
		logger.info("@前台执行退款拒绝申请结束,RESULT:" + bret);
		return bret;
	}

	@Override
	public Long submitRefundApply(RefundApplyDTO refundApplyDTO)
			throws Exception {
		RefundApply modelRefundApply = new RefundApply();
		modelRefundApply.setApplyAmount(new BigDecimal(refundApplyDTO
				.getApplyAmount()).multiply(new BigDecimal(1000)).longValue());
		modelRefundApply.setActualAmount(0L);
		modelRefundApply.setCreator(refundApplyDTO.getCreator());

		modelRefundApply.setOrderAmount(new BigDecimal(refundApplyDTO
				.getOrderAmount()).multiply(new BigDecimal(1000)).longValue());
		modelRefundApply.setOrderId(refundApplyDTO.getOrderId());
		modelRefundApply.setPartnerId(refundApplyDTO.getPartnerId());
		modelRefundApply.setReason(refundApplyDTO.getReason());
		modelRefundApply.setRefundType(refundApplyDTO.getRefundType());
		modelRefundApply.setStatus(1); // 新建
		modelRefundApply.setRemark1(refundApplyDTO.getRemark1());
		modelRefundApply.setRemark2(refundApplyDTO.getRemark2());

		try {
			return (Long) refundApplyDAO.create(modelRefundApply);
		} catch (Exception ex) {
			logger.error(StringFormatUtil.format(
					"@退款-创建退款申请异常,订单号:{0},商户编号:{1},退款金额:{2},错误消息:{3}",
					refundApplyDTO.getOrderId(), refundApplyDTO.getPartnerId(),
					refundApplyDTO.getApplyAmount(), ex));
			throw ex;
		}

	}

	@Override
	public RefundApplyDTO queryRefundApply(Long refundApplyNo) {
		logger.info("@退款申请-查询退款申请,REFUND_APPLY_NO:" + refundApplyNo);
		RefundApply refundApply = refundApplyDAO.findById(refundApplyNo);
		if (refundApply == null)
			return null;
		RefundApplyDTO refundApplyDTO = new RefundApplyDTO();

		refundApplyDTO.setApplyBatchNo(refundApply.getApplyBatchNo());
		refundApplyDTO.setAuditOperator(refundApply.getAuditOperator());
		refundApplyDTO.setCreateDate(DateUtil.dateToStr(
				refundApply.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
		refundApplyDTO.setUpdateDate(DateUtil.dateToStr(
				refundApply.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
		refundApplyDTO.setCreator(refundApply.getCreator());
		refundApplyDTO.setOrderAmount(new BigDecimal(refundApply
				.getOrderAmount()).divide(new BigDecimal(1000))
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		refundApplyDTO.setActualAmount(new BigDecimal(refundApply
				.getActualAmount()).divide(new BigDecimal(1000))
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		refundApplyDTO.setApplyAmount(new BigDecimal(refundApply
				.getApplyAmount()).divide(new BigDecimal(1000))
				.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		refundApplyDTO.setOrderId(refundApply.getOrderId());
		refundApplyDTO.setPartnerId(refundApply.getPartnerId());
		refundApplyDTO.setReason(refundApply.getReason());
		refundApplyDTO.setRefundApplyNo(refundApply.getRefundApplyNo());
		refundApplyDTO.setRefundOrderNo(refundApply.getRefundOrderNo());
		refundApplyDTO.setRefundType(refundApply.getRefundType());
		refundApplyDTO.setStatus(refundApply.getStatus());
		return refundApplyDTO;
	}

	public void setRefundApplyDAO(RefundApplyDAO refundApplyDAO) {
		this.refundApplyDAO = refundApplyDAO;
	}

	public void setRefundHelperService(RefundHelperService refundHelperService) {
		this.refundHelperService = refundHelperService;
	}

}
