/**
 * 
 */
package com.pay.gateway.controller.paylink;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkBaseInfo;
import com.pay.fi.chain.service.PayLinkService;
import com.pay.fi.chain.util.O2oEnum;
import com.pay.gateway.commons.BusinessType;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.model.GatewayRequest;
import com.pay.gateway.service.GatewayRequestService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import com.pay.util.WebUtil;

/**
 * 根据支付链接支付Controller
 * @author PengJiangbo
 *
 */
public class PayLinkPayController extends MultiActionController {

	private static final Log logger = LogFactory.getLog(PayLinkPayController.class) ;
	//private static final String payLinkUrl = GatewayConf.get(GatewayConf.payLinkUrl) ; 
	private static final String SHOP_TERM = "shopterm/" ;
	
	private String indexRedirectUrl ;
	private String invalidRedirectUrl ;
	private String payLinkUrl ;
	
	private PayLinkService payLinkService ;
	private GatewayRequestService gatewayRequestService ;
	
	/**
	 * 支付链支付默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response){
		String nPara = StringUtil.null2String(request.getParameter("n")) ;
		//无效请求
		if(null == nPara || "".equals(nPara)){
			logger.info("...无效的支付链接请求");
			return new ModelAndView("redirect:" + invalidRedirectUrl) ;
		}
		String payLinkName = payLinkUrl + "?n=" + nPara ;
		PayLink payLink = this.payLinkService.findPayLinkByName(payLinkName) ;
		//支付链接不存在
		if(null == payLink){
			logger.info("...支付链接不存在！");
			return new ModelAndView("redirect:" + invalidRedirectUrl) ;
		}
		int status = payLink.getStatus() ;
		Timestamp invalidTime = payLink.getInvalidTime() ;
		//失效时间
		long longInvalidTime = invalidTime.getTime() ;
		long longNowTime = new Date().getTime() ;
		//状态无效或当前时间超过支付链接的失效时间为无效支付链接
		if((status != O2oEnum.payLinkEffective.getStatus()) || (longNowTime > longInvalidTime)){
			logger.info("...支付链接已[失效]！");
			return new ModelAndView("redirect:" + invalidRedirectUrl) ;
		}
		//Long payLinkNo = payLink.getPaylinkNo() ;
		//List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(payLinkNo) ;
		Long memberCode = payLink.getMemberCode() ;
		String requestIP = WebUtil.getIp(request) ;
		String orderId = String.valueOf(System.currentTimeMillis());
		//支付链有效，保存网关请求
		CrosspayApiRequest crosspayApiRequest = this.genCrosspayApiRequest(requestIP, orderId, memberCode+"") ;
		this.saveRequest(requestIP, crosspayApiRequest) ;
		PayLinkBaseInfo baseInfo = loadBaseInfo(memberCode) ;
		String shoptermsName = baseInfo.getShoptermsName() ;
		String pathTail = SHOP_TERM + baseInfo.getMemberCode() + "/" + baseInfo.getShoptermsName() ;
		return new ModelAndView("redirect:" + indexRedirectUrl).addObject("payLinkName", payLinkName)
				.addObject("pathTail", pathTail)
				.addObject("shoptermsName", shoptermsName)
				.addObject("orderId", orderId);
		
	}
	
	//加载基本信息
	public PayLinkBaseInfo loadBaseInfo(final Long memberCode){
		PayLinkBaseInfo payLinkBaseInfo = null ;
		payLinkBaseInfo = this.payLinkService.payLinkBaseInfoQuery(memberCode) ;
		if(null == payLinkBaseInfo)
			payLinkBaseInfo = new PayLinkBaseInfo() ;
		return payLinkBaseInfo ;
	}
	
	private Long saveRequest(final String requestIP,
			final CrosspayApiRequest crosspayApiRequest) {
		GatewayRequest gatewayRequest = new GatewayRequest();
		gatewayRequest.setBusinessType(BusinessType.ACQUIRE.getType());
		gatewayRequest.setCreateDate(new Date());
		gatewayRequest.setFromDomain(requestIP);
		gatewayRequest.setOrderId(crosspayApiRequest.getOrderId());
		gatewayRequest.setPartnerId(Long.valueOf(crosspayApiRequest
				.getPartnerId()));
		gatewayRequest.setRequestContext(MapUtil
				.bean2String(crosspayApiRequest));
		gatewayRequest.setServiceVersion(crosspayApiRequest.getVersion());
		//针对支付链下单,不进行签名校验的处理
		if(null == crosspayApiRequest.getSignMsg() || "".equals(crosspayApiRequest.getSignMsg())){
			gatewayRequest.setSignMsg("支付链下单-null");
		}else{
			gatewayRequest.setSignMsg(crosspayApiRequest.getSignMsg());
		}
		gatewayRequest.setSignType(Integer.valueOf(crosspayApiRequest
				.getSignType()));
		gatewayRequest.setCharset(Integer.valueOf(crosspayApiRequest
				.getCharset()));
		gatewayRequest.setStatus(1);
		gatewayRequest.setRequestIp(requestIP);
		return gatewayRequestService.saveGatewayRequest(gatewayRequest);
	}
	
	private CrosspayApiRequest genCrosspayApiRequest(final String requestIP,final String orderId, final String memberCode){
		CrosspayApiRequest crosspayApiRequest = new CrosspayApiRequest() ;
		crosspayApiRequest.setOrderId(orderId);
		crosspayApiRequest.setPartnerId(memberCode);
		crosspayApiRequest.setVersion("0.1");
		//crosspayApiRequest.set
		crosspayApiRequest.setSignType("2");
		crosspayApiRequest.setCharset("1");
		return crosspayApiRequest ;
	}
	//---------------------------setter
	public void setIndexRedirectUrl(final String indexRedirectUrl) {
		this.indexRedirectUrl = indexRedirectUrl;
	}
	public void setInvalidRedirectUrl(final String invalidRedirectUrl) {
		this.invalidRedirectUrl = invalidRedirectUrl;
	}
	public void setPayLinkService(final PayLinkService payLinkService) {
		this.payLinkService = payLinkService;
	}
	public void setGatewayRequestService(final GatewayRequestService gatewayRequestService) {
		this.gatewayRequestService = gatewayRequestService;
	}

	public void setPayLinkUrl(final String payLinkUrl) {
		this.payLinkUrl = payLinkUrl;
	}
	
	
}
