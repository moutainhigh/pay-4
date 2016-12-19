/**
 *  <p>File: RCLimitRuleFacadeDao.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.facade.model.RCLimitParam;
import com.pay.rm.facade.model.RCLimitResult;

/**
 * <p>
 * </p>
 * 
 * @author zengli
 * @since 2011-5-11
 * @see
 */
public interface RCLimitRuleFacadeDao extends BaseDAO {
	/**
	 * 企业会员查询商户限额
	 * 
	 * @param rcLimitParam
	 * @return
	 */
	RCLimitResult queryBusinessRcRule(RCLimitParam rcLimitParam);

	/**
	 * 企业会员查询定制商户限额
	 * 
	 * @param rcLimitParam
	 * @return
	 */
	RCLimitResult queryCustomBusinessRcRule(RCLimitParam rcLimitParam);

	/**
	 * 个人会员查询用户限额
	 * 
	 * @param rcLimitParam
	 * @return
	 */
	RCLimitResult queryUserRcRule(RCLimitParam rcLimitParam);

	/**
	 * 个人定制会员查询
	 * 
	 * @param rcLimitParam
	 * @return
	 */
	RCLimitResult queryCustomUserRcRule(RCLimitParam rcLimitParam);

}
