/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import java.text.ParseException;
import java.util.Calendar;

import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 验证提交时间
 */
public class QueryTimeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();
		DateTimeFormatter SFFINAL = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		String mode = orderApiQueryRequest.getMode();
		String beginTime = orderApiQueryRequest.getBeginTime();
		String endTime = orderApiQueryRequest.getEndTime();
		if ("2".equals(mode)) {
			if(StringUtil.isEmpty(beginTime)){
				orderApiQueryResponse.setResultCode("0104");
				orderApiQueryResponse.setResultMsg("the startTime can't be null: 开始时间不能为空");
				return false;
			}
			if(StringUtil.isEmpty(endTime)){
				orderApiQueryResponse.setResultCode("0105");
				orderApiQueryResponse.setResultMsg("the endTime can't be null: 结束时间不能为空");
				return false;
			}
			if(beginTime.length() != 14){
				orderApiQueryResponse.setResultCode("0116");
				orderApiQueryResponse.setResultMsg("the startTime bad format: 开始时间格式错误");
				return false;
			}
			if(endTime.length() != 14){
				orderApiQueryResponse.setResultCode("0116");
				orderApiQueryResponse.setResultMsg("the endTime bad format: 结束时间格式错误");
				return false;
			}
			Calendar sDate =  Calendar.getInstance();
			try{
				sDate.setTime(SFFINAL.parseDateTime(beginTime).toDate());
			}catch (Exception e){
				orderApiQueryResponse.setResultCode("0106");
				orderApiQueryResponse.setResultMsg("the startTime bad format: 开始时间格式错误");
				return false;
			}
			Calendar eDate =  Calendar.getInstance();
			try{
				eDate.setTime(SFFINAL.parseDateTime(endTime).toDate());
			}catch (Exception e){
				orderApiQueryResponse.setResultCode("0107");
				orderApiQueryResponse.setResultMsg("the endTime bad format: 结束时间格式错误");
				return false;
			}
			if(eDate.compareTo(sDate) < 0){
				orderApiQueryResponse.setResultCode("0108");
				orderApiQueryResponse.setResultMsg("the endTime must large than startTime: 结束时间必须大于开始时间");
				return false;
			}
			Calendar sDateplus15 = Calendar.getInstance();
			sDateplus15.setTime(sDate.getTime());
			sDateplus15.add(Calendar.DATE, 15);
			if(eDate.compareTo(sDateplus15) > 0){
				orderApiQueryResponse.setResultCode("0109");
				orderApiQueryResponse.setResultMsg("Can only check for 15 days records: 只能查询15天记录");
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws ParseException {
		DateTimeFormatter sf = DateTimeFormat.forPattern("yyyyMMddHHmmss");

		System.out.println(sf.parseDateTime("20161018153919"));
	}
}
