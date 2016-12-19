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
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaAcctVerifyException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountVerifyService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.service.IMessageDigest;

public class AccountVerifyServiceImpl implements AccountVerifyService {
	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private MemberService memberService;
	private IMessageDigest iMessageDigest;
	private Log log = LogFactory.getLog(AccountVerifyServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountVerifyService#
	 * doVerifyAccountForPayPasswordNsTx(java.lang.Long, java.lang.Integer,
	 * java.lang.String)
	 */
	public boolean doVerifyAccountForPayPasswordNsTx(Long memberCode, Integer accountType, String payPwd) throws MaAcctVerifyException {

		// 验证参数
		this.checkQueryParameter(memberCode, accountType, payPwd);
		this.checkQueryMember(memberCode);

		// 查询账户是否存在
		AcctDto bcctDto = null;
		try {
			bcctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, accountType);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter ", e);
		} catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (null == bcctDto) {
			// return VERIFY_FAIL; //账户不存在
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaAcctVerifyException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号[" + memberCode + "]的账户不存在 ");
		}

		// 查询账户属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = acctAttribService.queryAcctAttribWithAcctCode(bcctDto.getAcctCode());
		} catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter ", e);
		} catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (null == acctAttribDto) {
			// return VERIFY_FAIL; //账户属性不存在
			log.error("会员号[" + memberCode + "]的账户属性不存在");
			throw new MaAcctVerifyException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "会员号[" + memberCode + "]的账户属性不存在");
		}

		// 密码加密
		try {
			payPwd = iMessageDigest.genMessageDigest(payPwd.getBytes());
		} catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaAcctVerifyException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}

		boolean verifyResult = VERIFY_FAIL;// 默认失败
		try {
			verifyResult = payPwd.equals(acctAttribDto.getPayPwd()); // 匹配
		} catch (Exception e) {
			log.error("member verify pay password error");
			throw new MaAcctVerifyException(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR, "member verify pay  password error");
		}

		return verifyResult;
	}

	// 验证参数
	private boolean checkQueryParameter(Long memberCode, Integer accountType, String payPwd) throws MaAcctVerifyException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or invaild! ");
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or invaild! ");
		} else if (null == accountType || accountType.intValue() <= 0) {
			log.error(" invaild parameter , accountType is null or invaild ! ");
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , accountType is null or invaild! ");
		} else if (null == payPwd || !StringUtils.hasText(payPwd)) {
			log.error(" invaild parameter , payPwd is null or invaild! ");
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , payPwd is null or invaild! ");
		}
		return true;

	}

	// 会员验证参数
	private MemberDto checkQueryMember(Long memberCode) throws MaAcctVerifyException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or invaild! ");
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or invaild! ");
		}
		// 验证会员是否激活
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaAcctVerifyException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaAcctVerifyException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "member non exist error");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaAcctVerifyException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非"
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
