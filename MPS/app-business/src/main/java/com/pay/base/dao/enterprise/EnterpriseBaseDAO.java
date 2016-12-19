/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise;

import java.util.List;

import com.pay.base.model.EnterpriseBase;

/**
 * 企业会员基本信息DAO
 * @author wangzhi
 * @version $Id: EnterpriseBaseDAO.java, v 0.1 2010-9-30 下午02:08:13 wangzhi Exp $
 */
public interface EnterpriseBaseDAO {
    /**
     * 根据会员号查找企业会员基本信息
     *
     * @param memberCode
     * @return
     */
    public EnterpriseBase findByMemberCode(long memberCode);
    
    /**
     * 根据主键ID查找企业会员基本信息
     *
     * @param enterpriseId
     * @return
     */
    public EnterpriseBase findByEnterpriseId(long enterpriseId);
    
    /**
     * 根据merchantCode查找企业会员基本信息
     *
     * @param merchantCode
     * @return EnterpriseBase
     */
    public EnterpriseBase findByMerchantCode(long merchantCode);
    
    
    /**
     * 更新企业会员基本信息
     *
     * @param enterpriseBase
     * @return
     */
    public int updateBaseInfo(EnterpriseBase enterpriseBase);
    
    /**
     * 创建企业会员基本信息
     *
     * @param enterpriseBase
     * @return
     */
    public Long createBaseInfo(EnterpriseBase enterpriseBase);
    
    public List<EnterpriseBase> queryCurrMaxMerchantCode(String merchantCodeTemp);
    
    /**
     * 检查企业营业执照号码唯一性
     *
     * @param bizLicenceCode
     * @return
     */
    public boolean checkByBizLicenceCode(String bizLicenceCode);
    
    /**
     * 
     * 检查企业机构证件号码唯一性
     * @param govCode
     * @return
     */
	public boolean checkByGovCode(String govCode);
	
	/**
	 * 
	 * 检查企业税务等级证件号码唯一性
	 * @param taxCode
	 * @return
	 */
	public boolean checkByTaxCode(String taxCode);
}
