/**
 * 
 */
package com.pay.app.service.operator.impl;

import java.util.Arrays;
import java.util.HashMap;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.checkcode.impl.SendMail;
import com.pay.acc.comm.Resource;
import com.pay.app.service.operator.BindMobileService;
import com.pay.app.service.operator.statusEnum.BindMobleSmsType;
import com.pay.app.service.sms.SmsService;
import com.pay.util.DateUtil;

/**
 * desc:
 * @author DaiDeRong
 * 2011-11-17 下午2:43:30
 */
public class BindMobileServiceImpl implements BindMobileService {

	private SmsService smsService;
	private SendMail sendMail;
	private CheckCodeService checkCodeService;
	
	
	
	
	/**
	 * @param sendMail the sendMail to set
	 */
	public void setSendMail(SendMail sendMail) {
		this.sendMail = sendMail;
	}
	/**
	 * @param smsService the smsService to set
	 */
	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	/**
	 * @param checkCodeService the checkCodeService to set
	 */
	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}
	@Override
	public boolean validateConfirmCode(Long memberCode, String checkCode,BindMobleSmsType bindSmsType) {
		CheckCodeDto ck = checkCodeService.getByLastCheckCode(memberCode, bindSmsType.getCheckCodeOriginEnum(),1);
		if (ck!=null  &&  ck.getCheckCode().equals(checkCode)){
			checkCodeService.updateCheckCodeStatus2Used(memberCode+"", bindSmsType.getCheckCodeOriginEnum().getValue());
			return true;
		}
		return false;
	}
	@Override
	public void sendConfirmCode(String mobile, Long memberCode,
			 BindMobleSmsType bindSmsType) {
		smsService.sendCheckCode(memberCode+"", bindSmsType.getCheckCodeOriginEnum().getValue(), Arrays.asList( new String[]{mobile}), bindSmsType.getType());
		
	}
	@Override
	public void sendBindSuccessEmail(String email, String memberName,
			String memberMobile) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("memberName", memberName);
		map.put("memberMobile", memberMobile.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1****$3")); 
		map.put("bindDate", DateUtil.formatDateTime("yyyy年M月d日 HH:mm:ss"));
		sendMail.sendMail(Arrays.asList(email), "手机绑定申请成功", Resource.BIND_SUCCESS_EMAIL_ID, map);
	}

}
