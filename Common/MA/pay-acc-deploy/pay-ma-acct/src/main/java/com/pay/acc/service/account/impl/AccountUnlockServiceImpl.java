package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.AccountUnlockService;
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;

public class AccountUnlockServiceImpl implements AccountUnlockService {

	private AcctAttribService acctAttribService;

	private MemberOperateService memberOperateService;

	private OperateLogService operateLogService;

	private AcctService acctService;

	private final static Log log = LogFactory
			.getLog(AccountUnlockServiceImpl.class);

	@Override
	public boolean unLock(long memberCode,int acctType) {
		try {
			
			// 更新账户锁定时间s
			AcctDto acctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode,acctType);
			AcctAttribDto aa  = acctAttribService.queryAcctAttribWithAcctCode(acctDto.getAcctCode());
			
			if(aa.getFrozen().intValue()==AcctLockTypeEnum.FREEZE_ACCOUNT.getLockValue().intValue()){
				log.info("账户已经冻结---accountCode:"+acctDto.getAcctCode());
				return false;
			}
			if(aa.getAllowOut().intValue()==AcctLockTypeEnum.FREEZE_OUT.getLockValue().intValue()){
				if(acctAttribService.countAcctAttribFreeze(acctDto.getAcctCode())<=0){
					acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(acctDto.getAcctCode(), AcctLockTypeEnum.UNFREEZE_OUT.getLockValue());
				}else{
					//如果是后台止出，则不解锁
					log.info("账户解除止出失败---后台锁定,accountCode:"+acctDto.getAcctCode());
					return false;
				}
			}else{
				//如果账户未锁定
				return true;
			}
			
			MemberOperateDto memberOperate = this.memberOperateService.queryMemberOperate(memberCode, MemberOperateTypeEnum.PAY_PWD.getCode(), aa.getAcctType());
			memberOperate.setFailTime(0l);//错误次数清零
			memberOperateService.update(memberOperate);
			log.info("账户解除止出---accountCode:" + acctDto.getAcctCode());
			this.addOperateLog(
					new Long(OperateTypeEnum.FREEZE_OUT.getCode()).longValue(),
					acctDto.getAcctCode(),
					OperateTypeEnum.FREEZE_OUT.getMessage());
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private void addOperateLog(Long type, String objectCode, String actionUrl) {
		try {
			OperateLog operateLog = new OperateLog();
			operateLog.setObjectCode(objectCode);
			// 支付名，支付IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);
		} catch (Exception e) {
			log.error("解除止出---记录日志失败:accountCode:" + objectCode, e);
		}
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

}
