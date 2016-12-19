/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise;

import java.util.List;

import com.pay.base.model.LiquidateInfo;
/**
 * 企业会员结算信息
 * @author zhi.wang
 * @version $Id: LiquidateInfo.java, v 0.1 2010-10-12 上午10:35:24 zhi.wang Exp $
 */
public interface LiquidateInfoDAO {
    
    /**
     * 根据会员号获取企业会员结算信息
     *
     * @param memberCode
     * @return
     */
    public List<LiquidateInfo> getByMemberCode(long memberCode);

    /**
     * 保存企业结算信息
     * @param li
     * @return
     */
	public Long createLiquidateInfo(LiquidateInfo li);
	
	 /**
     * 更新企业结算信息
     * @param li 
     * @return 
     */
	public int updateLiquidateInfo(LiquidateInfo li);
	
	/**
	 * 得到共绑定了几张卡
	 * @param memberCode
	 * @return  整数
	 */
	public int getCountByMemberCode(Long memberCode) ;
	
	/**
	 * 删除一个绑定的卡号
	 * @param memberCode 用户号
	 * @param id id
	 * @return 如果是1表示成功
	 * @author 戴德荣
	 */
	public int removeByMemberCodeAndId(String memberCode,Long id);
	
	/**
	 * 得到一个绑定卡号信息
	 * @param id id
	 * @return LiquidateInfo
	 * @author 戴德荣
 	 */
	public LiquidateInfo getById(Long id);
	/**
	 * 根据用户号和id号来更新状态
	 * @param memberCode
	 * @param liquidateId 可以为空，只更新条件为memberCode的
	 * @param status
	 * @return 更新的个数
	 */
	public int updateStatus(String memberCode,Long liquidateId,Integer status);
	/**
	 * 根据用户号来更新状态
	 * @param memberCode
	 * @param status
	 * @return 更新的个数
	 */
	public int updateStatus(String memberCode,Integer status);
	
	
	/**
	 * 取得结算模式
	 * @param memberCode
	 * @return 值
	 * @author DDR
	 */
	public Integer getAccountMode(Long memberCode);
    
}
