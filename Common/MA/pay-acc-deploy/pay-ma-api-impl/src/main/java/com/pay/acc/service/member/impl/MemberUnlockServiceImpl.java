package com.pay.acc.service.member.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.model.MemberOperate;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.member.MemberUnlockService;

public class MemberUnlockServiceImpl implements MemberUnlockService {

	private MemberService memberService;

	private MemberOperateService memberOperateService;

	private OperateLogService operateLogService;

	private final static Log log = LogFactory
			.getLog(MemberUnlockServiceImpl.class);

	private void addOperateLog(Long type, String objectCode, String actionUrl,
			long memberCode) {
		try {
			OperateLog operateLog = new OperateLog();
			operateLog.setObjectCode(objectCode);
			// 登录名，登录IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);
		} catch (Exception e) {
			log.error("登录密码解锁---记录日志失败:memberCode:" + memberCode, e);
		}
	}

	@Override
	public boolean unLock(long memberCode) {
		try {
			MemberDto member = memberService.queryMemberByMemberCode(memberCode);
			//如果会员没有锁定
			if(member.getStatus()!=2){
				return true;
			}
			MemberOperateDto mo4login = memberOperateService.getMemberOperate(
					memberCode, MemberOperateTypeEnum.LOGIN_PWD);
			memberService.unlockMember(mo4login);
			mo4login.setFailTime(0L);// 错误次数清零
			memberOperateService.update(mo4login);
			MemberOperateDto mo5login = memberOperateService.getMemberOperate(
					memberCode, MemberOperateTypeEnum.SAFEQUESTION_LOGIN);
			mo5login.setFailTime(0L);
			memberOperateService.update(mo5login);
			log.info("登录密码解锁---memberCode:" + memberCode + "，账户解锁");
			this.addOperateLog(
					new Long(OperateTypeEnum.UNFREEZE.getCode()).longValue(),
					String.valueOf(memberCode),
					OperateTypeEnum.UNFREEZE.getMessage(), memberCode);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

}
