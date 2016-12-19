package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.constant.AcctStatusEnum;
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaAcctUpdateException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountUpdateService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.service.IMessageDigest;

public class AccountUpdateServiceImpl implements AccountUpdateService {

	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private MemberService memberService;
	private IMessageDigest iMessageDigest;
	private Log log = LogFactory.getLog(AccountUpdateServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountUpdateService#
	 * doUpdateAccountForPayPwdRnTx(java.lang.Long, java.lang.Integer,
	 * java.lang.String)
	 */
	public boolean doUpdateAccountForPayPwdRnTx(Long memberCode, Integer acctType, String newPayPwd) throws MaAcctUpdateException {

		// 验证参数
		this.checkQueryParameter(memberCode, acctType, newPayPwd);
		this.checkQueryMember(memberCode);

		// 查询账户是否存在
		AcctDto bcctDto = null;
		try {
			bcctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, acctType);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (null == bcctDto) {
			// 账户不存在
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAcctUpdateException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号[" + memberCode + "]的账户不存在 ");
			// 需要判断用户是否有效，如果该用户无效或者冻结，就不能进行密码的设置
		}
		if (bcctDto.getStatus() == AcctStatusEnum.INVALID.getCode()) {
			log.error("会员号[" + memberCode + "]的账户无效");
			throw new MaAcctUpdateException(ErrorExceptionEnum.ACCT_INVALID_ERROR, "会员号[" + memberCode + "]的账户无效");
		}

		// 查询账户属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = acctAttribService.queryAcctAttribWithAcctCode(bcctDto.getAcctCode());
		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.ACCT_INVALID_ERROR, "invaild parameter", e);
		} catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (null == acctAttribDto) {
			// return UPDATE_FAIL; //账户属性不存在
			log.error("会员号[" + memberCode + "]的账户属性不存在");
			throw new MaAcctUpdateException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "会员号[" + memberCode + "]的账户属性不存在");
		}
		if (acctAttribDto.getFrozen().intValue() != ConstantHelper.ACCT_FREEZE_STATUS) {
			log.error("会员号为[" + memberCode + "]的账户被冻结");
			throw new MaAcctUpdateException(ErrorExceptionEnum.ACCT_FROZEN_ERROR, "会员号为[" + memberCode + "]的账户被冻结");
		}

		// 密码加密
		try {
			newPayPwd = iMessageDigest.genMessageDigest(newPayPwd.getBytes());
		} catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaAcctUpdateException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}

		boolean updateResult = UPDATE_FAIL;// 默认失败
		try {
			updateResult = acctAttribService.resetAcctAttribPwd(bcctDto.getAcctCode(), newPayPwd);
		} catch (Exception e) {
			log.error("memeber update pay password error");
			throw new MaAcctUpdateException(ErrorExceptionEnum.MEMBER_UPDATE_PASSWORD, "memeber update pay password error");
		}

		return updateResult;
	}

	// 验证参数
	private boolean checkQueryParameter(Long memberCode, Integer accountType, String newPayPwd) throws MaAcctUpdateException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		} else if (null == accountType || accountType.intValue() <= 0) {
			log.error(" invaild parameter , accountType is null or invaild! ");
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , accountType is null or invaild! ");
		} else if (null == newPayPwd || !StringUtils.hasText(newPayPwd)) {
			log.error(" invaild parameter , newPayPwd is null or invaild! ");
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , newPayPwd is null or invaild! ");
		}
		return true;

	}

	// 会员验证参数
	private MemberDto checkQueryMember(Long memberCode) throws MaAcctUpdateException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		// 验证会员是否激活
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaAcctUpdateException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaAcctUpdateException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为[" + memberCode + "]的会员");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaAcctUpdateException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public AcctAttribService getAcctAttribService() {
		return acctAttribService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

}
