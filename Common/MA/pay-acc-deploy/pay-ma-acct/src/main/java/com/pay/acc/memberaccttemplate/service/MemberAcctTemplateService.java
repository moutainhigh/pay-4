/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午07:17:36 2010-11-11
 */
package com.pay.acc.memberaccttemplate.service;

import java.util.List;
import java.util.Map;

import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;

/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午07:17:36 & 2010-11-11
 */

public interface MemberAcctTemplateService {

	/**
	 * 根据会员类型获取账户模版
	 * 
	 * @param memberType
	 * @return
	 */
	List<MemberAcctTemplateDto> queryTempalteByMemberType(Integer memberType);

	/**
	 * 获取会员默认账户模版
	 * 
	 * @param memberType
	 * @return
	 */
	MemberAcctTemplateDto queryDefaultTempateByMemberType(Integer memberType);

	/**
	 * 
	 * @param memberType
	 * @param acctType
	 * @return
	 */
	MemberAcctTemplateDto queryByMemberTypeAndAcctType(Integer memberType,
			Integer acctType);

	/**
	 * 查询 科目号
	 * 
	 * @author Sunny Ying
	 * @param map
	 * @throw null
	 * @return String
	 */
	public String getSubjectCode(Map map);
}
