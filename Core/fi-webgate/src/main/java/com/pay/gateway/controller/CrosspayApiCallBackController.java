package com.pay.gateway.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.TxncoreClientService;

import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;



/**
 * @desc 在线收单回调控制器
 * 
 */
public class CrosspayApiCallBackController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayApiCallBackController.class);
	
	private TxncoreClientService txncoreClientService;
	private JmsSender jmsSender;
	private TradeDataSingnatureService tradeDataSingnatureService;
	
	
	public TradeDataSingnatureService getTradeDataSingnatureService() {
		return tradeDataSingnatureService;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	

	public TxncoreClientService getTxncoreClientService() {
		return txncoreClientService;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	// 回调流程
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {

		 		   		    
		
		 
		 Map fields = new HashMap();
		    for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
		        String fieldName = (String) e.nextElement();
		        String fieldValue = request.getParameter(fieldName);
		        if ((fieldValue != null) && (fieldValue.length() > 0)) {
		            fields.put(fieldName, fieldValue);
		        }
		    }
		    Map resultMap = txncoreClientService.crosspayApi3DCallBack(fields);
		    resultMap.remove("paymentOrderNo");
		    if(resultMap.get("returnUrl")!=null){
		    	try {
		    		String orderAmount = String.valueOf(resultMap.get("orderAmount"));
					orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("10")).longValue() + "";
					response.setContentType("text/html; charset=utf-8");
					resultMap.put("orderAmount", orderAmount);
					String responeForm =getResponeForm(resultMap);
					
					PrintWriter pt=	response.getWriter();	
					pt.print(responeForm);
					pt.flush();
					pt.close();
					
				} catch (IOException e) {
					logger.error("writer error:", e);
				}
		    }
		    
		    if(resultMap.get("notifyUrl")!=null){
		    	
				notifyMerchant(resultMap);
			}
		    	
		    
		    
		    
		return null;
	}
	
	private void notifyMerchant(
			final Map notifyMap) {

		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			String notifyurl =notifyMap.get("notifyUrl").toString();
			notifyMap.remove("notifyUrl");
			notifyMap.remove("returnUrl");
			notifyMap.put("resultCode", notifyMap.get("responseCode"));
			notifyMap.remove("responseCode");
			notifyMap.put("resultMsg", notifyMap.get("responseDesc"));
			notifyMap.remove("responseDesc");
			notifyMap.remove("payAmount");
			notifyMap.put("dealId", notifyMap.get("tradeOrderNo").toString());
			notifyMap.remove("tradeOrderNo");
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(notifyMap.get("partnerId").toString());
			notifyRequest.setUrl(notifyurl);
			notifyRequest.setData(notifyMap);
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			logger.error("writer error:", e);
		}

	}
	
	
private String getResponeForm(Map<String,Object> resultMap){
		
			 StringBuffer submitform=new StringBuffer();
		StringBuffer sb =new StringBuffer();
		HashMap<String,Object> returnmap =new HashMap(resultMap);
			submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
			submitform.append("<form action='");
			submitform.append(returnmap.get("returnUrl"));
			submitform.append("' method='post'>");
			returnmap.remove("notifyUrl");
			returnmap.remove("returnUrl");
			returnmap.remove("payAmount");
			returnmap.put("resultCode", returnmap.get("responseCode"));
			returnmap.remove("responseCode");
			returnmap.put("resultMsg", returnmap.get("responseDesc"));
			returnmap.remove("responseDesc");
			returnmap.put("dealId", returnmap.get("tradeOrderNo"));
			returnmap.remove("tradeOrderNo");
			for (Entry<String, Object> entry : returnmap.entrySet()) {
				   
				String key = entry.getKey();
				  
				   if(entry.getValue()!=null){
					   	submitform.append("<input type='hidden' name='");
					   	submitform.append(key);
						submitform.append("' value='");
						submitform.append(entry.getValue());
						submitform.append("' />");
						if(sb.length()<1){
							sb.append(key);
							sb.append("=");
							sb.append(entry.getValue());
						}else{
							sb.append("&");
							sb.append(key);
							sb.append("=");
							sb.append(entry.getValue());
						}
				   }
				  
				  }
						
			submitform.append("<input type='hidden' name='signMsg' value='");
			submitform.append(geneSignMsg(returnmap, sb.toString()));
			submitform.append("' />");
			submitform.append("</form></BODY></html>");
			
			return submitform.toString();
			
		
	}



private String geneSignMsg(Map result,String signData) {
	String signMsg = null;
	try {

		Map<String, String> resultMap = txncoreClientService
				.crosspayPartnerConfigQuery(
						result.get("partnerId").toString(), "code1");
		String merchantKey = resultMap.get("value");

		

		logger.info("signData-api: " + signData);

		 signMsg = tradeDataSingnatureService.genSignMsgBySignType(
				signData, result.get("signType").toString(),
				 result.get("language").toString(), merchantKey);
		return signMsg;
	} catch (Exception e) {
		logger.error("gene signMsg error:", e);
	}
	return signMsg;
}
	
}
