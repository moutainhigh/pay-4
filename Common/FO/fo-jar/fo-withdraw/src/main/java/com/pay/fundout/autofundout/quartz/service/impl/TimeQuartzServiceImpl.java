/** @Description 
 * @project 	fo-withdraw
 * @file 		AutoTimeFundoutServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-10		Henry.Zeng			Create 
 */
package com.pay.fundout.autofundout.quartz.service.impl;

import java.util.Date;
import java.util.List;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.DateUtil;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-12-10
 * @see
 */
public class TimeQuartzServiceImpl extends AutofundoutAbstractService {

	@Override
	public void executeStartQuartz() {

		List<AutoFundoutResult> resultList = getAutoWithdrawService()
				.queryAutoTimeFundoutResult();

		if (null == resultList || resultList.isEmpty()) {
			LogUtil.info(TimeQuartzServiceImpl.class, "定时自动提现查询结果为空!",
					OPSTATUS.START,
					"TimeQuartzServiceImpl.executeStartQuartz()",
					"List<AutoFundoutResult>=" + resultList);
			return;
		}

		for (AutoFundoutResult autoFundoutResult : resultList) {

			Long memberCode = autoFundoutResult.getAutoFundoutConfig()
					.getMemberCode();
			String bankAcctCode = autoFundoutResult.getAutoFundoutConfig()
					.getBankAccCode();

			// 如果未到启动时间则跳过
			if (!isStartFlag(autoFundoutResult)) {
				continue;
			}

			// 如果提现银行卡与ma绑定改变不作提现
			if (!verifyBindBankCard(memberCode, bankAcctCode)) {
				continue;
			}
			// 如果企业开通企业自动业务
			if(!isHaveProduct(memberCode)){
				continue;
			}

			LogUtil.info(TimeQuartzServiceImpl.class, "定时自动提现开始执行",
					OPSTATUS.START, autoFundoutResult.getAutoFundoutConfig()
							.getMemberCode().toString(), "[memberCode="
							+ autoFundoutResult.getAutoFundoutConfig()
									.getMemberCode()
							+ "&memberType="
							+ autoFundoutResult.getAutoFundoutConfig()
									.getMemberType()
							+ "&bankName="
							+ autoFundoutResult.getAutoFundoutConfig()
									.getBankName()
							+ "&bankCode="
							+ autoFundoutResult.getAutoFundoutConfig()
									.getBankCode()
							+ "&updateDate="
							+ autoFundoutResult.getAutoFundoutConfig()
									.getUpdateDate()
							+ "&timeType="
							+ autoFundoutResult.getAutoTimeConfig()
									.getTimeType()
							+ "&timeSource="
							+ autoFundoutResult.getAutoTimeConfig()
									.getTimeSource() + "]");
			autoFundoutResult.setAutoType(AutoFundoutType.AUTO_TIME.getCode());
			getNotifyFacadeService().sendRequest(
					buildNotify2QueueRequest(autoFundoutResult));
		}
	}

	private boolean isStartFlag(AutoFundoutResult result) {
		Integer timeType = result.getAutoTimeConfig().getTimeType(); // 时间类型
		String timeSource = result.getAutoTimeConfig().getTimeSource(); // 时间值
		Date updateDate = result.getAutoFundoutConfig().getUpdateDate(); // 修改时间

		// 按天执行
		if (timeType.intValue() == AutoFundoutType.TIME_TYPE_DAY.getCode()) {
			// 计算设置的时间与当前相隔的天数
			long day = DateUtil.subtraction(new Date(), updateDate);
			if ((day % (Integer.valueOf(timeSource))) == 0) {
				return true;
			} else {
				return false;
			}
			// 按周执行
		} else if (timeType.intValue() == AutoFundoutType.TIME_TYPE_WEEK
				.getCode()) {
			int week = DateUtil.getWeekOfDay(new Date());
			return DateUtil.existInWeek(timeSource, week);
			// 按月执行
		} else if (timeType.intValue() == AutoFundoutType.TIME_TYPE_MONTH
				.getCode()) {
			int day = DateUtil.getDay(new Date());
			return (day == Integer.parseInt(timeSource));
		} else {
			return false;
		}
	}

}
