/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.enterprise.EnterpriseContactDAO;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.service.enterprise.EnterpriseContactService;

/**
 * 企业会员联系信息Service
 * @author wangzhi
 * @version $Id: EnterpriseContactServiceImpl.java, v 0.1 2010-9-30 下午03:56:35 wangzhi Exp $
 */
public class EnterpriseContactServiceImpl implements EnterpriseContactService {
    
    private static final Log  logger = LogFactory.getLog(EnterpriseContactServiceImpl.class);
    
    private EnterpriseContactDAO enterpriseContactDAO;

    /** 
     * @param contactId
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseContactService#findByContactId(long)
     */
    @Override
    public EnterpriseContact findByContactId(long contactId) {
        return enterpriseContactDAO.findByContactId(contactId);
    }

    /** 
     * @param memberCode
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseContactService#findByMemberCode(long)
     */
    @Override
    public EnterpriseContact findByMemberCode(long memberCode) {
        return enterpriseContactDAO.findByMemberCode(memberCode);
    }

    /** 
     * @param enterpriseContact
     * @return
     * @see com.pay.base.service.enterprise.EnterpriseContactService#updateContact(com.pay.base.model.EnterpriseContact)
     */
    @Override
    public int updateContact(EnterpriseContact enterpriseContact) {
        if(enterpriseContact != null && enterpriseContact.getContactId() > 0 ){
            return enterpriseContactDAO.updateContact(enterpriseContact);
        } else {
            logger.error("更新企业会员联系信息时，参数为空！");
        }
        return 0;
    }

    public void setEnterpriseContactDAO(EnterpriseContactDAO enterpriseContactDAO) {
        this.enterpriseContactDAO = enterpriseContactDAO;
    }

	/* (non-Javadoc)
	 * @see com.pay.base.service.enterprise.EnterpriseContactService#updateEnterpriseLinker(java.lang.Long, java.lang.String)
	 */
	@Override
	public boolean updateEnterpriseLinker(Long memberCode, String mobile) {
		EnterpriseContact enterpriseContact = new EnterpriseContact();
		enterpriseContact.setCompayLinkerTel(mobile);
		enterpriseContact.setMemberCode(memberCode);
		return enterpriseContactDAO.updateEnterpriseLinker(enterpriseContact) == 1;
	}

	
}
