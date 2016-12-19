package com.pay.fo.controller.fundout.enforce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fo.order.common.BankAcctType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.enforcewithdraw.EnforceWithdrawService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.dto.enforce.EnforceWithdrawDTO;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneService;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateException;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.util.AmountUtil;
import com.pay.util.StringUtil;

/**
 * 强制提现
 */
public class EnforceWithdrawController extends AbstractBaseController {

	/** 银行服务 **/
	private BankService bankService;
	/** 省份 **/
	private ProvinceService provinceService;
	/** 城市 **/
	private CityService cityService;
	/** lucene搜索查询 **/
	private LuceneService luceneService;
	/** 会员信息 **/
	private MemberQueryService memberQueryService;
	/** 账户信息 **/
	private AccountQueryService accountQueryService;
	/** 银行卡校验 **/
	protected Pay2BankValidateService pay2BankValidateService;
	/** 算费服务 **/
	protected AccountingService accountingFeeService;
	/** 检索出款渠道 **/
	private ConfigBankService configBankService;
	/** 强制提现服务 **/
	private EnforceWithdrawService enforceWithdrawService;

	/**
	 * 默认页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView showBankView = new ModelAndView(URL("confirmWithdraw"));

		// 获取银行列表
		List<Bank> bankList = bankService.getWithdrawBanks();
		showBankView.addObject("bankList", bankList);
		// 获取省份列表
		List<ProvinceDTO> provinceList = provinceService.findAll();
		showBankView.addObject("provinceList", provinceList);

		return showBankView;
	}

	/**
	 * 确认提现
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView confirmWithdraw(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			final EnforceWithdrawDTO enforceWithdrawDTO = new EnforceWithdrawDTO();
			bind(request, enforceWithdrawDTO, "enforceWithdrawDTO", null);
			MemberInfoDto memberInfoDto = memberQueryService
					.doQueryMemberInfoNsTx(enforceWithdrawDTO.getLoginName(),
							null, null, null);
			String memberCode = String.valueOf(memberInfoDto.getMemberCode());
			Integer memberType = memberInfoDto.getMemberType(); // 用户类型
			FundoutOrderDTO fundoutOrderDTO = new FundoutOrderDTO();
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(Long.valueOf(memberCode), 10);

			fundoutOrderDTO.setPayerName(memberInfoDto.getMemberName());
			fundoutOrderDTO.setPayerLoginName(memberInfoDto.getLoginName());
			fundoutOrderDTO.setPayerMemberCode(memberInfoDto.getMemberCode()); // 收款人memberCode
			fundoutOrderDTO
					.setPayerAcctCode(acctAttribDto.getAcctCode() != null ? acctAttribDto
							.getAcctCode().toString() : ""); // 收款人账户号
			fundoutOrderDTO.setPayerMemberType(memberType);// 用户类型
			fundoutOrderDTO.setPayerAcctType(10); // 账户类型,默认为RMB

			fundoutOrderDTO.setPayeeName(enforceWithdrawDTO.getAccHolder()); // 收款人姓名
			fundoutOrderDTO.setPayeeBankCode(enforceWithdrawDTO
					.getBankName());// 银行编号
			fundoutOrderDTO.setPayeeBankName(enforceWithdrawDTO
					.getBankNameStr());// 银行名称
			fundoutOrderDTO.setPayeeBankAcctCode(enforceWithdrawDTO
					.getBankAcct());// 收款人银行卡号
			fundoutOrderDTO.setPayeeBankAcctType(BankAcctType.DebitCard
					.getValue());

			fundoutOrderDTO.setFundoutMode(1);// 出款方式
			long withdrawAmountPerCent = Math
					.round(AmountUtil.formatAmount(Double
							.valueOf(enforceWithdrawDTO.getAmount())) * 1000);
			fundoutOrderDTO.setOrderAmount(Long.valueOf(withdrawAmountPerCent));// 提现金额
			logger.debug("接收到的可提现金额为(厘):" + enforceWithdrawDTO.getAmount());

			// 计算手续费
			if (2 == memberType) {
				double fee = caculateFee(withdrawAmountPerCent, memberCode);// 企业用户，计算手续费
				fundoutOrderDTO.setFee(Math.round(AmountUtil
						.formatAmount(fee * 1000)));// 手续费
				fundoutOrderDTO.setTradeType(1);
			} else {
				fundoutOrderDTO.setFee(0L);// 个人暂时不收手续费,设为0
				fundoutOrderDTO.setTradeType(0);
			}
			fundoutOrderDTO.setPayeeBankCity(String.valueOf(enforceWithdrawDTO
					.getBankCity()));// 城市编号
			fundoutOrderDTO.setPayeeBankCityName(enforceWithdrawDTO
					.getBankCityName()); // 城市名称
			fundoutOrderDTO.setPayeeBankProvince(String
					.valueOf(enforceWithdrawDTO.getBankProvince())); // 省份编号
			fundoutOrderDTO.setPayeeBankProvinceName(enforceWithdrawDTO
					.getBankProvinceName()); // 省份名称
			fundoutOrderDTO.setPaymentReason(enforceWithdrawDTO.getRemark()); // 付款理由、备注
			fundoutOrderDTO.setPayeeOpeningBankName(enforceWithdrawDTO
					.getBankBranch()); // 开户行

			fundoutOrderDTO.setOrderType(OrderType.WITHDRAW.getValue());// 提现
			fundoutOrderDTO.setOrderSmallType(OrderSmallType.FORCE_WITHDRAW
					.getValue());// 订单小类型
			fundoutOrderDTO.setOrderStatus(OrderStatus.INIT.getValue());
			fundoutOrderDTO.setIsPayerPayFee(1);
			fundoutOrderDTO.setRealoutAmount(fundoutOrderDTO.getOrderAmount());
			fundoutOrderDTO.setRealpayAmount(fundoutOrderDTO.getOrderAmount()
					+ fundoutOrderDTO.getFee());
			fundoutOrderDTO.setCreateDate(new Date());
			fundoutOrderDTO.setPriority(5);

			// 存储订单，更新余额并更新订单状态
			enforceWithdrawService.createOrder(fundoutOrderDTO);

			logger.debug("订单成功生成,流水号:" + fundoutOrderDTO.getOrderId());

			return index(request, response).addObject("msg",
					"强制提现申请提交成功，请等待审核!");
		} catch (Exception e) {
			log.error("提现失败", e);
			return index(request, response).addObject("msg", "强制提现申请失败!");
		}
	}

	/**
	 * 提现手续费计算
	 * 
	 * @param command
	 */
	private double caculateFee(Long amount, String memberCode) {
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(amount);
		accountingFeeRe.setPayer(memberCode);
		AccountingFeeRes dealResponse = null;
		try {
			dealResponse = accountingFeeService.caculateFee(accountingFeeRe);
		} catch (Exception e) {
			logger.error("caculate fee error:", e);
		}

		Long fee = dealResponse.getPayerFee();
		if (fee == null) {
			return 0L;
		}
		return fee * 0.001;
	}

