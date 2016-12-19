package com.pay.app.service.sms;

import java.util.List;
import java.util.Map;

import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-13 上午11:34:18 短信服务
 */
public interface SmsService {

	/**
	 * 
	 * @param memerCode
	 *            当前账户
	 * @return
	 */
	public CheckCodeDto getByMemerCode(String memerCode,
			CheckCodeOriginEnum originEnum);

	// public CheckCodeDto getByMemerCode(String memerCode);
	/**
	 * 
	 * @param memberCode
	 *            当前账户
	 * @param origin
	 *            来源
	 * @param userId
	 * @param mobiles
	 *            短信接收电话
	 * @param templateId
	 *            模板
	 */
	public boolean sendSms(String memberCode, String origin, String userId,
			List<String> mobiles, long templateId);

	/**
	 * 
	 * @param memberCode
	 * @param origin
	 * @param mobiles
	 * @param templateId
	 * @return
	 */
	public boolean sendCheckCode(String memberCode, String origin,
			List<String> mobiles, long templateId);

	/**
	 * 发送普通短信，不带校验码认证的
	 * 
	 * @param mobiles
	 * @param templateId
	 * @param data
	 *            模板中的数据
	 * @return
	 */
	public boolean sendSimpleSms(List<String> mobiles, long templateId,
			Map<String, String> data);

}
