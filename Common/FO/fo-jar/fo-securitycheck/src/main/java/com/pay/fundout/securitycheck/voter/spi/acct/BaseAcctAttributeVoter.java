/** @Description 
 * @project 	fo-securitycheck
 * @file 		AcctAttributeVoter.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-28		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.voter.spi.acct;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.fundout.securitycheck.common.VoterFamilyEnum;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.voter.AbstractVoter;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;

/**
 * <p>
 * 账户属性基础投票器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-28
 */
public abstract class BaseAcctAttributeVoter extends AbstractVoter {
	private boolean cachePayerAcctInfo = true;
	private boolean cachePayeeAcctInfo = true;
	protected MassPayService massPayService;
	protected Set<Integer> payerUndo = new HashSet<Integer>();
	protected Set<Integer> payeeUndo = new HashSet<Integer>();

	public BaseAcctAttributeVoter() {
		this.familyCode = VoterFamilyEnum.ACCT_FAMILY.getCode();
	}

	protected final AcctAttribDto cachePayerAcctIfNecessary(Principal principal) throws PossUntxException {
		if (cachePayerAcctInfo == false) {
			return getAcctAttrInfo(principal.getPayerMemberCode(), principal.getPayerAcctType());
		}

		AcctAttribDto acctAttribute = principal.getPayer();
		if (acctAttribute != null) {
			return acctAttribute;
		} else {
			acctAttribute = getAcctAttrInfo(principal.getPayerMemberCode(), principal.getPayerAcctType());
			principal.setPayer(acctAttribute);
			return acctAttribute;
		}
	}

	protected final AcctAttribDto cachePayeeAcctIfNecessary(Principal principal) throws PossUntxException {
		if (cachePayeeAcctInfo == false) {
			return getAcctAttrInfo(principal.getPayeeMemberCode(), principal.getPayeeAcctType());
		}

		AcctAttribDto acctAttribute = principal.getPayee();
		if (acctAttribute != null) {
			return acctAttribute;
		} else {
			acctAttribute = getAcctAttrInfo(principal.getPayeeMemberCode(), principal.getPayeeAcctType());
			principal.setPayee(acctAttribute);
			return acctAttribute;
		}
	}

	private AcctAttribDto getAcctAttrInfo(String memberCode, String acctType) throws PossUntxException {
		// 如果会员号或账户类型为空，则不进行账户信息查询 比如：中间科目情况
		if (StringUtils.isEmpty(memberCode) || StringUtils.isEmpty(acctType) || memberCode.length() == 13) {
			return null;
		}

		try {
			return massPayService.getAcctAttrInfo(new Long(memberCode), new Integer(acctType));
		} catch (Exception e) {
			logger.error("查询账户信息失败 [memberCode=" + memberCode + ",acctType=" + acctType + "]", e);
			throw new PossUntxException("查询账户信息失败 [memberCode=" + memberCode + ",acctType=" + acctType + "]", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
	}

	protected final MemberBaseInfoBO cachePayerMemberIfNecessary(Principal principal) throws PossUntxException {
		MemberBaseInfoBO memberInfo = principal.getPayerMember();
		if (memberInfo != null) {
			return memberInfo;
		} else {
			memberInfo = getMemberInfo(principal.getPayerMemberCode());
			principal.setPayerMember(memberInfo);
			return memberInfo;
		}
	}

	protected final MemberBaseInfoBO cachePayeeMemberIfNecessary(Principal principal) throws PossUntxException {
		MemberBaseInfoBO memberInfo = principal.getPayeeMember();
		if (memberInfo != null) {
			return memberInfo;
		} else {
			memberInfo = getMemberInfo(principal.getPayeeMemberCode());
			principal.setPayeeMember(memberInfo);
			return memberInfo;
		}
	}

	private MemberBaseInfoBO getMemberInfo(String memberCode) throws PossUntxException {
		// 如果会员号为空，则不进行账户信息查询 比如：中间科目情况
		if (StringUtils.isEmpty(memberCode) || memberCode.length() == 13) {
			return null;
		}
		try {
			return massPayService.queryMemberBaseInfoByMemberCode(new Long(memberCode));
		} catch (Exception e) {
			logger.error("查询会员信息失败 [memberCode=" + memberCode + "]", e);
			throw new PossUntxException("查询账户信息失败 [memberCode=" + memberCode + "]", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
	}

	public boolean isCachePayerAcctInfo() {
		return cachePayerAcctInfo;
	}

	public void setCachePayerAcctInfo(boolean cachePayerAcctInfo) {
		this.cachePayerAcctInfo = cachePayerAcctInfo;
	}

	public boolean isCachePayeeAcctInfo() {
		return cachePayeeAcctInfo;
	}

	public void setCachePayeeAcctInfo(boolean cachePayeeAcctInfo) {
		this.cachePayeeAcctInfo = cachePayeeAcctInfo;
	}

	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	public void setPayerUndo(Set<Integer> payerUndo) {
		this.payerUndo.addAll(payerUndo);
	}

	public void setPayeeUndo(Set<Integer> payeeUndo) {
		this.payeeUndo.addAll(payeeUndo);
	}

}
