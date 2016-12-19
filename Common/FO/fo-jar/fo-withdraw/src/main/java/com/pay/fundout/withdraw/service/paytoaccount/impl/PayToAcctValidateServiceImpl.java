/**
 * 
 */
package com.pay.fundout.withdraw.service.paytoaccount.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.withdraw.dao.paytoaccount.PayToAcctDao;
import com.pay.fundout.withdraw.service.paytoaccount.PayToAcctValidateService;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class PayToAcctValidateServiceImpl implements PayToAcctValidateService {

	private PayToAcctDao payToAcctDao;

	private FoRcLimitFacade foRcLimitFacade;

	private MassPayService massPayService;

	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	/**
	 * @param foRcLimitFacade
	 *            the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	public void setPayToAcctDao(PayToAcctDao payToAcctDao) {
		this.payToAcctDao = payToAcctDao;
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		return payToAcctDao.getMonthTotalAmount(memberCode);
	}

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		return payToAcctDao.getDayTotalAmount(memberCode);
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		return payToAcctDao.getMonthTotalCount(memberCode);
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		return payToAcctDao.getDayTotalCount(memberCode);
	}

	@Override
	public String validateRCLimit(Long memberCode, Long applyAmount)
			throws Exception {
		String info = "";

		if (null == applyAmount || applyAmount.longValue() <= 0) {
			return "errorAmount";
		}

		MemberInfoDto member = massPayService.getMemberInfo(null, memberCode,
				null, null);

		RCLimitResultDTO rcLimitResultDTO = null;
		if (member.getMemberType().intValue() == MemberTypeEnum.INDIVIDUL.getCode()) {
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(
					RCLIMITCODE.FO_PAY_PERSONAL_ACC.getKey(), null, memberCode);
		} else {
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(
					RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2E.getKey(), null,
					memberCode);
		}

		if (rcLimitResultDTO == null)
			throw new Exception("未配置风控规则");

		if (rcLimitResultDTO.getSingleLimit() < applyAmount) {
			info = "singleLimit";
		}

		if (StringUtil.isNull(info)) {

			Integer monthTotalCount = payToAcctDao
					.getMonthTotalCount(memberCode);
			Integer dayTotalCount = payToAcctDao.getDayTotalCount(memberCode);
			int monthLimitTimes = rcLimitResultDTO.getMonthTimes()
					- monthTotalCount.intValue();
			int dayLimitTimes = rcLimitResultDTO.getDayTimes()
					- dayTotalCount.intValue();

			if (monthLimitTimes < dayLimitTimes) {
				if (rcLimitResultDTO.getMonthTimes() < monthTotalCount + 1) {
					info = "monthTimes";
				}
			} else {
				if (rcLimitResultDTO.getDayTimes() < dayTotalCount + 1) {
					info = "dayTimes";
				}
			}
		}

		if (StringUtil.isNull(info)) {
			Long monthTotalAmount = payToAcctDao
					.getMonthTotalAmount(memberCode);
			Long dayTotalAmount = payToAcctDao.getDayTotalAmount(memberCode);
			long monthLimitAmount = rcLimitResultDTO.getMonthLimit()
					- monthTotalAmount.longValue();
			long dayLimitAmount = rcLimitResultDTO.getDayLimit()
					- dayTotalAmount.longValue();

			if (monthLimitAmount < dayLimitAmount) {

				if (rcLimitResultDTO.getMonthLimit() < monthTotalAmount
						+ applyAmount) {// 支付金额超出了本月金额限制
					info = "monthLimit";
				}
			} else {

				if (rcLimitResultDTO.getDayLimit() < dayTotalAmount
						+ applyAmount) {// 支付金额超出了今天金额限制
					info = "dayLimit";
				}
			}
		}

		return info;
	}

	@Override
	public String validatePayerInfo(Long memberCode) throws Exception {
		MemberInfoDto member = massPayService.getMemberInfo(null, memberCode,
				null, null);
		return validatePayerInfo(memberCode, member.getStatus());
	}

	@Override
	public String validatePayeeInfo(String loginName, String payerMemberCode)
			throws Exception {
		if (!IDContentUtil.validateEmail(loginName)
				&& !IDContentUtil.validateMobile(loginName)) {
			return "收款方用户名格式不正确";
		}

		MemberInfoDto payeeMemberInfoDto = massPayService.getMemberInfo(
				loginName, null, null, null);
		return validatePayeeInfo(payeeMemberInfoDto, payerMemberCode);

	}

	@Override
	public String validatePayeeInfo(MemberInfoDto payeeMemberInfoDto,
			String payerMemberCode) throws Exception {
		if (payeeMemberInfoDto == null) {
			return "收款方用户名尚未注册";
		}
		// 不能给自己付款
		if (payerMemberCode.equals(String.valueOf(payeeMemberInfoDto
				.getMemberCode()))) {
			return "收款方用户和付款方用户名不能相同";
		}
		String errorMsg = "";
		if (payeeMemberInfoDto.getStatus() != MemberStatusEnum.NORMAL.getCode()) {
			String msg = "";
			if (payeeMemberInfoDto.getStatus() == MemberStatusEnum.DELETE
					.getCode()) {
				msg = MemberStatusEnum.DELETE.getMessage();
			} else if (payeeMemberInfoDto.getStatus() == MemberStatusEnum.FROZEEN
					.getCode()) {
				msg = MemberStatusEnum.FROZEEN.getMessage();
			} else if (payeeMemberInfoDto.getStatus() == MemberStatusEnum.NO_ACTIVE
					.getCode()) {
				msg = MemberStatusEnum.NO_ACTIVE.getMessage();
			}
			if (!StringUtil.isNull(msg)) {
				errorMsg = "收款方会员处于" + msg + "状态,不能收款";
			} else {
				errorMsg = "收款方会员暂不能收款";
			}
		}

		if (StringUtil.isNull(errorMsg)) {
			AcctAttribDto payeeAcctAttribDto = massPayService.getAcctAttrInfo(
					payeeMemberInfoDto.getMemberCode(),
					AcctTypeEnum.BASIC_CNY.getCode());
			if (payeeAcctAttribDto == null) {
				errorMsg = "收款方账户不存在";
			} else if (payeeAcctAttribDto.getFrozen() == 0) {
				errorMsg = "收款方账户已冻结";
			} else if (payeeAcctAttribDto.getAllowIn() == 0
					|| payeeAcctAttribDto.getAllowTransferIn() == 0) {
				errorMsg = "收款方账户止入";
			}
		}

		return errorMsg;
	}

	@Override
	public String validatePayerInfo(Long memberCode, Integer status)
			throws Exception {
		String errorMsg = "";
		if (status != MemberStatusEnum.NORMAL.getCode()) {
			String msg = "";
			if (status == MemberStatusEnum.DELETE.getCode()) {
				msg = MemberStatusEnum.DELETE.getMessage();
			} else if (status == MemberStatusEnum.FROZEEN.getCode()) {
				msg = MemberStatusEnum.FROZEEN.getMessage();
			} else if (status == MemberStatusEnum.NO_ACTIVE.getCode()) {
				msg = MemberStatusEnum.NO_ACTIVE.getMessage();
			}
			if (!StringUtil.isNull(msg)) {
				errorMsg = "您的会员账户处于" + msg + "状态,暂不能付款。如有疑问，请联系客服";

			} else {
				errorMsg = "您的会员账户状态异常，暂不能付款。如有疑问，请联系客服";
			}
		}

		if (StringUtil.isNull(errorMsg)) {
			AcctAttribDto acctAttrib = massPayService.getAcctAttrInfo(
					memberCode, AcctTypeEnum.BASIC_CNY.getCode());
			if (acctAttrib.getFrozen() == 0) {
				errorMsg = "您的账户已被冻结，暂不能付款。如有疑问，请联系客服";
			} else if (acctAttrib.getAllowOut() == 0
					|| acctAttrib.getAllowTransferOut() == 0) {
				errorMsg = "您的账户暂不能付款。如有疑问，请联系客服";
			}

		}

		return errorMsg;
	}

}
