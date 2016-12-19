/**
 * 
 */
package com.pay.fundout.withdraw.service.bankcorp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceRespDTO;
import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fo.bankcorp.service.BankAccBalanceNotifyService;
import com.pay.inf.dao.BaseDAO;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;

/**
 * @author new
 * 
 */
public class BankAccBalanceNotifyServiceImpl implements
		BankAccBalanceNotifyService {

	final Logger log = LoggerFactory.getLogger(getClass());

	private BaseDAO<BankChannelConfig> bankChannelConfigDAO;

	private NotifyFacadeService notifyFacadeService;

	private long emailTemplateId;

	private long smsTemplateId;

	public void setBankChannelConfigDAO(
			BaseDAO<BankChannelConfig> bankChannelConfigDAO) {
		this.bankChannelConfigDAO = bankChannelConfigDAO;
	}

	/**
	 * @param notifyFacadeService
	 *            the notifyFacadeService to set
	 */
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	/**
	 * @param emailTemplateId
	 *            the emailTemplateId to set
	 */
	public void setEmailTemplateId(long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	/**
	 * @param smsTemplateId
	 *            the smsTemplateId to set
	 */
	public void setSmsTemplateId(long smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.bankcorp.service.BankAccBalanceNotifyService#process(com.pay
	 * .fo.bankcorp.dto.BankCorpQueryBalanceRespDTO)
	 */
	@Override
	public void process(BankCorpQueryBalanceRespDTO resp) {
		BankChannelConfig config = bankChannelConfigDAO.findById(resp
				.getChannelCode());
		if (config.getMinRemindedBalance().longValue() > 0
				&& config.getMinRemindedBalance().longValue() >= resp
						.getBankAccBalance()) {
			// 如果余额小于等于最低提醒余额，则发消息报警

			notifyMessage(resp, config);
		}

	}

	private void notifyMessage(BankCorpQueryBalanceRespDTO resp,
			BankChannelConfig config) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("accNo", config.getBankAcc());
		data.put("bankName", config.getBankName());
		data.put("balance", resp.getBankAccBalance().toString());
		data.put("time", getTime());
		Map<String, String> notifyList = getPropertiesValue();
		notifyEmail(data, notifyList.get("email"));
		notifySMS(data, notifyList.get("mobile"));

	}

	private void notifyEmail(Map<String, String> data, String emailList) {
		String[] emailArray = emailList.split(",");
		// 发送邮件通知
		NotifyTargetRequest request = new NotifyTargetRequest();

		for (int i = 0; i < emailArray.length; i++) {
			if (StringUtils.isNotEmpty(emailArray[i])
					&& StringUtils.trimToEmpty(emailArray[i]).length() != 0
					&& emailArray[i].indexOf("@") == 1) {
				request.getRecAddress().add(emailArray[i]);
			}
		}

		if (request.getRecAddress().size() > 0) {
			request.setData(data);
			request.setSubject("银企直连银存账号余额提醒");
			request.setNotifyType(RequestType.EMAIL.value());
			request.setFromAddress(Constants.SYSTEM_MAIL);
			request.setTemplateId(emailTemplateId);
			request.setRequestTime(new Date());
			notifyFacadeService.notifyRequest(request);
		}
	}

	private void notifySMS(Map<String, String> data, String mobileList) {
		String[] mobileArray = mobileList.split(",");
		// 发送短信通知
		NotifyTargetRequest request = new NotifyTargetRequest();
		for (int i = 0; i < mobileArray.length; i++) {
			if (StringUtils.isNotEmpty(mobileArray[i])
					&& StringUtils.trimToEmpty(mobileArray[i]).length() != 0) {
				request.getMobiles().add(mobileArray[i]);
			}
		}

		if (request.getMobiles().size() > 0) {
			request.setNotifyType(RequestType.SMS.value());
			// request.setSubject(subject);
			request.setData(data);
			request.setTemplateId(smsTemplateId);
			request.setRequestTime(new Date());
			notifyFacadeService.notifyRequest(request);
		}
	}

	private Map<String, String> getPropertiesValue() {
		Map<String, String> notifyList = new HashMap<String, String>();
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(
					"/opt/pay/config/fo/bankcorp/notification.properties");
			p.load(is);
			notifyList.put("email",
					p.getProperty("bank.corp.bankAccBalanceNotify.email"));
			notifyList.put("mobile",
					p.getProperty("bank.corp.bankAccBalanceNotify.mobile"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				is = null;
				e.printStackTrace();
			}
		}
		return notifyList;
	}

	private String getTime() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
		return df.format(date);
	}
}
