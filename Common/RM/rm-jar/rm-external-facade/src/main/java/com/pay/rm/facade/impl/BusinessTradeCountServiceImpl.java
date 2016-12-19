package com.pay.rm.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.rm.facade.BusinessTradeCountService;
import com.pay.rm.facade.dao.BusinessTRadeCountDAO;
import com.pay.rm.facade.dto.BusinessTradeCountDTO;
import com.pay.rm.facade.model.BusinessTradeCount;

public class BusinessTradeCountServiceImpl implements BusinessTradeCountService {
	
	private BusinessTRadeCountDAO  businessTradeCountDAO;

	@Override
	public List<BusinessTradeCountDTO> getBusinessTradeCountDTO(
			BusinessTradeCountDTO countDTO) {
		
		BusinessTradeCount count = new BusinessTradeCount();
		count.setMemberCode(countDTO.getMemberCode());
		count.setYear(countDTO.getYear());
		count.setMonth(countDTO.getMonth());
		count.setDay(countDTO.getDay());
		
		List<BusinessTradeCount> counts = businessTradeCountDAO.getBusinessTradeCount(count);
		
		List<BusinessTradeCountDTO> countDTOList = null;
		
		if(counts!=null&&counts.size()>0){			
			countDTOList = new ArrayList<BusinessTradeCountDTO>();
			for(BusinessTradeCount count_:counts){
				BusinessTradeCountDTO countDTO_ = new BusinessTradeCountDTO();
				countDTO_.setCreateTime(count_.getCreateTime());
				countDTO_.setDayAmount(count_.getDayAmount());
				countDTO_.setDayCount(count_.getDayCount());
				countDTO_.setMonthAmount(count_.getMonthAmount());
				countDTO_.setMonthCount(count_.getMonthCount());
				countDTO_.setYearAmount(count_.getYearAmount());
				countDTO_.setYearCount(count_.getYearCount());
				countDTO_.setDay(count_.getDay());
				countDTO_.setMemberCode(count_.getMemberCode());
				countDTO_.setMonth(count_.getMonth());
				countDTO_.setYear(count_.getYear());
				
				countDTOList.add(countDTO_);
			}
			return countDTOList;
		}
		
		
		return countDTOList;
	}

	@Override
	public BusinessTradeCountDTO getBusinessTradeCountDTO(
			Map<String, Object> params) {
		
		BusinessTradeCount count = businessTradeCountDAO.getBusinessTradeCount(params);
		
		BusinessTradeCountDTO countDTO = null;
		
		if(count!=null){
			countDTO = new BusinessTradeCountDTO();
			countDTO.setCreateTime(count.getCreateTime());
			countDTO.setDay(count.getDay());
			countDTO.setDayAmount(count.getDayAmount());
			countDTO.setDayCount(count.getDayCount());
			countDTO.setMemberCode(count.getMemberCode());
			countDTO.setYear(count.getYear());
			countDTO.setYearAmount(count.getYearAmount());
			countDTO.setYearCount(count.getYearCount());
			countDTO.setMonth(count.getMonth());
			countDTO.setMonthAmount(count.getMonthAmount());
			countDTO.setMonthCount(count.getMonthCount());
			countDTO.setUpdateTime(count.getUpdateTime());
		}
		
		return countDTO;
	}

	@Override
	public BusinessTradeCountDTO createTradeCountDTO(
			BusinessTradeCountDTO countDTO) {
		
		BusinessTradeCount count = new BusinessTradeCount();
		count.setCreateTime(countDTO.getCreateTime());
		count.setDay(countDTO.getDay());
		count.setDayAmount(countDTO.getDayAmount());
		count.setDayCount(countDTO.getDayCount());
		count.setMemberCode(countDTO.getMemberCode());
		count.setYear(countDTO.getYear());
		count.setYearAmount(countDTO.getYearAmount());
		count.setYearCount(countDTO.getYearCount());
		count.setMonth(countDTO.getMonth());
		count.setMonthAmount(countDTO.getMonthAmount());
		count.setMonthCount(countDTO.getMonthCount());
		count.setUpdateTime(countDTO.getUpdateTime());
		
		BusinessTradeCount count_= businessTradeCountDAO
				            .createBusinessTradeCount(count);
		if(count_!=null){
            return countDTO;
		}
		return null;
	}

	public void setBusinessTradeCountDAO(BusinessTRadeCountDAO businessTradeCountDAO) {
		this.businessTradeCountDAO = businessTradeCountDAO;
	}

	@Override
	public boolean updateBTC(Map<String, Object> params) {
		return businessTradeCountDAO.updateBTC(params);
	}
}
