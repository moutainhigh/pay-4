/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.acc.checkcode;

import java.io.InputStream;
import java.util.List;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;

/**
 * 会员激活信息服务<br>
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午03:24:51
 * 
 */
public interface CheckCodeService {

	/**
	 * 验证码已使用
	 */
	public static final int USED = 2;

	/**
	 * 验证码未使用
	 */
	public static final int UN_USED = 1;

	/**
	 * 创建用户激活信息
	 * 
	 * @param str
	 * @param memberCode
	 * @param origin
	 * @param email
	 * @param phone
	 */
	public void createCheckCode(String checkCode, Long memberCode,
			String origin, String email, String phone);

	/**
	 * 查询验证码状态
	 * 
	 * @param checkCode
	 * @return
	 */
	public int findStatesByCheckCode(String checkCode);

	/**
	 * 根据checkCode和来源获取
	 * 
	 * @param checkCode
	 * @param originEnum
	 * @return
	 */
	public CheckCodeDto getByCheckCodeAndOrigin(String checkCode,
			CheckCodeOriginEnum originEnum);

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
	public CheckCodeDto getByCheckCode(String checkCode);

	public boolean updateCheckCode(CheckCodeDto checkCode);

	/**
	 * 根据会员号和激活状态获取checkCode对象
	 * 
	 * @param memerCode
	 * @param status
	 */
	public List<CheckCodeDto> getByMemerCodeAndStatus(long memerCode, int status);

	public void updateCheckCodeStatus2Used(String memberCode, String origin);

	/**
	 * 得到用户的最后一个checkCode
	 * 
	 * @param memberCode
	 * @return CheckCode
	 */
	public CheckCodeDto getByLastCheckCode(Long memberCode,
			CheckCodeOriginEnum originEnum, String mobile);

	/**
	 * 得到用户的最后一个checkCode
	 * 
	 * @param memberCode
	 * @return CheckCode
	 */
	public CheckCodeDto getByLastCheckCode(Long memberCode,
			CheckCodeOriginEnum originEnum, Integer status);

	/**
	 * 重新发送激活信
	 * 
	 * @param userId
	 * @param recAddress
	 * @param checkCode
	 * @param url
	 * @param templateId
	 * @param subject
	 * @param limitedDay
	 * @return
	 */
	public boolean resendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId,
			String subject, int limitedDay);

	/**
	 * 发送带有附件的EMAIL
	 * 
	 * @param checkCode
	 * @param userId
	 * @param recAddress
	 * @param subject
	 * @param url
	 * @param templateId
	 * @param attachmentName
	 * @param content
	 * @return
	 */
	boolean sendMail(CheckCodeDto checkCode, String date,
			List<String> recAddress, String subject, String url,
			long templateId, String attachmentName, InputStream content);

	/**
	 * 发送激活信
	 * 
	 * @param userId
	 * @param recAddress
	 * @param checkCode
	 * @param url
	 * @param templateId
	 * @param subject
	 * @return
	 */
	public boolean sendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject);

	public String getOrderId();
}
