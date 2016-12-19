/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise.impl;


import java.util.List;

import com.pay.base.dao.enterprise.EnterpriseBaseDAO;
import com.pay.base.model.EnterpriseBase;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 企业会员基本信息DAO
 * @author wangzhi
 * @version $Id: EnterpriseBaseDAOImpl.java, v 0.1 2010-9-30 下午02:17:12 wangzhi Exp $
 */
public class EnterpriseBaseDAOImpl extends BaseDAOImpl  implements EnterpriseBaseDAO {

    /**
     * 
     * @param enterpriseId
     * @return
     * @see com.pay.base.dao.enterprise.EnterpriseBaseDAO#findByEnterpriseId(long)
     */
    @Override
    public EnterpriseBase findByEnterpriseId(long enterpriseId) {
        return (EnterpriseBase)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findByEnterpriseId"), enterpriseId);
    }

    /** 
     * @param memberCode
     * @return
     * @see com.pay.base.dao.enterprise.EnterpriseBaseDAO#findByMemberCode(long)
     */
    @Override
    public EnterpriseBase findByMemberCode(long memberCode) {
        return (EnterpriseBase)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findByMemberCode"), memberCode);
    }

    /** 
     * @param enterpriseBase
     * @return
     * @see com.pay.base.dao.enterprise.EnterpriseBaseDAO#updateBaseInfo(com.pay.base.model.EnterpriseBase)
     */
    @Override
    public int updateBaseInfo(EnterpriseBase enterpriseBase) {
        return this.getSqlMapClientTemplate().update(getNamespace().concat("updateEnterpriseBase"), enterpriseBase);
    }

	@Override
	public Long createBaseInfo(EnterpriseBase enterpriseBase) {
		return (Long) super.create(enterpriseBase);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EnterpriseBase> queryCurrMaxMerchantCode(String merchantCodeTemp) {
		return (List<EnterpriseBase>) this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryCurrMaxMerchantCode"), merchantCodeTemp);
	}
	
	public boolean checkByBizLicenceCode(String bizLicenceCode){
		List list =  this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryByBizLicenceCode"), bizLicenceCode);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	public boolean checkByGovCode(String govCode){
		List list =  this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryByGovCode"), govCode);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}
	public boolean checkByTaxCode(String taxCode){
		List list = this.getSqlMapClientTemplate().queryForList(getNamespace().concat("queryByTaxCode"), taxCode);
		if(list != null && list.size() >0){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.pay.base.dao.enterprise.EnterpriseBaseDAO#findByMerchantCode(long)
	 */
	@Override
	public EnterpriseBase findByMerchantCode(long merchantCode) {
		return (EnterpriseBase)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("findByMerchantCode"), merchantCode);
	}
}
