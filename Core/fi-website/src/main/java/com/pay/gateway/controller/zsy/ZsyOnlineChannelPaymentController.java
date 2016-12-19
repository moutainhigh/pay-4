package com.pay.gateway.controller.zsy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.service.MemberService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.PaymentChannelItemDto;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.gateway.model.CouponList;
import com.pay.gateway.service.CouponListService;
import com.pay.inf.service.ValidateService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 渠道支付控制组器
 * 
 * @author
 *
 */
public class ZsyOnlineChannelPaymentController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ZsyOnlineChannelPaymentController.class);
	private String alipayView;
	private String weixinView;
	private String cashierView;
	private String successView;
	private String closeView;
	private TxncoreClientService txncoreClientService;
	private ChannelClientService channelClientService;
	private ValidateService validateService;
	private MemberService memberService;
	private CouponListService couponListService;

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map resultMap = new HashMap();

		String orderAmount = request.getParameter("orderAmount");
		String paymentAmount = request.getParameter("paymentAmount");
		String payType = request.getParameter("payType");
		String orderId = request.getParameter("orderId");
		String partnerId = request.getParameter("partnerId");
		String orgCode = request.getParameter("orgCode");
		String payFlag = request.getParameter("payFlag");
		String couponNumber = request.getParameter("couponNumber");

		long couponValue = 0;
		// 判断优惠券
		if (!StringUtil.isEmpty(couponNumber)) {
			String[] str = couponNumber.split(",");
			if (str != null && str.length > 0) {
				for (String s : str) {
					CouponList cou = couponListService.queryCoupon("0000", s);
					if (null != cou) {
						couponValue += cou.getValue();
					}
				}
			}
		}

		PaymentInfo paymentInfo = new PaymentInfo();

		paymentInfo.setTradeOrderNo(orderId);
		paymentInfo.setPartnerId(partnerId);
		paymentInfo.setPayType(payType);
		paymentInfo.setPaymentAmount(new BigDecimal(paymentAmount).multiply(
				new BigDecimal("1000")).longValue()
				+ "");
		paymentInfo.setCouponValue(couponValue + "");
		paymentInfo.setCouponNumber(couponNumber);
		// 调用支付
		Map returnMap = txncoreClientService.channelPayment(paymentInfo);

		// MemberDto memberDto =
		// memberService.queryMemberByMemberCode(Long.valueOf(partnerId));

		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		paymentChannelItem.setOrgCode(orgCode);
		List<Map> list = channelClientService
				.queryChannelItem(paymentChannelItem);
		paymentChannelItem = MapUtil.map2Object(PaymentChannelItemDto.class,
				list.get(0));

		String pageUrl = paymentChannelItem.getFrontCallbackUrl();

		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		Map<String, String> result = (Map) resultMap.get("result");

		if (logger.isInfoEnabled()) {
			logger.info("responseCode:" + responseCode);
			logger.info("responseDesc:" + responseDesc);
			logger.info("returnMap:" + result);
		}

		resultMap.put("orderAmount",
				new BigDecimal(orderAmount).multiply(new BigDecimal(1000)));
		resultMap.put("payType", payType);
		resultMap.put("orderId", orderId);
		resultMap.put("pageUrl", pageUrl);
		resultMap.put("payFlag", payFlag);

		if ("alipay".equals(payType)) {
			return new ModelAndView(alipayView, resultMap);
		} else {
			return new ModelAndView(weixinView, resultMap);
		}

	}

	public ModelAndView result(HttpServletRequest request,
			HttpServletResponse response) {

		String payResult = request.getParameter("PAY_SUCCESS");
		String orderId = request.getParameter("orderId");

		Map<String, String> paymentInfo = txncoreClientService
				.getPaymentedAmount(orderId);
		String orderAmount = paymentInfo.get("orderAmount");
		String paymentedAmount = paymentInfo.get("paymentedAmount");
		String couponValue = paymentInfo.get("couponValue");

		Long payedAmount = 0L;
		if (!StringUtil.isEmpty(couponValue)) {
			Long pAmount = Long.valueOf(paymentedAmount);
			Long cAmount = Long.valueOf(couponValue);
			payedAmount = pAmount + cAmount;
		}

		if (!orderAmount.equals(payedAmount.toString())) {
			Map paraMap = new HashMap();
			paraMap.put("tradeOrderNo", orderId);
			paraMap.put("orderAmount",
					new BigDecimal(orderAmount).divide(new BigDecimal("1000")));
			paraMap.put("paymentedAmount", new BigDecimal(payedAmount)
					.divide(new BigDecimal("1000")));
			paraMap.put(
					"paymentAmount",
					new BigDecimal(orderAmount).subtract(
							new BigDecimal(paymentedAmount)).divide(
							new BigDecimal("1000")));
			return new ModelAndView(cashierView, paraMap);
		}

		ModelAndView view = new ModelAndView(successView);
		return view
				.addObject("payResult", payResult)
				.addObject(
						"orderAmount",
						new BigDecimal(orderAmount).divide(new BigDecimal(
								"1000")))
				.addObject("orderId", orderId)
				.addObject(
						"couponValue",
						new BigDecimal(couponValue).divide(new BigDecimal(
								"1000")))
				.addObject(
						"paymentedAmount",
						new BigDecimal(paymentedAmount).divide(new BigDecimal(
								"1000")));
	}

	public ModelAndView toClose(HttpServletRequest request,
			HttpServletResponse response) {

		String orderId = request.getParameter("orderId");
		String accountNo = request.getParameter("accountNo");

		Map paraMap = new HashMap();

		paraMap.put("orderId", orderId);

		paraMap.put("accountNo", accountNo);

		return new ModelAndView(closeView, paraMap);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getCoupon(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("utf-8");
		String couponNumber = request.getParameter("couponNumber");

		CouponList coupon = new CouponList();
		coupon.setCouponNumber(couponNumber);
		coupon.setStatus(0);
		List<CouponList> list = couponListService.queryCoupon(coupon);
		if (null != list && !list.isEmpty()) {
			coupon = list.get(0);
			String s = coupon.getValue() + "," + coupon.getMinOrderAmount();
			String str = "本券面值" + coupon.getValue() + "元，可用于"
					+ coupon.getMinOrderAmount() + "抵" + coupon.getValue()
					+ "元.";
			response.getWriter().print(s);
		} else {
			response.getWriter().print(0);
		}
		return null;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void setCouponListService(CouponListService couponListService) {
		this.couponListService = couponListService;
	}

	public void setAlipayView(String alipayView) {
		this.alipayView = alipayView;
	}

	public void setWeixinView(String weixinView) {
		this.weixinView = weixinView;
	}

	public void setCashierView(String cashierView) {
		this.cashierView = cashierView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setCloseView(String closeView) {
		this.closeView = closeView;
	}

}