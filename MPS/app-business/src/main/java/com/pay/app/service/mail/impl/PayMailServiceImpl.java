package com.pay.app.service.mail.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.dao.CheckCodeDAO;
import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.checkcode.impl.SendMail;
import com.pay.app.common.helper.AppConf;
import com.pay.app.service.mail.MailService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.BeanConvertUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-26 下午03:42:50
 */
public class PayMailServiceImpl implements MailService {

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	private String checkCode() {
		return UUIDUtil.uuid();
	}

	private final Log log = LogFactory.getLog(PayMailServiceImpl.class);
	private SendMail sendMailManager;

	public void setSendMailManager(SendMail sendMailManager) {
		this.sendMailManager = sendMailManager;
	}

	@Override
	public boolean sendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject) {
		boolean mailBoolean = false;
		if (checkCode != null && checkCode.getMemberCode() != null
				&& checkCode.getOrigin() != null
				&& checkCode.getEmail() != null) {
			Long memberCode = checkCode.getMemberCode();
			String origin = checkCode.getOrigin();
			String email = checkCode.getEmail();
			try {
				String str = checkCode();
				this.saveCheckCode(str, memberCode, origin, email, null);
				sendMailManager.sendMail(userId, recAddress, subject, url,
						templateId, str);// 返回生成的验证码
				mailBoolean = true;
			} catch (Exception e) {
				log.error("send mail error.The detail [" + e + "]");
			}
		}

		return mailBoolean;
	}

	@Override
	public void saveCheckCode(String str, Long memberCode, String origin,
			String email, String phone) {
		CheckCode checkCode = new CheckCode();
		checkCode.setCheckCode(str);
		checkCode.setCreateTime(new Date());
		checkCode.setMemberCode(memberCode);
		checkCode.setStatus(1);// 默认新推邮件均为未校验 即1
		checkCode.setOrigin(origin);
		if (email != null && !"".equals(email) && !"null".equals(email)) {
			checkCode.setEmail(email);
		}
		if (phone != null && !"".equals(phone) && !"null".equals(phone)) {
			checkCode.setPhone(phone);
		}
		((CheckCodeDAO) mainDao).createCheckCode(checkCode);
	}

	@Override
	public String findStatesByCheckCode(String checkcode) {
		return "" + ((CheckCodeDAO) mainDao).findStatesByCheckCode(checkcode);
	}

	@Override
	public CheckCodeDto getByCheckCode(String checkcode) {
		CheckCode model = (CheckCode) ((CheckCodeDAO) mainDao)
				.getByCheckCode(checkcode);
		return BeanConvertUtil.convert(CheckCodeDto.class, model);
	}

	@Override
	public CheckCodeDto getByMemerCode(String memerCode) {
		CheckCode model = (CheckCode) ((CheckCodeDAO) mainDao)
				.getByMemerCode(memerCode);
		return BeanConvertUtil.convert(CheckCodeDto.class, model);
	}

	public CheckCodeDto getByMemerCode(String memerCode, String origin) {
		CheckCode model = (CheckCode) ((CheckCodeDAO) mainDao).getByMemerCode(
				memerCode, origin);

		return BeanConvertUtil.convert(CheckCodeDto.class, model);
	}

	@Override
	public boolean isActive(String memerCode) {
		int result = ((CheckCodeDAO) mainDao).findStatesByMemerCode(memerCode);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public void updateCheckCodeStates(String checkcode) {
		((CheckCodeDAO) mainDao).updateCheckCodeStates(checkcode);
	}

	@Override
	public boolean resendMail(String userId, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject) {
		boolean mailBoolean = false;
		CheckCodeDto ccode = this.getByMemerCode(checkCode.getMemberCode() + "");
		if (!StringUtil.isNull(ccode)) {
			Date createtime = ccode.getCreateTime();
			Integer days = new Integer(AppConf.get(AppConf.mail_interval));
			long diff = FormatDate.sceondOfTwoDate(createtime, days * 24 * 60);
			if (diff > 0) {
				String chCode = ccode.getCheckCode();
				try {
					sendMailManager.resendMail(userId, recAddress, subject,
							url, templateId, chCode);// 返回生成的验证码
					ccode.setCreateTime(new Date());
					mainDao.update(BeanConvertUtil.convert(CheckCodeDto.class,
							ccode));
					mailBoolean = true;
				} catch (Exception e) {
					log.error("send mail error.The detail [" + e + "]");
				}
			} else {
				this.sendMail(userId, recAddress, checkCode, url, templateId,
						subject);
				mailBoolean = true;
			}
		}
		return mailBoolean;
	}

	@Override
	public boolean sendMail(String userId, List<String> recAddress,
			String subject, String url, long templateId, String attachmentName,
			InputStream content) {
		boolean mailBoolean = false;
		try {
			String str = checkCode();
			sendMailManager.sendMail(userId, recAddress, subject, url,
					templateId, attachmentName, content, str);
			// this.saveCheckCode(str, memberCode, origin, email,null);
			mailBoolean = true;
		} catch (Exception e) {
			log.error("send mail error.The detail [" + e + "]");
		}

		return mailBoolean;
	}

}
