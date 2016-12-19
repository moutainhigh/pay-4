/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fo.order.dao.batchpayment.BatchPaymentReqBaseInfoDAO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseInfo;
import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseInfoExample;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;

/**
 * @author NEW
 *
 */
public class BatchPaymentReqBaseInfoServiceImpl implements
		BatchPaymentReqBaseInfoService {
	
	private BatchPaymentReqBaseInfoDAO batchPaymentReqBaseInfoDAO;

	@Override
	public Long create(BatchPaymentReqBaseInfoDTO info) {
		BatchPaymentReqBaseInfo model = new BatchPaymentReqBaseInfo();
		info.setUpdateDate(info.getCreateDate());
		BeanUtils.copyProperties(info, model);
		return batchPaymentReqBaseInfoDAO.insertSelective(model);
	}
	
	
	@Override
	public boolean update(BatchPaymentReqBaseInfoDTO info) {
		if(info!=null){
			BatchPaymentReqBaseInfo model = new BatchPaymentReqBaseInfo();
			BeanUtils.copyProperties(info, model);
			return batchPaymentReqBaseInfoDAO.updateByPrimaryKeySelective(model)==1?true:false;
		}
		return false;
	}

	@Override
	public boolean updateStatus(BatchPaymentReqBaseInfoDTO info, int oldStatus) {
		BatchPaymentReqBaseInfo model = new BatchPaymentReqBaseInfo();
		model.setAuditor(info.getAuditor());
		model.setAuditRemark(info.getAuditRemark());
		model.setUpdateDate(info.getUpdateDate());
		model.setStatus(info.getStatus());
		model.setBusinessBatchNo(info.getBusinessBatchNo());
		
		BatchPaymentReqBaseInfoExample example = new BatchPaymentReqBaseInfoExample();
		  example.createCriteria()
		  .andRequestSeqEqualTo(info.getRequestSeq())
		  .andStatusEqualTo(oldStatus);
		return batchPaymentReqBaseInfoDAO.updateByExampleSelective(model, example)==1?true:false;
	}

	@Override
	public BatchPaymentReqBaseInfoDTO getBatchPaymentReqBaseInfo(Long requestSeq) {
		BatchPaymentReqBaseInfo model = batchPaymentReqBaseInfoDAO.selectByPrimaryKey(requestSeq);
		 if(model!=null){
			 BatchPaymentReqBaseInfoDTO dto = new BatchPaymentReqBaseInfoDTO();
			 BeanUtils.copyProperties(model, dto);
			 return dto;
		 }
		return null;
	}

	@Override
	public BatchPaymentReqBaseInfoDTO getBatchPaymentReqBaseInfo(
			Long memberCode, Integer requstType, String businessBatchNo) {
		BatchPaymentReqBaseInfoExample example = new BatchPaymentReqBaseInfoExample();
		  example.createCriteria()
		  .andPayerMemberCodeEqualTo(memberCode)
		  .andRequestTypeEqualTo(requstType)
		  .andBusinessBatchNoEqualTo(businessBatchNo);
		  List results = batchPaymentReqBaseInfoDAO.selectByExample(example);
		  if(null!=results && results.size()>0){
			  BatchPaymentReqBaseInfo model = (BatchPaymentReqBaseInfo)results.get(0);
			  BatchPaymentReqBaseInfoDTO dto = new BatchPaymentReqBaseInfoDTO();
			  BeanUtils.copyProperties(model, dto);
			  return dto;
		  }
		return null;
	}


	
	/**
	 * @param batchPaymentReqBaseInfoDAO the batchPaymentReqBaseInfoDAO to set
	 */
	public void setBatchPaymentReqBaseInfoDAO(
			BatchPaymentReqBaseInfoDAO batchPaymentReqBaseInfoDAO) {
		this.batchPaymentReqBaseInfoDAO = batchPaymentReqBaseInfoDAO;
	}
	
	

}
