/**
 * 
 */
package com.pay.fi;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.TxncoreClientService;
import com.pay.base.fi.model.PartnerConfig;
import com.pay.base.fi.service.PartnerConfigService;
import com.pay.cardbin.model.Response;
import com.pay.cardbin.util.HttpUtils;
import com.pay.fi.commons.RequestVersionEnum;
import com.pay.fi.dto.CrossPayApiResponse;
import com.pay.fi.dto.CrosspayApiRequest;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.util.BankCardCorrectValidUtil;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;
import com.pay.util.XMLUtil;
import com.pay.util.security.MD5BaseAlgorithms;

/**
 * VCC交易
 * @author PengJiangbo
 *
 */
public class VccMgrController extends MultiActionController {
	
	private static final Log logger = LogFactory.getLog(VccMgrController.class) ;
	private static final String PAY_KEY_NULL_TIP = "密钥为空,请前去安全中心下载md5密钥!" ;
	private String vccPayAction ;
	private String vccPayView ;
	
	private PartnerConfigService partnerConfigService ;
	private TxncoreClientService txncoreClientService ;
	
	/**
	 * vcc支付首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(this.vccPayView) ;
		CrosspayApiRequest crosspayApiRequest = new CrosspayApiRequest() ;
		this.constuctDefaultViewData(mv, crosspayApiRequest);
		this.constructView(mv, crosspayApiRequest, null) ;
		return mv.addObject("suba", "suba")
				;
	}
	
	/**
	 * 生产MD5签名串
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView genSignMsg(HttpServletRequest request, HttpServletResponse response, CrosspayApiRequest crosspayApiRequest){
		ModelAndView view = new ModelAndView(this.vccPayView) ;
		String ip2 = WebUtil.getIp() ;
		crosspayApiRequest.setCustomerIP(ip2);
		this.crosspayApiRequestValid(crosspayApiRequest);
		//处理交易金额， 此处*100转成分送往网关VCC支付接口
		String orderAmount = crosspayApiRequest.getOrderAmount() ;
		orderAmount = new BigDecimal(orderAmount).multiply(new BigDecimal(100)).longValue()+"" ;
		crosspayApiRequest.setOrderAmount(orderAmount);
		String signData = crosspayApiRequest.generateSign() ;
		if(logger.isInfoEnabled()){
			logger.info("mps商户控台signData:" + signData);
		}
		String pkey = this.getPkey();
		String genSignDataS = "N"  ;
		String signMsg = "" ;
		if(StringUtils.isEmpty(pkey)){
			view.addObject("pkeynull", PAY_KEY_NULL_TIP) ;
		}else{
			signData += "&pkey=" + pkey ;
			signMsg = MD5BaseAlgorithms.getMD5Str(signData) ;
			if(StringUtils.isNotEmpty(signData)){
				genSignDataS = "Y" ;
			}
		}
		this.constuctDefaultViewData(view, crosspayApiRequest);
		this.constructView(view, crosspayApiRequest, "process") ;
		return view.addObject("suba", "").addObject("genSignDataS", genSignDataS)
				.addObject("signMsg", signMsg)
				.addObject("signData", signData)
				.addObject("action", this.vccPayAction) ;
	}
	
	/**
	 * 部分参数按接口规范检查处理
	 * @param crosspayApiRequest
	 */
	private void crosspayApiRequestValid(CrosspayApiRequest crosspayApiRequest){
		if(null != crosspayApiRequest){
			String goodsName = crosspayApiRequest.getGoodsName() ;
			String goodsDesc = crosspayApiRequest.getGoodsDesc() ;
			String orderId = crosspayApiRequest.getOrderId() ;
			String cardHolderFirstName = crosspayApiRequest.getCardHolderFirstName() ;
			String cardHolderLastName = crosspayApiRequest.getCardHolderLastName() ;
			strLenValid(crosspayApiRequest, "goodsName", goodsName, 256);
			strLenValid(crosspayApiRequest, "goodsDesc", goodsDesc, 2000);
			strLenValid(crosspayApiRequest, "orderId", orderId, 32);
			strLenValid(crosspayApiRequest, "cardHolderFirstName", cardHolderFirstName, 128);
			strLenValid(crosspayApiRequest, "cardHolderLastName", cardHolderLastName, 100);
		}
		
	}

