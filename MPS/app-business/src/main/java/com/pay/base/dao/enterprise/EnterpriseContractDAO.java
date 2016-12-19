/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.dao.enterprise;

import java.util.List;

import com.pay.base.model.EnterpriseContract;

/**
 *
 * @author zhi.wang
 * @version $Id: EnterpriseContractDAO.java, v 0.1 2011-2-21 下午03:32:53 zhi.wang Exp $
 */
public interface EnterpriseContractDAO {

	/**
     * 创建企业合同信息
     *
     * @param enterpriseContract
     * @return
     */
    public Long createContract(EnterpriseContract enterpriseContract); 
    
    
	public List<EnterpriseContract> getContractByMemberCode(Long memberCode);
}
