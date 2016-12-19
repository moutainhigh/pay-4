package com.pay.fundout.channel.service.channel;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.channel.model.channel.ChannelRequest;

public class ValidateChannelRequest {

	/**
	 * 验证渠道查询参数是否合规
	 * @param request
	 * @return
	 */
	public static String validate(ChannelRequest request){
		StringBuffer message = new StringBuffer();
		if(request == null){
			message.append("渠道查询参数不能为空");
		}else if(StringUtils.isBlank(request.getProductCode())){
			message.append("产品编号不能为空");
		}else if(request.getTargetBank() == null){
			message.append("目的银行ID不能为空");
		}
		return message.toString();
	}
	
}
