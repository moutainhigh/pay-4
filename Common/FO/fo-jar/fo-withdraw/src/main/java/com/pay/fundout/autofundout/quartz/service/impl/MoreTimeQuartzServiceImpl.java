package com.pay.fundout.autofundout.quartz.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.DateUtil;

/**
 * 
 * @author NEW
 *
 */
public class MoreTimeQuartzServiceImpl extends AutofundoutAbstractService {
	
	
	private final static String DATETIME_FMT="yyyy-MM-dd HH:mm";
	private Log logger = LogFactory.getLog(MoreTimeQuartzServiceImpl.class);

	@Override
	public void executeStartQuartz() {

		List<AutoFundoutResult> resultList = getAutoWithdrawService()
				.queryAutoMoreTimeFundoutResult();

		if (null == resultList || resultList.isEmpty()) {
			LogUtil.info(MoreTimeQuartzServiceImpl.class, "定时自动提现查询结果为空!",
					OPSTATUS.START,
					"MoreTimeQuartzServiceImpl.executeStartQuartz()",
					"List<AutoFundoutResult>=" + resultList);
			return;
		}
		
		Date currentDate = new Date();
		for (AutoFundoutResult autoFundoutResult : resultList) {

			Long memberCode = autoFundoutResult.getAutoFundoutConfig()
					.getMemberCode();
			String bankAcctCode = autoFundoutResult.getAutoFundoutConfig()
					.getBankAccCode();

			// 如果未到启动时间则跳过
			if (!isStartFlag(autoFundoutResult,currentDate)) {
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

			LogUtil.info(MoreTimeQuartzServiceImpl.class, "定时自动提现开始执行",
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

	private boolean isStartFlag(AutoFundoutResult result,Date currentDate) {
		Integer timeType = result.getAutoTimeConfig().getTimeType(); // 时间类型
		

		// 按时间点执行
		if (timeType.intValue() == AutoFundoutType.TIME_TYPE_HHMM.getCode()) {
			
			String timeSource = result.getAutoTimeConfig().getTimeSource(); 
			// 当前时间值
			String commonDateStr = DateUtil.dateToStr(currentDate, "yyyy-MM-dd");
			
			Date configDate = DateUtil.strToDate(commonDateStr+" "+timeSource,DATETIME_FMT);
			int configHour = DateUtil.getHour(configDate);
			int configMinute = DateUtil.getMinute(configDate);
			int currentHour = DateUtil.getHour(currentDate);
			
			if(logger.isInfoEnabled()){
				logger.info("merchantId:"+result.getAutoFundoutConfig().getMemberCode());
				logger.info("merchant configure timeSource:"+timeSource);
				logger.info("currentHour:"+currentHour);
				logger.info("configHour==currentHour&&configMinute==0 || currentHour-configHour==1&&configMinute>0");
			}
			
			if(configHour==currentHour&&configMinute==0){//当前小时值与设置小时值相等且设置分钟值等于0
				return true;
			}else if(currentHour-configHour==1&&configMinute>0){//当前小时值减设置小时值等于1且设置分钟值大于0
				return true;
			}
			
			SimpleDateFormat sdf_config = new SimpleDateFormat(DATETIME_FMT);
			String lastExecTime = getTime(currentDate,0, -1, 0,DATETIME_FMT);
			String configTime = getTime(currentDate,0, -1, 0,"yyyy-MM-dd")+" "+timeSource;
			String currentTime = sdf_config.format(currentDate);
			
			try{
				Date dateLast = sdf_config.parse(lastExecTime);
				Date dateConfig = sdf_config.parse(configTime);
				Date dateCurrent = sdf_config.parse(currentTime);
				if(dateConfig.after(dateLast) && dateConfig.before(dateCurrent)){
					return true;
				}
			}catch(ParseException e){
				logger.error("parseTime error:", e);
				return false;
			}
		} 
		return false;
	}
	
	public String getTime(Date date,int skipDay, int skipHour, int skipMinute,String partten) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		cal.add(GregorianCalendar.DAY_OF_MONTH, skipDay);
		cal.add(GregorianCalendar.HOUR_OF_DAY, skipHour);
		cal.add(GregorianCalendar.MINUTE, skipMinute);

		SimpleDateFormat dateFormat = new SimpleDateFormat(partten);

		return dateFormat.format(cal.getTime());
	}
	
/*
	public static void main(String[] args){
		MoreTimeQuartzServiceImpl impl = new MoreTimeQuartzServiceImpl();
		AutoTimeConfig config = new AutoTimeConfig();
		AutoFundoutConfig fundoutConfig = new AutoFundoutConfig();
		fundoutConfig.setMemberCode(1000000001L);
		AutoFundoutResult autoFundoutResult = new AutoFundoutResult();
		autoFundoutResult.setAutoTimeConfig(config);
		autoFundoutResult.setAutoFundoutConfig(fundoutConfig);
		autoFundoutResult.getAutoTimeConfig().setTimeType(4);
		autoFundoutResult.getAutoTimeConfig().setTimeSource("23:00");
		
		Date date = TimeUtil.parseTime("2012-07-26 23:00:00");
		
		boolean flag = impl.isStartFlag(autoFundoutResult, date);
		
		System.out.print(flag);
		
	}
	*/
}
