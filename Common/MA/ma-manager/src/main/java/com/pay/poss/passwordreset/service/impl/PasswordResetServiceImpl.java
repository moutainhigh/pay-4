package com.pay.poss.passwordreset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.poss.base.common.constants.SendMailConstants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.passwordreset.dao.PasswordResetrDAO;
import com.pay.poss.passwordreset.dto.PasswordResetDto;
import com.pay.poss.passwordreset.service.PasswordResetService;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.UUIDUtil;

/**
 * @Description
 * @project ma-membermanager
 * @file PasswordResetServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-12-15 tianqing_wang Create
 */
public class PasswordResetServiceImpl implements PasswordResetService {

	private final Log log = LogFactory.getLog(PasswordResetServiceImpl.class);
	private PasswordResetrDAO passwordResetrDAO;
	private JmsSender jmsSender;
	private String logingPwdRetUrl;
	private String payPasswordUrl;

	public void setPayPasswordUrl(String payPasswordUrl) {
		this.payPasswordUrl = payPasswordUrl;
	}

	public void setLogingPwdRetUrl(String logingPwdRetUrl) {
		this.logingPwdRetUrl = logingPwdRetUrl;
	}

	public Long createEnterprisePasswordReset(PasswordResetDto dto) {
		Long id = passwordResetrDAO.createEnterprisePasswordReset(dto);
		return id;
	}

	public List<PasswordResetDto> queryEnterprisePasswordReset(
			PasswordResetDto paramDto, Page page) {
		Integer totalCount = passwordResetrDAO
				.countEnterprisePasswordReset(paramDto);
		PasswordResetDto dto = setPage(paramDto, page, totalCount);
		if (null == dto)
			return null;
		return passwordResetrDAO.queryEnterprisePasswordReset(dto);
	}

	public PasswordResetDto queryEnterprisePasswordResetById(
			PasswordResetDto dto) {
		return passwordResetrDAO.queryEnterprisePasswordResetById(dto);
	}

	public Integer updateEnterprisePasswordReset(PasswordResetDto dto) {
		return passwordResetrDAO.updateEnterprisePasswordReset(dto);
	}

