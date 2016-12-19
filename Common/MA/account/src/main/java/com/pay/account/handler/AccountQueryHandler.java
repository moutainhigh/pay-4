package com.pay.account.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;


/**
 * 查询账户所有信息
 * 
 * @author xiaodai.Rainy
 *
 * @data 2015年12月2日下午7:25:58
 *  
 * @param
 */
public class AccountQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(AccountQueryHandler.class);
	private MemberService memberService;
	private AcctService acctService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}


	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		@SuppressWarnings("unchecked")
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String memberCode = paraMap.get("memberCode"); 
		
		//查询会员信息
		MemberDto member=memberService.queryMemberByMemberCode(Long.valueOf(memberCode));
		
		if(member==null){
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
			return JSonUtil.toJSonString(result);
		}
		
		try {
			//这边开始报错了，返回没有查到值
			List<AcctDto> acctDto = acctService.queryAcctByMemberCode(Long.valueOf(memberCode));
			result.put("acctDto", acctDto);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("QueryAcctount is failed", e);
		}
		return JSonUtil.toJSonString(result);
	}

}
