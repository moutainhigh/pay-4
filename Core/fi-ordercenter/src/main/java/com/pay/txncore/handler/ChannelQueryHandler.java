/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class ChannelQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelQueryHandler.class);
	private HessianInvokeService invokeService;
	private MemberService memberService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, String> request = null;
		try {
			request = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String partner = request.get("partner");

		MemberDto memberDto = memberService.queryMemberByMemberCode(Long
				.valueOf(partner));

		request.put("memberType", memberDto.getType() + "");

		String reqMsg = JSonUtil.toJSonString(request);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		String result = invokeService.invoke(SerCode.CHANNEL_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		resultMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		return JSonUtil.toJSonString(resultMap);
	}
}
