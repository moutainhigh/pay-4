package com.pay.account.handler;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.service.MemberService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;


/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月7日上午10:42:54
 *  会员冻结
 * @param status,
 */
public class UpdateLockMemberHander implements EventHandler{
	
	public final Log logger = LogFactory.getLog(AccountUpdateHandler.class); 
	private MemberService memberService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());
		Map<String, Object> result = new HashMap<String, Object>();
		//获取请求参数
		String memberCode = paraMap.get("memberCode");
		//判断参数是否为空
		if(StringUtil.isEmpty(memberCode)){
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}
		try {
			// 根据会员号去更新会员状态
		Boolean isSuccess=memberService.lockMember(Long.valueOf(memberCode));
			String sign = "";
			if (isSuccess) {
				sign = "开通成功!";
			} else {
				sign = "开通失败,请与管理员联系...";
			}
			result.put("sign", sign);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
			return JSonUtil.toJSonString(result);
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			//会员状态更新失败			
			logger.error("update Lockmember status error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			
		}
		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
