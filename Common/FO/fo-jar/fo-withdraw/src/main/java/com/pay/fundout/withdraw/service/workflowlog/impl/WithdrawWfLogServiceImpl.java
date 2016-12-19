package com.pay.fundout.withdraw.service.workflowlog.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.workflowlog.WithdrawWfLogDAO;
import com.pay.fundout.withdraw.dto.workflowlog.WithdrawWfLogDTO;
import com.pay.fundout.withdraw.model.workflowlog.WithdrawWfLog;
import com.pay.fundout.withdraw.service.workflowlog.WithdrawWfLogService;

public class WithdrawWfLogServiceImpl implements WithdrawWfLogService {

	private WithdrawWfLogDAO withdrawWfLogDAO;
	
	public void setWithdrawWfLogDAO(WithdrawWfLogDAO withdrawWfLogDAO) {
		this.withdrawWfLogDAO = withdrawWfLogDAO;
	}
	public WithdrawWfLogDAO getWithdrawWfLogDAO() {
		return withdrawWfLogDAO;
	}
	
	@Override
	public List<WithdrawWfLogDTO> queryWithdrawWfLogList(String workflowId) {
		List<WithdrawWfLog> modelList;
		WithdrawWfLogDTO model = new WithdrawWfLogDTO();
		model.setWorkflowId(workflowId);
		modelList = withdrawWfLogDAO.findBySelective(model);
		if (modelList == null || modelList.size() == 0) {
			return null;
		}
		List<WithdrawWfLogDTO> dtoList = new ArrayList<WithdrawWfLogDTO>();
		for (WithdrawWfLog mm : modelList) {
			WithdrawWfLogDTO dto = new WithdrawWfLogDTO();
			BeanUtils.copyProperties(mm, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * 保存历史信息
	 */
	@Override
	public Long saveWithdrawWfLog(WithdrawWfLogDTO dto) {
		if(dto != null){
			WithdrawWfLog model = new WithdrawWfLog();
			BeanUtils.copyProperties(dto, model);
			return (Long) withdrawWfLogDAO.create(model);
		}
		return null;
	}
	
	
	/**
	 * 根据工单信息查询历史
	 * @param workOrderKy
	 * @return
	 */
	@Override
	public List<WithdrawWfLogDTO> queryWithDrawWfLogByWorkOrderKy(
			Long workOrderKy) {
		List<WithdrawWfLog> modelList;
		WithdrawWfLogDTO model = new WithdrawWfLogDTO();
		model.setWorkOrderKy(workOrderKy);
		modelList = withdrawWfLogDAO.findBySelective(model);
		if (modelList == null || modelList.size() == 0) {
			return null;
		}
		List<WithdrawWfLogDTO> dtoList = new ArrayList<WithdrawWfLogDTO>();
		for (WithdrawWfLog mm : modelList) {
			WithdrawWfLogDTO dto = new WithdrawWfLogDTO();
			BeanUtils.copyProperties(mm, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	@Override
	public List<WithdrawWfLogDTO> queryWithDrawWfLogByOrderKy(
			Long OrderKy) {
		List<WithdrawWfLog> modelList;
		modelList = withdrawWfLogDAO.findByQuery("findByOrderId", OrderKy);
		if (modelList == null || modelList.size() == 0) {
			return null;
		}
		List<WithdrawWfLogDTO> dtoList = new ArrayList<WithdrawWfLogDTO>();
		for (WithdrawWfLog mm : modelList) {
			WithdrawWfLogDTO dto = new WithdrawWfLogDTO();
			BeanUtils.copyProperties(mm, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
