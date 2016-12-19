/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise;

import com.pay.base.model.EnterpriseBase;

/**
 * 企业会员基本信息Service
 * @author wangzhi
 * @version $Id: EnterpriseBaseService.java, v 0.1 2010-9-30 下午02:27:22 wangzhi Exp $
 */
public interface EnterpriseBaseService {
    
    /**
     * 根据会员号查找企业会员基本信息
     *
     * @param memberCode
     * @return
     */
    public EnterpriseBase findByMemberCode(long memberCode);
    
    /**
     * 根据merchantCode查找base信息
     *
     * @param merchantCode
     * @return EnterpriseBase
     */
    public EnterpriseBase findByMerchantCode(long merchantCode);
    
    /**
     * 根据主键ID查找企业会员基本信息
     *
     * @param enterpriseId
     * @return
     */
    public EnterpriseBase findByEnterpriseId(long enterpriseId);
    
    /**
     * 更新企业会员基本信息
     *
     * @param enterpriseBase
     * @return
     */
    public int updateBaseInfo(EnterpriseBase enterpriseBase);
}
