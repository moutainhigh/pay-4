/**
 *  <p>File: RCLimitRuleFacadeDaoImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.facade.dao.RCLimitRuleFacadeDao;
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
@SuppressWarnings("unchecked")
public class RCLimitRuleFacadeDaoImpl extends BaseDAOImpl implements
		RCLimitRuleFacadeDao {

	@Override
	public RCLimitResult queryBusinessRcRule(RCLimitParam rcLimitParam) {
		return (RCLimitResult) this.findObjectByCriteria("queryBusinessRcRule", rcLimitParam);
	}

	@Override
	public RCLimitResult queryCustomBusinessRcRule(RCLimitParam rcLimitParam) {
		return (RCLimitResult) this.findObjectByCriteria("queryCustomBusinessRcRule", rcLimitParam);
	}

	@Override
	public RCLimitResult queryUserRcRule(RCLimitParam rcLimitParam) {
		return (RCLimitResult) this.findObjectByCriteria("queryUserRcRule", rcLimitParam);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.rm.facade.dao.RCLimitRuleFacadeDao#queryCustomUserRcRule(com
	 * .pay.rm.facade.model.RCLimitParam)
	 */
	@Override
	public RCLimitResult queryCustomUserRcRule(RCLimitParam rcLimitParam) {
		return (RCLimitResult) this.findObjectByCriteria("queryCustomUserRcRule", rcLimitParam);
	}

}
