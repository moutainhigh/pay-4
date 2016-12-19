/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午07:18:42 2010-11-11
 */
package com.pay.acc.memberaccttemplate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.memberaccttemplate.dao.MemberAcctTemplateDAO;
import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;
import com.pay.acc.memberaccttemplate.model.MemberAcctTemplate;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午07:18:42 & 2010-11-11
 */

public class MemberAcctTemplateServiceImpl implements MemberAcctTemplateService {

	private MemberAcctTemplateDAO memberAcctTemplateDAO;

	public String getSubjectCode(Map map) {
		return memberAcctTemplateDAO.getSubjectCode(map);
	}

	public void setMemberAcctTemplateDAO(
			MemberAcctTemplateDAO memberAcctTemplateDAO) {
		this.memberAcctTemplateDAO = memberAcctTemplateDAO;
	}

	@Override
	public List<MemberAcctTemplateDto> queryTempalteByMemberType(
			Integer memberType) {

		List<MemberAcctTemplate> memberAcctTempaltes = memberAcctTemplateDAO
				.findByCriteria("queryAcctTemplateByMemberType", memberType);

		return (List<MemberAcctTemplateDto>) BeanConvertUtil.convert(
				MemberAcctTemplateDto.class, memberAcctTempaltes);
	}

	@Override
	public MemberAcctTemplateDto queryDefaultTempateByMemberType(
			Integer memberType) {

		List<MemberAcctTemplate> memberAcctTemplates = memberAcctTemplateDAO
				.findByCriteria("queryDefaultTempateByMemberType", memberType);
		if (null != memberAcctTemplates) {
			return (MemberAcctTemplateDto) BeanConvertUtil.convert(
					MemberAcctTemplateDto.class, memberAcctTemplates.get(0));
		} else {
			return null;
		}

	}

	@Override
	public MemberAcctTemplateDto queryByMemberTypeAndAcctType(
			Integer memberType, Integer acctType) {
		Map paraMap = new HashMap();
		paraMap.put("memberType", memberType);
		paraMap.put("acctType", acctType);
		MemberAcctTemplate memberAcctTemplate = (MemberAcctTemplate) memberAcctTemplateDAO
				.findObjectByCriteria("queryByMemberTypeAndAcctType", paraMap);
		return (MemberAcctTemplateDto) BeanConvertUtil.convert(
				MemberAcctTemplateDto.class, memberAcctTemplate);
	}

}
