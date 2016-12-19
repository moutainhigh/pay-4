package com.pay.fundout.withdraw.service.autocheck.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.service.autocheck.JudgeService;
import com.pay.poss.base.calendar.service.CalendarService;
import com.pay.util.DateUtil;

/**
 * 风控自动审核判断标准服务类
 * @author meng.li
 *
 */
public class JudgeServiceImpl implements JudgeService {
	
	private AcctAttribService acctAttribService;
	
	private CalendarService calendarService;
	
	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}
	
	public void setCalendarService(CalendarService calendarService) {
		this.calendarService = calendarService;
	}
	
	@Override
	public boolean methodA(WithdrawAuditDTO dto) {
		if (WithDrawConstants.AUDIT_WAIT == dto.getStatus()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean methodB(WithdrawAuditDTO dto) throws Exception {
		String memberAcctCode = String.valueOf((dto.getMemberCode())).concat(String.valueOf((dto.getMemberAccType())));
		AcctAttribDto acc = acctAttribService.queryAcctAttribWithMemberAcctCode(Long.valueOf(memberAcctCode));
		if (acc == null || (0 == acc.getAllowOut() || 0 == acc.getFrozen())) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean methodC(WithdrawAuditDTO dto) {
		int added = 0;
		boolean extendFlag = true;// 默认顺延处理
		// 企业查结算周期，个人直接视为t+0
		if (2l == dto.getMemberType().longValue()) {
			// 0,1表示t+0和t+0顺延;2,3表示t+1和t+1顺延;4,5表示t+2和t+2顺延;6,7表示t+3和t+3顺延;8,9表示t+4和t+4顺延;10,11表示t+5和t+5顺延;
			added = Integer.parseInt((dto.getAccountMode())) / 2;
			int modResult = Integer.parseInt((dto.getAccountMode())) % 2;
			if (modResult == 0) {
				extendFlag = false;
			}
		}
		
		//个人提现T+1
		if(1== dto.getMemberType().longValue()){
			if(subtraction(new Date(),dto.getCreateTime())>0){
				return true;
			}else{
				return false;
			}
		}
		
		Date settleDay = DateUtil.skipDateTime(dto.getCreateTime(), added);
		Date actualSettleDay = calendarService.getActualWorkDay(settleDay, extendFlag);
		if (actualSettleDay != null) {
			if (actualSettleDay.before(new Date())) {
				return true;
			}
		} else {
			// 如果工作日是不存在日历中的，先按照到解散周期处理
			return true;
		}
		return false;
	}
	
	public static long subtraction(Date minuend, Date subtrahend) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date currDate = null;
		Date subDate = null;
		try {
			currDate = format.parse(format.format(minuend));
			subDate = format.parse(format.format(subtrahend));
		} catch (Exception e) {
			//log.error(e.getMessage(), e);
			currDate = minuend;
			subDate = subtrahend;
		}

		long daterange = currDate.getTime() - subDate.getTime();
		long time = 1000 * 3600 * 24;

		return (daterange % time == 0) ? (daterange / time)
				: (daterange / time + 1);
	}	
	
	public static void main(String[] args) throws Exception{
		Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-05-15 02:00:00");
		Date c = new Date();
		System.out.println(subtraction(c,d));
	}
}
