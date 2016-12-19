package com.pay.app.service.mail;

import java.io.InputStream;
import java.util.List;

import com.pay.acc.checkcode.dto.CheckCodeDto;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-26 下午03:40:20 邮件操作service
 */
public interface MailService {
	/**
	 * 发送EMAIL (必传:memberCode origin email)
	 * 
	 * @param membercode
	 *            membercode
	 * @param recAddress
	 *            收件人列表
	 * @param CheckCode
	 *            对象
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @param subject
	 *            主题
	 * @return boolean
	 */
	public boolean sendMail(String membercode, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject);

	/**
	 * 发送带有附件的邮件
	 * 
	 * @param userId
	 *            收件人名称(LOGINNAME)
	 * @param recAddress
	 *            收件人列表
	 * @param subject
	 *            邮件主题
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @param attachmentName
	 *            附件名称
	 * @param content
	 *            附件
	 * @return
	 */
	public boolean sendMail(String userId, List<String> recAddress,
			String subject, String url, long templateId, String attachmentName,
			InputStream content);

	/**
	 * 保存checkcode,用于执行激活校验（邮箱跟手机只传一个）
	 * 
	 * @param str
	 * @param memberCode
	 * @param origin
	 */
	void saveCheckCode(String str, Long memberCode, String origin,
			String email, String phone);

	/**
	 * 查询激活码是否有效，为已校验即（2）则失效，为1则未校验
	 * 
	 * @param checkcode
	 * @return
	 */
	public String findStatesByCheckCode(String checkcode);

	/**
	 * 当激活校验发生时,根据checkcode更新激活码状态
	 * 
	 * @param checkcode
	 */
	public void updateCheckCodeStates(String checkcode);

	/**
	 * 根据memberCode和来源获取checkCode
	 *
	 * @param memerCode
	 * @param origin
	 * @return
	 */
	public CheckCodeDto getByMemerCode(String memerCode, String origin);

	/**
	 * 根据checkcode获取checkCode对象
	 * 
	 * @param checkcode
	 */
	CheckCodeDto getByCheckCode(String checkcode);

	CheckCodeDto getByMemerCode(String memerCode);

	boolean isActive(String memberCode);

	/**
	 * 发送EMAIL (必传:memberCode origin email)
	 * 
	 * @param membercode
	 *            membercode
	 * @param recAddress
	 *            收件人列表
	 * @param CheckCode
	 *            对象
	 * @param url
	 *            推送URL
	 * @param templateId
	 *            模板ID
	 * @param subject
	 *            主题
	 * @return boolean
	 */
	public boolean resendMail(String membercode, List<String> recAddress,
			CheckCodeDto checkCode, String url, long templateId, String subject);

	/**
	 * 发送EMAIL (必传:userId checkCode mail对象)
	 * 
	 * @param mail
	 *            推送URL
	 * @param userId
	 *            邮件称呼
	 * @return boolean
	 */
	// public String sendMail(String userId, Mail mail);
}
