/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午06:11:12 2010-11-11
 */
package com.pay.base.dao.memberaccttemplate.impl;

import java.util.Map;

import com.pay.base.dao.memberaccttemplate.MemberAcctTemplateDAO;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午06:11:12 & 2010-11-11
 */

public class MemberAcctTemplateDAOImpl extends BaseDAOImpl implements
		MemberAcctTemplateDAO {

	public String getSubjectCode(Map map) {
		return (String) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("getSubjectCode"), map);
	}

}
