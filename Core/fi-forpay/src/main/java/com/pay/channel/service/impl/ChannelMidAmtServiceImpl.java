package com.pay.channel.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.pay.channel.dao.ChannelMidAmtDao;
import com.pay.channel.service.ChannelMidAmtService;
import com.pay.forpay.client.TxncoreClientService;
import com.pay.jms.notification.request.NotifyRequest;

public class ChannelMidAmtServiceImpl implements ChannelMidAmtService{
	
	private ChannelMidAmtDao channelMidAmtDao; 
	
	private TxncoreClientService txncoreClientService; 
	
	@Override
	public boolean process(NotifyRequest notifyRequest) {
		System.out.println("接收到发送的MQ。。。。。。。"+notifyRequest.getData());
		Map data = notifyRequest.getData();
		String exchangeRate=null;
		if(data.get("currencyCode").equals("CNY")){ //人民币默认汇率为1不查询
			exchangeRate="1";	
		}else{
			data.put("currency", data.get("currencyCode"));
			data.put("memberCode", data.get("partnerId"));
			Map tradeRate=txncoreClientService.queryTradeRate(data); //查询支付币种转人民币的交易汇率
			if(tradeRate.get("exchangeRate")!=null){
				exchangeRate=(String)tradeRate.get("exchangeRate");
			}
		}
		if(!StringUtils.isEmpty(exchangeRate)){
			BigDecimal payAmount = new BigDecimal(data.get("payAmount")+"");
			BigDecimal multiply = payAmount.multiply(new BigDecimal(exchangeRate));
			data.put("payAmount", multiply.toString());//交易汇率乘支付金额
			this.channelMidAmtDao.createChannelMidAmt(data);
			return true;
		}
		return false;
	
	}
	
	public void setChannelMidAmtDao(ChannelMidAmtDao channelMidAmtDao) {
		this.channelMidAmtDao = channelMidAmtDao;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	
}
