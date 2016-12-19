/**
 *  File: MassPaytobankOrderServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.masspaytobank.MassPaytobankOrderDAO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankOrder;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankOrderTotalInfo;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankOrderService;
import com.pay.util.StringUtil;

public class MassPaytobankOrderServiceImpl implements MassPaytobankOrderService {
	
	private MassPaytobankOrderDAO massPaytobankOrderDAO;

	public void setMassPaytobankOrderDAO(MassPaytobankOrderDAO massPaytobankOrderDAO) {
		this.massPaytobankOrderDAO = massPaytobankOrderDAO;
	}
	
	@Override
	public MassPaytobankOrderDTO getMassPaytobankOrderInfo(Long massOrderSeq) {
		MassPaytobankOrder model = massPaytobankOrderDAO.findById(massOrderSeq);	
		if(null==model){
			return null;
		}
		MassPaytobankOrderDTO dto = new MassPaytobankOrderDTO();
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public MassPaytobankOrderDTO getMassPaytobankOrderInfo(String businessNo,Long memberCode) {
		if(StringUtil.isNull(businessNo) || memberCode == null){
			return null;
		}
		MassPaytobankOrder order = new MassPaytobankOrder();
		order.setBusinessNo(businessNo);
		order.setPayerMemberCode(memberCode);
		MassPaytobankOrder model = massPaytobankOrderDAO.findObjectBySelective(order);
		if(null==model){
			return null;
		}
		MassPaytobankOrderDTO dto = new MassPaytobankOrderDTO();
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public Long saveMassPaytobankOrderInfoRnTx(MassPaytobankOrderDTO dto) {
		if(dto != null){
			MassPaytobankOrder model = new MassPaytobankOrder();
			BeanUtils.copyProperties(dto, model);
			return (Long) massPaytobankOrderDAO.create(model);
		}
		return null;
	}

	@Override
	public boolean updateMassPaytobankOrderInfo(MassPaytobankOrderDTO dto) {
		if(dto != null){
			MassPaytobankOrder model = new MassPaytobankOrder();
			BeanUtils.copyProperties(dto, model);
			return massPaytobankOrderDAO.update(model);
		}
		return false;
	}

	@Override
	public List<MassPaytobankOrderDTO> getCompleteMassPaytobankOrder() {
		List<MassPaytobankOrderDTO> dtos = new ArrayList<MassPaytobankOrderDTO>();
		List<MassPaytobankOrder> models = massPaytobankOrderDAO.getCompleteMassPaytobankOrder();
		for (MassPaytobankOrder model : models) {
			if(null!=model){
				MassPaytobankOrderDTO dto = new MassPaytobankOrderDTO();
				BeanUtils.copyProperties(model, dto);
				dtos.add(dto);
			}
		}
		return dtos;
		
	}

	@Override
	public MassPaytobankOrderTotalInfo totalComplateMassPaytobankOrderInfo(
			Long massOrderSeq) {
		return massPaytobankOrderDAO.totalComplateMassPaytobankOrderInfo(massOrderSeq);
	}

}
