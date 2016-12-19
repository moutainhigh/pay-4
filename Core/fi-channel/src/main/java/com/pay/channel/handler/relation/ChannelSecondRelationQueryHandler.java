/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler.relation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.model.ChannelSecondRelation;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 渠道处理
 * 
 * @author chma
 */
public class ChannelSecondRelationQueryHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(ChannelSecondRelationQueryHandler.class);
	private ChannelSecondRelationDAO channelSecondRelationDAO;

	public ChannelSecondRelationDAO getChannelSecondRelationDAO() {
		return channelSecondRelationDAO;
	}

	public void setChannelSecondRelationDAO(ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
			String memberCode = paraMap.get("memberCode");
			String orgMerchantCode = paraMap.get("orgMerchantCode");
			String paymentChannelItemId = paraMap.get("paymentChannelItemId");
			Map<String,String> searchMap = new HashMap<String, String>();
			if(!StringUtil.isEmpty(memberCode)){
				searchMap.put("memberCode",memberCode);
			}
			if(!StringUtil.isEmpty(orgMerchantCode)){
				searchMap.put("orgMerchantCode",orgMerchantCode);
			}
			if(!StringUtil.isEmpty(paymentChannelItemId)){
				searchMap.put("paymentChannelItemId",paymentChannelItemId);
			}

			List<ChannelSecondRelation> secondRelations = channelSecondRelationDAO.findByCriteria(searchMap);
			resultMap.put("secondRelations", secondRelations);
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
}
