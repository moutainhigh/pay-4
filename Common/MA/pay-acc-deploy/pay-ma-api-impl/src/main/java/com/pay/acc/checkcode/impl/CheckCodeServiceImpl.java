/**
 * 
 */
/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.acc.checkcode.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dao.CheckCodeDAO;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.util.BeanConvertUtil;
import com.pay.util.FormatDate;
import com.pay.util.UUIDUtil;

/**
 * 会员激活信息服务<br>
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 下午03:26:11
 * 
 */
public class CheckCodeServiceImpl implements CheckCodeService {

	private final Log logger = LogFactory.getLog(CheckCodeServiceImpl.class);
	/** 会员激活信息DAO */
	private CheckCodeDAO checkCodeDAO;
	/** 邮件服务 */
	private SendMail sendMailManager;

	public void setSendMailManager(SendMail sendMailManager) {
		this.sendMailManager = sendMailManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#createCheckCode(com.pay.app
	 * .model.TCheckCode)
	 */
	@Override
	public void createCheckCode(String checkCode, Long memberCode,
			String origin, String email, String phone) {
		CheckCode check = new CheckCode();
		check.setCheckCode(checkCode);
		check.setMemberCode(memberCode);
		check.setStatus(1);// 默认新推邮件均为未校验 即1
		check.setOrigin(origin);
		if (email != null && !"".equals(email) && !"null".equals(email)) {
			check.setEmail(email);
		}
		if (phone != null && !"".equals(phone) && !"null".equals(phone)) {
			check.setPhone(phone);
		}
		checkCodeDAO.createCheckCode(check);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#findStatesByCheckCode(java.
	 * lang.String)
	 */
	@Override
	public int findStatesByCheckCode(String checkCode) {
		return checkCodeDAO.findStatesByCheckCode(checkCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#getByCheckCode(java.lang.String
	 * )
	 */
	@Override
	public CheckCodeDto getByCheckCode(String checkCode) {
		CheckCode checkCodeModel = checkCodeDAO.getByCheckCode(checkCode);
		return BeanConvertUtil.convert(CheckCodeDto.class, checkCodeModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#getByMemerCodeAndStatus(java
	 * .lang.String, int)
	 */
	@Override
	public List<CheckCodeDto> getByMemerCodeAndStatus(long memerCode, int status) {
		List<CheckCode> models = checkCodeDAO.getByMemerCodeAndStatus(
				memerCode, status);
		return (List<CheckCodeDto>) BeanConvertUtil.convert(CheckCodeDto.class,
				models);
	}

	public CheckCodeDto getByCheckCodeAndOrigin(String checkCode,
			CheckCodeOriginEnum originEnum) {
		if (StringUtils.isNotBlank(checkCode) && originEnum != null) {
			CheckCode checkCodeModel = checkCodeDAO.getByCheckCodeAndOrigin(
					checkCode, originEnum.getValue());
			return BeanConvertUtil.convert(CheckCodeDto.class, checkCodeModel);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#updateCheckCodeStates(java.
	 * lang.String)
	 */
	@Override
	public void updateCheckCodeStates(String checkCode) {
		checkCodeDAO.updateCheckCodeStates(checkCode);
	}

	public void updateCheckStateByMemCode(long memberCode, String checkCode) {
		if (StringUtils.isNotBlank(checkCode)) {
			checkCodeDAO.updateCheckStateByMemCode(memberCode, checkCode);
			return;
		} else {
			logger.warn("updateCheckStateByMemCode is fialed!checkCode is null.memberCode="
					+ memberCode);
		}
	}

	private void updateCreateTime(String checkCode) {
		checkCodeDAO.updateCreateTime(checkCode);
	}

	public void setCheckCodeDAO(CheckCodeDAO checkCodeDAO) {
		this.checkCodeDAO = checkCodeDAO;
	}

	private String generateMailCheckCode() {
		return UUIDUtil.uuid();
	}

	@Override
	public void updateCheckCodeStatus2Used(String memberCode, String origin) {

		checkCodeDAO.updateCheckCodeStates2Used(memberCode, origin);

	}

	@Override
	public CheckCodeDto getByLastCheckCode(Long memberCode,
			CheckCodeOriginEnum originEnum, String mobile) {
		CheckCode checkCodeModel = checkCodeDAO.getByLastCheckCode(memberCode,
				originEnum.getValue(), mobile);
		return BeanConvertUtil.convert(CheckCodeDto.class, checkCodeModel);
	}

	@Override
	public boolean updateCheckCode(CheckCodeDto checkCode) {

		return checkCodeDAO.update(BeanConvertUtil.convert(CheckCode.class,
				checkCode));
	}

	@Override
	public CheckCodeDto getByLastCheckCode(Long memberCode,
			CheckCodeOriginEnum originEnum, Integer status) {
		List<CheckCode> list = checkCodeDAO.getByMemCodeAndStatOrigin(
				memberCode, 1, originEnum.getValue());
		if (list.size() > 0) {
			return BeanConvertUtil.convert(CheckCodeDto.class, list.get(0));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.mail.CheckCodeService#resendMail(java.lang.String,
	 * java.util.List, com.pay.app.model.TCheckCode, java.lang.String, long,
	 * java.lang.String, int)
	 */
	@Override
	public boolean resendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId,
			String subject, int limitedDay) {
		boolean mailBoolean = false;
		List<CheckCode> list = checkCodeDAO.getByMemCodeAndStatOrigin(
				checkCode.getMemberCode(), UN_USED, checkCode.getOrigin());
		if (list != null && list.size() > 0) {
			CheckCode ccode = list.get(0);
			Date createtime = ccode.getCreateTime();
			long diff = FormatDate.sceondOfTwoDate(createtime,
					limitedDay * 24 * 60);
			if (diff > 0) {
				String chCode = ccode.getCheckCode();
				try {
					sendMailManager.resendMail(userId, recAddress, subject,
							url, templateId, chCode);// 返回生成的验证码
					ccode.setCreateTime(new Date());
					this.updateCreateTime(chCode);
					mailBoolean = true;
				} catch (Exception e) {
					logger.error(
							"重新发送激活邮件失败!memberCode="
									+ checkCode.getMemberCode(), e);
				}
			} else {
				mailBoolean = this.sendMail(userId, recAddress, checkCode, url,
						templateId, subject);
			}
		} else {
			logger.warn("重新发送激活邮件失败,获取CheckCode为空！memberCode="
					+ checkCode.getMemberCode() + ",Origin="
					+ checkCode.getOrigin());
		}
		return mailBoolean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.app.service.mail.CheckCodeService#sendMail(java.lang.String,
	 * java.util.List, com.pay.app.model.TCheckCode, java.lang.String, long,
	 * java.lang.String)
	 */
	@Override
	public boolean sendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject) {
		boolean mailBoolean = false;
		if (checkCode != null && checkCode.getMemberCode() > 0
				&& checkCode.getOrigin() != null
				&& checkCode.getEmail() != null) {
			long memberCode = checkCode.getMemberCode();
			String origin = checkCode.getOrigin();
			String email = checkCode.getEmail();
			try {
				String str = generateMailCheckCode();
				sendMailManager.sendMail(userId, recAddress, subject, url,
						templateId, str);// 返回生成的验证码
				this.createCheckCode(str, memberCode, origin, email, null);
				mailBoolean = true;
			} catch (Exception e) {
				logger.error("发送激活邮件失败!memberCode=" + memberCode, e);
			}
		}
		return mailBoolean;
	}

	@Override
	public boolean sendMail(CheckCodeDto checkCode, String date,
			List<String> recAddress, String subject, String url,
			long templateId, String attachmentName, InputStream content) {
		boolean mailBoolean = false;
		if (null != checkCode && StringUtils.isNotEmpty(checkCode.getOrigin()))
			try {
				Long memberCode = checkCode.getMemberCode();
				String origin = checkCode.getOrigin();
				String email = checkCode.getEmail();
				String str = generateMailCheckCode();
				sendMailManager.sendMail(date, recAddress, subject, url,
						templateId, attachmentName, content, str);
				this.createCheckCode(str, memberCode, origin, email, null);
				mailBoolean = true;
			} catch (Exception e) {
				logger.error("发送附件失败!", e);
			}

		return mailBoolean;
	}

	@Override
	public String getOrderId() {

		return checkCodeDAO.getOrderId();
	}
}
