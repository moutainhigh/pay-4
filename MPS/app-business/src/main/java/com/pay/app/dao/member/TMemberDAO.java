/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.member;

import com.pay.app.model.TMember;



/**
 * 会员基本信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:10:48
 * 
 */
public interface TMemberDAO {
	
    /**
     * 根据会员号查找会员信息
     * @param memberCode
     * @return
     */
    public TMember findMemberByMemCode(long memberCode);
    
    /**
     * 更新会员信息
     * @param member
     * @return
     */
    public int updateMember(TMember member);
    
    
    /**
     * 根据登录名查询会员 (add by zengjin)
     * @param loginName
     * @return
     */
    public Long findMemberByLoginName(String loginName);
    
}
