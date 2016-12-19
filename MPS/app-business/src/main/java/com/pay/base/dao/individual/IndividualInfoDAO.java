/**
 *  File: BanklimitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.individual;

import com.pay.base.model.IndividualInfo;
import com.pay.inf.dao.BaseDAO;



public interface IndividualInfoDAO extends BaseDAO<IndividualInfo>{

    /**
     * 根据memberCode获取个人会员详细信息
     * @param memberCode
     * @return
     */
    public IndividualInfo getIndividualByMemberCode(Long memberCode);
    
    /**
     * 创建个人会员详细信息
     * @param individualInfo
     * @return
     */
    public Long createIndividualInfo(IndividualInfo individualInfo);
    
    /**
     * 校验个人会员email是否唯一
     * @param email
     * @return
     */
    public boolean checkEmail(String email);
    
    /**
     * 校验个人会员mobile是否唯一
     * @param mobile
     * @return
     */
    public boolean checkMobile(String mobile);

}
