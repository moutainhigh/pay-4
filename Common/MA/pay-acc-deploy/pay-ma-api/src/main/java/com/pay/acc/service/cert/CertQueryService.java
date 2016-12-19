/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.service.cert;

import com.pay.acc.service.cert.dto.CertManageDto;

/**
 * @author fjl
 * @date 2011-11-16
 */
public interface CertQueryService {


	/**
	 * 根据会员编号和操作员编号查询是否为证书用户
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public abstract boolean isCertUser(Long memberCode, Long operatorId);

	/**
	 * 根据会员编号和操作员编号查询证书状态(服务器CFCA 状态)
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	//public abstract CerStatusEnum queryUserCertStatus(Long memberCode, Long operatorId);
	
	/**
	 * 根据会员编号和操作员编号查询证书本地状态（本地数据库）
	 * @param memberCode
	 * @param operatorId
	 * @return MemberCert.StatusEnum;
	 */
	public abstract Integer queryUserCertLocalStatus(Long memberCode, Long operatorId);

	/**
	 * 根据会员编号和操作员编号验证是否合法证书用户即证书为激活状态
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public abstract boolean isValidCertUser(Long memberCode, Long operatorId);
	
	/**
	 * 根据会员编号，操作员编号，机器标示查询当前的使用地点
	 * @param memberCode
	 * @param operatorId
	 * @param machineId
	 * @return
	 */
	public CertManageDto queryUsePalce(Long memberCode,Long operatorId,String machineId);
	
	
	
	/**
	 * 根据会员编号，操作员编号，机器标示查询未安装完成的使用地点
	 * @param memberCode
	 * @param operatorId
	 * @param machineId
	 * @return
	 */
	public CertManageDto queryTempUsePalce(Long memberCode,Long operatorId,String machineId);

}
