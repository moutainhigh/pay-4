/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午08:56:55 2010-11-9
 */
package com.pay.base.service.individualseller;

import java.util.Map;

import com.pay.base.common.enums.ServiceLevelEnum;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午08:56:55 & 2010-11-9
 */

public interface IndividualSellerService {

	/**
	 * 验证用户的实名认证状态
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return boolean
	 */
	public boolean checkUserCidVerify(String memberCode);
	
	
	/**
	 * 开通 个人卖家的 各种账户
	 * @author Sunny Ying
	 * @param map
	 * @throw null
	 * @return int
	 */
	public int openUserAccountRdTx(Map<String,String> map);
	
	/**
	 * 更新用户为 个人卖家身份  (废弃)
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public int editMemberTypeByMemberCode(String memberCode);
	
	/**
	 * 根据账号类型 和  科目Id 查询 科目代号
	 * @author Sunny Ying
	 * @param map
	 * @throw null
	 * @return String
	 */
	public String getSubjectCode(Map map);
	
	/**
	 * 查询个人卖家有多少个 行业卡账户
	 * @author Sunny Ying
	 * @param map
	 * @throw null
	 * @return Map
	 */
	public Map isExitsCardAccount(Map<String,String> map);
	
	/**
	 * 更新用户为 个人卖家身份
	 * @author Sunny Ying
	 * @param sellerCode
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public int editMemberTypeByMemberCode(Integer sellerCode,String memberCode);
}
