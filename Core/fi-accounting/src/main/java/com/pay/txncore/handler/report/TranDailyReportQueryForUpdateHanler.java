/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.TranDailyReportVo;
import com.pay.txncore.service.TranDailyReportService;
import com.pay.util.JSonUtil;

/**
 * 交易日报表
 * 
 * 
 */
@SuppressWarnings({"unchecked"})
public class TranDailyReportQueryForUpdateHanler implements EventHandler {

	public final Log logger = LogFactory.getLog(TranDailyReportQueryForUpdateHanler.class);
	
	private TranDailyReportService tranDailyReportService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			System.out.println("TranDailyReportQueryForUpdateHanler handle............") ;
			Map<String, Object> paraMap = null;
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			List<TranDailyReportVo> tranDailyReportList = this.tranDailyReportService.queryTranDailyReportList(paraMap) ;
			if(null != tranDailyReportList){
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
				resultMap.put("result", tranDailyReportList) ;
			}
		} catch (Exception e) {
			logger.error("TranDailyReportQueryForUpdateHanler query error....");
			resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	
	public void setTranDailyReportService(
			TranDailyReportService tranDailyReportService) {
		this.tranDailyReportService = tranDailyReportService;
	}
}
