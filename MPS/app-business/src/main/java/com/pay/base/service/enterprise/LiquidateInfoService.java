/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise;

import java.util.List;

import com.pay.base.model.LiquidateInfo;
import com.pay.inf.exception.AppException;

/**
 * 企业会员结算信息服务
 * @author zhi.wang
 * @version $Id: LiquidateInfoService.java, v 0.1 2010-10-12 上午10:44:23 zhi.wang Exp $
 */
public interface LiquidateInfoService {
	
	
    /**
     * 根据会员号获取企业会员结算信息
     *
     * @param memberCode
     * @return
     */
    public List<LiquidateInfo> getByMemberCode(long memberCode);
    
    /**
     *  保存结算信息
     * @param liquidateInfo
     * @return
     */
    public boolean saveOrUpdate(LiquidateInfo liquidateInfo);
    
   /**
    *  添加或更新一条 企业银行卡绑定 信息
    * @param corpBankAcc 增加 的银行卡号
    * @param memberCode 用户账户号
    * @param cardNo 卡号
    * @return 1表成功  
    */
	public Integer addOrUpdateCorpBankAcctRnTx(LiquidateInfo corpBankAcc,Long memberCode,String cardNo) throws AppException;
	
	
	
	/**
	 * 通过用户号和 id号删除绑定的卡号
	 * @param memberCode 用户号
	 * @param id
	 * @return true表示成功
	 * @author 戴德荣
	 */
	public boolean removeCorpAcctByMemberCodeAndId(String memberCode,Long id);
	
	/**
	 * 得到一个绑定的卡片信息
	 * @param id
	 * @return
	 * @author 戴德荣
	 */
	public LiquidateInfo getById(long id) ;
	/**
	 * 设置默认的账户卡号
	 * @param memberCode
	 * @param id 
	 * @return true
	 */
	public boolean setDefaultCordAcctRnTx(String memberCode,Long id);
	
	/**
	 * 取消默认的账户卡号
	 * @param memberCode
	 * @param id 
	 * @return true
	 */
	public boolean setNotDefaultCordAcctRnTx(String memberCode,Long id);
	
	
	/**
	 * 得到共绑定了几张卡
	 * @param memberCode
	 * @return  卡片的数量
	 * @author 戴德荣
	 */
	public int getCountByMemberCode(Long memberCode) ;
	
	/**
	 * 判断 卡片是否可以修改
	 * @param memberCode 要修改卡的用户
	 * @param id 要修改的id号
	 * @param cardNo 将要改为的卡号
	 * @return 0为可以修改，1为有重复的，不能再修改
	 * @author 戴德荣
	 */
	public int validateEditCard(String memberCode,Long id,String cardNo) ;
	
	/**
	 * 根据memberCode判断是否有权限
	 * @param sessionMebmerCode
	 * @param paramMemberCode
	 * @return true
	 */
	public boolean hasPromisson(Long sessionMebmerCode,Long paramMemberCode);
	/**
	 *查询银行卡列表 
	 * @param memberCode
	 * @return
	 */
	public List<LiquidateInfo> queryBankCardList(String memberCode);
}
