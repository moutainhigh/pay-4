/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise;

import com.pay.app.dto.UserPhoneDto;
import com.pay.base.model.EnterpriseContact;

/**
 * 企业会员联系信息Service
 * @author wangzhi
 * @version $Id: EnterpriseContactService.java, v 0.1 2010-9-30 下午03:55:40 wangzhi Exp $
 */
public interface EnterpriseContactService {
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
    * 修改联系人手机号码
    * @param memberCode
    * @param mobile
    * @return true/false
    */
    public boolean updateEnterpriseLinker(Long memberCode,String mobile);
}
