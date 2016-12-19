/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.external.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ExternalTypeEnum;
import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.dao.external.ExternalLogDao;
import com.pay.base.model.ExternalLog;
import com.pay.base.service.external.ExternalLogService;

/**
 * @author fjl
 * @date 2011-9-19
 */
public class ExternalLogServiceImpl implements ExternalLogService {

	private Log log = LogFactory.getLog(this.getClass());
	
	private ExternalLogDao externalLogDao;
	
	private static final String PREFIX_CODE = "300";
	
	@Override
	public boolean rechargeGetWaySuccess(Long id,String getWayIOrderNo,String remark) {
		log.info("网关状态成功，网关收单号为："+getWayIOrderNo+"，处理开始。。。");
		/*
		String[] values  = parsePayObjs(remark);
		int payType = new Integer(values[0]);
		String payChannel = values[1];
		*/
		ExternalLog ets = new ExternalLog();
		ets.setId(id);
		ets.setUpdateDate(new Date());
		/*
		if(payType>-1){
			ets.setPayType(payType);
			String pce = PayChannelUtil.getPayChannelNameByCode(payChannel);
			if(pce != null)
				ets.setPayChannel(pce);
		}*/
		
		ets.setRemark(remark);
		ets.setOrderNo(getWayIOrderNo);
		ets.setExternalProcessStatus(ExternalProcessStatus.EXTERNAL_PROCESS_SUCCED.getValue());	
		try{
			boolean procees = this.externalLogDao.update(ets);
			if(procees){
				log.info("更新RechargeExternalProcessStatus成功，网关收单号为："+getWayIOrderNo+"，本地日志ID为："+id+"，准备更新余额...");
			}else{
				log.info("更新RechargeExternalProcessStatus失败，网关收单号为："+getWayIOrderNo+"，本地日志ID为："+id+"");
				return false;
			}
		}catch (Exception e) {
			log.info("更新RechargeExternalProcessStatus失败，网关收单号为："+getWayIOrderNo+"，本地日志ID为："+id+"，准备更新余额...");
			//等补单了
			return false ;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param remark
	 * @return
	 */
	private String[] parsePayObjs(String remark){
		String[] values = new String[]{"-1",""};
		if(remark!=null && remark.contains("[") && remark.contains("]") ){
			int indexBg = remark.indexOf("[")+1;
			int indexEnd = remark.indexOf("]");
			String str = remark.substring(indexBg,indexEnd+1);
			if(str.contains(",")){
				String []v2s = str.split(",");
				values = new String[]{v2s[0],v2s[1].replaceAll("([a-zA-Z]+)(.)+", "$1")};
			}
		}
		return values;
	}
	
	
	@Override
	public boolean rechargeGetWayFailed(Long id, String getWayOrderNo,
			String remark) {
		/*
		String[] values  = parsePayObjs(remark);
		int payType = new Integer(values[0]);
		String payChannel = values[1];
		*/
		ExternalLog ets = new ExternalLog();
		ets.setId(id);
		ets.setUpdateDate(new Date());
		/*
		if(payType>-1){
			ets.setPayType(payType);
			String pce = PayChannelUtil.getPayChannelNameByCode(payChannel);
			if(pce != null)
				ets.setPayChannel(pce);
		}*/
		
		ets.setRemark(remark);
		ets.setOrderNo(getWayOrderNo);
		ets.setExternalProcessStatus(ExternalProcessStatus.EXTERNAL_PROCESS_FAILED.getValue());	
		ets.setProcessStatus(ProcessStatus.PROCESS_FAILED.getValue());
		this.externalLogDao.update(ets);
		//更新状态
		
		return true;
	}
	
	@Override
	public Long createExternalLogRdTx(ExternalLog externalLog) throws Exception {
		
		externalLog.setProcessStatus(ProcessStatus.PROCESS_INIT.getValue());
		externalLog.setExternalProcessStatus(null);
		if(externalLog.getExternalType() == null){
			externalLog.setExternalType(ExternalTypeEnum.PAY_CHAIN.getType());
		}
		if(externalLog.getOrderNo()== null){
			externalLog.setOrderNo(getOrderNo());
		}
		
		externalLog.setCreateDate(new Date());
		externalLog.setUpdateDate(null);
		
		BigDecimal amount = externalLog.getAmount();
		externalLog.setAmount(amount.multiply(BigDecimal.valueOf(1000)));//用1000存放
		
		/*
		externalLog.setCardNo(cardNo);
		externalLog.setOrderNo(orderNo);
		externalLog.setCreateDate(new Date());
		externalLog.setExternalType(2);//商旅卡充值 是1 ,支付链是2
		*/
		Long id = (Long) externalLogDao.create(externalLog);
		//金额还原到元
		externalLog.setAmount(amount);
		return id;
	}
	
	@Override
	public boolean updateStatusRdTx(ExternalLog externalLog) throws Exception {
		
		if(externalLog.getId() == null){
			throw new NullPointerException("id not null");
		}
		
		if(externalLog.getProcessStatus()== null || externalLog.getExternalProcessStatus() == null){
			throw new NullPointerException("status not null");
		}
		
		return externalLogDao.update(externalLog);
	}
	

	@Override
	public ExternalLog findByOrderNo(String orderNo) {
		ExternalLog externalLog  = externalLogDao.findByOrderNo(orderNo);
		if(externalLog!=null){
			externalLog.setAmount(externalLog.getAmount().divide(BigDecimal.valueOf(1000)));		
		}
		return externalLog;
	}

	@Override
	public ExternalLog selectExernalTransByStatus(Long id) {
		return externalLogDao.selectExernalTransByStatus(id, 1, 1);
	}


	@Override
	public int updateProcessStatus(Long id, ProcessStatus status) {
		return externalLogDao.updateProcessStatus(id, status);
	}


	public  String getOrderNo(){
		return externalLogDao.selectOrderNo(PREFIX_CODE);
	}
	
	public  String getOrderNo(String prefixCode){
		return externalLogDao.selectOrderNo(prefixCode);
	}

	public void setExternalLogDao(ExternalLogDao externalLogDao) {
		this.externalLogDao = externalLogDao;
	}
	
}
