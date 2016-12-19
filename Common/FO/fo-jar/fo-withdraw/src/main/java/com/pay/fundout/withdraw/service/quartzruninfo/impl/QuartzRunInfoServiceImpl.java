package com.pay.fundout.withdraw.service.quartzruninfo.impl;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.quartzruninfo.QuartzRunInfoDAO;
import com.pay.fundout.withdraw.dto.quartzruninfo.QuartzRunInfoDTO;
import com.pay.fundout.withdraw.model.quartzruninfo.QuartzRunInfo;
import com.pay.fundout.withdraw.service.quartzruninfo.QuartzRunInfoService;

public class QuartzRunInfoServiceImpl implements QuartzRunInfoService {

	private QuartzRunInfoDAO quartzRunInfoDAO;

	public void setQuartzRunInfoDAO(QuartzRunInfoDAO quartzRunInfoDAO) {
		this.quartzRunInfoDAO = quartzRunInfoDAO;
	}
	
	/**
	 * 保存quartzrunInfo 信息
	 * @param dto
	 */
	@Override
	public Long createQuartzRunInfo(QuartzRunInfoDTO dto) {
		if(dto != null){
			QuartzRunInfo model = new QuartzRunInfo();
			BeanUtils.copyProperties(dto, model);
			return (Long) quartzRunInfoDAO.create(model);
		}
		return null;
	}

	/**
	 * 通过主标识查找
	 * @param sequenceId
	 * @return
	 */
	@Override
	public QuartzRunInfoDTO getQuartzRunInfoById(Long sequenceId) {
		QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
		QuartzRunInfo model = quartzRunInfoDAO.findById(sequenceId);
		if(model != null){
			BeanUtils.copyProperties(model, dto);
			return dto;
		}
		return null;
	}

	
	/**
	 * 根据外键和业务类型查找
	 * @param fkId
	 * @param bussiType
	 * @return
	 */
	@Override
	public QuartzRunInfoDTO getQuartzRunInfoByBussiType(Long fkId,
			Integer busiType) {
		QuartzRunInfo model = new QuartzRunInfo();
		model.setBusiType(busiType);
		model.setFkId(fkId);
		QuartzRunInfo reModel = quartzRunInfoDAO.findObjectBySelective(model);
		if(null != reModel){
			QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
			BeanUtils.copyProperties(reModel, dto);
			return dto;
		}
		return null;
	}

	/**
	 * 更新Quartz信息
	 * @param dto
	 */
	@Override
	public void updateQuartzRunInfo(QuartzRunInfoDTO dto) {
		if(dto != null){
			QuartzRunInfo model = new QuartzRunInfo();
			BeanUtils.copyProperties(dto, model);
			quartzRunInfoDAO.update(model);
		}

	}

}
