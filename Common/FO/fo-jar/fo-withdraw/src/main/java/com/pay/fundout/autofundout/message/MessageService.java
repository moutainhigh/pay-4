package com.pay.fundout.autofundout.message;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;

public interface MessageService {
	/**
	 * 发送失败Email
	 * @param param
	 * @param amount
	 * @param errorMsg
	 * @param title
	 */
	public void sendFailEmail(AutoFundoutResult param) ;
	/**
	 * 发送成功Email
	 * @param param
	 */
	public void sendScuEmail(AutoFundoutResult param);
	
	/**
	 * 发送消息到后台产生工单
	 * @param fundoutOrderDTO
	 */
	public void sendMessage(FundoutOrderDTO fundoutOrderDTO);
}
