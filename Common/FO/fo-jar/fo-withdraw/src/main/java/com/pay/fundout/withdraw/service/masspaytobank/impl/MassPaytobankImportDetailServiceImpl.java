/**
 *  File: MassPaytobankImportDetailServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.masspaytobank.MassPaytobankImportDetailDAO;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankImportDetail;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportDetailService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.exception.AppException;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class MassPaytobankImportDetailServiceImpl implements MassPaytobankImportDetailService {

	private MassPaytobankImportDetailDAO massPaytobankImportDetailDAO;

	private WithdrawOrderService withdrawOrderService;

	public void setMassPaytobankImportDetailDAO(MassPaytobankImportDetailDAO massPaytobankImportDetailDAO) {
		this.massPaytobankImportDetailDAO = massPaytobankImportDetailDAO;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	/**
	 * 根据上传流水号获得所有批量付款到银行记录信息
	 * 
	 * @param uploadSeq
	 * @return
	 */
	@Override
	public List<MassPaytobankImportDetailDTO> getMassPaytobankImportDetails(Long uploadSeq) {
		MassPaytobankImportDetail detail = new MassPaytobankImportDetail();
		detail.setUploadSeq(uploadSeq);
		return this.getMassPaytobankImportDetails(detail);
	}

	/**
	 * 根据上传流水号获取有效的批量付款到银行记录信息
	 * 
	 * @param uploadSeq
	 * @return
	 */
	@Override
	public List<MassPaytobankImportDetailDTO> getInValidMassPaytobankImportDetails(Long uploadSeq) {
		MassPaytobankImportDetail detail = new MassPaytobankImportDetail();
		detail.setUploadSeq(uploadSeq);
		detail.setValidateStatus(Integer.valueOf(0));
		return this.getMassPaytobankImportDetails(detail);
	}

	/**
	 * 根据上传流水号获取无效的批量付款到银行记录信息
	 * 
	 * @param uploadSeq
	 * @return
	 */
	@Override
	public List<MassPaytobankImportDetailDTO> getValidMassPaytobankImportDetails(Long uploadSeq) {
		MassPaytobankImportDetail detail = new MassPaytobankImportDetail();
		detail.setUploadSeq(uploadSeq);
		detail.setValidateStatus(Integer.valueOf(1));
		return this.getMassPaytobankImportDetails(detail);
	}

	/**
	 * 保存上传的批量付款到银行明细记录信息
	 * 
	 * @param dto
	 */
	@Override
	public Long saveMassPaytobankImportDetailInfo(MassPaytobankImportDetailDTO dto) {
		if (dto != null) {
			MassPaytobankImportDetail model = new MassPaytobankImportDetail();
			BeanUtils.copyProperties(dto, model);
			return (Long) massPaytobankImportDetailDAO.create(model);
		}
		return null;
	}
	
	@Override
	public void saveMassPaytobankImportDetailsRdTx(List<MassPaytobankImportDetailDTO> details,Long uploadSeq) throws AppException{
		List<MassPaytobankImportDetail> models = new ArrayList<MassPaytobankImportDetail>();
		for (MassPaytobankImportDetailDTO dto : details) {
			MassPaytobankImportDetail model = new MassPaytobankImportDetail();
			BeanUtils.copyProperties(dto, model);
			model.setUploadSeq(uploadSeq);
			models.add(model);
		}
		massPaytobankImportDetailDAO.createDetails(models);
	}

	/**
	 * 根据传入参数获得批量到银行信息
	 * 
	 * @param detail
	 * @return
	 */
	private List<MassPaytobankImportDetailDTO> getMassPaytobankImportDetails(MassPaytobankImportDetail detail) {
		List<MassPaytobankImportDetail> modelList;
		modelList = massPaytobankImportDetailDAO.findBySelective(detail);
		if (modelList == null || modelList.size() == 0) {
			return null;
		}
		List<MassPaytobankImportDetailDTO> dtoList = new ArrayList<MassPaytobankImportDetailDTO>();
		for (MassPaytobankImportDetail model : modelList) {
			MassPaytobankImportDetailDTO dto = new MassPaytobankImportDetailDTO();
			BeanUtils.copyProperties(model, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List getDetailInfoList(Map params) {
		return massPaytobankImportDetailDAO.getDetailInfoList(params);
	}

	@Override
	public Integer getTotalCount(Map params) {
		return massPaytobankImportDetailDAO.getTotalCount(params);
	}

	@Override
	public List getDetailInfoListAll(Map params) {
		return massPaytobankImportDetailDAO.getDetailInfoListAll(params);
	}

	@Override
	public int updateOrderStatus(MassPaytobankImportDetailDTO detail) {
		return massPaytobankImportDetailDAO.updateOrderStatus(detail);
	}

	@Override
	public void createSingleOrderRnTx(MassPaytobankImportDetailDTO detail, WithdrawOrderAppDTO withdrawOrderAppDTO)
			throws PossException {
		if (updateOrderStatus(detail) == 1) {
			Long id = withdrawOrderService.createWithdrawOrderRnTx(withdrawOrderAppDTO);
			withdrawOrderAppDTO.setSequenceId(id);
			if (id == null) {
				LogUtil.error(getClass(), "插入订单失败", OPSTATUS.EXCEPTION, "detailId:" + detail.getDetailSeq(), "", "", "", "");
				throw new PossException("子订单生成错误", ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE);
			}
		}
	}

}
