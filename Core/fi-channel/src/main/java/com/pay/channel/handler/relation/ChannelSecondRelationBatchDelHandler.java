/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.relation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.service.PaymentChannelService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelSecondRelationBatchDelHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChannelSecondRelationBatchDelHandler.class);
	private PaymentChannelService paymentChannelService;
	private JmsSender jmsSender;

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String id = String.valueOf(paraMap.get("id"));
			String orgCode = String.valueOf(paraMap.get("orgCode"));
			String orgMerchantCode = String.valueOf(paraMap
					.get("orgMerchantCode"));
			String cardOrg = String.valueOf(paraMap
					.get("cardOrg"));
			List<ChannelSecondRelation> channelSecondRelations = null;
			channelSecondRelations = new ArrayList<ChannelSecondRelation>();
			ChannelSecondRelation relation = new ChannelSecondRelation();
			if (!StringUtil.isEmpty(id)) {
				relation.setId(Long.valueOf(id));
			}
			if (!StringUtil.isEmpty(orgCode)) {
				relation.setOrgCode(orgCode);
			}
			if (!StringUtil.isEmpty(orgMerchantCode)) {
				relation.setOrgMerchantCode(orgMerchantCode);
			}
			if (!StringUtil.isEmpty(cardOrg)) {
				relation.setCardOrg(cardOrg);
			}

			channelSecondRelations.add(relation);
			paymentChannelService
					.delChannelSecondRelation(channelSecondRelations);
			
			
			sendAlertMsg(orgCode,id,orgMerchantCode);

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	
	
	
	private void sendAlertMsg(String orgCode,String id,String orgMerchantCode){
		
		Map<String,String> data = new HashMap<String, String>();

		data.put("first","有人删除了二级商户号，请确认是否是正常删除,除删的通道编号,orgCode: "+orgCode+
				" ,id:"+id+" ,orgMerchantCode: "+orgMerchantCode);
		data.put("keyword1","通道二级商户号删除提醒！");
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
        data.put("keyword2",formatter.format(new Date()));
        data.put("remark","");
		
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);
		
		request.setData(data);
		jmsSender.send(request);
	}
}