	/**
	 * 
	 * @param crosspayApiRequest
	 * @param goodsName
	 */
	private void strLenValid(CrosspayApiRequest crosspayApiRequest,
			String decalaredField, String str, int len) {
		if(StringUtils.isNotEmpty(str)){
			if(str.length() > len){
				str = str.substring(0, len) ;
				crosspayApiRequest.setGoodsName(str);
				try {
					//Class clazz = Class.forName("com.pay.fi.dto.CrosspayApiRequest") ;
					Class<CrosspayApiRequest> clazz = CrosspayApiRequest.class ;
					Object obj = clazz.newInstance() ;
					Field field = clazz.getDeclaredField(decalaredField) ;
					field.setAccessible(true);
					field.set(obj, str);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * 构建VCC虚拟信用卡下单页面默认数据
	 * @param view
	 * @param crosspayApiRequest
	 */
	private void constuctDefaultViewData(ModelAndView view, CrosspayApiRequest crosspayApiRequest){
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		CurrencyCodeEnum[] currencyCodes = CurrencyCodeEnum.values() ;
		view.addObject("version", RequestVersionEnum.ONLINE_1_0.getCode())
			.addObject("partnerId", memberCode)
			.addObject("currencyCodes", currencyCodes)
			;
		
	}
	/**
	 * 构建VCC虚拟信用卡下单页面需要回显数据view
	 * @param view
	 * @param crosspayApiRequest
	 * @return
	 */
	private void constructView(ModelAndView view, CrosspayApiRequest crosspayApiRequest, String processAmount){
		//处理orderAmount, orderAmount回显时需要除以100，转成元显示
		String orderAmount = crosspayApiRequest.getOrderAmount() ;
		if(StringUtils.isNotEmpty(processAmount)){
			if(StringUtils.isNotEmpty(orderAmount)){
				orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal(100)).toString() ;
			}
		}
		view.addObject("submitTime", crosspayApiRequest.getSubmitTime())
		.addObject("goodsName", crosspayApiRequest.getGoodsName())
		.addObject("goodsDesc", crosspayApiRequest.getGoodsDesc())
		.addObject("siteId", crosspayApiRequest.getSiteId())
		.addObject("orderId", crosspayApiRequest.getOrderId())
		.addObject("orderAmount", orderAmount) //crosspayApiRequest.getOrderAmount()
		.addObject("currencyCode", crosspayApiRequest.getCurrencyCode())
		.addObject("cardHolderNumber", crosspayApiRequest.getCardHolderNumber())
		.addObject("cardHolderFirstName", crosspayApiRequest.getCardHolderFirstName())
		.addObject("cardHolderLastName", crosspayApiRequest.getCardHolderLastName())
		.addObject("cardExpirationMonth", crosspayApiRequest.getCardExpirationMonth())
		.addObject("cardExpirationYear", crosspayApiRequest.getCardExpirationYear())
		.addObject("securityCode", crosspayApiRequest.getSecurityCode())
		.addObject("submitTime", crosspayApiRequest.getSubmitTime())
		.addObject("customerIP", crosspayApiRequest.getCustomerIP())
		.addObject("action", this.vccPayAction) ;
		;
	}
	/**
	 * 获取pkey
	 * @return
	 */
	private String getPkey(){
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("partnerId", memberCode) ;
		hMap.put("paramCode", "code1") ;
		PartnerConfig partnerConfig = this.partnerConfigService.findPartnerConfig(hMap) ;
		String pkey = "" ;
		if(null != partnerConfig){
			pkey = partnerConfig.getValue() ;
		}
		return pkey ;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView vccDo(HttpServletRequest request, HttpServletResponse response, CrosspayApiRequest crosspayApiRequest){
		ModelAndView view = new ModelAndView(this.vccPayView) ;
		try {
			String signData = com.pay.util.StringUtil.null2String(request.getParameter("signData")) ;
			String signMsg = StringUtil.null2String(request.getParameter("signMsg")) ;
			String params = new StringBuilder(signData).append("&signMsg=").append(signMsg).toString() ;
			Response responseContent = HttpUtils.sendPostReq(this.vccPayAction, params) ;
			String statusCode = responseContent.getStatusCode() ;
			if(logger.isInfoEnabled()){
				logger.info("mps端VCC交易调用接口下单http(s)请求返回 statussCode:" + statusCode);
			}
			CrossPayApiResponse crossPayApiResponse = XMLUtil.xml2bean(responseContent.getContent(), CrossPayApiResponse.class) ;
			String resultCode = ""; 
			String resultMsg = "" ;
			if(null != crossPayApiResponse){
				resultCode = crossPayApiResponse.getResultCode() ;
				resultMsg = crossPayApiResponse.getResultMsg() ;
				
			}else{
				resultCode = "timeout" ;
				resultMsg = "支付超时" ;
			}
			view.addObject("resultCode", resultCode) ;
			view.addObject("resultMsg", resultMsg) ;
			view.addObject("suba", "suba") ;
			this.constuctDefaultViewData(view, crosspayApiRequest);
			if(!ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)){
				this.constructView(view, crosspayApiRequest, null);
				return view ;
			}else{
				String orderId = crosspayApiRequest.getOrderId() ;
				return view.addObject("orderId", orderId) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view ;
	}
	
	/**
	 * 域名检查
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView siteIdChecked(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null ;
		try {
			String siteId = StringUtil.null2String(request.getParameter("siteId")) ;
			String partnerId = SessionHelper.getMemeberCodeBySession() ;
			List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
					partnerId, siteId, "1,6");

			out = response.getWriter() ;
			out.print("");
			if (null != map && !map.isEmpty()) {
				out.print("Y");
			} else {
				out.print("N");
		   }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close(); 
			}
		}
		return null ;
	}
	
	/**
	 * 银行卡号正确性校验
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView cardHolderNumberValid(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null ;
		try {
			out = response.getWriter() ;
			String bankCard = StringUtil.null2String(request.getParameter("cardHolderNumber")) ;
			if(StringUtils.isNotEmpty(bankCard)){
				int result = BankCardCorrectValidUtil.calc(bankCard) ;
				if(result%10==0){  // 结果可以被10整除  
					out.print("Y");
			    }else{  
			    	out.print("N");
			    }  
			}else{
				out.print("N");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.close();
			}
		}
		return null ;
	}
	
	public void setVccPayView(String vccPayView) {
		this.vccPayView = vccPayView;
	}

	public void setPartnerConfigService(PartnerConfigService partnerConfigService) {
		this.partnerConfigService = partnerConfigService;
	}

	public void setVccPayAction(String vccPayAction) {
		this.vccPayAction = vccPayAction;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	
	
	
}
