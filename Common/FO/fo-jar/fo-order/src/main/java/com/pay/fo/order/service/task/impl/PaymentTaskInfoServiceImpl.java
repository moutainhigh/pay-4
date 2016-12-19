/**
 * 
 */
package com.pay.fo.order.service.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.dao.task.PaymentTaskInfoDAO;
import com.pay.fo.order.dto.task.PaymentTaskInfoDTO;
import com.pay.fo.order.model.task.PaymentTaskInfo;
import com.pay.fo.order.model.task.PaymentTaskInfoExample;
import com.pay.fo.order.service.task.PaymentTaskInfoService;

/**
 * @author NEW
 *
 */
public class PaymentTaskInfoServiceImpl implements PaymentTaskInfoService {
	
	private PaymentTaskInfoDAO paymentTaskInfoDAO;
	
	

	public void setPaymentTaskInfoDAO(PaymentTaskInfoDAO paymentTaskInfoDAO) {
		this.paymentTaskInfoDAO = paymentTaskInfoDAO;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.task.PaymentTaskInfoService#create(com.pay.fo.order.dto.task.PaymentTaskInfoDTO)
	 */
	@Override
	public void create(PaymentTaskInfoDTO task) {
		PaymentTaskInfo record = new PaymentTaskInfo();
		BeanUtils.copyProperties(task, record);
		paymentTaskInfoDAO.insertSelective(record);
	}
	
	@Override
	public boolean update(PaymentTaskInfoDTO task, Integer oldStatus) {
		PaymentTaskInfo record = new PaymentTaskInfo();
		BeanUtils.copyProperties(task, record);
		PaymentTaskInfoExample example = new PaymentTaskInfoExample();
		record.setMemberCode(null);
		record.setTaskType(null);
		example.createCriteria()
			   .andMemberCodeEqualTo(task.getMemberCode())
			   .andTaskTypeEqualTo(task.getTaskType())
			   .andTaskBatchNoEqualTo(task.getTaskBatchNo())
			   .andStatusEqualTo(oldStatus);
		return paymentTaskInfoDAO.updateByExampleSelective(record, example)==1;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.task.PaymentTaskInfoService#get(java.lang.String, java.lang.Integer, java.lang.Long)
	 */
	@Override
	public PaymentTaskInfoDTO get(String taskBatchNo, Integer taskType,
			Long memberCode) {
		PaymentTaskInfoExample example = new PaymentTaskInfoExample();
		example.createCriteria()
			   .andMemberCodeEqualTo(memberCode)
			   .andTaskTypeEqualTo(taskType)
			   .andTaskBatchNoEqualTo(taskBatchNo);
		List records = paymentTaskInfoDAO.selectByExample(example);
		PaymentTaskInfo record = null; 
		PaymentTaskInfoDTO dto = null;
		if(records!=null&&records.size() == 1){
			record = (PaymentTaskInfo)records.get(0);
			dto = new PaymentTaskInfoDTO();
			BeanUtils.copyProperties(record, dto);
		}
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.task.PaymentTaskInfoService#getList(java.lang.Integer)
	 */
	@Override
	public List<PaymentTaskInfoDTO> getList(Integer status) {
		List<PaymentTaskInfoDTO> results = new ArrayList<PaymentTaskInfoDTO>();
		PaymentTaskInfoDTO dto = null;
		PaymentTaskInfo record = null;
		PaymentTaskInfoExample example = new PaymentTaskInfoExample();
		example.createCriteria()
			   .andStatusEqualTo(status);
		List records = paymentTaskInfoDAO.selectByExample(example);
		for (Object obj : records) {
			dto = new PaymentTaskInfoDTO();
			record = (PaymentTaskInfo)obj;
			BeanUtils.copyProperties(record, dto);
			results.add(dto);
		}
		return results;
	}

	@Override
	public List<PaymentTaskInfoDTO> getList(Integer status, Date excuteDate) {
		List<PaymentTaskInfoDTO> results = new ArrayList<PaymentTaskInfoDTO>();
		PaymentTaskInfoDTO dto = null;
		PaymentTaskInfo record = null;
		PaymentTaskInfoExample example = new PaymentTaskInfoExample();
		example.createCriteria()
			   .andStatusEqualTo(status)
			   .andExcuteDateLessThanOrEqualTo(excuteDate);
		List records = paymentTaskInfoDAO.selectByExample(example);
		for (Object obj : records) {
			dto = new PaymentTaskInfoDTO();
			record = (PaymentTaskInfo)obj;
			BeanUtils.copyProperties(record, dto);
			results.add(dto);
		}
		return results;
	}

}
