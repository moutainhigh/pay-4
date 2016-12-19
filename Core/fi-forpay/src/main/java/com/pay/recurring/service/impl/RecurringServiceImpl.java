package com.pay.recurring.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.jms.notification.request.NotifyRequest;
import com.pay.recurring.dao.RecurringDAO;
import com.pay.recurring.service.RecurringService;


/**
 * 循环扣款处理service
 * @author peiyu.yang
 *
 */
public class RecurringServiceImpl implements RecurringService {
	
	private RecurringDAO recurringDao;
	
	public void setRecurringDao(RecurringDAO recurringDao) {
		this.recurringDao = recurringDao;
	}

	@Override
	public boolean process(NotifyRequest notifyRequest) {
		//	System.out.println("notifyRequest: "+notifyRequest);
		Map<String, String> data = notifyRequest.getData(); //获取MQ发送的数据
		String recurringStatus=data.get("recurringStatus"); // 获取类型 1 创建 2取消
		if(recurringStatus.equals("1")){
			String maxFailedTimes= data.get("maxFailedTimes");
			String period= data.get("period");
			if(StringUtils.isEmpty(maxFailedTimes)){
				data.put("maxFailedTimes","3");
			}
			if(StringUtils.isEmpty(period)){
				data.put("period","9999");
			}
			data.put("effFlag","Y");
			data.put("paymentDay", new Date().getDate()+"");
			this.recurringDao.createRecurring(data);
			return true;
		}
		return false;
	}

}
