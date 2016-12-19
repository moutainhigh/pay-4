package com.pay.app.facade.cidverify.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.AccountBalanceService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.AccountLockService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.AccountBalanceChangeDto;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.account.exception.MaAcctLockException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.app.common.cidverify.CidVerifyEnum;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.facade.bankacct.impl.BankAcctServiceFacadeImpl;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;
import com.pay.util.BeanConvertUtil;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-9-15 下午07:12:27
 */
public class CidVerify2GovServiceFacadeImpl implements
		CidVerify2GovServiceFacade {

	private MemberVerifyService memberVerifyService;
	private AccountQueryService accountQueryService;
	private AccountInfoService accountInfoService;
	private AccountLockService accountLockService;
	private AccountBalanceHandlerService accountBalanceHandlerService;
	private AccountBalanceService accountBalanceService;
	private PEService peService;
	private MemberQueryService memberQueryService;

	private static final Log log = LogFactory
			.getLog(BankAcctServiceFacadeImpl.class);
	private static int fail_result = 0;
	private static Integer dealTypeOut = 17; // 公安网退费 交易类型
	private static final int verifySuccess = 1;

	public Map doFeeToGovAccount(String orderId) {
		if (orderId != null && !"".equals(orderId)) {
			Map map = new HashMap();
			CalFeeRequest calFeeRequest = this.setFeeToGovAccount(orderId);
			CalFeeReponse calFeeRespone = this.caculateFee(calFeeRequest);
			if (null != calFeeRespone) {
				boolean dealResult = calFeeRespone.isHasCaculatedPrice();// 计费是否成功
				if (!dealResult) {
					return null;
				}
				CalFeeReponseDto calFeeReponseDto = this
						.setCalFeeReponseDto(calFeeRespone);
				Integer balance_result = this.updateAccountBalance(
						calFeeReponseDto, CidVerifyEnum.dealTypeIn);
				map = this.getCrDrAccount(calFeeReponseDto);
				map.put("updateResult", balance_result);
				map.put("payType", CidVerifyEnum.dealTypeIn);
				return map;
			}
		}
		return null;
	}

	public Map doFeeToBusinessAccount(String orderId) {
		if (orderId != null && !"".equals(orderId)) {
			Map map = new HashMap();
			CalFeeRequest calFeeRequest = this.setSuccessCaculateFee(orderId);
			CalFeeReponse calFeeRespone = this.caculateFee(calFeeRequest);
			if (null != calFeeRespone) {
				boolean dealResult = calFeeRespone.isHasCaculatedPrice();// 计费是否成功
				if (!dealResult) {
					return null;
				}
				CalFeeReponseDto calFeeReponseDto = this
						.setCalFeeReponseDto(calFeeRespone);
				Integer balance_result = this.updateAccountBalance(
						calFeeReponseDto, CidVerifyEnum.dealTypeIn);
				map = this.getCrDrAccount(calFeeReponseDto);
				map.put("updateResult", balance_result);
				map.put("payType", CidVerifyEnum.dealTypeIn);
				return map;
			}
		}
		return null;
	}

	/**
	 * 得到 借、贷 方的账户
	 * 
	 * @author Sunny Ying
	 * @param calFeeReponseDto
	 * @throw null
	 * @return Map
	 */
	public Map getCrDrAccount(CalFeeReponseDto calFeeReponseDto) {
		if (calFeeReponseDto != null) {
			Map map = new HashMap();
			String recvAcct = "";
			String payAcct = "";
			List<CalFeeDetailDto> calFeeDetailDtos = calFeeReponseDto
					.getCalFeeDetailDtos();
			for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
				if (calFeeDetailDto.getCrdr() == CidVerifyEnum.cr) {
					payAcct = calFeeDetailDto.getAcctcode();
				} else {
					recvAcct = calFeeDetailDto.getAcctcode();
				}
			}
			map.put("recvAcct", recvAcct);
			map.put("payAcct", payAcct);
			return map;
		}
		return null;
	}

	public Map refundMember(AcctAttribDto acctAttribDto, String memberCode,
			String orderId) {
		Map map = new HashMap();
		CalFeeRequest calFeeRequest_back = this.setBackCaculateFee(
				acctAttribDto, memberCode, orderId);
		CalFeeReponse calFeeRespone_back = this.caculateFee(calFeeRequest_back);
		if (null != calFeeRespone_back) {
			boolean dealResult = calFeeRespone_back.isHasCaculatedPrice();// 计费是否成功
			if (!dealResult) {
				return null;
			}
			CalFeeReponseDto calFeeReponseDto_back = this
					.setCalFeeReponseDto(calFeeRespone_back);
			Integer balance_result = this.updateAccountBalance(
					calFeeReponseDto_back, dealTypeOut);
			map = this.getCrDrAccount(calFeeReponseDto_back);
			map.put("updateResult", balance_result);
			map.put("payType", dealTypeOut);

			return map;
		}
		return null;
	}

	public CalFeeReponseDto setCalFeeReponseDto(CalFeeReponse calFeeRespone) {
		CalFeeReponseDto calFeeReponseDto = BeanConvertUtil.convert(
				CalFeeReponseDto.class, calFeeRespone);
		CalFeeRequestDto calFeeRequestDto = BeanConvertUtil.convert(
				CalFeeRequestDto.class, calFeeRespone.getCalFeeRequest());
		List<CalFeeDetailDto> calDetailList = new ArrayList<CalFeeDetailDto>();
		for (CalFeeDetail calFeeDetail : calFeeRespone.getCalFeeDetails()) {
			CalFeeDetailDto calFeeDetailDtos = new CalFeeDetailDto();
			calFeeDetailDtos.setAcctcode(calFeeDetail.getAcctcode());
			calFeeDetailDtos.setCrdr(calFeeDetail.getCrdr());
			calFeeDetailDtos.setCreatedate(calFeeDetail.getCreatedate());
			calFeeDetailDtos.setCurrencyCode(calFeeDetail.getCurrencyCode());
			calFeeDetailDtos.setDealId(calFeeDetail.getDealId());
			calFeeDetailDtos.setEntrycode(calFeeDetail.getEntrycode());
			calFeeDetailDtos.setExchangeRate(calFeeDetail.getExchangeRate());
			calFeeDetailDtos.setMaBlanceBy(calFeeDetail.getMaBlanceBy());
			calFeeDetailDtos.setPaymentServiceId(calFeeDetail
					.getPaymentServiceId());
			calFeeDetailDtos.setStatus(calFeeDetail.getStatus());
			calFeeDetailDtos.setText(calFeeDetail.getText());
			calFeeDetailDtos.setTransactiondate(calFeeDetail
					.getTransactiondate());
			calFeeDetailDtos.setValue(calFeeDetail.getValue());
			calFeeDetailDtos.setVouchercode(calFeeDetail.getVouchercode());
			calDetailList.add(calFeeDetailDtos);
		}
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
		calFeeReponseDto.setCalFeeDetailDtos(calDetailList);
		return calFeeReponseDto;
	}

	public CalFeeRequest setFeeToGovAccount(String orderId) {
		Integer payerOrgType = 3; // MessageConvertFactory.getMessage("payerOrgType");//1代表个人
									// 付款方机构类型代码，MEMBER(1), BANK(3),
									// WY(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
		String payerOrgCode = "010010001";// MessageConvertFactory.getMessage("payerOrgCode");//实名认证
											// 最后一级科目编号
		Integer dealCode = 184;// 公安网 成功交易代码
		Integer orderCode = 190;// 公安网 订单代码
		Integer payMethod = 1;// 支付方式
		String acctountId = MessageConvertFactory.getMessage("checkOrgCode");
		String payeeOrgCode = "010010007";

		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setOrderAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);

		calFeeRequest.setPayerOrgCode(payerOrgCode);
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayeeOrgCode(payeeOrgCode);
		calFeeRequest.setPayeeOrgType(payerOrgType);
		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	/**
	 * 接到公安部门反馈验证成功信息，成功计费
	 * 
	 * @author Sunny Ying
	 * @param acctAttribDto
	 * @param memberCode
	 * @param orderId
	 * @throw null
	 * @return CalFeeRequest
	 */
	public CalFeeRequest setSuccessCaculateFee(String orderId) {
		Integer payerOrgType = 3; // MessageConvertFactory.getMessage("payerOrgType");//1代表个人
									// 付款方机构类型代码，MEMBER(1), BANK(3),
									// WY(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
		String payerOrgCode = MessageConvertFactory.getMessage("payerOrgCode");// 实名认证
																				// 最后一级科目编号
		Integer dealCode = 182;// 公安网 成功交易代码
		Integer orderCode = 190;// 公安网 订单代码
		Integer payMethod = 1;// 支付方式

		String acctountId = MessageConvertFactory.getMessage("checkOrgCode");
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setOrderAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);

		calFeeRequest.setPayerOrgCode(payerOrgCode);
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayeeOrgCode(payerOrgCode);
		calFeeRequest.setPayeeOrgType(payerOrgType);
		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	public CalFeeRequest setBackCaculateFee(AcctAttribDto acctAttribDto,
			String memberCode, String orderId) {
		Integer rmb_account = 10;// 10;//人民币账户
		Integer payerOrgType = 1; // MessageConvertFactory.getMessage("payerOrgType");//1代表个人
									// 付款方机构类型代码，MEMBER(1), BANK(3),
									// KQ(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
		String payerOrgCode = MessageConvertFactory.getMessage("payerOrgCode");// 实名认证
																				// 最后一级科目编号
		Integer dealCode = 183;// 公安网 退费交易代码
		Integer orderCode = 190;// 公安网 订单代码
		Integer payMethod = 1;// 支付方式
		String acctCode = acctAttribDto.getAcctCode();
		String acctountId = memberCode + rmb_account;

		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);
		calFeeRequest.setOrderAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setPayerOrgCode(payerOrgCode);
		calFeeRequest.setPayerOrgType(OrgType.BANK.getValue());
		calFeeRequest.setPayee(memberCode);
		calFeeRequest.setPayeeAcctType(rmb_account);
		calFeeRequest.setPayeeOrgType(payerOrgType);
		calFeeRequest.setPayeeMemberAcctCode(acctountId);
		calFeeRequest.setPayeeFullMemberAcctCode(acctCode);
		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	public CalFeeRequest setCaculateFee(AcctAttribDto acctAttribDto,
			String memberCode, String orderId) {

		Integer rmb_account = 10;// 10;//人民币账户
		Integer payerOrgType = 1;// 1代表个人 付款方机构类型代码，MEMBER(1), BANK(3),
									// KQ(4)(如果付款方是会员账户时候，设置为1，如果付款方式为科目的时候，设置为3)
		String payerOrgCode = MessageConvertFactory.getMessage("payerOrgCode");// 实名认证
																				// 最后一级科目编号
		Integer dealCode = 190;// 公安网 交易代码
		Integer orderCode = 190;// 公安网 订单代码
		Integer payMethod = 1;// 支付方式
		String acctCode = acctAttribDto.getAcctCode();
		String acctountId = memberCode + rmb_account;

		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);
		calFeeRequest.setOrderAmount(Long.parseLong(MessageConvertFactory
				.getMessage("pe_amount")));
		calFeeRequest.setPayeeOrgCode(payerOrgCode);
		calFeeRequest.setPayeeOrgType(OrgType.BANK.getValue());
		calFeeRequest.setPayer(memberCode); // 科目无需设置payee
		calFeeRequest.setPayerAcctType(rmb_account);
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayerMemberAcctCode(acctountId); // 科目无需设置账户账号全称
		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setPayerFullMemberAcctCode(acctCode);
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	public boolean doHandlerAccountLockRnTx(String memberCode) {
		AcctTypeEnum acctType = AcctTypeEnum.BASIC_CNY;
		AcctLockTypeEnum acctLockType = AcctLockTypeEnum.FREEZE_OUT;
		try {
			return accountLockService.doHandlerAccountLockRnTx(
					Long.parseLong(memberCode), acctType, acctLockType);
		} catch (MaAcctLockException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl doHandlerAccountLockRnTx throws error:",
					e);
			return false;
		} catch (NumberFormatException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl doHandlerAccountLockRnTx throws error:",
					e);
			return false;
		}

	}

	public MaResultDto getPayPwdResultDto(String memberCode, int accountType,
			String payPassward, Long operatorId) {
		try {
			return accountInfoService.doVerifyPayPassword(
					Long.parseLong(memberCode), accountType, payPassward,
					operatorId);
		} catch (NumberFormatException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl doHandlerAccountLockRnTx throws error:",
					e);
			return null;
		}
	}

	public int getPayPwdLeavingTime(String memberCode, int accountType,
			String payPassward, Long operatorId) {
		int leavingTime = 3;
		MaResultDto maResultDto = this.getPayPwdResultDto(memberCode,
				accountType, payPassward, operatorId);
		if (maResultDto != null) {
			if (maResultDto.getResultStatus() != verifySuccess) {
				VerifyResultDto verifyResultDto = (VerifyResultDto) maResultDto
						.getObject();
				if (verifyResultDto != null) {
					return verifyResultDto.getLeavingTime();
				}
			}
			return leavingTime;
		}
		return 0;
	}

	public CalFeeReponse caculateFee(CalFeeRequest calFeeRequest) {
		CalFeeReponse calFeeReponse = null;// peService.calculateFeeDetail(calFeeRequest);
		return calFeeReponse;
	}

	public MemberInfoDto doQueryMemberInfoNsTx(String loginName) {
		try {
			MemberInfoDto memberInfoDto = memberQueryService
					.doQueryMemberInfoNsTx(loginName, null, null, null);
			return memberInfoDto;
		} catch (MaMemberQueryException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl getAccountAcctAttrib throws error:",
					e);
			return null;
		}

	}

	public AcctAttribDto getAccountAcctAttrib(Long memberCode,
			Integer accountType) {
		try {
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(memberCode, accountType);
			return acctAttribDto;
		} catch (MaAccountQueryUntxException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl getAccountAcctAttrib throws error:",
					e);
			return null;
		}

	}

	public Integer updateAccountBalance(CalFeeReponseDto calFeeReponseDto,
			Integer dealType) {
		Integer update_result = 0;// 初始更新余额结果
		// Integer deal_type= 2;//PAYFOR(2,"即时交易")
		try {
			// accountBalanceService.doUpdateAcctBalanceRntx(accountBalanceChangeDto);
			// CalFeeReponseDto calFeeReponseDto=new CalFeeReponseDto();
			update_result = accountBalanceHandlerService
					.doUpdateAcctBalanceRntx(calFeeReponseDto, dealType);
			return update_result;
		} catch (MaAcctBalanceException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl updateAccountBalance throws error:",
					e);
			return update_result;
		}
	}

	public Integer updateAccountBalance(
			AccountBalanceChangeDto accountBalanceChangeDto) {
		Integer update_result = 0;// 初始更新余额结果
		// Integer deal_type= 2;//PAYFOR(2,"即时交易")
		try {
			update_result = accountBalanceService
					.doUpdateAcctBalanceRntx(accountBalanceChangeDto);
			// CalFeeReponseDto calFeeReponseDto=new CalFeeReponseDto();
			// update_result=accountBalanceHandlerService.doUpdateAcctBalanceRntx(calFeeReponseDto,deal_type);
			return update_result;
		} catch (MaAcctBalanceException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl updateAccountBalance throws error:",
					e);
			return update_result;
		}
	}

	public int checkPayPassword(Long memberCode, Integer accountType,
			String newPayPwd) {
		try {
			return accountInfoService.doVerifyPayPasswordNsTx(memberCode,
					accountType, newPayPwd);
		} catch (MaMemberQueryException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl checkPayPassword throws error:",
					e);
			return 0;
		}
	}

	public boolean checkQueryCidVerify(String membercode) {
		boolean bool = false;
		try {
			bool = memberVerifyService.doQueryRealNameVerifyNsTx(new Long(
					membercode));
		} catch (Exception e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl checkQueryCidVerify throws error:",
					e);
		}
		return bool;
	}

	public Long getAccountBalance(Long memberCode, Integer accountType) {
		try {
			BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
					memberCode, accountType);
			return balancesDto.getBalance();
		} catch (MaAccountQueryUntxException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl getAccountBalance throws error:",
					e);
			return null;
		}
	}

	public boolean getAccountState(Long memberCode, Integer accountType) {
		try {
			boolean result = false;
			int frozen = 0;// 账户冻结状态,1未冻结,0冻结
			int allowOut = 0;// 账户 止出 状态,1未止出,0止出
			int right_account = 1;// 账户状态正常
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(memberCode, accountType);
			frozen = acctAttribDto.getFrozen();
			allowOut = acctAttribDto.getAllowOut();
			if (frozen == right_account) {
				if (allowOut == right_account) {
					result = true;
					return result;
				} else {
					return result;
				}
			} else {
				return result;
			}

		} catch (MaAccountQueryUntxException e) {
			log.error(
					"CidVerify2GovServiceFacadeImpl getAccountState throws error:",
					e);
			return false;
		}

	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

	public void setAccountBalanceService(
			AccountBalanceService accountBalanceService) {
		this.accountBalanceService = accountBalanceService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public AccountLockService getAccountLockService() {
		return accountLockService;
	}

	public void setAccountLockService(AccountLockService accountLockService) {
		this.accountLockService = accountLockService;
	}

}
