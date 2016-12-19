/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.acc.checkcode.dao;

import java.util.List;

import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.inf.dao.BaseDAO;

/**
 * 会员激活信息DAO<br>
 * 对应ACC库
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午02:52:31
 * 
 */
public interface CheckCodeDAO extends BaseDAO {

	/**
	 * 创建用户激活信息
	 * 
	 * @param checkCode
	 */
	public void createCheckCode(CheckCode checkCode);

	/**
	 * 查询验证码状态
	 * 
	 * @param checkCode
	 * @return
	 */
	public int findStatesByCheckCode(String checkCode);

	/**
	 * 根据checkCode和来源获取CheckCode
	 *
	 * @param checkCode
	 * @param origin
	 * @return
	 */
	public CheckCode getByCheckCodeAndOrigin(String checkCode, String origin);

	/**
	 * 更新状态为已校验
	 * 
	 * @param checkCode
	 */
	public void updateCheckCodeStates(String checkCode);

	/**
	 * 根据会员号和校验码更新校验状态
	 * 
	 * @param memberCode
	 * @param checkCode
	 */
	public void updateCheckStateByMemCode(long memberCode, String checkCode);

	/**
	 * 根据激活码获取checkCode对象
	 * 
	 * @param checkCode
	 */
	public CheckCode getByCheckCode(String checkCode);

	/**
	 * 根据会员号和激活状态获取checkCode对象
	 * 
	 * @param memberCode
	 * @param status
	 */
	public List<CheckCode> getByMemerCodeAndStatus(long memberCode, int status);

	/**
	 * 更新创建时间
	 * 
	 * @param checkCode
	 */
	public void updateCreateTime(String checkCode);

	public String getOrderId();

	public void updateCheckCodeStates2Used(String memberCode, String origin);

	/**
	 * 根据会员号和激活状态获取checkCode对象
	 * 
	 * @param memberCode
	 * @param status
	 */
	public List<CheckCode> getByMemCodeAndStatOrigin(long memberCode,
			int status, String origin);

	CheckCode getByLastCheckCode(Long memberCode, String businessType,
			String mobile);

	/**
	 * 获取checkCode对象
	 * 
	 * @param memerCode
	 */
	public CheckCode getByMemerCode(String memerCode);

	/**
	 * 根据memberCode和来源获取checkCode
	 *
	 * @param memerCode
	 * @param origin
	 * @return
	 */
	public CheckCode getByMemerCode(String memerCode, String origin);
	
	public int findStatesByMemerCode(String memerCode);
}
