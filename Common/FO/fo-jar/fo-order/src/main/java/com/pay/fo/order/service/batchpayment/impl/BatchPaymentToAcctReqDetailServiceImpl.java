/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dao.batchpayment.BatchPaymentToAcctReqDetailDAO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.model.batchpayment.BatchPaymentToAcctReqDetail;
import com.pay.fo.order.model.batchpayment.BatchPaymentToAcctReqDetailExample;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;

/**
 * @author NEW
 *
 */
public class BatchPaymentToAcctReqDetailServiceImpl implements
		BatchPaymentToAcctReqDetailService {
	
	private BatchPaymentToAcctReqDetailDAO batchPaymentToAcctReqDetailDAO; 

	@Override
	public void create(BatchPaymentToAcctReqDetailDTO detail) {
		BatchPaymentToAcctReqDetail model = new BatchPaymentToAcctReqDetail();
		detail.setUpdateDate(detail.getCreateDate());
		BeanUtils.copyProperties(detail, model);
		batchPaymentToAcctReqDetailDAO.insert(model);
	}

	@Override
	public void create(List<RequestDetail> details,BatchPaymentReqBaseInfoDTO reqInfo) throws AppException {
		List<BatchPaymentToAcctReqDetail> modelList = new ArrayList<BatchPaymentToAcctReqDetail>();
		
		for (RequestDetail requestDetail : details) {
			if(requestDetail instanceof BatchPaymentToAcctReqDetailDTO){
				BatchPaymentToAcctReqDetailDTO detail = (BatchPaymentToAcctReqDetailDTO)requestDetail;
				detail.setRequestSeq(reqInfo.getRequestSeq());
				detail.setCreateDate(reqInfo.getCreateDate());
				detail.setUpdateDate(detail.getCreateDate());
				detail.setOrderStatus(0);
				BatchPaymentToAcctReqDetail model = new BatchPaymentToAcctReqDetail();
				BeanUtils.copyProperties(detail, model);
				modelList.add(model);
			}
		}
		batchPaymentToAcctReqDetailDAO.insert(modelList);

	}

	@Override
	public boolean updateStatus(BatchPaymentToAcctReqDetailDTO detail,
			int oldStatus) {
		if(null==detail){
			return false;
		}
		BatchPaymentToAcctReqDetail model = new BatchPaymentToAcctReqDetail();
		model.setOrderStatus(detail.getOrderStatus());
		model.setUpdateDate(detail.getUpdateDate());
		
		BatchPaymentToAcctReqDetailExample example = new BatchPaymentToAcctReqDetailExample();
		example.createCriteria()
		.andDetailSeqEqualTo(detail.getDetailSeq())
		.andOrderStatusEqualTo(oldStatus);
		
		return batchPaymentToAcctReqDetailDAO.updateByExampleSelective(model, example)==1?true:false;
	}

	@Override
	public BatchPaymentToAcctReqDetailDTO getDetail(Long detailSeq) {
		BatchPaymentToAcctReqDetail model = batchPaymentToAcctReqDetailDAO.selectByPrimaryKey(detailSeq);
		if(null==model){
			return null;
		}
		BatchPaymentToAcctReqDetailDTO dto = new BatchPaymentToAcctReqDetailDTO();
		BeanUtils.copyProperties(model, dto);
		return dto;
		
	}

	@Override
	public List<BatchPaymentToAcctReqDetailDTO> getValidateDetailList(
			Long requestSeq, Integer validateStatus) {

		BatchPaymentToAcctReqDetailExample example = new BatchPaymentToAcctReqDetailExample();
		example.createCriteria()
		.andRequestSeqEqualTo(requestSeq)
		.andValidateStatusEqualTo(validateStatus);
		
		List<BatchPaymentToAcctReqDetailDTO> details = new ArrayList<BatchPaymentToAcctReqDetailDTO>();
		List models = batchPaymentToAcctReqDetailDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			BatchPaymentToAcctReqDetail model = (BatchPaymentToAcctReqDetail) iterator.next();
			BatchPaymentToAcctReqDetailDTO dto = new BatchPaymentToAcctReqDetailDTO();
			BeanUtils.copyProperties(model, dto);
			dto.setForeignOrderId(dto.getForeignOrderId()==null?"":dto.getForeignOrderId()+"\t");
			details.add(dto);
		}
		return details;
	}

	@Override
	public List<BatchPaymentToAcctReqDetailDTO> getCreateOrderDetailList(
			Long requestSeq, Integer orderStatus) {
		BatchPaymentToAcctReqDetailExample example = new BatchPaymentToAcctReqDetailExample();
		example.createCriteria()
		.andRequestSeqEqualTo(requestSeq)
		.andOrderStatusEqualTo(orderStatus)
		.andValidateStatusEqualTo(1);
		
		List<BatchPaymentToAcctReqDetailDTO> details = new ArrayList<BatchPaymentToAcctReqDetailDTO>();
		List models = batchPaymentToAcctReqDetailDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			BatchPaymentToAcctReqDetail model = (BatchPaymentToAcctReqDetail) iterator.next();
			BatchPaymentToAcctReqDetailDTO dto = new BatchPaymentToAcctReqDetailDTO();
			BeanUtils.copyProperties(model, dto);
			details.add(dto);
		}
		return details;
	}

	/**
	 * @param batchPaymentToAcctReqDetailDAO the batchPaymentToAcctReqDetailDAO to set
	 */
	public void setBatchPaymentToAcctReqDetailDAO(
			BatchPaymentToAcctReqDetailDAO batchPaymentToAcctReqDetailDAO) {
		this.batchPaymentToAcctReqDetailDAO = batchPaymentToAcctReqDetailDAO;
	}
	
	

}
