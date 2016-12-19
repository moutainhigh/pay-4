package com.pay.poss.externalmanager.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.externalmanager.model.Member;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		IMerchantDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */

public interface IExternalDao extends BaseDAO<Member> {
	public Long queryMemberByEmailAndOrgCode(String email,String orgCode);
	public Integer queryAccountByMemberCode(Long memberCode);
	public Long  isMemberActive(String memberName,String loginName);
	public Boolean  isMemberAccountActive(Long memberCode,Integer accountType);
}