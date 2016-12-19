package com.pay.acc.member.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dao.MemberDAO;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberMessageBeanDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.Member;
import com.pay.acc.member.model.MemberMessageBean;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;

public class MemberServiceImpl implements MemberService {

	private final Log logger = LogFactory.getLog(getClass());
	private MemberDAO memberDAO;
	private IndividualInfoService individualInfoService;
	private MemberAcctTemplateService memberAcctTemplateService;
	private AcctAttribService acctAttribService;
	private AcctService acctService;
	private IMessageDigest iMessageDigest;
	private MemberIdentityService memberIdentityService;

	public Long createMember(MemberDto memberDto) {
		// 创建会员
		Member member = new Member();
		BeanUtils.copyProperties(memberDto, member);
		Long memberCode = (Long) memberDAO.create(member);
		return memberCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.service.member.MemberService#queryMemberByMemberCode
	 * (long)
	 */
	public MemberDto queryMemberByMemberCode(Long memberCode) {
		Member member = this.memberDAO.queryMemberWithMemberCode(memberCode);
		return BeanConvertUtil.convert(MemberDto.class, member);
	}

	@Override
	public MemberDto queryMemberByLoginName(String loginName) {
		Member member = memberDAO.queryMemberByLoginName(loginName);
		return BeanConvertUtil.convert(MemberDto.class, member);
	}

	public MemberMessageBeanDto queryMemberInfo(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws MemberUnknowException {
		Map<String, Object> memberSearchMap = new HashMap<String, Object>();
		memberSearchMap.put("loginName", loginName);
		memberSearchMap.put("memberCode", memberCode);
		memberSearchMap.put("memberType", memberType);
		try {
			MemberMessageBean memberMessageBean = memberDAO
					.findMemberMessageBeanByMap("queryMemberInfo",
							memberSearchMap);
			return BeanConvertUtil.convert(MemberMessageBeanDto.class,
					memberMessageBean);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
	}

	@Override
	public List<Long> queryMemberByName(String name) {
		return memberDAO.queryMemberByName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.member.service.MemberService#queryMemberWithMemberCode
	 * (java.lang.Long)
	 */
	@Override
	public MemberDto queryMemberWithMemberCode(Long memberCode)
			throws MemberException, MemberUnknowException {
		if (memberCode == null || memberCode.longValue() <= 0) {
			throw new MemberException("输入的参数 " + memberCode + " 有误");
		}
		Member member = null;
		try {
			member = this.memberDAO.queryMemberWithMemberCode(memberCode);
			return BeanConvertUtil.convert(MemberDto.class, member);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.member.service.MemberService#queryMemberWithSsoUserId(java
	 * .lang.String)
	 */
	public MemberDto queryMemberWithSsoUserId(String ssoUserId)
			throws MemberException, MemberUnknowException {
		if (ssoUserId == null || !StringUtils.hasText(ssoUserId)) {
			throw new MemberException("ssoUserId输入的参数 " + ssoUserId + " 有误");
		}
		Member member = null;
		try {
			member = this.memberDAO.queryMemberWithSsoUserId(ssoUserId);
			return BeanConvertUtil.convert(MemberDto.class, member);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.member.service.MemberService#updateMember(com.pay.acc.member
	 * .dto.MemberDto)
	 */
	public boolean updateMember(MemberDto memberDto) throws MemberException,
			MemberUnknowException {
		if (null == memberDto) {
			throw new MemberException("memberDto参数输不正确");
		}
		boolean flag = false;
		try {
			flag = memberDAO.update(BeanConvertUtil.convert(Member.class,
					memberDto));
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return flag;
	}

	@Override
	public int unlockMember(MemberOperateDto mo, int frozenMinute) {
		Calendar c = Calendar.getInstance();
		c.setTime(mo.getUpdateTime());
		c.add(Calendar.MINUTE, frozenMinute);
		Calendar c1 = Calendar.getInstance();
		if (c1.after(c)) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("memberCode", mo.getMemberCode());
			paramMap.put("updateTime", new Date());
			return memberDAO.unlockMember(paramMap);
		}
		return -1;

	}

	@Override
	public int unlockMember(MemberOperateDto mo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", mo.getMemberCode());
		paramMap.put("updateTime", new Date());
		return memberDAO.unlockMember(paramMap);
	}

	@Override
	public boolean lockMember(Long memberCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("updateTime", new Date());
		return memberDAO.lockMember(paramMap);
	}

	@Override
	public boolean active(Long memberCode) {

		return memberDAO.active(memberCode);
	}

	@Override
	public boolean updateLoginPwd(Long memberCode, String loginPwd) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);

		try {
			loginPwd = iMessageDigest.genMessageDigest(loginPwd.getBytes());
		} catch (Exception e) {
			logger.error("updateLoginPwd error:", e);
		}

		paramMap.put("loginPwd", loginPwd);
		return memberDAO.updateLoginPwd(paramMap);
	}

	@Override
	public boolean updateLoginNameByMemberCode(MemberDto member)
			throws MemberException, MemberUnknowException {
		if (null == member) {
			throw new MemberException("memberDto参数输不正确");
		}
		boolean flag = false;
		try {
			flag = memberDAO.updateLoginNameByMemberCode(BeanConvertUtil
					.convert(Member.class, member));
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return flag;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setMemberIdentityService(
			MemberIdentityService memberIdentityService) {
		this.memberIdentityService = memberIdentityService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

}
