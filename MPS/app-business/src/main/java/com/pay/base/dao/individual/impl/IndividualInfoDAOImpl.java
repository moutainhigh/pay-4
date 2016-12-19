/**
 *  File: BanklimitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.individual.impl;

import com.pay.base.dao.individual.IndividualInfoDAO;
import com.pay.base.model.IndividualInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;




public class IndividualInfoDAOImpl extends BaseDAOImpl<IndividualInfo> implements IndividualInfoDAO{

    @Override
    public IndividualInfo getIndividualByMemberCode(Long memberCode){
        return (IndividualInfo)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("queryIndividualInfoByMemberCode"), memberCode);
    }
    
    @Override
    public Long createIndividualInfo(IndividualInfo individualInfo){
        return (Long)super.create(individualInfo);
    }

    
    public boolean checkEmail(String email){
        int result=1;
        result=(Integer)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("getCountByEmail"), email);
        if(result==0)
            return true;
        return false;
    }
    
    public boolean checkMobile(String mobile){
        int result=1;
        result=(Integer)this.getSqlMapClientTemplate().queryForObject(getNamespace().concat("getCountByMobile"), mobile);
        if(result==0)
            return true;
        return false;
    }
}