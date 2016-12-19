 /** @Description 
 * @project 	poss-refund
 * @file 		MyServiceUtil.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-30		sunsea.li		Create 
*/
package com.pay.poss.refund.common.util;

import org.apache.commons.lang.StringUtils;

import com.pay.inf.dao.Page;
import com.pay.poss.dto.fi.DepositQueryParamDTO;
import com.pay.poss.refund.model.MemberInfoDTO;
import com.pay.poss.refund.model.WebQueryRefundDTO;

public class MyServiceUtil {
	
	//封装调用网关接口获取充值信息的查询条件
	public static void wrapQueryDto(Page page,
			WebQueryRefundDTO dto,DepositQueryParamDTO gwQueryDto,MemberInfoDTO memberInfo){
		gwQueryDto.setPageNo(new Integer(page.getPageNo()));//分页参数
		gwQueryDto.setPageSize(new Integer(page.getPageSize()));//分页参数
		if(null!=dto.getAccount() && !StringUtils.isEmpty(dto.getAccount().trim())){
			gwQueryDto.setMemberCode(new Long(dto.getAccount().trim()));//账户名
		}else{
			gwQueryDto.setMemberCode(new Long(memberInfo.getMemberNo()));//账户名
		}
		if(!StringUtils.isEmpty(dto.getMemberType())){
			gwQueryDto.setMemberTypeEnum(Integer.valueOf(dto.getMemberType()));//会员类型
		}
		if(!StringUtils.isEmpty(dto.getBankCode())){
			gwQueryDto.setBankChannel(dto.getBankCode());//充值渠道
		}
		if(!StringUtils.isEmpty(dto.getRechargeStatus())){
			gwQueryDto.setDepositStatus(new Integer(dto.getRechargeStatus()));//充值状态
		}
		if(null!=dto.getRechargeSeq() && !StringUtils.isEmpty(dto.getRechargeSeq().trim())){
			gwQueryDto.setDepositProtocolId(new Long(dto.getRechargeSeq().trim()));//充值流水号
		}
		if(null!=dto.getBankSeq() && !StringUtils.isEmpty(dto.getBankSeq().trim())){
			gwQueryDto.setBankOrderId(dto.getBankSeq().trim());//充值 银行流水号(****)
		}
		if(null!=dto.getStartTime()){
			gwQueryDto.setDepositProtocolStartDate(dto.getStartTime());//开始时间
		}
		if(null!=dto.getEndTime()){
			gwQueryDto.setDepositProtocolEndDate(dto.getEndTime());//结束时间
		}
		if(null!=dto.getQueryType()){
			//gwQueryDto.setQueryType(Integer.valueOf(dto.getQueryType()));
		}
		if(StringUtils.isNotEmpty(dto.getMerchantOrderId())){
			//gwQueryDto.setMerchantOrderId(dto.getMerchantOrderId());
		}
		
	}
	
	/**
	 * 将对象转换成字符串
	 * @param obj
	 * @return String
	 */
	public static String transfObj(Object obj){
		if(null == obj) return "";
		return obj.toString();
	}
}
