/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.enterprise.EnterpriseBaseDAO;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;

/**
 * 企业会员基本信息Service
 * @author wangzhi
 * @version $Id: EnterpriseBaseServiceImpl.java, v 0.1 2010-9-30 下午02:28:45 wangzhi Exp $
 */
public class EnterpriseBaseServiceImpl implements EnterpriseBaseService {
    
    private static final Log  logger = LogFactory.getLog(EnterpriseBaseServiceImpl.class);
    
    private EnterpriseBaseDAO enterpriseBaseDAO;
    /** 
     * @param enterpriseId
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseBaseService#findByEnterpriseId(long)
     */
    @Override
    public EnterpriseBase findByEnterpriseId(long enterpriseId) {
        return enterpriseBaseDAO.findByEnterpriseId(enterpriseId);
    }

    /** 
     * @param memberCode
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseBaseService#findByMemberCode(long)
     */
    @Override
    public EnterpriseBase findByMemberCode(long memberCode) {
        return enterpriseBaseDAO.findByMemberCode(memberCode);
    }

    /** 
     * @param enterpriseBase
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseBaseService#updateBaseInfo(com.pay.base.model.EnterpriseBase)
     */
    @Override
    public int updateBaseInfo(EnterpriseBase enterpriseBase) {
        if(enterpriseBase  != null &&  enterpriseBase.getEnterpriseId() > 0){
            return enterpriseBaseDAO.updateBaseInfo(enterpriseBase);
        } else {
            logger.error("更新企业会员基本信息时，参数为空！");
        }
        return 0;
    }

    public void setEnterpriseBaseDAO(EnterpriseBaseDAO enterpriseBaseDAO) {
        this.enterpriseBaseDAO = enterpriseBaseDAO;
    }

	/* (non-Javadoc)
	 * @see com.pay.base.service.enterprise.EnterpriseBaseService#findByMerchantCode(long)
	 */
	@Override
	public EnterpriseBase findByMerchantCode(long merchantCode) {
		return  enterpriseBaseDAO.findByMerchantCode(merchantCode);
	}

}
