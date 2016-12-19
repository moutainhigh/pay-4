/**
 *  File: MassPaytobankImportBaseServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.masspaytobank.MassPaytobankImportBaseDAO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankImportBase;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportBaseService;

public class MassPaytobankImportBaseServiceImpl implements MassPaytobankImportBaseService {

	private MassPaytobankImportBaseDAO massPaytobankImportBaseDAO;

	public void setMassPaytobankImportBaseDAO(MassPaytobankImportBaseDAO massPaytobankImportBaseDAO) {
		this.massPaytobankImportBaseDAO = massPaytobankImportBaseDAO;
	}

	/**
	 * 根据上传流水获取批量付款到银行的基本信息
	 * 
	 * @param uploadSeq
	 */
	@Override
	public MassPaytobankImportBaseDTO getMassPaytobankImportBaseInfo(Long uploadSeq) {
		MassPaytobankImportBase model = massPaytobankImportBaseDAO.findById(uploadSeq);
		if (model != null) {
			MassPaytobankImportBaseDTO dto = new MassPaytobankImportBaseDTO();
			BeanUtils.copyProperties(model, dto);
			return dto;
		}
		return null;
	}

	/**
	 * 根据业务参考号获取批量付款到银行的基本信息
	 * 
	 * @param businessNo
	 * @return
	 */
	@Override
	public MassPaytobankImportBaseDTO getMassPaytobankImportBaseByBusinessNo(String businessNo,Long membCode) {
		MassPaytobankImportBase model = new MassPaytobankImportBase();
		model.setBusinessNo(businessNo);
		model.setPayerMomberCode(membCode);
		model = massPaytobankImportBaseDAO.findObjectBySelective(model);
		if (model != null) {
			MassPaytobankImportBaseDTO dto = new MassPaytobankImportBaseDTO();
			BeanUtils.copyProperties(model, dto);
			return dto;
		}
		return null;
	}

	/**
	 * 保存批量付款到银行的基本信息
	 * 
	 * @param dto
	 */
	@Override
	public Long saveMassPaytobankImportBaseInfo(MassPaytobankImportBaseDTO dto) {
		if (dto != null) {
			MassPaytobankImportBase model = new MassPaytobankImportBase();
			BeanUtils.copyProperties(dto, model);
			return (Long) massPaytobankImportBaseDAO.create(model);
		}
		return null;
	}


	@Override
	public Integer getTotalCount(Map params) {
		return massPaytobankImportBaseDAO.getTotalCount(params);
	}

	@Override
	public List getUploadFileInfoList(Map params) {
		return massPaytobankImportBaseDAO.getUploadFileInfoList(params);
	}

	/**
	 * 判断批次号是否存在
	 */
	@Override
	public boolean isExistBusinessNo(String businessNo,Long membCode) {
		MassPaytobankImportBaseDTO dto =  getMassPaytobankImportBaseByBusinessNo(businessNo,membCode);
		if(dto != null){
			return true;
		}
		return false;
	}

	@Override
	public boolean updatePaytobankImportBase(MassPaytobankImportBaseDTO dto) {
		if (dto != null) {
			MassPaytobankImportBase model = new MassPaytobankImportBase();
			BeanUtils.copyProperties(dto, model);
			return massPaytobankImportBaseDAO.update(model);
		}
		return false;
	}
}
