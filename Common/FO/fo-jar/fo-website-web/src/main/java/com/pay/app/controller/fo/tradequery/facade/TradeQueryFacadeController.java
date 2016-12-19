package com.pay.app.controller.fo.tradequery.facade;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.app.controller.fo.tradequery.BaseTradeQueryController;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;

/**
 * @author lIWEI
 * @Date 2011-4-25
 * @Description 转发外系统交易明细查询请求
 * @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class TradeQueryFacadeController extends BaseTradeQueryController {

	private PayToAcctOrderProcessService payToAcctOrderProcessService;

	// 网关付款交易类型映射
	private static Map<String, Integer> gatewayPayDealTypeMap = new HashMap<String, Integer>();
	static {
//		gatewayPayDealTypeMap.put(String.valueOf(PayForEnum.FI_B2CPAY.getCode()), PayForEnum.FI_B2CPAY.getCode());
//		gatewayPayDealTypeMap.put(String.valueOf(PayForEnum.FI_B2BPAY.getCode()), PayForEnum.FI_B2BPAY.getCode());
//		gatewayPayDealTypeMap.put(String.valueOf(PayForEnum.FI_BIGPAY.getCode()), PayForEnum.FI_BIGPAY.getCode());
//		gatewayPayDealTypeMap.put(String.valueOf(PayForEnum.FI_ACCTPAY.getCode()), PayForEnum.FI_ACCTPAY.getCode());
	}
	
	// 网关充值类型映射
	private static Map<String, Integer> gatewayChargeDealTypeMap = new HashMap<String, Integer>();
	static {
		gatewayChargeDealTypeMap.put(String.valueOf(PayForEnum.B2C_DEAL.getCode()), PayForEnum.B2C_DEAL.getCode());
	}

	private static Map<String, Integer> fundoutPay2BankDealTypeMap = new HashMap<String, Integer>();
	static {
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_PAYTO_BANK.getCode()), PayForEnum.FO_PAYTO_BANK.getCode());
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_PAYTO_BANK_REFUND_TICKET.getCode()),PayForEnum.FO_PAYTO_BANK_REFUND_TICKET.getCode());
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_PAYTO_BANK_REFUNDMENT.getCode()),PayForEnum.FO_PAYTO_BANK_REFUNDMENT.getCode());
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_PAYTO_BANK_REFUND_TICKET.getCode()),PayForEnum.FO_PAYTO_BANK_REFUND_TICKET.getCode());
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_BATACH_PAYTO_BANK_REFUND_TICKET.getCode()),PayForEnum.FO_BATACH_PAYTO_BANK_REFUND_TICKET.getCode());
//		fundoutPay2BankDealTypeMap.put(String.valueOf(PayForEnum.FO_BATACH_PAYTO_BANK_REFUND.getCode()),PayForEnum.FO_BATACH_PAYTO_BANK_REFUND.getCode());
	}

	private static Map<String, Integer> fundoutAcctPayDealTypeMap = new HashMap<String, Integer>();
	static {
//		fundoutAcctPayDealTypeMap.put(String.valueOf(PayForEnum.FO_PAYTO_ACCT.getCode()), PayForEnum.FO_PAYTO_ACCT.getCode());
//		fundoutAcctPayDealTypeMap.put(String.valueOf(PayForEnum.FO_BATACH_PAYTO_ACCT.getCode()),PayForEnum.FO_BATACH_PAYTO_ACCT.getCode());
//		fundoutAcctPayDealTypeMap.put(String.valueOf(PayForEnum.FO_BATACH_PAYTO_ACCT_REFUND.getCode()),PayForEnum.FO_BATACH_PAYTO_ACCT.getCode());
	}

	/*
	 * 单笔付款记录-账户channel=1表示账户
	 * /corp/singlepaydetail.htm?method=querySinglePayDetail
	 * &serialNo=2001104211219006247&channel=1
	 * 
	 * 单笔付款记录-银行卡channel=2表示银行卡
	 * /corp/singlepaydetail.htm?method=querySinglePayDetail
	 * &serialNo=2001104172347000115&channel=2
	 * 
	 * 批量付款到银行卡
	 * /corp/singlepaybatchdetail.htm?method=querySinglePayBatchDetail&channel
	 * =2&batchNum=INIT_122
	 * 
	 * 批量付款到账户
	 * /corp/singlepaybatchdetail.htm?method=querySinglePayBatchDetail&channel
	 * =1&batchNum=INIT_1104221942000106
	 * 
	 * 网关订单 /corp/incomedetail.htm?method=querySingleIncomeDetail&serialNo=
	 * 
	 * 提现 /corp/withdrawOrderQuery.htm?method=detail&dealId=
	 */
	/**
	 * 企业查询转发
	 * 
	 * @param request
	 * @param response
	 * @author LIWEI
	 * @throws Exception
	 */
	public ModelAndView forCorp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = "";
		String busiType = request.getParameter("busiType");
		String serialNo = request.getParameter("serialNo");
		if (String.valueOf("").equals(busiType)
				) {// 单笔付款到账户
			url = "singlepaydetail.htm?method=querySinglePayDetail&serialNo="
					+ serialNo + "&channel=1";
			if (String.valueOf("")
					.equals(busiType)) {
				url += "&refund=1";
			}
		} else if (String.valueOf("")
				.equals(busiType)) {
			PayToAcctOrderDTO Pay2AcctOrder = payToAcctOrderProcessService.getOrder(Long
					.valueOf(serialNo));
			if (null != Pay2AcctOrder) {
				url = "singlepaydetail.htm?method=querySinglePayDetail&serialNo="
						+ serialNo + "&channel=1&flag=0";
			} else {
				url = "singlepaybatchdetail.htm?method=querySinglePayBatchDetail&channel=1&serialNo="
						+ serialNo;
			}
		} else if (null != fundoutPay2BankDealTypeMap.get(busiType)) {// 单笔付款到银行
			url = "singlepaydetail.htm?method=querySinglePayDetail&serialNo="
					+ serialNo + "&channel=2&busiType=" + busiType;
		} else if (null != gatewayPayDealTypeMap.get(busiType)) {
			url = "incomedetail.htm?method=querySingleIncomeDetail&serialNo="
					+ serialNo;
		} else if (null != gatewayChargeDealTypeMap.get(busiType)) {
			url = "chargeOrderQuery.htm?method=detail&dealId=" + serialNo;
		} else if (String.valueOf(PayForEnum.MERCHANT_WITHDRAW_APPLY.getCode()).equals(
				busiType)
				|| String.valueOf(PayForEnum.MERCHANT_WITHDRAW_SUCCESS.getCode())
						.equals(busiType)
				|| String.valueOf(
						PayForEnum.MERCHANT_WITHDRAW_FAIL.getCode()).equals(
						busiType)) {// 提现 提现退款
			url = "withdrawOrderQuery.htm?method=detail&dealId=" + serialNo;
		} else if (String.valueOf("111")
				.equals(busiType)) {
			url = "singlepaybatchdetail.htm?method=querySinglePayBatchDetail&channel=2&serialNo="
					+ serialNo;
		} else {
			return new ModelAndView(errorView).addObject("errorInfo",
					"暂不支持此类交易的查询,请检查交易类型!");
		}
		request.getRequestDispatcher(url).forward(request, response);
		return null;
	}

	/*
	 * 提现:app/singlewithdrawinfodetail.htm?method=querySingleWithdrawDetail&serialNo
	 * =2001104152227000009
	 * /app/singletradeinfodetail.htm?method=querySingleTradeDetail
	 * &serialNo=XXX&busiType=YYY 0:网关 1:付款到账户 2:付款到银行
	 */
	/**
	 * 个人查询转发
	 * 
	 * @param request
	 * @param response
	 * @author LIWEI
	 * @throws Exception
	 */
	public ModelAndView forApp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = "";
		String busiType = request.getParameter("busiType");
		String serialNo = request.getParameter("serialNo");
		if (String.valueOf("").equals(busiType)) {// 单笔付款到账户
			url = "singletradeinfodetail.htm?method=querySingleTradeDetail&serialNo="
					+ serialNo + "&busiType=1";
		} else if (String.valueOf("").equals(
				busiType)) {// 单笔付款到银行
			url = "singletradeinfodetail.htm?method=querySingleTradeDetail&serialNo="
					+ serialNo + "&busiType=2";
		} else if (null != gatewayPayDealTypeMap.get(busiType)) {// 担保交易 即时交易
			url = "singletradeinfodetail.htm?method=querySingleTradeDetail&serialNo="
					+ serialNo + "&busiType=0";
		} else if (String.valueOf("").equals(
				busiType)
				|| String.valueOf("")
						.equals(busiType)) {// 提现 提现退款
			url = "singlewithdrawinfodetail.htm?method=querySingleWithdrawDetail&serialNo="
					+ serialNo;
		} else {
			return new ModelAndView(errorView).addObject("errorInfo",
					"暂不支持此类交易的查询,请检查交易类型!");
		}
		request.getRequestDispatcher(url).forward(request, response);
		return null;
	}

	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

}
