/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.paychain.impl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

import com.pay.app.common.util.MD5BaseAlgorithms;
import com.pay.app.common.util.WebUrlUtil;
import com.pay.util.DateUtil;
import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.service.paychain.PayChainPayService;

/**
 * @author fjl
 * @date 2011-9-22
 */
public class PayChainPayServiceImpl implements PayChainPayService {
	
	private Log log = LogFactory.getLog(PayChainPayServiceImpl.class);
	
	private String fiPkey = "";
	
	private Long platformId ;
	
	private String payUrl  = "";
	
	private String payNoticeUrl = "";
	
	private String payCallBackUrl = "";
	
	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl =   payUrl;
	}

	public void setPayNoticeUrl(String payNoticeUrl) {
		this.payNoticeUrl =  payNoticeUrl;
	}

	public void setPayCallBackUrl(String payCallBackUrl) {
		this.payCallBackUrl = payCallBackUrl;
	}

	public void setFiPkey(String fiPkey) {
		this.fiPkey = fiPkey;
	}

	@Override
	public LinkedHashMap<String, String> createPayMap(PayChainPayInfo payChainPayInfo) {
		
		BigDecimal amount = new BigDecimal(payChainPayInfo.getAmount());
		BigDecimal amount1 = amount.multiply(BigDecimal.valueOf(100));//把元变成分为单位
		Long totalAmount = amount1.longValue();
		
		String version = "mrp1.0";
		String dateTimeStr = DateUtil.formatDateTime("yyyyMMddHHmmss");
		String submitTime = dateTimeStr;
		
		String serialID = payChainPayInfo.getOrderNo();
		String customerIP = "pay.com[127.0.0.1]";	
		String orderDetails = serialID+","+totalAmount+","+payChainPayInfo.getPayeeName()+","+ payChainPayInfo.getPayChainName()+",1";
		String returnUrl = payCallBackUrl+"?order_no="+serialID;//防止与网关的orderNo冲突加上"_"
		String noticeUrl = payNoticeUrl;
		String remark = payChainPayInfo.getPayeeMemberCode()+"支付链收款";
		String type = "1004";//支付链商家收款
		String payerMarked = payChainPayInfo.getPayerEmail() == null ? "" : payChainPayInfo.getPayerEmail();
		String platformID = platformId.toString();
		
		//以下顺序是不能变的
		LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("version", version);//版本号
		map2.put("serialID", serialID);
		map2.put("submitTime",submitTime);
		map2.put("failureTime","");//失效时间s
		map2.put("customerIP",customerIP);
		map2.put("orderDetails",orderDetails);
		map2.put("totalAmount",totalAmount.toString());
		map2.put("type",type);
		map2.put("payeeMarked", payChainPayInfo.getPayeeMemberCode());//收款方会员号
		map2.put("payerMarked", payerMarked);//付款方标识，联系方式
		map2.put("payType","ALL");
		map2.put("currencyCode", "1");
		map2.put("returnUrl",returnUrl);
		map2.put("noticeUrl",noticeUrl);
		map2.put("platformID",platformID);//必填
		
		map2.put("remark",remark);
		map2.put("charset","1");
		map2.put("signType","2");
		map2.put("pkey", fiPkey);
		
		String signMsgUrl = WebUrlUtil.mapToUrl(map2);
		
		String signMsg = "";
		try {
			signMsg = MD5BaseAlgorithms.getMD5Str(signMsgUrl);
			map2.put("signMsg", signMsg);
			map2.remove("pkey");//不传key到客户端
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置付款签名异常", e);
		}
		map2.put("payChainID", payChainPayInfo.getPayChainCode());//支付链编号（不参与 加签）
		map2.put("payUrl", this.payUrl);
		return map2;
	}

	@Override
	public boolean validateNoticeMap(HttpServletRequest request) {
		
		String signMsg =ServletRequestUtils.getStringParameter(request, "signMsg","") ;
		LinkedHashMap<String, String> map2 = new  LinkedHashMap<String, String>();
		String keysLinked = "orderID,resultCode,stateCode,orderAmount,payAmount,acquiringTime,completeTime,orderNo,platformID,payeeMarked,payerMarked,remark,charset,signType";
		String[]keys = keysLinked.split(",");
		for(String k:keys){
			map2.put(k, ServletRequestUtils.getStringParameter(request, k,""));
		}
		map2.put("pkey", fiPkey);
		String signUrl = WebUrlUtil.mapToUrl(map2);
		String sign = "";
		try {
			sign = MD5BaseAlgorithms.getMD5Str(signUrl);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加签名异常");
		}
		if(  signMsg == null || ! signMsg.equals(sign) ){
			//签名失败了
			log.error("验证参数，签名失败");
			return false;
		}
		return true;
	}

	
}
