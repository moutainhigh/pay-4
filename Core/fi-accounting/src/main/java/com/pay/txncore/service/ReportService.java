/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.txncore.model.ChannelOrderReport;
import com.pay.txncore.model.TranDailyReportVo;

/**
 * 收单、支付服务
 * 
 * @author chaoyue
 *
 */
public interface ReportService {

	/**
	 * 交易日报表
	 * 
	 * @param maxSize
	 */
	List<TranDailyReportVo> tranDailyReport(List<ChannelOrderReport> channelOrders);
	
	
	
}
