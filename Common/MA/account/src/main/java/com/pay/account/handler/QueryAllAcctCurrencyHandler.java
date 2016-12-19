package com.pay.account.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribInfDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;


/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月7日下午8:17:28
 *  
 * @param
 */
public class QueryAllAcctCurrencyHandler implements EventHandler {

	private AcctAttribService acctAttribService;
	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	private MemberService memberService;

	public void setAcctService(AcctService acctService) {
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public final Log logger = LogFactory.getLog(AccountQueryHandler.class);

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		@SuppressWarnings("unchecked")
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();
		//获取请求参数
		String memberCode = paraMap.get("memberCode");
		
		//参数进行校验
		if (StringUtils.isEmpty(memberCode)) {
			result.put("responseCode", ResponseCodeEnum.INVALID_PARAM.getCode());
			result.put("responseCode", ResponseCodeEnum.INVALID_PARAM.getDesc());
			return JSonUtil.toJSonString(result);
		}
		try {
			// 根据用户查询其所有币种账户
			List<AcctAttribInfDto> acctAttribInfDto = acctAttribService.queryAcctCurCodeByMemberCode(Long.valueOf(memberCode));
			
//			AcctAttribInfDto AcctAttribInfDto1= new AcctAttribInfDto();
//			AcctAttribInfDto1.setAcctCode("acctCode");
//			AcctAttribInfDto1.setCurrenyCode("currenyCode");
			
			result.put("acctAttribInfDto", acctAttribInfDto);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(result);
		} catch (Exception e) {
			logger.error("QueryAllAcctCurrency error:", e);
			e.printStackTrace();
		}
		
		return JSonUtil.toJSonString(result);
	}
}
