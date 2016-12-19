package com.pay.rm.blacklistcheck.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.rm.blacklistcheck.dao.BlackChecklistDAO;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;
import com.pay.rm.blacklistcheck.service.BlacklistCheckService;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;

public class BlacklistCheckServiceImpl implements BlacklistCheckService{
	
	private BlackWhiteListService blackWhiteListService;
	private BlackChecklistDAO blackChecklistDAO;

	public void setBlackChecklistDAO(BlackChecklistDAO blackChecklistDAO) {
		this.blackChecklistDAO = blackChecklistDAO;
	}


	public void setBlackWhiteListService(BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}


	@Override
	public Long addBlackchecklist(BlackChecklistDTO dto) {
		return blackChecklistDAO.addBlackChecklist(dto);
	}


	@Override
	public Page<BlackChecklistDTO> queryBlacklistCheck(Map map,
			Page<BlackChecklistDTO> page) {
		return blackChecklistDAO.queryBlacklistCheck(page,map);
	}

	@Override
	public boolean updateBlacklistCheckStatus(BlackChecklistDTO blackCheck,
			List<BlackChecklistDTO> result,String operator) {
		if(result!=null){
				//调大爷的service 加入黑名单
			List<BlackWhiteListDto> blackWhiteListDtos=new ArrayList<BlackWhiteListDto>();
			for (BlackChecklistDTO blackChecklistDTO : result) {
				BlackWhiteListDto blackWhiteListDto=new BlackWhiteListDto();
				blackWhiteListDto.setMemberCode(blackChecklistDTO.getMemberCode());
				blackWhiteListDto.setBusinessTypeId(blackChecklistDTO.getBusinessTypeId());
				blackWhiteListDto.setListType(2);
				blackWhiteListDto.setStatus(0);
				blackWhiteListDto.setPartType(1);
				blackWhiteListDto.setContent(blackChecklistDTO.getContent());
				blackWhiteListDto.setApprovalType(1);
				blackWhiteListDto.setRemark("风险订单识别");
				blackWhiteListDto.setOperator(operator);
				blackWhiteListDtos.add(blackWhiteListDto);
			}
			boolean createBlackWhiteList = blackWhiteListService.createBlackWhiteList(blackWhiteListDtos);
			if(!createBlackWhiteList ){
			return createBlackWhiteList;
			}
		}
		return blackChecklistDAO.updateBlacklistCheckStatus(blackCheck);
	}

}
