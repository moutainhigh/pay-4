package com.pay.fo.quartz.balancewarning;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmInfo;
import com.pay.fundout.withdraw.dto.balancewarning.OrgBalanceAlarmTask;
import com.pay.fundout.withdraw.service.balancewarning.BalanceWarningService;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;

/**
 * @author wucan
 * @Description 余额预警
 */
public class BalanceWarningTaskExecutor extends QuartzJobBean {

	protected transient Log log = LogFactory.getLog(getClass());

	// 查询余额服务
	private AccountQueryService accountQueryService;
	// 查询所有配置预警商户信息
	private BalanceWarningService balanceWarningService;
	// 消息服务
	private NotifyFacadeService notifyFacadeService;

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setBalanceWarningService(BalanceWarningService balanceWarningService) {
		this.balanceWarningService = balanceWarningService;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	/**
	 * 余额预警
	 */
	public void balanceWarning() {
		if (log.isInfoEnabled()) {
			log.info("-----余额不足预警开始-----");
		}
		// 查询机构余额预警信息
		List<OrgBalanceAlarmInfo> orgBalanceAlarmInfoList = balanceWarningService.queryOrgBalanceAlarmInfo();

		for (OrgBalanceAlarmInfo dto : orgBalanceAlarmInfoList) {
			BalancesDto balancesDto;
			try {
				// 查询机构余额
				balancesDto = accountQueryService.doQueryBalancesNsTx(dto.getMemberCode(), 10);
				// 账户余额
				Long accountBalance = balancesDto.getBalance();
				// 下限金额
				Long minBalance = dto.getMinBalance();
				if (accountBalance < minBalance) {
					// 下次预警时间
					Date beginDate = dto.getBeginDate();
					if (null == beginDate || DateUtil.compareTime(new Date(), beginDate)) {
						// 报警周期0：半小时1：一小时2：按天
						Integer cycleType = dto.getCycleType();
						if (0 == cycleType) {
							dto.setBeginDate(DateUtil.addMinute(30));
						} else if (1 == cycleType) {
							dto.setBeginDate(DateUtil.addHour(1));
						} else if (2 == cycleType) {
							String cycleValue = StringUtils.isNotBlank(dto.getCycleValue()) ? dto.getCycleValue().trim() : "0";
							int hour = StringUtils.isNotBlank(cycleValue) ? Integer.valueOf(cycleValue) : 0;
							dto.setBeginDate(DateUtil.addDay(1, hour));
						}
						notifyEmail(dto, "账户余额不足");
						notifyPayeeSMS(dto, "账户余额不足");
						createOrgBalanceAlarmInfo(dto, accountBalance);
						balanceWarningService.updateOrgBalanceAlarmInfo(dto);
					}
				}
			} catch (MaAccountQueryUntxException e) {
				log.error("-----查询机构余额异常-----", e);
			}
		}

		if (log.isInfoEnabled()) {
			log.info("-----余额不足预警结束-----");
		}
	}

	private Long createOrgBalanceAlarmInfo(OrgBalanceAlarmInfo dto, Long accountBalance) {

		OrgBalanceAlarmTask orgBalanceAlarmTask = new OrgBalanceAlarmTask();
		orgBalanceAlarmTask.setMemberCode(dto.getMemberCode());
		orgBalanceAlarmTask.setMemberName(dto.getMemberName());
		orgBalanceAlarmTask.setBalance(accountBalance);
		orgBalanceAlarmTask.setMsgContent("账户余额不足");
		orgBalanceAlarmTask.setContactInfo(dto.getMobile());
		orgBalanceAlarmTask.setCreateDate(new Date());
		orgBalanceAlarmTask.setUpdateDate(new Date());
		return balanceWarningService.createOrgBalanceAlarmInfo(orgBalanceAlarmTask);
	}

	/**
	 * 预警邮件
	 * 
	 * @param memberName
	 * @param email
	 * @param tempId
	 * @param subject
	 */
	private void notifyEmail(OrgBalanceAlarmInfo dto, String subject) {
		Map<String, String> data = new HashMap<String, String>();
		// 账户名称
		data.put("memberName", dto.getMemberName());
		data.put("minBalance", AmountUtils.numberFormat(dto.getMinBalance()));

		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		List<String> emailList = null;
		String[] emails = dto.getEmail().split(",");
		if (emails.length >= 1) {
			emailList = new ArrayList<String>();
			for (String email : emails) {
				emailList.add(email);
			}
		}

		request.setRecAddress(emailList);
		request.setTemplateId(Integer.valueOf(dto.getEmailTemplate()));
		request.setRequestTime(new Date());
		request.setSubject(subject);
		notifyFacadeService.notifyRequest(request);
	}

	/**
	 * 预警短信
	 * 
	 * @param dto
	 */
	private void notifyPayeeSMS(OrgBalanceAlarmInfo dto, String subject) {
		
		Map<String, String> data = new HashMap<String, String>();
		// 账户名称
		data.put("memberName", dto.getMemberName());
		data.put("minBalance", AmountUtils.numberFormat(dto.getMinBalance()));

		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.SMS.value());
		request.setTemplateId(Integer.valueOf(dto.getSmsTemplate()));
		request.setRequestTime(new Date());

		List<String> phoneList = null;
		String[] mobilePhones = dto.getMobile().split(",");
		if (mobilePhones.length >= 1) {
			phoneList = new ArrayList<String>();
			for (String phone : mobilePhones) {
				phoneList.clear();
				phoneList.add(phone);
				request.setMobiles(phoneList);
				notifyFacadeService.notifyRequest(request);
			}
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
	}

}
