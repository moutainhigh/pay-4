package com.pay.txncore.service.refund.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.txncore.commons.DestTypeEnum;
import com.pay.txncore.dto.AccountInfoValidateDTO;
import com.pay.txncore.dto.VerifyPayPasswordResultDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.service.refund.RefundHelperService;
import com.pay.util.DateStringUtil;

public class RefundHelperServiceImpl implements RefundHelperService {

	private TradeDataSingnatureService tradeSingnatureService;
	private AccountQueryService accountQueryService;
	private AccountInfoService accountInfoService;

	@Override
	public BigDecimal queryBalanceByMemberCode(String memberCode) {
		Long blance = accountQueryService.doQueryBalancesNsTx(
				Long.valueOf(memberCode), AcctTypeEnum.BASIC_CNY.getCode())
				.getBalance();
		return new BigDecimal(blance).divide(new BigDecimal(1000)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public RefundTransactionServiceParamDTO constractRefundParam(
			String orderId, String partner, String remark, String refundAmount,
			String refundType) {

		RefundTransactionServiceParamDTO refundParam = new RefundTransactionServiceParamDTO();
		String strDate = DateStringUtil.date2String(new Date());
		refundParam.setRemark(remark);
		refundParam.setOrderId(orderId);
		refundParam.setRefundAmount(String.valueOf(new BigDecimal(refundAmount)
				.multiply(new BigDecimal(100)).longValue()));
		refundParam.setPartnerId(partner);
		refundParam.setDestType(String.valueOf(DestTypeEnum.REFUND_BACKTRACK
				.getCode()));
		refundParam.setRefundOrderId("PAGE" + strDate);
		refundParam.setRefundTime(strDate);
		refundParam.setNoticeUrl("");
		refundParam.setRefundType(refundType);
		return refundParam;
	}

	public void setTradeSingnatureService(
			TradeDataSingnatureService tradeSingnatureService) {
		this.tradeSingnatureService = tradeSingnatureService;
	}

	@Override
	public boolean validateAccountInfo(AccountInfoValidateDTO accountInfo) {
		// 数据验证
		String memberCode = String.valueOf(accountInfo.getMemberCode());
		Integer acctType = Integer.valueOf(AcctTypeEnum.BASIC_CNY.getCode());

		// 操作员ID
		Long operaterId;
		if (null == accountInfo.getOperaterId()) {
			operaterId = null;
		} else {
			operaterId = Long.valueOf(accountInfo.getOperaterId());
		}
		// 验证支付密码
		VerifyPayPasswordResultDTO verifyDTO = verifyPayPasswordOfLockAccount(
				memberCode, acctType, accountInfo.getPayPassword(), operaterId);

		accountInfo.setValidateMsg(verifyDTO.getErrorMsg());
		accountInfo
				.setValidateResult(verifyDTO.getResultStatus().intValue() == 1 ? true
						: false);
		accountInfo.setTime(verifyDTO.getLeavingTime() == null ? "" : String
				.valueOf(verifyDTO.getLeavingTime()));
		accountInfo.setLeaveTime(verifyDTO.getLeavingMinute() == null ? ""
				: String.valueOf(verifyDTO.getLeavingMinute()));
		return accountInfo.isValidateResult();
	}

	@Override
	public RefundTransactionServiceParamDTO constractRefundParam(
			String orderId, String detailId, String partner, String remark,
			String refundAmount, String refundType, String refundDest) {

		RefundTransactionServiceParamDTO refundParam = new RefundTransactionServiceParamDTO();
		String strDate = DateStringUtil.date2String(new Date());
		refundParam.setRemark(remark);
		refundParam.setOrderId(orderId);
		// 网关退款接口是精确到分的
		refundParam.setRefundAmount(String.valueOf(new BigDecimal(refundAmount)
				.divide(new BigDecimal(10))
				.setScale(0, BigDecimal.ROUND_HALF_UP).toString()));
		refundParam.setPartnerId(partner);
		refundParam.setDestType(refundDest);
		refundParam.setRefundOrderId("BATCH" + detailId);
		refundParam.setRefundTime(strDate);
		refundParam.setNoticeUrl("");
		refundParam.setRefundType(refundType);
		return refundParam;
	}

	@Override
	public RefundTransactionServiceParamDTO constractSplitRefundParam(
			String orderId, String partner, String remark, String refundAmount,
			String refundType) {
		RefundTransactionServiceParamDTO refundParam = new RefundTransactionServiceParamDTO();
		String strDate = DateStringUtil.date2String(new Date());
		refundParam.setRemark(remark);
		refundParam.setOrderId(orderId);
		refundParam.setRefundAmount(String.valueOf(new BigDecimal(refundAmount)
				.divide(new BigDecimal(10))
				.setScale(0, BigDecimal.ROUND_HALF_UP).toString()));
		refundParam.setPartnerId(partner);
		refundParam.setDestType(String.valueOf(DestTypeEnum.REFUND_BACKTRACK
				.getCode()));
		refundParam.setRefundOrderId("Split" + strDate);
		refundParam.setRefundTime(strDate);
		refundParam.setNoticeUrl("");
		refundParam.setRefundType(refundType);
		return refundParam;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	public VerifyPayPasswordResultDTO verifyPayPasswordOfLockAccount(
			String memberCode, Integer accountType, String payPassword,
			Long operatorId) {

		MaResultDto maResultDto = accountInfoService.doVerifyPayPassword(
				new Long(memberCode), accountType, payPassword, operatorId);

		VerifyPayPasswordResultDTO resultDTO = new VerifyPayPasswordResultDTO();

		Object obj = maResultDto.getObject();

		resultDTO.setResultStatus(maResultDto.getResultStatus());
		resultDTO.setErrorCode(maResultDto.getErrorCode());
		resultDTO.setErrorMsg(maResultDto.getErrorMsg());
		if (obj != null) {
			VerifyResultDto verifyResultDto = (VerifyResultDto) maResultDto
					.getObject();
			resultDTO.setTotalTime((long) verifyResultDto.getTotalTime());
			resultDTO.setLeavingTime((long) verifyResultDto.getLeavingTime());
			resultDTO.setLeavingMinute((long) verifyResultDto
					.getLeavingMinute());
		}
		return resultDTO;
	}

}
