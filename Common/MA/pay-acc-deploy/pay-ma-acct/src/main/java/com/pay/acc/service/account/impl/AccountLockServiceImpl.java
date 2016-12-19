/**
 * 
 */
package com.pay.acc.service.account.impl;

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
import com.pay.acc.commons.AcctEnum;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.AccountLockService;
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.exception.MaAcctLockException;

/**
 * @author Administrator
 * 
 */
public class AccountLockServiceImpl implements AccountLockService {

	private MemberService memberService;

	private AcctService acctService;

	private AcctAttribService acctAttribService;
	
	private OperateLogService operateLogService;

	private Log log = LogFactory.getLog(AccountLockServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.service.account.AccountLockService#doHandlerAccountLockRnTx
	 * (java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public boolean doHandlerAccountLockRnTx(Long memberCode, AcctTypeEnum acctType, AcctLockTypeEnum acctLockType)
			throws MaAcctLockException {
		if (log.isInfoEnabled()) {
			log.info("###################调用[" + this.toString() + "]的方法开始[doHandlerAccountLockRnTx]##################");
		}
		// 验证入参
		this.checkEnterParameter(memberCode);
		// 验证会员如果会员冻结或者不存在后面就不用做了
		this.handlerCheckMemberWithMemberCode(memberCode);
		this.handlerFreezeTrans(memberCode, acctType, acctLockType);
		return true;
	}

	private void handlerFreezeTrans(Long memberCode, AcctTypeEnum acctType, AcctLockTypeEnum acctLockType) throws MaAcctLockException {

		AcctDto acctDto = null;
		String acctCode=memberCode.toString();
		String actionUrl=null;
		// 账户冻结
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.FREEZE_ACCOUNT) {
			actionUrl=OperateTypeEnum.FREEZE.getMessage();
			this.handlerFreezeAcctWithAcctCode(memberCode, acctType.getCode(), acctLockType.getLockValue(), true);
		}
		// 账户解冻
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.UNFREEZ_ACCOUNT) {
			actionUrl=OperateTypeEnum.UNFREEZE.getMessage();
			this.handlerFreezeAcctWithAcctCode(memberCode, acctType.getCode(), acctLockType.getLockValue(), false);
		}
		// 账户止入
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.FREEZE_IN) {
			actionUrl=OperateTypeEnum.FREEZE_IN.getMessage();
			acctDto = this.handlerQueryAcctWithMemberAcctCode(memberCode, acctType.getCode(), true);			
			this.handlerFreezeIn(acctDto.getAcctCode(), acctLockType.getLockValue(), true);
		}
		// 账户解止入
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.UNFREEZE_IN) {
			actionUrl=OperateTypeEnum.UNFREEZE_IN.getMessage();
			acctDto = this.handlerQueryAcctWithMemberAcctCode(memberCode, acctType.getCode(), true);
			this.handlerFreezeIn(acctDto.getAcctCode(), acctLockType.getLockValue(), false);
		}
		// 账户止出
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.FREEZE_OUT) {
			actionUrl=OperateTypeEnum.FREEZE_OUT.getMessage();
			acctDto = this.handlerQueryAcctWithMemberAcctCode(memberCode, acctType.getCode(), true);
			this.handlerFreezeOut(acctDto.getAcctCode(), acctLockType.getLockValue(), true);
		}
		// 账户解止出
		if (acctLockType.getAcctLockTypeCode() == ConstantHelper.UNFREEZE_OUT) {
			actionUrl=OperateTypeEnum.UNFREEZE_OUT.getMessage();
			acctDto = this.handlerQueryAcctWithMemberAcctCode(memberCode, acctType.getCode(), true);
			this.handlerFreezeOut(acctDto.getAcctCode(), acctLockType.getLockValue(), false);
		}
		if(null!=acctDto){
			acctCode=acctDto.getAcctCode();
		}
		//将所有的操作记录日志
		try{
			operateLogService.addOperateLog(Long.valueOf(acctLockType.getAcctLockTypeCode()), acctCode, actionUrl, Long.valueOf(acctLockType.getAcctLockTypeCode()));
		}catch(Exception e){
			new MaAcctLockException(e);
		}
		if (log.isInfoEnabled()) {
			log.info("###################调用[" + this.toString() + "]的方法结束[doHandlerAccountLockRnTx]##################");
		}

	}

	private void checkEnterParameter(Long memberCode) throws MaAcctLockException {
		if (memberCode == null || memberCode.longValue() < 0) {
			if (log.isErrorEnabled()) {
				log.error("你输入的参数[" + memberCode + "] 有误");
			}
			throw new MaAcctLockException("你输入的参数[" + memberCode + "] 有误");
		}
	}

	private MemberDto handlerCheckMemberWithMemberCode(Long memberCode) throws MaAcctLockException {
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		} catch (MemberUnknowException e) {
			if (log.isErrorEnabled()) {
				log.error("您输入的会员号[" + memberCode + "]出现" + e.getMessage());
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (memberDto == null) {
			if (log.isErrorEnabled()) {
				log.error("关于会员号：[" + memberCode + "] 不存在");
			}
			throw new MaAcctLockException("关于会员号：[" + memberCode + "] 不存在");
		}
		if (memberDto.getStatus() != MemberStatusEnum.NORMAL.getCode()) {
			if (log.isErrorEnabled()) {
				log.error("对于会员号[" + memberCode + "] 未激活或者冻结状态");
			}
			throw new MaAcctLockException("对于会员号[" + memberCode + "] 未激活或者冻结状态");
		}

		return memberDto;
	}

	private AcctDto handlerQueryAcctWithMemberAcctCode(Long memberCode, Integer acctType, boolean checkUnfreeze) throws MaAcctLockException {
		AcctDto acctDto = null;
		try {
			acctDto = this.acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, acctType);
		} catch (AcctServiceException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		} catch (AcctServiceUnkownException e) {
			if (log.isErrorEnabled()) {
				log.error("您输入的会员号[" + memberCode + "]出现" + e.getMessage());
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (acctDto == null) {
			if (log.isErrorEnabled()) {
				log.error("对于会员号[" + memberCode + "] 不存在账户");
			}
			throw new MaAcctLockException("对于会员号[" + memberCode + "] 不存在账户");

		}
		if (checkUnfreeze) {
			if (acctDto.getStatus() != AcctEnum.VALID.getCode()) {
				if (log.isErrorEnabled()) {
					log.error("对于会员号：[" + memberCode + "] 账户已经无效");
				}
				throw new MaAcctLockException("对于会员号：[" + memberCode + "] 账户已经无效");
			}
		}
		return acctDto;
	}

	private AcctAttribDto handlerQueryAcctAttribWithAcctCode(String acctCode) throws MaAcctLockException {

		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.acctAttribService.queryAcctAttribWithAcctCode(acctCode);
		} catch (AcctAttribException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);

		} catch (AcctAttribUnknowException e) {
			if (log.isErrorEnabled()) {
				log.error("输入的账户[" + acctCode + "]出现" + e.getMessage());
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (acctAttribDto == null) {
			if (log.isErrorEnabled()) {
				log.error("输入的账户[" + acctCode + "]不存在");
			}
			throw new MaAcctLockException("输入的账户[" + acctCode + "]不存在");
		}

		return acctAttribDto;

	}

	private void handlerFreezeAcctWithAcctCode(Long memberCode, Integer acctType, Integer status, boolean isFreeze)
			throws MaAcctLockException {
		AcctDto acctDto = this.handlerQueryAcctWithMemberAcctCode(memberCode, acctType, true);
		AcctAttribDto acctAttribDto = this.handlerQueryAcctAttribWithAcctCode(acctDto.getAcctCode());
		if (isFreeze) {
			if (!acctAttribDto.getFrozen().equals(ConstantHelper.ACCT_FREEZE_STATUS)) {
				if (log.isErrorEnabled()) {
					log.error("该会员号：[" + memberCode + "]账户已经被冻结");
					throw new MaAcctLockException("该会员号：[" + memberCode + "]账户已经被冻结");
				}
			}

		} else {
			if (acctAttribDto.getFrozen().equals(ConstantHelper.ACCT_FREEZE_STATUS)) {
				if (log.isErrorEnabled()) {
					log.error("该会员号：[" + memberCode + "]账户已经解冻");
				}
				throw new MaAcctLockException("该会员号：[" + memberCode + "]账户已经解冻");
			}
		}
		boolean result = false;
		try {
			result = this.acctAttribService.updateAcctFreezeWithAcctCode(acctDto.getAcctCode(), status);
		} catch (AcctAttribException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		} catch (AcctAttribUnknowException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (!result) {
			if (log.isErrorEnabled()) {
				log.error("对于账号[" + acctDto.getAcctCode() + "]冻结或者解冻操作失败");
			}
			throw new MaAcctLockException("对于账号[" + acctDto.getAcctCode() + "]冻结或者解冻操作失败");
		}

	}

	private void handlerFreezeIn(String acctCode, Integer status, boolean isFreeze) throws MaAcctLockException {
		AcctAttribDto acctAttribDto = this.handlerQueryAcctAttribWithAcctCode(acctCode);
		// 进行冻结操作
		if (isFreeze) {
			if (!acctAttribDto.getAllowIn().equals(ConstantHelper.ALLOW_IN)) {
				if (log.isErrorEnabled()) {
					log.error("对账户[" + acctCode + "]已经被止入");
				}
				throw new MaAcctLockException("对账户[" + acctCode + "]已经被止入");
			}

		}// 进行解冻操作
		else {
			if (acctAttribDto.getAllowIn().equals(ConstantHelper.ALLOW_IN)) {
				if (log.isErrorEnabled()) {
					log.error("对账户[" + acctCode + "]已经解止入");
				}
				throw new MaAcctLockException("对账户[" + acctCode + "]已经解止入");
			}
		}
		boolean result = false;
		try {
			result = this.acctAttribService.updateAcctFreezeAllowInStatusWithAcctCode(acctCode, status);
		} catch (AcctAttribException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		} catch (AcctAttribUnknowException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (!result) {
			if (log.isErrorEnabled()) {
				log.error("对于账号[" + acctCode + "]止入或者解止入操作失败");
			}
			throw new MaAcctLockException("对于账号[" + acctCode + "]止入或者解止入操作失败");
		}
	}

	//对账户进行止出，解止出操作
	private void handlerFreezeOut(String acctCode, Integer status, boolean isFreeze) throws MaAcctLockException {
		AcctAttribDto acctAttribDto = this.handlerQueryAcctAttribWithAcctCode(acctCode);
		// 进行止出操作
		if (isFreeze) {
			if (!acctAttribDto.getAllowOut().equals(ConstantHelper.ALLOW_OUT)) {
				if (log.isErrorEnabled()) {
					log.error("对账户[" + acctCode + "]已经被止出");
				}
				throw new MaAcctLockException("对账户[" + acctCode + "]已经被止出");
			}

		}// 进行解止出操作
		else {	
			if (acctAttribDto.getAllowOut().equals(ConstantHelper.ALLOW_OUT)) {
				if (log.isErrorEnabled()) {
					log.error("对账户[" + acctCode + "]已经未止出");
				}
				throw new MaAcctLockException("对账户[" + acctCode + "]已经未止出");
			}
			//如果该账户不是调用接口API止出的，则不充许解止出
			if(this.acctAttribService.countAllowOutRecord(acctCode)==0){
				if (log.isErrorEnabled()) {
					log.error("对账户[" + acctCode + "]不是API止出的，则不充许进行解止出");
				}
				throw new MaAcctLockException("对账户[" + acctCode + "]不是API止出的，则不充许进行解止出");
			}else{//解止出，同时更新 止出的状态为解止出
				this.operateLogService.updateOperateLog(acctCode, AcctLockTypeEnum.UNFREEZE_OUT.getAcctLockTypeCode());
			}
		}
		boolean result = false;
		try {
			result = this.acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(acctCode, status);
		} catch (AcctAttribException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		} catch (AcctAttribUnknowException e) {
			if (log.isErrorEnabled()) {
				log.debug(e.getMessage(), e);
			}
			throw new MaAcctLockException(e.getMessage(), e);
		}
		if (!result) {
			if (log.isErrorEnabled()) {
				log.error("对于账号[" + acctCode + "]止出或者解止出操作失败");
			}
			throw new MaAcctLockException("对于账号[" + acctCode + "]止出或者解止出操作失败");
		}

	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param acctService
	 *            the acctService to set
	 */
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	/**
	 * @param acctAttribService
	 *            the acctAttribService to set
	 */
	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}


	public void setOperateLogService(OperateLogService operateLogService) {
    	this.operateLogService = operateLogService;
    }

}
