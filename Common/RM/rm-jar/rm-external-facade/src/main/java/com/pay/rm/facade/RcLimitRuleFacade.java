/**
 *  <p>File: RcLimitRuleProvider.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade;

import java.util.List;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.facade.dto.RCLimitParamDTO;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;

/**
 * <p>风控限额限次Facade</p>
 * @author zengli
 * @since 2011-5-11
 * @see 
 */
public interface RcLimitRuleFacade {
	/**
	 *  获取风控规则对象
	 * @param appType
	 * @param busiType
	 * @param memberType
	 * @param memberCode
	 * @return
	 */
	RCLimitResultDTO getRule(RCLimitParamDTO rcLimitParamDTO)throws RMFacadeException ;
	
	/**
	 * 检查mcc Code是否存在
	 * @param mcc
	 * @return
	 * @throws RMFacadeException
	 */
	public boolean isExistMcc(Long mccCode) throws RMFacadeException ;
	
	/**
	 * 获取风控等级
	 * @return
	 */
	public List<RcRiskLevel> getRiskLevelList();
	
}
