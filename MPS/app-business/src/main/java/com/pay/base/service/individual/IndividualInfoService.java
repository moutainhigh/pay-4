/**
 *  File: BanklimitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.individual;

import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberInfoDto;




public interface IndividualInfoService{

	/**
	 * 查询会员信息
	 * @param memberCode
	 * @return
	 */
	public IndividualInfoDto queryIndividualInfoByMemberCode(Long memberCode);
	
	
	
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
    
	/**
	 * 更新会员详细信息
	 * @param individualInfoDto
	 * @return
	 */
	public boolean updateIndividualInfo(IndividualInfoDto individualInfoDto);

    
    /**
     * 创建个人会员详细信息
     * @param individualInfoDto
     * @return
     */
    public Long createIndividualInfo(IndividualInfoDto individualInfoDto);
    
    
    /**组装详细信息对象
     * @param memberInfoDto
     * @param memberCode
     * @return
     */
    public IndividualInfoDto convertIndividualInfo(MemberInfoDto memberInfoDto,Long memberCode);
}
