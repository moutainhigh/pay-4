package com.pay.app.service.sms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.service.mail.MailService;
import com.pay.app.service.sms.SmsService;
import com.pay.app.sms.SendSms;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-13 上午11:34:49
 */
@SuppressWarnings("unused")
public class SmsServiceImpl implements SmsService {

	private final Log log = LogFactory.getLog(SmsServiceImpl.class);

	private SendSms sendSmsManager;
	private MailService checkCodeService;

	public void setCheckCodeService(MailService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setSendSmsManager(SendSms sendSmsManager) {
		this.sendSmsManager = sendSmsManager;
	}

	/**
	 * 获取6位随机数字验证码
	 * 
	 * @return
	 */
	private String checkCode() {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < 6; i++) {
			int tmp = random.nextInt(10);
			str += tmp + "";
		}
		return str;
	}

	@Override
	public boolean sendSms(String memberCode, String origin, String userId,
			List<String> mobiles, long templateId) {
		boolean smsBoolean = false;
		// Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
		try {
			String str = checkCode();
			checkCodeService.saveCheckCode(str, Long.valueOf(memberCode),
					origin, null, mobiles.get(0).toString());
			sendSmsManager.sendSms(userId, mobiles, templateId, str);// 验证码
			smsBoolean = true;
		} catch (Exception e) {
			log.error("sendSmsManager.sendSms throws error:", e);
		}
		return smsBoolean;
	}

	/*
	 * @Override public CheckCodeDto getByMemerCode(String memerCode) {
	 * CheckCode checkcode=checkCodeService.getByMemerCode(memerCode); return
	 * BeanConvertUtil.convert(CheckCodeDto.class, checkcode); }
	 */

	@Override
	public CheckCodeDto getByMemerCode(String memerCode,
			CheckCodeOriginEnum originEnum) {
		if (StringUtils.isNotBlank(memerCode) && originEnum != null) {
			CheckCodeDto checkcode = checkCodeService.getByMemerCode(memerCode,
					originEnum.getValue());
			return checkcode;
		} else {
			return null;
		}
	}

	@Override
	public boolean sendCheckCode(String memberCode, String origin,
			List<String> mobiles, long templateId) {
		boolean smsBoolean = false;
		// Integer minuttes=new Integer(AppConf.get(AppConf.sms_interval));
		try {
			String checkCode = checkCode();
			checkCodeService.saveCheckCode(checkCode, Long.valueOf(memberCode),
					origin, null, mobiles.get(0).toString());
			Map<String, String> data = new HashMap<String, String>();
			data.put("checkcode", checkCode);
			sendSmsManager.sendSms(mobiles, templateId, data);// 验证码
			smsBoolean = true;
		} catch (Exception e) {
			log.error("sendSmsManager.sendSms throws error:", e);
			return false;
		}
		return smsBoolean;
	}

	@Override
	public boolean sendSimpleSms(List<String> mobiles, long templateId,
			Map<String, String> data) {
		boolean smsBoolean = false;
		try {
			smsBoolean = sendSmsManager.sendSms(mobiles, templateId, data);
		} catch (Exception e) {
			log.error("sendSmsManager.sendSms throws error:", e);
			return false;
		}
		return smsBoolean;
	}

}
