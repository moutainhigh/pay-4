/**
 *  File: LinkerDAO.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.acc.member.dao;

import com.pay.acc.member.model.Linker;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 * 
 */
public interface LinkerDAO extends BaseDAO<Linker>{

	/**
	 * 根据双方的会员号查询是否互为联系人
	 * 
	 * @param myMemberCode
	 *            我会员号
	 * @param linkMemberCode
	 *            联系人会员号
	 * @return
	 */
	public Linker queryMyLinkerWithMemberCode(Long orgMemberCode, Long linkMemberCode);

}
