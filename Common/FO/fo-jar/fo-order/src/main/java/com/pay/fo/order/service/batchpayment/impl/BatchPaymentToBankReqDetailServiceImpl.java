/**
 * 
 */
package com.pay.fo.order.service.batchpayment.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dao.batchpayment.BatchPaymentToBankReqDetailDAO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.model.batchpayment.BatchPaymentToBankReqDetail;
import com.pay.fo.order.model.batchpayment.BatchPaymentToBankReqDetailExample;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;

/**
 * @author NEW
 *
 */
public class BatchPaymentToBankReqDetailServiceImpl implements
		BatchPaymentToBankReqDetailService {
	
	private BatchPaymentToBankReqDetailDAO batchPaymentToBankReqDetailDAO; 

	@Override
	public void create(BatchPaymentToBankReqDetailDTO detail) {
		BatchPaymentToBankReqDetail model = new BatchPaymentToBankReqDetail();
		detail.setUpdateDate(detail.getCreateDate());
		BeanUtils.copyProperties(detail, model);
		batchPaymentToBankReqDetailDAO.insert(model);
	}

	@Override
	public void create(List<RequestDetail> details,BatchPaymentReqBaseInfoDTO reqInfo) throws AppException {
		List<BatchPaymentToBankReqDetail> modelList = new ArrayList<BatchPaymentToBankReqDetail>();
		
		for (RequestDetail requestDetail : details) {
			if(requestDetail instanceof BatchPaymentToBankReqDetailDTO ){
				BatchPaymentToBankReqDetailDTO detail = (BatchPaymentToBankReqDetailDTO)requestDetail;
				detail.setRequestSeq(reqInfo.getRequestSeq());
				detail.setCreateDate(reqInfo.getCreateDate());
				detail.setUpdateDate(reqInfo.getCreateDate());
				detail.setOrderStatus(0);
				BatchPaymentToBankReqDetail model = new BatchPaymentToBankReqDetail();
				BeanUtils.copyProperties(detail, model);
				modelList.add(model);
			}else{
				return;
			}
		}
		batchPaymentToBankReqDetailDAO.insert(modelList);

	}

	@Override
	public boolean updateStatus(BatchPaymentToBankReqDetailDTO detail,
			int oldStatus) {
		if(null==detail){
			return false;
		}
		BatchPaymentToBankReqDetail model = new BatchPaymentToBankReqDetail();
		model.setOrderStatus(detail.getOrderStatus());
		model.setUpdateDate(detail.getUpdateDate());
		
		BatchPaymentToBankReqDetailExample example = new BatchPaymentToBankReqDetailExample();
		example.createCriteria()
		.andDetailSeqEqualTo(detail.getDetailSeq())
		.andOrderStatusEqualTo(oldStatus);
		
		return batchPaymentToBankReqDetailDAO.updateByExampleSelective(model, example)==1?true:false;
	}

	@Override
	public BatchPaymentToBankReqDetailDTO getDetail(Long detailSeq) {
		BatchPaymentToBankReqDetail model = batchPaymentToBankReqDetailDAO.selectByPrimaryKey(detailSeq);
		if(null==model){
			return null;
		}
		BatchPaymentToBankReqDetailDTO dto = new BatchPaymentToBankReqDetailDTO();
		BeanUtils.copyProperties(model, dto);
		return dto;
		
	}

	@Override
	public List<BatchPaymentToBankReqDetailDTO> getValidateDetailList(
			Long requestSeq, Integer validateStatus) {

		BatchPaymentToBankReqDetailExample example = new BatchPaymentToBankReqDetailExample();
		example.createCriteria()
		.andRequestSeqEqualTo(requestSeq)
		.andValidStatusEqualTo(validateStatus);
		
		List<BatchPaymentToBankReqDetailDTO> details = new ArrayList<BatchPaymentToBankReqDetailDTO>();
		List models = batchPaymentToBankReqDetailDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			BatchPaymentToBankReqDetail model = (BatchPaymentToBankReqDetail) iterator.next();
			BatchPaymentToBankReqDetailDTO dto = new BatchPaymentToBankReqDetailDTO();
			BeanUtils.copyProperties(model, dto);
			dto.setForeignOrderId(dto.getForeignOrderId()==null?"":dto.getForeignOrderId()+"\t");
			dto.setPayeeBankAcctCode(dto.getPayeeBankAcctCode()==null?"":dto.getPayeeBankAcctCode()+"\t");
			details.add(dto);
		}
		return details;
	}

	@Override
	public List<BatchPaymentToBankReqDetailDTO> getCreateOrderDetailList(
			Long requestSeq, Integer orderStatus) {
		BatchPaymentToBankReqDetailExample example = new BatchPaymentToBankReqDetailExample();
		example.createCriteria()
		.andRequestSeqEqualTo(requestSeq)
		.andOrderStatusEqualTo(orderStatus)
		.andValidStatusEqualTo(1);
		
		List<BatchPaymentToBankReqDetailDTO> details = new ArrayList<BatchPaymentToBankReqDetailDTO>();
		List models = batchPaymentToBankReqDetailDAO.selectByExample(example);
		for (Iterator iterator = models.iterator(); iterator.hasNext();) {
			BatchPaymentToBankReqDetail model = (BatchPaymentToBankReqDetail) iterator.next();
			BatchPaymentToBankReqDetailDTO dto = new BatchPaymentToBankReqDetailDTO();
			BeanUtils.copyProperties(model, dto);
			details.add(dto);
		}
		return details;
	}

	/**
	 * @param batchPaymentToBankReqDetailDAO the batchPaymentToBankReqDetailDAO to set
	 */
	public void setBatchPaymentToBankReqDetailDAO(
			BatchPaymentToBankReqDetailDAO batchPaymentToBankReqDetailDAO) {
		this.batchPaymentToBankReqDetailDAO = batchPaymentToBankReqDetailDAO;
	}
	
	

}
