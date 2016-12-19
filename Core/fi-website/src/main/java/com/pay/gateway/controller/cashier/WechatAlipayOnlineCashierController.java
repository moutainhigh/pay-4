package com.pay.gateway.controller.cashier;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.commons.HTTPProtocolHandleUtil;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.client.OrderClientService;

/**
 * 在线收银台控制器
 * @author JiangboPeng 
 * 
 */
public class WechatAlipayOnlineCashierController extends MultiActionController {
	
	private final Log logger = LogFactory.getLog(WechatAlipayOnlineCashierController.class);
	
	private OrderClientService orderClientService ;
	
	//微信扫码支付
	private String wechatCodeImageView ;
	//微信WAP支付
	private String wechatWapView ;
	//支付宝扫码支付
	private String aliCodeImageView ;
	//支付宝统一下单支付(js)
	private String aliWapView ;
	
	
	/**
	 * @param orderClientService the orderClientService to set
	 */
	public void setOrderClientService(OrderClientService orderClientService) {
		this.orderClientService = orderClientService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("WechatAlipayOnlineCashierController->method index start...");
		ModelAndView view = new ModelAndView() ;
		// 设置请求回应的字符编码UTF-8，清缓存
		HTTPProtocolHandleUtil.setAll(request, response);
		String tradeType = request.getParameter("tradeType"); 
		String tradeOrderNo = request.getParameter("tradeOrderNo") ;
		String codeImgUrl = request.getParameter("codeImgUrl") ;
		String payInfo = request.getParameter("payInfo") ;
	    String payUrl = request.getParameter("payUrl");
	    String outTradeNo = request.getParameter("outTradeNo") ;
	    String totalFee = request.getParameter("totalFee") ;
	    String body = request.getParameter("body");
	    view.addObject("tradeOrderNo", tradeOrderNo) ;
		view.addObject("codeImgUrl", codeImgUrl) ;
		view.addObject("payInfo", payInfo) ;
		view.addObject("payUrl", payUrl) ;
		view.addObject("outTradeNo", outTradeNo) ;
		view.addObject("totalFee", totalFee) ;
		view.addObject("body", body) ;
		setViewName(view, tradeType);
		return view ;
	}

	/**
	 * @param view
	 * @param tradeType
	 */
	private void setViewName(ModelAndView view, String tradeType) {
		if(TradeTypeEnum.WFT_WEIXIN_SM.getCode().equals(tradeType)){
			view.setViewName(this.wechatCodeImageView);
		}else if(TradeTypeEnum.WFT_ALIPAY_SM.getCode().equals(tradeType)){
			view.setViewName(this.aliCodeImageView);
		}else if(TradeTypeEnum.WFT_WEIXIN_WAP.getCode().equals(tradeType)){
			view.setViewName(this.wechatWapView);
		}else if(TradeTypeEnum.WFT_ALIPAY_WAP.getCode().equals(tradeType)){
			view.setViewName(this.aliWapView);
		}
	}

	/**
	 * 网关订单状态查询，是否支付成功
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView payResultQuery(HttpServletRequest request, HttpServletResponse response){
		String tradeOrderNo = request.getParameter("tradeOrderNo") ;
		String res = "process" ;
		PrintWriter writer = null ;
		try {
			writer = response.getWriter() ;
			if(StringUtils.isEmpty(tradeOrderNo) || !NumberUtils.isNumber(tradeOrderNo)){
				writer.write(res);
				return null ;
			}
			Map<String, Object> paraMap = new HashMap<String, Object>() ;
			paraMap.put("tradeOrderNo", tradeOrderNo) ;
			Map<String, Object> resultMap = this.orderClientService.queryTradeOrderById(paraMap) ;
			
			if(null != resultMap){
				Map<String, Object> result = (Map<String, Object>) resultMap.get("result") ;
				String status = result.get("status")+"" ;
				if("4".equals(status)){//支付成功
					res = "success" ;
				}else if("5".equals(status)){//支付失败
					res = "fail" ;
				}
			}
			writer.write(res);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != writer){
				writer.flush();
				writer.close();
			}
		}
		return null ;
	}
	
	/**
	 * 扫码支付支付成功跳转
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView codeImageaPaySuccess(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechatAlipay/pay_success") ;
	}
	
	/**
	 * 扫码支付支付失败跳转
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView codeImageaPayFaiil(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechatAlipay/pay_fail") ;
	}

	/**
	 * @param wechatCodeImageView the wechatCodeImageView to set
	 */
	public void setWechatCodeImageView(String wechatCodeImageView) {
		this.wechatCodeImageView = wechatCodeImageView;
	}

	/**
	 * @param wechatWapView the wechatWapView to set
	 */
	public void setWechatWapView(String wechatWapView) {
		this.wechatWapView = wechatWapView;
	}

	/**
	 * @param aliCodeImageView the aliCodeImageView to set
	 */
	public void setAliCodeImageView(String aliCodeImageView) {
		this.aliCodeImageView = aliCodeImageView;
	}

	/**
	 * @param aliWapView the aliWapView to set
	 */
	public void setAliWapView(String aliWapView) {
		this.aliWapView = aliWapView;
	}
	
	
	
}