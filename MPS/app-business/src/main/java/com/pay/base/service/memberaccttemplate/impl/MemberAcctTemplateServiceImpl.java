/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午07:18:42 2010-11-11
 */
package com.pay.base.service.memberaccttemplate.impl;

import java.util.Map;

import com.pay.base.dao.memberaccttemplate.MemberAcctTemplateDAO;
import com.pay.base.service.memberaccttemplate.MemberAcctTemplateService;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午07:18:42 & 2010-11-11
 */

public class MemberAcctTemplateServiceImpl implements MemberAcctTemplateService{

		private MemberAcctTemplateDAO memberAcctTemplateDAO;

				public String getSubjectCode(Map map) {
					return memberAcctTemplateDAO.getSubjectCode(map);
				}

				public void setMemberAcctTemplateDAO(MemberAcctTemplateDAO memberAcctTemplateDAO) {
					this.memberAcctTemplateDAO = memberAcctTemplateDAO;
				}

}
