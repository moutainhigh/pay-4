package com.pay.acc.service.account.impl;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.MaConstant;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.SafeQuestionVerifyService;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.inf.service.IMessageDigest;

public class SafeQuestionVerifyServiceImpl implements SafeQuestionVerifyService {
	private final static Log log = LogFactory.getLog(SafeQuestionVerifyServiceImpl.class);
	
	private MemberService memberService;
	
	private IMessageDigest iMessageDigest;
	@Override
	public MaResultDto doVerify(long memberCode, int questionId, String answer) {

		MaResultDto result = new MaResultDto();
		
		try{
		
			//验证会员是否存在
			MemberDto member = memberService.queryMemberByMemberCode(memberCode);
			
			//如果会员不存在
			if(member==null){
				log.info("安全问题－登录密码---会员不存在:memberCode:"+memberCode);
				result.setResultStatus(MaConstant.MEMBER_NOT_EXISTS);
				return result;
			}
			
			String code_answer = iMessageDigest.genMessageDigest(answer.getBytes());
			if(code_answer.equals(member.getSecurityAnswer()) && questionId==member.getSecurityQuestion().intValue()){
				//如果安全问题答案符合
				
				result.setResultStatus(MaConstant.SECCESS);
				result.setObject(member);
				return result;
			}else{
				result.setResultStatus(MaConstant.FAILED);
				return result;
			}
		
		}catch(Exception e){
			log.error("安全问题－登录密码---运行时异常:memberCode:"+memberCode, e);
			result.setResultStatus(MaConstant.RUNTIME_EXCEPTION);
		}
		
		return null;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}


	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
}