	/**
	 * 查询可提现余额
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getAvailableBalances(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sbf = new StringBuffer();

		String loginName = request.getParameter("loginName");
		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				loginName, null, null, null);
		if (null != memberInfoDto) {
			Long memberCode = memberInfoDto.getMemberCode();
			// 获取可提现余额
			BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
					Long.valueOf(memberCode), 10);
			Long availableBalances = balancesDto.getWithdrawBalance();
			sbf.append(availableBalances);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		response.getWriter().print(sbf.toString());
		return null;
	}

	/**
	 * 用户名校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkMemberInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuffer sbf = new StringBuffer();

		String loginName = request.getParameter("loginName");
		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				loginName, null, null, null);
		if (null == memberInfoDto) {
			sbf.append("错误的用户名或用户名不存在!");
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		response.getWriter().print(sbf.toString());
		return null;
	}

	/**
	 * 卡bin校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkBankAcct(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String message = "";
		String bankAcct = StringUtil.null2String(request
				.getParameter("bankAcct"));
		String orgCode = StringUtil
				.null2String(request.getParameter("orgCode"));
		String tradeType = StringUtil.null2String(request
				.getParameter("tradeType"));
		String msgCode = "";

		if (!StringUtil.isNull(bankAcct) && !StringUtil.isNull(orgCode)
				&& !StringUtil.isNull(tradeType)) {
			if (!StringUtil.isNull(tradeType)
					&& "0".equalsIgnoreCase(tradeType)) {
				msgCode = pay2BankValidateService.validateCardBin(bankAcct,
						orgCode);
			}
			if (!StringUtil.isNull(msgCode)) {
				Pay2BankValidateException pay2BankValidateException = Pay2BankValidateException
						.valueof(msgCode);
				message = pay2BankValidateException.getDescription();
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(StringUtil.null2String(message));
		return null;
	}

	/**
	 * 通过省份查找城市
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView city(HttpServletRequest request,
			HttpServletResponse response) {
		int provinceId = StringUtils
				.isEmpty(request.getParameter("provinceId")) ? 0 : Integer
				.parseInt(request.getParameter("provinceId"));
		List<CityDTO> cityList = cityService.findByProvinceId(provinceId);
		return new ModelAndView(URL("city")).addObject("cityList", cityList);
	}

	/**
	 * 查询该银行是否支持提现
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getWithdrawChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targetBankId = request.getParameter("targetBankId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetBankId", targetBankId);// 目的银行编号
		map.put("foMode", "1");// 出款方式:暂时为1手工,以后直连接入在进行修改
		map.put("fobusiness", String.valueOf(0));// 出款业务:提现0
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		String supportWithdraw = "1";
		if (StringUtils.isEmpty(outBankCode)) {
			supportWithdraw = "0";
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(supportWithdraw);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}

	/**
	 * 查询开户行
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryOpeningBankNameList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<SearchResultInfoDTO> results = new ArrayList<SearchResultInfoDTO>();
		StringBuffer sbf = new StringBuffer();
		try {
			String bankCode = request.getParameter("bankOrgCode");
			SearchParamInfoDTO params = new SearchParamInfoDTO();
			params.setResultSize(300);
			params.setBankName(StringUtil.null2String(request.getParameter("b")));
			params.setCityName(StringUtil.null2String(request.getParameter("c")));
			params.setProvinceName(StringUtil.null2String(request
					.getParameter("p")));
			params.setKeyWord(StringUtil.null2String(request.getParameter("k")));
			params.setType(1);
			results = luceneService.searchBankUnionCodes(params);

			for (SearchResultInfoDTO searchResultInfoDTO : results) {
				sbf.append("<option value='"
						+ searchResultInfoDTO.getBankName() + "'>");
				sbf.append(searchResultInfoDTO.getBankName());
				sbf.append("</option>");
			}
		} catch (Exception e) {
			log.error("call luceneService.searchUnionBankCodeInfo faild", e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		response.getWriter().print(sbf.toString());
		return null;
	}

	public void setAccountingFeeService(AccountingService accountingFeeService) {
		this.accountingFeeService = accountingFeeService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public void setEnforceWithdrawService(
			EnforceWithdrawService enforceWithdrawService) {
		this.enforceWithdrawService = enforceWithdrawService;
	}

}
