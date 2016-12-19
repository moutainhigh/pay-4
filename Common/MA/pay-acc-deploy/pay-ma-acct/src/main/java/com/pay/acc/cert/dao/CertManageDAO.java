/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.dao;

import java.util.List;

import com.pay.acc.cert.model.CertManage;
import com.pay.inf.dao.BaseDAO;

/**
* @author fjl
* @date 2011-11-15
*/
public interface CertManageDAO extends BaseDAO<CertManage>{
	
	/**
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public int queryCount(Long memberCode, Long operatorId);
	
	/**
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public List<CertManage> queryCertManage(Long memberCode, Long operatorId);
	
	/**
	 * 查询使用地点是哪里
	 * @param memberCode
	 * @param operatorId
	 * @param machineId
	 * @return CertManage或是null
	 */
	public CertManage queryCertManage(Long memberCode, Long operatorId,String machineId );
	
	
	/**
	 * 查询未完成的使用地点
	 * @param memberCode
	 * @param operatorId
	 * @param machineId
	 * @return
	 */
	public CertManage queryTempCertManage(Long memberCode, Long operatorId,String machineId );
	
	/**
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateStatus(Long id ,CertManage.StatusEnum status);
	
	
	/**
	 * 注销所有证书位置
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public void disableAll(Long memberCode, Long operatorId);
	
	
}
