/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.service.member;

import com.pay.base.dto.MemberRelationDto;



public interface MemberRelationService {
	
	/**
	 * 创建MemberRelationDto，返回id号
	 * @param currMemberCode
	 * @param usteelId
	 * @param usteelName
	 * @param fatherMemberCode
	 * @return id号
	 */
	public Long createMemberRelation(Long currMemberCode,Long  usteelId,String usteelName,Long fatherMemberCode);
	
	
	
	public MemberRelationDto getMemberRelationBySonMemberCode(Long sonMemberCode);
	/**
	 * 根据指定的会员编号确定是否为父子关系
	 * @param sonMemberCode
	 * @param fatherMemberCode
	 * @return
	 */
	public boolean isFatherAndSon(Long sonMemberCode,Long fatherMemberCode);
	
	/**
	 * 判断是否存在zhName在数据库中，zhName是全称 
	 * @param zhName
	 * @return true/false
	 */
	public boolean isExistsSonZhName(String zhName);
	
	/**
	 * 判断是否存在enName在数据库中，enName是简称
	 * @param enName
	 * @return true/false
	 */
	public boolean isExistsSonEnName(String enName);
	
	/**
	 * 判断中心商户号是否存在
	 * @param originCode
	 * @return true/false
	 */
	public boolean isExistsOriginCode(Long originCode);
}
