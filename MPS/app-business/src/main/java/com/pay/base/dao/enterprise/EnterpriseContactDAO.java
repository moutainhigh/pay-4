/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise;

import java.util.Map;

import com.pay.base.model.EnterpriseContact;

/**
 * 企业会员联系信息DAO
 * @author wangzhi
 * @version $Id: EnterpriseContactDAO.java, v 0.1 2010-9-30 下午03:47:25 wangzhi Exp $
 */
public interface EnterpriseContactDAO {
    /**
     * 根据会员号查找企业会员联系信息
     *
     * @param memberCode
     * @return
     */
    public EnterpriseContact findByMemberCode(long memberCode);
    /**
     * 根据主键查找企业会员联系信息
     *
     * @param contactId
     * @return
     */
    public EnterpriseContact findByContactId(long contactId);
    /**
     * 更新企业会员联系信息
     *
     * @param enterpriseContact
     * @return
     */
    public int updateContact(EnterpriseContact enterpriseContact); 
    
    /**
     * 创建企业会员联系信息
     *
     * @param enterpriseContact
     * @return
     */
    public Long createContact(EnterpriseContact enterpriseContact); 
    
    /**
     * 修改联系人信息
     * @param enterpriseContact
     * @return 修改的个数
     */
    public int updateEnterpriseLinker(EnterpriseContact enterpriseContact);
}
