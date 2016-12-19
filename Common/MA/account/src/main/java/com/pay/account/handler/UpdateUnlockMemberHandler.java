package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;


/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月7日下午8:34:52
 *  会员解冻
 * @param
 */
public class UpdateUnlockMemberHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(AccountUpdateHandler.class);
	
	private MemberService memberService;
	private MemberOperateDto memberOperateDto;

	public void setMemberOperateDto(MemberOperateDto memberOperateDto) {
		this.memberOperateDto = memberOperateDto;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());
		Map<String, Object> result = new HashMap<String, Object>();
		//获取参数校验
		String memberCode =paraMap.get("memberCode");
		
		if(StringUtil.isEmpty(memberCode)){
			result.put("ResponseCode",ResponseCodeEnum.INVALID_PARAM.getCode());
			result.put("responseDesc",ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}
		
		try {
			//根据会员号去解冻会员状态
			MemberOperateDto memberOperateDto =new MemberOperateDto();
			memberOperateDto.setMemberCode(Long.valueOf(memberCode));
			memberService.unlockMember(memberOperateDto);
			
			
			result.put("msg", "会员已解冻！");
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update  UnlockMemberStatus error:", e);
			result.put("responseCode",ResponseCodeEnum.getResponseCodeEnum(dataMsg));
			result.put("responseDesc",ResponseCodeEnum.getResponseCodeEnum(dataMsg));
		}
		return JSonUtil.toJSonString(result);
	}
	
}
