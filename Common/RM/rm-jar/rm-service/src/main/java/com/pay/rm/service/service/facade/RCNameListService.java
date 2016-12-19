/**
 * 
 */
package com.pay.rm.service.service.facade;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.service.dto.rmnamelist.RcNameListDTO;

/**
 * @author Jason.li
 *
 */
public interface RCNameListService {
	/**
	 * 
	 * @param memberId
	 * @param businessId
	 * @param memberType 默认1 账户类型(10人民币账户99积分账户)
	 * @return
	 * @throws RMFacadeException
	 */
	public boolean isBlack(String memberId,int memberType,int businessId) throws RMFacadeException;
	/**
	 * 
	 * @param memberId
	 * @param businessId
	 * @param memberType 默认1 账户类型(10人民币账户99积分账户) 
	 * @return
	 * @throws RMFacadeException
	 */
	public boolean isWhite(String memberId,int memberType,int businessId) throws RMFacadeException;
	/**
	 * 返回值,0不存在 ,或者返回名单类型1白名单2黑名单
	 * @param memberId  用户ID
	 * @param businessId 
	 * @param memberType 默认1 账户类型(10人民币账户99积分账户)
	 * @return
	 * @throws RMFacadeException
	 */
	public int typeOfNameList(String memberId,int memberType,int businessId) throws RMFacadeException;
	/**
	 * 
	 * @param memberId
	 * @param memberType
	 * @param businessId
	 * @param nameType
	 * @return
	 * @throws RMFacadeException
	 */
	public long createNameList(RcNameListDTO rcNameListDTO) throws RMFacadeException;
	
}
