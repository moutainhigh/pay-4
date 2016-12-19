/**
 * 
 */
package com.pay.gateway.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.cardbin.model.CardBinLearning;
import com.pay.cardbin.service.CardBinLearningService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.MapUtil;

/**
 * @author PengJiangbo
 *
 */
public class TestController extends MultiActionController {

	private CardBinLearningService cardBinLearningService ;
	private JmsSender jmsSender;
	
	public ModelAndView test(final HttpServletRequest request, final HttpServletResponse response){
		CardBinLearning cardBinLearning = new CardBinLearning() ;
		cardBinLearning.setBank("Merchant of China");
		cardBinLearning.setBin("654321");
		cardBinLearning.setBrand("VISA");
		cardBinLearning.setCard_category("category");
		cardBinLearning.setCard_type("credit");
		cardBinLearning.setCompleteTime(new Timestamp(new Date().getTime()));
		cardBinLearning.setCountry_code("CN");
		cardBinLearning.setCountry_name("CHINA");
		cardBinLearning.setCreateTime(new Timestamp(new Date().getTime()));
		cardBinLearning.setLatitude("15");
		cardBinLearning.setLongitude("108");
		cardBinLearning.setQuery_time("3333ms");
		cardBinLearning.setSub_brand("no");
		this.cardBinLearningService.saveCardBin(cardBinLearning);
		return null ;
	}
	//
	
	public ModelAndView notityTest(final HttpServletRequest request, final HttpServletResponse response){
		notifyCardBin() ;
		System.out.println("=====notifyCardBin==================");
		return null ;
	}
	public ModelAndView notityTest222(final HttpServletRequest request, final HttpServletResponse response){
		notifyMerchant(new CrosspayApiRequest(), new CrosspayApiResponse()) ;
		System.out.println("=====notifyMerchant=================");
		return null ;
	}
	private void notifyCardBin() {
		try {
			CardBinNotifyRequest notifyRequest = new CardBinNotifyRequest();
			notifyRequest.setBin("601382");
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//通知商户
	private void notifyMerchant(final CrosspayApiRequest crosspayApiRequest,
			final CrosspayApiResponse crosspayApiResponse) {
		
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String, String> notifyMap = MapUtil
					.bean2map(crosspayApiResponse);
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(crosspayApiResponse.getPartnerId());
			notifyRequest.setUrl(crosspayApiRequest.getNoticeUrl());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setCardBinLearningService(
			final CardBinLearningService cardBinLearningService) {
		this.cardBinLearningService = cardBinLearningService;
	}

	public void setJmsSender(final JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	
}
