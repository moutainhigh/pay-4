package com.pay.account.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberBankService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBankDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.account.exception.ErrorExceptionEnum;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fo.order.service.withdraw.WithdrawOrderService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 提现
 * 
 * @author terry
 */
public class WithdrawHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(WithdrawHandler.class);
	private MemberService memberService;
	private MemberBankService memberBankService;
	private BankService bankService;
	private MemberQueryService memberQueryService;
	private AccountQueryService accountQueryService;
	protected WithdrawOrderService withdrawOrderService;
	protected PaymentValidateService paymentValidateService;
	private ProvinceService provinceService;
	private CityService cityService;
	private ConfigBankService configBankService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	public void setMemberBankService(MemberBankService memberBankService) {
		this.memberBankService = memberBankService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String amount = paraMap.get("amount");
		String bankAcct = paraMap.get("bankAcct");

		// 获取用户信息
		MemberDto member = memberService.queryMemberByLoginName(StringUtils
				.trim(StringUtils.lowerCase(loginName)));

		if (member == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}

		// 临时会员提示用户不存在
		if (member.getStatus() == MemberStatusEnum.PROVISIONAL.getCode()) {
			// 用户不存在
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}
		Long memberCode = member.getMemberCode();

		MemberBankDto memberBankDto = memberBankService.doQueryMemberBankNsTx(
				memberCode, DESUtil.encrypt(bankAcct));

		if (null == memberBankDto) {
			result.put("responseCode",
					ErrorExceptionEnum.BANK_ACCT_NOT_EXISTS.getResponseCode());
			result.put("responseDesc",
					ErrorExceptionEnum.BANK_ACCT_NOT_EXISTS.getResponseDesc());
			return JSonUtil.toJSonString(result);
		}

		String bankName = bankService.getBankById(memberBankDto.getBankId());

		// 验证付款方会员状态
		String message = paymentValidateService
				.validatePayerMemberInfo(memberCode);

		// 验证提现收款方基本信息
		if (!StringUtil.isNull(message)) {
			message = paymentValidateService
					.validatePayeeBankAcctInfo(bankName,
							DESUtil.decrypt(memberBankDto.getBankAcctId()),
							memberBankDto.getBankId(),
							OrderType.WITHDRAW.getValue(), 1);
		}

		if (!StringUtil.isEmpty(message)) {
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc", message);
			return JSonUtil.toJSonString(result);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// 目的银行编号
		map.put("targetBankId", memberBankDto.getBankId());
		// 出款方式
		map.put("foMode", 1);
		// 出款业务
		map.put("fobusiness", 0);

		String fundoutBank = configBankService.queryFundOutBank2Withdraw(map);

		if (StringUtil.isNull(fundoutBank)) {
			String errorMsg = "暂不支持" + bankName + "出款";
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc", errorMsg);
			return JSonUtil.toJSonString(result);
		}

		Long withdrawAmount = new BigDecimal(amount).multiply(
				new BigDecimal("10")).longValue();

		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				loginName, memberCode, 1,
				AcctTypeEnum.BASIC_CNY.getCode());

		try {
			
			// 完善订单信息
			FundoutOrderDTO order = buildOrder(withdrawAmount, memberBankDto,
					memberInfoDto,fundoutBank);
			// 存储订单、更新余额、发送消息
			withdrawOrderService.createOrder(order);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("update balance error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(result);
	}

	private String buildFailResponse(Map<String, Object> result,
			ErrorExceptionEnum errorExceptionEnum) {

		result.put("responseCode", errorExceptionEnum.getResponseCode());
		result.put("responseDesc", errorExceptionEnum.getResponseDesc());
		return JSonUtil.toJSonString(result);
	}

	private FundoutOrderDTO buildOrder(Long withdrawAmount,
			MemberBankDto memberBankDto, MemberInfoDto memberInfoDto,String fundoutBank) {
		FundoutOrderDTO order = new FundoutOrderDTO();

		order.setPayerMemberType(memberInfoDto.getMemberType());
		order.setPayerName(memberInfoDto.getMemberName());
		order.setPayeeName(memberInfoDto.getMemberName());
		order.setPayerLoginName(memberInfoDto.getLoginName());
		/*if (memberInfoDto.getMemberType() == MemberType.CORPORATION.getValue()) {
			order.setTradeType(TradeType.TO_BUSINESS.getValue());
		} else {
			order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());
		}*/
		order.setTradeAlias(OrderSmallType.COMMON_WITHDRAW.getDesc());
		order.setPayeeBankAcctCode(DESUtil.decrypt(memberBankDto
				.getBankAcctId()));
		order.setPayeeBankAcctType(1);
		order.setPayeeBankCode(memberBankDto.getBankId());
		
		order.setPayeeBankName(bankService.getBankById(memberBankDto.getBankId()));
		order.setPayeeOpeningBankName(memberBankDto.getBranchBankName());
		order.setUpdateDate(new Date());
		order.setCreateDate(new Date());
		order.setFundoutMode(1);
		order.setTradeAlias("提现");
		order.setFundoutBankCode(fundoutBank);
		order.setPriority(1);
		order.setPayeeBankProvince(memberBankDto.getProvince() + "");
		order.setPayeeBankProvinceName(provinceService.findById(
				memberBankDto.getProvince().intValue()).getProvincename());

		order.setPayeeBankCity(memberBankDto.getCity() + "");
		order.setPayeeBankCityName(cityService.findByCityCode(
				memberBankDto.getCity().intValue()).getCityname());
		// 增加联行号的引入
		order.setBankNumber(memberBankDto.getBranchBankId() + "");
		// 结束修改

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberInfoDto.getMemberCode(),
						AcctTypeEnum.BASIC_CNY.getCode());
		order.setPayerAcctCode(acctAttribDto.getAcctCode());
		order.setPayerAcctType(10);

		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderType(0);
		order.setOrderSmallType("001");
		order.setOrderAmount(withdrawAmount);
		order.setIsPayerPayFee(1);
		order.setFee(0L);
		order.setRealoutAmount(withdrawAmount);
		order.setRealpayAmount(withdrawAmount);
		order.setPayerMemberCode(memberInfoDto.getMemberCode());
		order.setPayerMemberType(memberInfoDto.getMemberType());
		return order;
	}

}
