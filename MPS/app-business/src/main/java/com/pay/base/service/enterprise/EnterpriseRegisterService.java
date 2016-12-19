/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.service.enterprise;

import com.pay.base.dto.EnterpriseRegisterInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.LiquidateInfo;
import com.pay.inf.exception.AppException;

/**
 * 企业会员注册服务
 * @author zhi.wang
 * @version $Id: EnterpriseRegisterService.java, v 0.1 2011-2-21 上午10:49:29 zhi.wang Exp $
 */
public interface EnterpriseRegisterService {
	/**
	 * 
	 * 企业会员注册，填写基本信息(无事务).
	 * @param info
	 * @return
	 * @throws AppException
	 */
	public ResultDto doRegisterBaseInfo(EnterpriseRegisterInfo info)throws AppException; 

	/**
	 * 企业会员注册
	 * @param info
	 * @param liquidateInfo
	 * @return
	 * @throws AppException
	 */
	public ResultDto doRegisterRdTx(EnterpriseRegisterInfo info,LiquidateInfo liquidateInfo) throws AppException;
	
	/**
	 * 检查企业营业执照号码\企业机构证件号码\企业税务等级证件号码  唯一性 <br>
	 * return 0 : 检查通过;1 : 企业营业执照号码 不唯一;<br>
	 * 2 : 企业机构证件号码 不唯一; 3 : 企业税务等级证件号码 不唯一.
	 * @param info
	 * @return
	 */
	public int checkUnique(EnterpriseRegisterInfo info);
	/**
	 * 
	 * @param memberCode
	 * @return EnterpriseContract
	 */
	public EnterpriseContract getContractByMemberCode(Long memberCode);
}