	@SuppressWarnings("unchecked")
	public Boolean passwordResetConfirmTrans(PasswordResetDto dto)
			throws PossException {
		Boolean isSuess = false;
		String typeName = "";
		try {
			if (SendMailConstants.TYPENAME_TYPELOGIN.equals(dto.getTypeName())) {
				typeName = SendMailConstants.ZN_TYPENAME_TYPELOGIN;
				dto.setStatus(3);
			} else if (SendMailConstants.TYPENAME_TYPEPAY.equals(dto
					.getTypeName())) {
				typeName = SendMailConstants.ZN_TYPENAME_TYPEPAY;
				dto.setStatus(5);
			}
			// 1:更新表t_password_reset
			passwordResetrDAO.updateEnterprisePasswordReset(dto);
			// 2:得到发邮件mailCheckCode
			String mailCheckCode = this.mailCheckCode();
			// 3:得到发短信messageCheckCode
			String messageCheckCode = this.messageCheckCode();
			// 4:设置插入发送email t_check_code表的参数

			Map mailCheckCodeMap = this.setChekCodeMap(mailCheckCode,
					dto.getTypeName(), dto.getLoginName(), dto.getMobile());
			// 5:设置插入发送短信t_check_code表的参数
			Map messageCheckCodeMap = this.setChekCodeMap(messageCheckCode,
					dto.getTypeName(), dto.getLoginName(), dto.getMobile());
			// 6:插入t_check_code表
			passwordResetrDAO.createCheckCode(mailCheckCodeMap,
					messageCheckCodeMap);
			// 7:发邮件
			this.sendMail(dto, typeName, mailCheckCode);
			// 8:发短信
			// this.sendMessage(dto,typeName,messageCheckCode);
			isSuess = true;
		} catch (Exception e) {
			log.error("PasswordResetServiceImpl.passwordResetConfirmTrans is error...");
			e.printStackTrace();
			throw new PossException("企业会员密码重置失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuess;

	}

	public PasswordResetDto queryMemberCodeByLoginName(String loginName) {
		return passwordResetrDAO.queryMemberCodeByLoginName(loginName);
	}

	// 企业会员密码重置发送邮件
	private void sendMail(PasswordResetDto dto, String typeName,
			String mailChcekCode) {
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(dto.getLoginName());
		Map<String, String> data = new HashMap<String, String>();
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(SendMailConstants.PASSWORDRESET_ENTERPRISE_EMAIL_SENDER);// 默认邮件发送者
		request.setRecAddress(recAddress);// 收件人列表
		request.setBizCode("pwdReset4");
		request.setSubject(SendMailConstants.PASSWORDRESET_ENTERPRISE_EMAIL_TITLE);// 邮件主题
		data.put("userName", dto.getMemberName());
		data.put("typeName", typeName);// 支付类型
		// String checkCode = this.mailCheckCode();
		if (SendMailConstants.ZN_TYPENAME_TYPELOGIN.equals(typeName)) {
			data.put("url", logingPwdRetUrl + mailChcekCode);// url 登录链接
			request.setTemplateId(SendMailConstants.PASSWORDRESET_ENTERPRISE_LOGIN_EMAIL_TEMPID);// 模板ID
		}
		if (SendMailConstants.ZN_TYPENAME_TYPEPAY.equals(typeName)) {
			data.put("url", payPasswordUrl + mailChcekCode);// url 支付链接
			request.setTemplateId(SendMailConstants.PASSWORDRESET_ENTERPRISE_PAY_EMAIL_TEMPID);// 模板ID
		}
		request.setData(data);
		jmsSender.send(request);
		// return checkCode;
	}

	// 企业会员密码重置发送短信
	@SuppressWarnings("unused")
	private void sendMessage(PasswordResetDto dto, String typeName,
			String messageCheckCode) {
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(dto.getMobile());

		Map<String, String> data = new HashMap<String, String>();
		// String checkCode = this.messageCheckCode();
		SMSNotifyRequest request = new SMSNotifyRequest();
		request.setMobiles(mobiles);
		request.setTemplateId(SendMailConstants.PASSWORDRESET_ENTERPRISE_MESSAGE_TEMPID);
		data.put("abc", "验证码是:" + messageCheckCode);
		data.put("typeName", typeName);// 支付类型
		request.setData(data);
		jmsSender.send(request);
		// return checkCode;
	}

	@SuppressWarnings("unchecked")
	// 设置map的值
	private Map setChekCodeMap(String checkCode, String origin, String email,
			String phone) {
		PasswordResetDto dto = passwordResetrDAO
				.queryMemberCodeByLoginName(email);
		Map map = new HashMap();
		map.put("checkCode", checkCode);
		map.put("status", 1);
		map.put("memberCode", dto.getMemberCode());
		if (SendMailConstants.TYPENAME_TYPEPAY.equals(origin)) {
			map.put("origin", SendMailConstants.ORIGN_TYPEPAY);
		} else if (SendMailConstants.TYPENAME_TYPELOGIN.equals(origin)) {
			map.put("origin", SendMailConstants.ORIGN_TYPELOGIN);
		} else {
			map.put("origin", origin); // 兼容已经有的数据
		}
		map.put("email", email);
		map.put("phone", phone);
		return map;

	}

	@SuppressWarnings("unchecked")
	private PasswordResetDto setPage(PasswordResetDto dto, Page page,
			Integer totalCount) {
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
		if (null == page || totalCount == 0) {
			return null;
		}
		page.setTotalCount(totalCount);
		pageEndRow = page.getPageNo() * page.getPageSize();
		if ((page.getPageNo() - 1) == 0) {
			pageStartRow = 0;
		} else {
			pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		}
		dto.setPageStartRow(pageStartRow);
		dto.setPageEndRow(pageEndRow);
		return dto;
	}

	private String mailCheckCode() {
		return UUIDUtil.uuid();
	}

	/**
	 * 获取6位随机数字验证码
	 * 
	 * @return
	 */
	private String messageCheckCode() {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < 6; i++) {
			int tmp = random.nextInt(9);
			str += tmp + "";
		}
		return str;
	}

	public PasswordResetrDAO getPasswordResetrDAO() {
		return passwordResetrDAO;
	}

	public void setPasswordResetrDAO(PasswordResetrDAO passwordResetrDAO) {
		this.passwordResetrDAO = passwordResetrDAO;
	}

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

}
