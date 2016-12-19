package com.pay.pe.manualbooking.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.dao.VouchDataLogDao;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.model.VouchDataLog;
import com.pay.pe.manualbooking.service.VouchLogService;
import com.pay.pe.manualbooking.util.VouchDataStatus;

/**
 *
 * 手工记账日志服务实现
 */
public class VouchLogServiceImpl implements VouchLogService {	
	private static final Log LOG = LogFactory.getLog(VouchLogServiceImpl.class);
	/**
	 * 手工记账日志数据访问对象
	 */
	private VouchDataLogDao vouchDataLogDao;
	
	public VouchDataLogDao getVouchDataLogDao() {
		return vouchDataLogDao;
	}

	public void setVouchDataLogDao(VouchDataLogDao vouchDataLogDao) {
		this.vouchDataLogDao = vouchDataLogDao;
	}

	public VouchLogServiceImpl() {
		
	}
	
	private VouchDataLog constructLog(VouchDataDto vouchDataDto, String operator, String ip, String remark) {
		VouchDataLog vouchDataLog = new VouchDataLog();
		
		vouchDataLog.setApplicationCode(vouchDataDto.getApplicationCode());
		vouchDataLog.setApplyDate(vouchDataDto.getCreateDate());
		vouchDataLog.setCreator(vouchDataDto.getCreator()); 
		vouchDataLog.setIp(ip);
		vouchDataLog.setOperator(operator);
		vouchDataLog.setRemark(remark);
		
		//vouchDataLog.setAuditor(vouchDataDto.getAuditor());
		//vouchDataLog.setAuditDate(vouchDataDto.getAuditDate());
		//vouchDataLog.setAccountingDate(vouchDataDto.getAccountingDate()); 
		//vouchDataLog.setCancelDate(vouchDataDto.getCreateDate());
		//vouchDataLog.setOriginalStatus(vouchDataDto.getStatus());
		//vouchDataLog.setExpectStatus(vouchDataDto.getStatus());
		//vouchDataLog.setVouchCode(vouchDataDto.getVouchCode());
		
		return vouchDataLog;
	}

	public void logApproveFailure(VouchDataDto currentDto, String operator,
			String ip, String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(currentDto, operator, ip, remark);
		
		//auditor
		vouchDataLog.setAuditor(operator);
		//audit date
		vouchDataLog.setAuditDate(new Date());
		//original status
		vouchDataLog.setOriginalStatus(currentDto.getStatus());
		//expected status
		vouchDataLog.setExpectStatus(VouchDataStatus.APPROVED.getValue());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logApproveSuccess(VouchDataDto beforeActionDto,
			VouchDataDto afterActionDto, String operator, String ip,
			String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(beforeActionDto, operator, ip, remark);
		
		//auditor
		vouchDataLog.setAuditor(operator);
		//audit date
		vouchDataLog.setAuditDate(afterActionDto.getAuditDate());
		//account date
		vouchDataLog.setAccountingDate(afterActionDto.getAccountingDate());
		//original status
		vouchDataLog.setOriginalStatus(beforeActionDto.getStatus());
		//expect status
		vouchDataLog.setExpectStatus(afterActionDto.getStatus());
		//vouch code
		vouchDataLog.setVouchCode(afterActionDto.getVouchCode());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logCancelFailure(VouchDataDto currentDto, String operator,
			String ip, String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(currentDto, operator, ip, remark);
		
		//cancel date
		vouchDataLog.setCancelDate(new Date());
		//original status
		vouchDataLog.setOriginalStatus(currentDto.getStatus());
		//expect status
		vouchDataLog.setExpectStatus(VouchDataStatus.CANCELED.getValue());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logCancelSuccess(VouchDataDto beforeActionDto,
			VouchDataDto afterActionDto, String operator, String ip,
			String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(beforeActionDto, operator, ip, remark);
		
		//cancel date
		vouchDataLog.setCancelDate(new Date());
		//original status
		vouchDataLog.setOriginalStatus(beforeActionDto.getStatus());
		//expect status
		vouchDataLog.setExpectStatus(afterActionDto.getStatus());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logCreate(VouchDataDto currentDto, String operator, String ip,
			String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(currentDto, operator, ip, remark);
		
		//expect status
		vouchDataLog.setExpectStatus(currentDto.getStatus());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logRejectFailure(VouchDataDto currentDto, String operator,
			String ip, String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(currentDto, operator, ip, remark);
		
		//auditor
		vouchDataLog.setAuditor(operator);
		//audit date
		vouchDataLog.setAuditDate(new Date());
		//original status
		vouchDataLog.setOriginalStatus(currentDto.getStatus());
		//expected status
		vouchDataLog.setExpectStatus(VouchDataStatus.REJECTED.getValue());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}

	public void logRejectSuccess(VouchDataDto beforeActionDto,
			VouchDataDto afterActionDto, String operator, String ip,
			String remark) {
		LOG.info("Start");
		
		VouchDataLog vouchDataLog = constructLog(beforeActionDto, operator, ip, remark);
		
		//auditor
		vouchDataLog.setAuditor(operator);
		//audit date
		vouchDataLog.setAuditDate(afterActionDto.getAuditDate());
		//original status
		vouchDataLog.setOriginalStatus(beforeActionDto.getStatus());
		//expected status
		vouchDataLog.setExpectStatus(afterActionDto.getStatus());
		
		this.vouchDataLogDao.saveVouchDataLog(vouchDataLog);
		LOG.info("End");
	}
	
	

}
