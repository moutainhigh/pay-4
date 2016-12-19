/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.dao.member;

import java.util.List;
import java.util.Map;

import com.pay.base.dto.MemberBalancesDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.model.Member;



/**
 * 会员基本信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:10:48
 * 
 */
public interface MemberDAO {
	
    /**
     * 根据会员号查找会员信息
     * @param memberCode
     * @return
     */
    public Member findMemberByMemCode(long memberCode);
    
    
    /**
     * 验证会员的安全问题是否正确
     * @param memberCode 会员号
     * @param questionId 安全问题ID
     * @param securInfo 安全答案的信息
     * @return
     */
    public Integer validateSecurMemberQuestion(Long memberCode,Integer questionId,String securInfo);
    
    
    
    /**
     * 更新会员信息
     * @param member
     * @return
     */
    public int updateMember(Member member);
    
    /**
     * 更新ssoUserid
     * @param ssoMap
     * @return
     */
    public boolean updateMemberSsoUserid(Map<String,Object> ssoMap);
    
    
    /**
     * 根据登录名查询会员 
     * @param loginName
     * @return
     */
    public Member findMemberByLoginName(String loginName);
    
    /**
     * 根据登录名查询会员 
     * @param loginName
     * @param type
     * @return
     */
	public Long findMemberCodeByLoginName(String loginName);
    
    /**
     * 查询会员信息
     * @param loginName
     * @param type
     * @return
     */
    public Member findMemberByLogin(String loginName,Integer type);
    
    
    /**
     * 根据登录获取memberCode
     * @param loginName
     * @param loginPwd
     * @param memberType 登录角色（企业，个人）
     * @return
     */
    public Long findMemberCodeByLogin(String loginName,String loginPwd,int memberType);
    
    
    /**
     * 更新激活状态
     * @param memberCode
     * @return
     */
    public int updateMemberStatus(Long memberCode);
    
    
    /**
     * 校验登录名是否唯一
     * @param loginName
     * @return
     */
    public boolean checkIndividualLoginName(String loginName);
    
    /**
     * 校验登录名是否唯一
     * @param loginName
     * @return 会员状态
     */
    public Integer checkIndividualLoginNameAndStatus(String loginName);
    
    /**创建个人会员基本信息
     * @param member
     * @return
     */
    public Long createIndividualMember(Member member);
    
    /**
     * 创建会员基本信息
     *
     * @param member
     * @return
     */
    public Long createCorpMember(Member member);
    
    public Member convertIndividualMember(MemberInfoDto memberInfo);
    
    /**
     * 根据ssoUserid查询会员
     * @param ssoUserid
     * @return
     */
    public Member queryMemberBySsoUserid(String ssoUserid);
    /**
     * 根据ssoUserid查询会员
     * @param ssoUserid
     * @return
     */
    public Member queryPersonMemberBySsoUserid(String ssoUserid);
    
    /**
     * 根据会员号更新会员状态
     *
     * @param memberCode
     * @param status
     * @return
     */
    public int updateStatusByMemberCode(long memberCode,int status);
    
    /**
     * 更新 用户为  个人卖家  身份
     * @author Sunny Ying
     * @param map
     * @throw null
     * @return int
     */
    public int editMemberTypeByMemberCode(Map map);
    
    /**
     * 更新会员服务级别
     * @param serviceLevelCode
     * @param memberCode
     * @return
     */
    public int updateServiceLevelByMemCode(int serviceLevelCode,long memberCode);
    
    
    /**
	 * 根据父会员编号查询子会员信息
	 * @param memberCode
	 * @return
	 */
	public List<MemberBalancesDto> querySonMemberByParent(Map<String,Object> param);
	
	/**
	 * 根据父会员编号查询子会员数量
	 * @param param
	 * @return
	 */
	public int querySonMemberCountByParent(Map<String,Object> param);
}
