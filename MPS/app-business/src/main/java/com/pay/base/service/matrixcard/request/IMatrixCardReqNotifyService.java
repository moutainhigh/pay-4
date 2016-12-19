package com.pay.base.service.matrixcard.request;

import java.util.Date;
import java.util.List;

import com.pay.acc.checkcode.dao.model.CheckCode;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public interface IMatrixCardReqNotifyService {
	// 发送申请成功的邮件通知
	public void sendReqSuccEmail(Long mcId, String mcSn, String sessionId,
			String serverName, String contextPath, String emailAddr);

	/**
	 * @param lastValidDate
	 *            最后有效期
	 * @param recAddress
	 * @param subject
	 * @param url
	 * @param templateId
	 * @param imgId
	 * @return
	 */
	boolean sendReqAttachmentEmail(CheckCode checkCode, Date lastValidDate,
			List<String> recAddress, String subject, String url,
			long templateId, String imgId);

}
