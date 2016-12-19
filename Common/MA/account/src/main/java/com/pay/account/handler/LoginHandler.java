
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.exception.MaMemberException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.login.LoginService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;


/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月1日上午10:22:50
 *  
 * @param
 */
public class LoginHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(LoginHandler.class);
	private LoginService loginService;
	private MemberIdentityService memberIdentityService;
	private MemberService memberService;

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void setMemberIdentityService(
			MemberIdentityService memberIdentityService) {
		this.memberIdentityService = memberIdentityService;
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

		String loginName = paraMap.get("loginName");
		String loginPwd = paraMap.get("loginPwd");
		String type = paraMap.get("type");
		String identity = paraMap.get("identity");//操作员标识
		String status =paraMap.get("status");
		
		//参数校验是否正确
		if(StringUtil.isEmpty(loginName)||StringUtil.isEmpty(loginPwd)||StringUtil.isEmpty(type)){
			result.put("ResponseCode",ErrorExceptionEnum.INVAILD_PARAMETER.getErrorCode());
			result.put("responseDesc", ErrorExceptionEnum.INVAILD_PARAMETER.getMessage());
			return JSonUtil.toJSonString(result);
		}
		// 将用户名转为小写
		loginName = StringUtils.lowerCase(loginName);
		//查询会员是否存在
		try {
			//查询登录名（注册邮箱）
			MemberDto memberDto = memberService
					.queryMemberByLoginName(loginName);
			if (memberDto == null) {
				throw new MaMemberException(
						ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "会员不存在！");
			}

			Long memberCode = memberDto.getMemberCode();
			/*
			 * 校验login    password和member
			 * 
			 */
			//个人会员登录
			if(type.equals("1")){
				
				MemberInfoDto memberInfoDto = loginService.login(loginName,loginPwd);
				
				result.put("memberInfo",memberInfoDto );
				result.put("responseCode",ResponseCodeEnum.SUCCESS.getCode());
				result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				return JSonUtil.toJSonString(result);
			}else {
				//企业登录
				if(type.equals("2")){
					
				MemberInfoDto memberInfoDto = loginService.login(loginName,loginPwd);
				result.put("memberInfo",memberInfoDto);
				result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				return JSonUtil.toJSonString(result);
				}
			}

			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(result);
		} catch (MaMemberException e) {
			logger.error("error:", e);
			result.put("responseCode", e.getErrorEnum().getErrorCode());
			result.put("responseDesc", e.getErrorEnum().getMessage());
			return JSonUtil.toJSonString(result);
		} catch (Exception e) {
			logger.error("parse request error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return JSonUtil.toJSonString(result);
		}
	}

}
