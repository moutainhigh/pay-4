package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.InnerMemberFlowDAO;
import com.pay.poss.report.dto.InnerMemberFlowDTO;
import com.pay.poss.report.service.InnerMemberFlowService;

public class InnerMemberFlowServiceImpl implements InnerMemberFlowService {
	
	private InnerMemberFlowDAO innerMemberFlowDAO;

	public void setInnerMemberFlowDAO(InnerMemberFlowDAO innerMemberFlowDAO) {
		this.innerMemberFlowDAO = innerMemberFlowDAO;
	}
	
	@Override
	public Page<InnerMemberFlowDTO> queryInnerMemberFlow(Page<InnerMemberFlowDTO> page,
			Map<String, Object> map) {
		return innerMemberFlowDAO.queryInnerMemberFlow(page, map);
	}

	@Override
	public List<InnerMemberFlowDTO> queryInnerMemberFlow(Map<String, Object> map) {
		return innerMemberFlowDAO.queryInnerMemberFlow(map);
	}

	
	
}
