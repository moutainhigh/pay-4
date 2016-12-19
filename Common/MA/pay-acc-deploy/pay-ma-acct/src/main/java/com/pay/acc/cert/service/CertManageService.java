/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service;

import java.util.List;

import com.pay.acc.cert.model.CertManage;

/**
 * @author fjl
 * @date 2011-11-15
 */
public interface CertManageService {

	
	/**
	 * 创建一个证书存放位置
	 * @param model
	 * @return
	 */
	public long createCerManage(CertManage model);
	
	/**
	 * 修改一个证书存放位置
	 * @param model
	 */
	public boolean modifyCerManage(CertManage model);
	
	/**
	 * 查询会员证书存放位置个数
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public int queryCount(Long memberCode, Long operatorId);
	
	/**
	 * 查询会员证书存放位置
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public List<CertManage> queryCertManage(Long memberCode, Long operatorId);
	
	/**
	 * 根据会员号,机器名查询证书
	 * @param memberCode
	 * @param operatorId
	 * @param mechineId
	 * @return CertManage/null
	 */
	public CertManage queryCertManage(Long memberCode, Long operatorId,String machineId);
	
	
	/**
	 * 根据会员号,机器名查询证书
	 * @param memberCode
	 * @param operatorId
	 * @param mechineId
	 * @return
	 */
	public CertManage queryTempCertManage(Long memberCode, Long operatorId,String machineId);
	
	/**
	 * 逻辑删除
	 * @param id
	 */
	public boolean logicDeleteById(Long id);
	
	/**
	 * 注销证书时，逻辑删除所有证书位置信息
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public void logicDeleteAll(Long memberCode, Long operatorId);
	

}
