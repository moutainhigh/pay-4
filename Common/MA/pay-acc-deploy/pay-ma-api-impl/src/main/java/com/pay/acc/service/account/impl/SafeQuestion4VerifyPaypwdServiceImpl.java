package com.pay.acc.service.account.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.service.AcctAttribService;
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
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.inf.service.IMessageDigest;

public class SafeQuestion4VerifyPaypwdServiceImpl implements
		SafeQuestionVerifyService {

	private MemberService memberService;
	
	private AcctAttribService acctAttribService;

	private MemberOperateService memberOperateService;
	
	private IMessageDigest iMessageDigest;
	
	private OperateLogService operateLogService;
	
	private AcctService acctService;
	
	private final static Log log = LogFactory.getLog(SafeQuestion4VerifyPaypwdServiceImpl.class);
	
    public void addOperateLog(Long type,String objectCode,String actionUrl,long memberCode) {
		try{
			OperateLog operateLog=new OperateLog();
			operateLog.setObjectCode(objectCode);	
			//支付名，支付IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);	    
		}catch(Exception e){
			log.error("安全问题－支付密码---记录日志失败:memberCode:"+memberCode,e);
		}
    }
	
    /**
     * 该会员对应的所有账户都作止出
     */
	@Override
	public MaResultDto doVerify(long memberCode, int questionId, String answer) {

		MaResultDto result = new MaResultDto();
		
		try{
			
			//验证会员是否存在
			MemberDto member = memberService.queryMemberByMemberCode(memberCode);
			
			//如果会员不存在
			if(member==null){
				log.info("安全问题－支付密码---会员不存在:memberCode:"+memberCode);
				result.setResultStatus(MaConstant.MEMBER_NOT_EXISTS);
				return result;
			}
			
			MemberOperateDto mo = memberOperateService.queryMemberOperate(member.getMemberCode(), MemberOperateTypeEnum.SAFEQUESTION_PAY.getCode());
			
			if(mo!=null){
				// 清零
				memberOperateService.cleanFailTime(mo, VerifyPasswordConfig.getInstance().getPayFrozenMinute());
			}else{
				log.info("安全问题－支付密码验证---member_operate数据不存在，开始初始化数据。memberCode:"+member.getMemberCode());
				mo = new MemberOperateDto();
				mo.setMemberCode(member.getMemberCode());
				mo.setUpdateTime(new Date());
				mo.setCreateTime(new Date());
				mo.setFailTime(0L);
				mo.setType(new Long(MemberOperateTypeEnum.SAFEQUESTION_PAY.getCode()));
				memberOperateService.insert(mo);
				log.info("安全问题－支付密码验证---member_operate数据初始化结束。memberCode:"+member.getMemberCode());
			}
			
			long failTime = mo.getFailTime();
			
			//如果错误次数大于等于限制次数
			if(failTime>=VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount()){
				log.info("安全问题－支付密码---安全问题错误次数超过限制，memberCode:"+memberCode);
				result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				VerifyResultDto vr = new VerifyResultDto();
				int minutes = VerifyPasswordConfig.getInstance().getSafeQuestion4PayFrozenMinute()-(int)(System.currentTimeMillis()-mo.getUpdateTime().getTime())/60000;
				vr.setLeavingMinute(minutes);
				vr.setLeavingTime(0);
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount());
				result.setObject(vr);
				return result;
			}
			
			List<AcctDto> l = acctService.queryAcctByMemberCode(new Long(memberCode));
			
			if(StringUtils.isBlank(answer)){
				log.info("安全问题－支付密码---验证失败:memberCode:"+memberCode);
				// 答案为空，错误次数加1
				mo.setFailTime(mo.getFailTime()+1);
				memberOperateService.update(mo);
				VerifyResultDto vr = new VerifyResultDto();
				if(mo.getFailTime()==VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount()){
					log.info("安全问题－支付密码---验证失败:memberCode:"+memberCode+" 账户止出");
					acctAttribService.updateAcctFreezeAllowOutStatusWithMemberCode(String.valueOf(memberCode), AcctLockTypeEnum.FREEZE_OUT.getLockValue());
					
					//更新账户锁定时间s
					List<MemberOperateDto> mo4pays = getMemberOperateService(memberCode, MemberOperateTypeEnum.PAY_PWD,l);
					for(MemberOperateDto mo4pay:mo4pays){
						memberOperateService.update(mo4pay);
					}
					
					
					log.info("安全问题-登录密码---更新锁定时间:memberCode:"+memberCode);
					
					
					this.addOperateLog(new Long(OperateTypeEnum.FREEZE_OUT.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.FREEZE_OUT.getMessage(),memberCode);
					result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				}else{
					result.setResultStatus(MaConstant.ERROR_NOT_LOCK);
				}
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount());
				vr.setLeavingMinute(VerifyPasswordConfig.getInstance().getSafeQuestion4PayFrozenMinute());
				vr.setLeavingTime((int)(VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount()-mo.getFailTime()));
				result.setObject(vr);
				return result;
			}
			
			String code_answer = iMessageDigest.genMessageDigest(answer.getBytes());
			if(code_answer.equals(member.getSecurityAnswer()) && questionId==member.getSecurityQuestion().intValue()){
				//如果安全问题答案符合
				log.info("安全问题－支付密码---验证成功:memberCode:"+memberCode);
				//重置错误次数
				mo.setFailTime(0l);
				memberOperateService.update(mo);
				//会员解锁
				AcctAttribDto aa  = null;
				for(AcctDto acctDto:l){
					aa = acctAttribService.queryAcctAttribWithAcctCode(acctDto.getAcctCode());
					//如果账户已经冻结
					if(aa.getFrozen().intValue()==AcctLockTypeEnum.FREEZE_ACCOUNT.getLockValue().intValue()){
						log.info("安全问题－支付密码---账户已经冻结:acctCode:"+acctDto.getAcctCode());
						continue;
						
					}
					if(aa.getAllowOut().intValue()==AcctLockTypeEnum.FREEZE_OUT.getLockValue().intValue()){//如果账户已经止出
						if(acctAttribService.countAcctAttribFreeze(acctDto.getAcctCode())<=0){
							//如果不是后台锁定，则解锁
							acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(acctDto.getAcctCode(), AcctLockTypeEnum.UNFREEZE_OUT.getLockValue());
							log.info("安全问题－支付密码---验证成功:memberCode:"+memberCode+"，账户解除止出");
						}
					}
				}
				
				
				//更新账户锁定时间s
				List<MemberOperateDto> mo4pays = getMemberOperateService(memberCode, MemberOperateTypeEnum.PAY_PWD,l);
				for(MemberOperateDto mo4pay:mo4pays){
					mo4pay.setFailTime(0L);//错误次数清零
					memberOperateService.update(mo4pay);
				}
				
				this.addOperateLog(new Long(OperateTypeEnum.UNFREEZE_OUT.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.UNFREEZE_OUT.getMessage(),memberCode);
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
				if(mo.getFailTime()==VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount()){
					log.info("安全问题－支付密码---验证失败:memberCode:"+memberCode+" 账户止出");
					
					for(AcctDto acct:l){
						if(acctAttribService.countAcctAttribFreeze(acct.getAcctCode())<=0){
							acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(acct.getAcctCode(), AcctLockTypeEnum.FREEZE_OUT.getLockValue());
						}
					}
					
					//更新锁定时间
					List<MemberOperateDto> mo4pays = getMemberOperateService(memberCode, MemberOperateTypeEnum.PAY_PWD,l);
					for(MemberOperateDto mo4pay:mo4pays){
						memberOperateService.update(mo4pay);
					}
					this.addOperateLog(new Long(OperateTypeEnum.FREEZE_OUT.getCode()).longValue(), String.valueOf(member.getMemberCode()), OperateTypeEnum.FREEZE_OUT.getMessage(),memberCode);
					result.setResultStatus(MaConstant.ERROR_AND_LOCK);
				}else{
					result.setResultStatus(MaConstant.ERROR_NOT_LOCK);
				}
				vr.setTotalTime(VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount());
				vr.setLeavingMinute(VerifyPasswordConfig.getInstance().getSafeQuestion4PayFrozenMinute());
				vr.setLeavingTime((int)(VerifyPasswordConfig.getInstance().getSafeQuestion4PayTotalCount()-mo.getFailTime()));
				log.info("安全问题－支付密码---验证失败:memberCode:"+memberCode+"，剩余"+vr.getLeavingTime()+"次");
				result.setObject(vr);
				return result;
			}
		
		}catch(Exception e){
			log.error("安全问题－支付密码---运行时异常:memberCode:"+memberCode, e);
			result.setResultStatus(MaConstant.RUNTIME_EXCEPTION);
		}
		
		return null;
	}
	
	private List<MemberOperateDto> getMemberOperateService(long memberCode,MemberOperateTypeEnum mote,List<AcctDto> l) throws AcctServiceException, AcctServiceUnkownException, AcctAttribException, AcctAttribUnknowException{
		
		List<MemberOperateDto> result = null;
		
		if(l!=null){
		
			result = new ArrayList<MemberOperateDto>();
			
			for(AcctDto acctDto:l){
			
				AcctAttribDto aa = acctAttribService.queryAcctAttribWithAcctCode(acctDto.getAcctCode());

				if(aa.getFrozen().intValue()==AcctLockTypeEnum.FREEZE_ACCOUNT.getLockValue().intValue()){
					continue;//如果账户已经冻结，则跳出
				}
				
				if(acctAttribService.countAcctAttribFreeze(acctDto.getAcctCode())>0){
					continue;//如果是后台锁定，则跳出
				}
				
				MemberOperateDto memberOperate = memberOperateService.queryMemberOperate(memberCode, mote.getCode(),aa.getAcctType());
				
				if(memberOperate==null){
					log.info("安全问题－支付密码验证---member_operate数据不存在，开始初始化数据。memberCode:"+memberCode);
					memberOperate = new MemberOperateDto();
					memberOperate.setMemberCode(memberCode);
					memberOperate.setUpdateTime(new Date());
					memberOperate.setCreateTime(new Date());
					memberOperate.setFailTime(0L);
					memberOperate.setAccType(aa.getAcctType());
					memberOperate.setType(new Long(MemberOperateTypeEnum.PAY_PWD.getCode()));
					memberOperateService.insert(memberOperate);
					log.info("安全问题－支付密码验证---member_operate数据初始化结束。memberCode:"+memberCode);
				}
				
				if(acctAttribService.countAcctAttribFreeze(acctDto.getAcctCode())>0){
					continue;
				}
				result.add(memberOperate);
				
			}
		}
		
		
		return result;
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

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	
	
}
