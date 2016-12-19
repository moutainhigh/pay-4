package com.pay.acc.service.account.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.MaConstant;
import com.pay.acc.common.config.VerifyPasswordConfig;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.model.MemberOperate;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.SafeQuestionVerifyService;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.inf.service.IMessageDigest;

public class SafeQuestion4VerifyLoginpwdServiceImpl implements
		SafeQuestionVerifyService {
	
	private MemberService memberService;

	private MemberOperateService memberOperateService;
	
	private IMessageDigest iMessageDigest;
	
	private OperateLogService operateLogService;
	
	private final static Log log = LogFactory.getLog(SafeQuestion4VerifyLoginpwdServiceImpl.class);
	
    public void addOperateLog(Long type,String objectCode,String actionUrl,long memberCode) {
		try{
			OperateLog operateLog=new OperateLog();
			operateLog.setObjectCode(objectCode);	
			//登录名，登录IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);	    
		}catch(Exception e){
			log.error("安全问题－登录密码---记录日志失败:memberCode:"+memberCode,e);
		}
    }
	
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
			
			MemberOperateDto mo = memberOperateService.queryMemberOperate(member.getMemberCode(), MemberOperateTypeEnum.SAFEQUESTION_LOGIN.getCode());
			
			if(mo!=null){
				// 清零
				memberOperateService.cleanFailTime(mo, VerifyPasswordConfig.getInstance().getLoginFrozenMinute());
			}else{
				log.info("安全问题－登录密码验证---member_operate数据不存在，开始初始化数据。memberCode:"+member.getMemberCode());
				mo = new MemberOperateDto();
				mo.setMemberCode(member.getMemberCode());
				mo.setUpdateTime(new Date());
				mo.setCreateTime(new Date());
				mo.setFailTime(0L);
				mo.setType(new Long(MemberOperateTypeEnum.SAFEQUESTION_LOGIN.getCode()));
				memberOperateService.insert(mo);
				log.info("安全问题－登录密码验证---member_operate数据初始化结束。memberCode:"+member.getMemberCode());
			}
			
			long failTime = mo.getFailTime();
			
			//如果错误次数大于等于限制次数
			if(failTime>=VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount()){
				log.info("安全问题－登录密码---安全问题错误次数超过限制，memberCode:"+memberCode);
				result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				VerifyResultDto vr = new VerifyResultDto();
				int minutes = VerifyPasswordConfig.getInstance().getSafeQuestion4LoginFrozenMinute()-(int)(System.currentTimeMillis()-mo.getUpdateTime().getTime())/60000;
				vr.setLeavingMinute(minutes);
				vr.setLeavingTime(0);
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount());
				result.setObject(vr);
				return result;
			}
			
			if(StringUtils.isBlank(answer)){
				// 答案为空，错误次数加1
				mo.setFailTime(mo.getFailTime()+1);
				memberOperateService.update(mo);
				VerifyResultDto vr = new VerifyResultDto();
				if(mo.getFailTime()==VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount()){
					log.info("安全问题－登录密码---验证失败:memberCode:"+memberCode+" 账户锁定");
					memberService.lockMember(memberCode);
					
					MemberOperateDto mo4login = memberOperateService.getMemberOperate(memberCode, MemberOperateTypeEnum.LOGIN_PWD);
					memberOperateService.update(mo4login);
					
					log.info("安全问题-登录密码---更新锁定时间:memberCode:"+memberCode);
					memberOperateService.update(mo4login);
					
					this.addOperateLog(new Long(OperateTypeEnum.FREEZE.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.FREEZE.getMessage(),memberCode);
					result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				}else{
					result.setResultStatus(MaConstant.ERROR_NOT_LOCK);
				}
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount());
				vr.setLeavingMinute(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginFrozenMinute());
				vr.setLeavingTime((int)(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount()-mo.getFailTime()));
				log.info("安全问题－登录密码---验证失败:memberCode:"+memberCode+"，剩余"+vr.getLeavingTime()+"次");
				result.setObject(vr);
				return result;
			}
			
			String code_answer = iMessageDigest.genMessageDigest(answer.getBytes());
			if(code_answer.equals(member.getSecurityAnswer()) && questionId==member.getSecurityQuestion().intValue()){
				//如果安全问题答案符合
				
				//重置错误次数
				mo.setFailTime(0l);
				memberOperateService.update(mo);
				log.info("安全问题－登录密码---验证成功:memberCode:"+memberCode);
				//会员解锁
				memberService.unlockMember(mo);
				log.info("安全问题－登录密码---验证成功:memberCode:"+memberCode+"，账户解锁");
				this.addOperateLog(new Long(OperateTypeEnum.UNFREEZE.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.UNFREEZE.getMessage(),memberCode);
				//返回结果
				result.setResultStatus(MaConstant.SECCESS);
				result.setObject(member);
				return result;
			}else{
				//如果安全问题答案不符
				//错误次数加1
				mo.setFailTime(mo.getFailTime()+1);
				memberOperateService.update(mo);
				VerifyResultDto vr = new VerifyResultDto();
				if(mo.getFailTime()==VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount()){
					log.info("安全问题－登录密码---验证失败:memberCode:"+memberCode+" 账户锁定");
					memberService.lockMember(memberCode);
					
					MemberOperateDto mo4login = memberOperateService.getMemberOperate(memberCode, MemberOperateTypeEnum.LOGIN_PWD);
					memberOperateService.update(mo4login);
					
					log.info("安全问题-登录密码---更新锁定时间:memberCode:"+memberCode);
					this.addOperateLog(new Long(OperateTypeEnum.FREEZE.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.FREEZE.getMessage(),memberCode);
					result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				}else{
					result.setResultStatus(MaConstant.ERROR_NOT_LOCK);
				}
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount());
				vr.setLeavingMinute(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginFrozenMinute());
				vr.setLeavingTime((int)(VerifyPasswordConfig.getInstance().getSafeQuestion4LoginTotalCount()-mo.getFailTime()));
				log.info("安全问题－登录密码---验证失败:memberCode:"+memberCode+"，剩余"+vr.getLeavingTime()+"次");
				result.setObject(vr);
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

	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
	
	

}
