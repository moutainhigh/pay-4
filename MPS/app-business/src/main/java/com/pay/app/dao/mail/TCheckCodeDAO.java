/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.mail;

import java.util.List;

import com.pay.app.model.TCheckCode;

/**
 * 会员激活信息DAO<br>
 * 对应ACC库
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午02:52:31
 * 
 */
public interface TCheckCodeDAO {
	
	/**
	 * 创建用户激活信息
	 * @param checkCode
	 */
	public void createCheckCode(TCheckCode checkCode);
	/**
	 * 查询验证码状态
	 * @param checkCode
	 * @return
	 */
	public int findStatesByCheckCode(String checkCode);
	
	
	/**
	 * 更新状态为已校验
	 * @param checkCode
	 */
	public void updateCheckCodeStates(String checkCode);
	
	/**
	 * 根据激活码获取checkCode对象
	 * @param checkCode
	 */
	public TCheckCode getByCheckCode(String checkCode);
	/**
	 * 根据会员号和激活状态获取checkCode对象
	 * @param memberCode
	 * @param status
	 */
	public List<TCheckCode> getByMemerCodeAndStatus(long memberCode,int status);
	
	/**
	 * 更新创建时间
	 * @param checkCode
	 */
	public void updateCreateTime(String checkCode);
	
	/**
	 * 查询 acc库的seq 得到 订单id
	 * @return String
	 */
	public String getOrderId();
}
