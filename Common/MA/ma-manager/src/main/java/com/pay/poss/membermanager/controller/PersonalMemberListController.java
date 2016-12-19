package com.pay.poss.membermanager.controller;
/*package com.pay.poss.membermanager.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import com.pay.ma.constant.CertTypeEnum;
import com.pay.ma.constant.MemberInfoStatusEnum;
import com.pay.ma.service.bo.member.PersonBO;
import com.pay.ma.service.bo.member.PersonSearchBO;
import com.pay.poss.base.dao.model.Page;
import com.pay.poss.base.util.PageUtils;
import com.pay.poss.membermanager.formbean.PersonalSearchFormBean;
import com.pay.poss.service.ma.MB4MaServiceApi;

*//**
 * 
 * @Description
 * @project poss-membermanager
 * @file PersonalMemberListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-3 gungun_zhang Create
 *//*
public class PersonalMemberListController extends SimpleFormController {

	private Log log = LogFactory.getLog(PersonalMemberListController.class);
	private MB4MaServiceApi memberService;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("MemberListController.referenceData() is running...");
		CertTypeEnum[] certTypeEnumArray = CertTypeEnum.values();
		MemberInfoStatusEnum[] MemberInfoStatusEnumArray = MemberInfoStatusEnum.values();
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("certTypeArray", certTypeEnumArray);
		dataMap.put("userStatusArray", MemberInfoStatusEnumArray);

		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("MemberListController.onSubmit() is running...");
		PersonalSearchFormBean memberSearchFormBean = (PersonalSearchFormBean) command;
		PersonSearchBO personSearchBO = new PersonSearchBO();

		if (StringUtils.isNotEmpty(memberSearchFormBean.getUserName())) {
			personSearchBO.setUserName(memberSearchFormBean.getUserName());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getMoblie())) {
			personSearchBO.setMoblie(memberSearchFormBean.getMoblie());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getEmail())) {
			personSearchBO.setEmail(memberSearchFormBean.getEmail());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getUserStatus())) {
			personSearchBO.setUserStatus(Integer.valueOf(memberSearchFormBean.getUserStatus()));
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getCertType())) {
			personSearchBO.setCertType(memberSearchFormBean.getCertType());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getCertNo())) {
			personSearchBO.setCertNo(memberSearchFormBean.getCertNo());
		}

		Page<PersonBO> page = PageUtils.getPage(request);

		List<PersonBO> memberList = memberService.queryPersonMember(personSearchBO, page.getPageNo(), page.getPageSize());
		if (memberList != null && memberList.size() != 0) {
			for (int i = 0; i < memberList.size(); i++) {
				PersonBO person = memberList.get(i);

				if (StringUtils.isNotEmpty(person.getCertType())) {
					CertTypeEnum certTypeEnum = CertTypeEnum.getByCode(Integer.valueOf(person.getCertType()));
					if (certTypeEnum != null) {
						person.setCertType(certTypeEnum.getDescription());
					}

				}
				person.setStatusString((MemberInfoStatusEnum.getByCode(person.getUserStatus()).getDescription()));
			}
		}
		Long memberListCount = memberService.queryPersonMemberCount(personSearchBO);
		page.setResult(memberList);
		page.setTotalCount(memberListCount.intValue());
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	}

	public void setMemberService(MB4MaServiceApi memberService) {
		this.memberService = memberService;
	}

	

}
*/