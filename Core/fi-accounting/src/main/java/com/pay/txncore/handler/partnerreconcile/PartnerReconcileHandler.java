/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.partnerreconcile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 商户对账单下载
 * 
 * @author chma
 */
public class PartnerReconcileHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(PartnerReconcileHandler.class);
	private BaseDAO crossPayOrderQueryDao;

	public void setCrossPayOrderQueryDao(BaseDAO crossPayOrderQueryDao) {
		this.crossPayOrderQueryDao = crossPayOrderQueryDao;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String partnerId = paraMap.get("partnerId");
		String monthTime = paraMap.get("monthTime");
		String dayTime = paraMap.get("dayTime");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("memberCode", partnerId);
		if (null != monthTime) {
			mapParam.put("monthTime", monthTime);
		} else {
			mapParam.put("dayTime", dayTime);
		}

		try {
			if (!StringUtil.isEmpty(monthTime)) {
				mapParam.put("startTime", monthTime + "-01");
				mapParam.put("endTime", getNextMonthFirstDay(monthTime + "-01"));
			}

			if (!StringUtil.isEmpty(dayTime)) {
				mapParam.put("startTime", dayTime);
				mapParam.put("endTime", getNextDay(dayTime));
			}
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		List settlementList = crossPayOrderQueryDao.findByCriteria(
				"querySettlementOrder", mapParam);
		
		List riskList = crossPayOrderQueryDao.findByCriteria("queryRiskOrder",
				mapParam);
		List refundList = crossPayOrderQueryDao.findByCriteria(
				"queryRefundOrder", mapParam);

		resultMap.put("settlementList", settlementList);
		resultMap.put("refundList", refundList);
		resultMap.put("riskList", riskList);

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());

		return JSonUtil.toJSonString(resultMap);
	}

	// 获取下个月第一天
	private String getNextMonthFirstDay(String dateTime) throws Exception {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateTime);
		c.setTime(date);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	// 获取第二天
	private String getNextDay(String dateTime) throws Exception {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateTime);
		c.setTime(date);
		c.add(c.DATE, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
}
