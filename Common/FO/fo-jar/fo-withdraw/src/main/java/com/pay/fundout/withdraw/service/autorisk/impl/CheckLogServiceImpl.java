package com.pay.fundout.withdraw.service.autorisk.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.pay.fundout.withdraw.common.util.WithDrawAutoRiskConstants;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.dao.autorisk.CheckLogDAO;
import com.pay.fundout.withdraw.dao.autorisk.LoginFailLogDAO;
import com.pay.fundout.withdraw.dao.flowprocess.WithdrawAuditWorkOrderDao;
import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.fundout.withdraw.dto.autorisk.LoginFailLogDto;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.fundout.withdraw.service.autorisk.CheckLogService;
import com.pay.inf.dao.Page;

public class CheckLogServiceImpl implements CheckLogService {

	private CheckLogDAO checkLogDAO;
	
	private LoginFailLogDAO loginFailLogDAO;
	
	private WithdrawAuditWorkOrderDao withdrawAuditWorkOrderDao;
	
	public void setCheckLogDAO(CheckLogDAO checkLogDAO) {
		this.checkLogDAO = checkLogDAO;
	}
	
	public void setLoginFailLogDAO(LoginFailLogDAO loginFailLogDAO) {
		this.loginFailLogDAO = loginFailLogDAO;
	}

	public void setWithdrawAuditWorkOrderDao(WithdrawAuditWorkOrderDao withdrawAuditWorkOrderDao) {
		this.withdrawAuditWorkOrderDao = withdrawAuditWorkOrderDao;
	}

	@Override
	public Long createCheckLog(AutoRiskDto dto) {
		return this.checkLogDAO.createCheckLog(dto);
	}
	
	@Override
	public Long createLoginFailLog(LoginFailLogDto dto) {
		return this.loginFailLogDAO.createLoginFailLog(dto);
	}

	@Override
	public void createCheckLogsRdTx(List<AutoRiskDto> list) {
		if(list != null && list.size() > 0){
			for(AutoRiskDto dto : list){
				this.checkLogDAO.createCheckLog(dto);
			}
		}
	}

	@Override
	public AutoRiskDto findById(Long recordNo) {
		return this.checkLogDAO.findById(recordNo);
	}

	@Override
	public Page<AutoRiskDto> getCheckLogList(Page<AutoRiskDto> page, AutoRiskDto dto) {
		return this.checkLogDAO.getCheckLogList(page, dto);
	}
	
	@Override
	public List<AutoRiskDto> queryCheckLogList(AutoRiskDto dto) {
		return this.checkLogDAO.queryCheckLogList(dto);
	}

	@Override
	public void updateCheckLog(AutoRiskDto dto) {
		this.checkLogDAO.updateCheckLog(dto);
	}
	
	@Override
	public void CheckLogsRdTx(String recordNos, String operator, String checkRemark) {
		List<String> recordNoList = Arrays.asList(recordNos.split(","));
		for(String recordNo : recordNoList){
			AutoRiskDto dto = this.findById(Long.valueOf(recordNo));
			dto.setStatus(WithDrawAutoRiskConstants.STATUS_CHECKED);
			dto.setUpdateDate(new Date());
			dto.setCheckRemark(checkRemark);
			dto.setOperator(operator);
			this.updateCheckLog(dto);
			
			AutoRiskDto queryDto = new AutoRiskDto();
			queryDto.setOrderNo(dto.getOrderNo());
			queryDto.setStatus(WithDrawAutoRiskConstants.STATUS_NOT_CHECKED);
			List<AutoRiskDto> notCheckedLogs = this.queryCheckLogList(queryDto);
			// 这笔订单没有未审核记录的情况则更新原始工单状态为初始状态，否则不更新
			if(notCheckedLogs == null || notCheckedLogs.size() < 1){
				WithdrawWorkorder withdrawWorkorder = this.withdrawAuditWorkOrderDao.queryWorkOrderById("WF.queryWorkOrderByOrderId", dto.getOrderNo());
				if(withdrawWorkorder.getStatus() == WithDrawConstants.AUTO_RISK_TO_DEAL){
					withdrawWorkorder.setStatus(WithDrawConstants.AUDIT_INIT);
					// 修改操作
					this.withdrawAuditWorkOrderDao.update("WF.update", withdrawWorkorder);
				}
			}
		}
		
	}

}
