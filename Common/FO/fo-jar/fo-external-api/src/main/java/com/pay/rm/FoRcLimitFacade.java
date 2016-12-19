/**
 *  <p>File: FoRcLimitFacade.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm;

import com.pay.poss.base.exception.PossException;
import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * <p></p>
 * @author zengli
 * @since 2011-5-12
 * @see 
 */
public interface FoRcLimitFacade {
	/**
	 * 获取企业限额
	 * @param busiType
	 * @param mccCode
	 * @param memberCode
	 * @return
	 * @throws PossException
	 */
	public RCLimitResultDTO getBusinessRcLimit(int busiType,Long mccCode,long memberCode) ;
	/**
	 * 获取个人限额
	 * @param busiType
	 * @param mccCode
	 * @param memberCode
	 * @return
	 */
	public RCLimitResultDTO getUserRcLimit(int busiType,Long mccCode,long memberCode);

}
