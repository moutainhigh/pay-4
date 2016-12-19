package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.MemberFlowDAO;
import com.pay.poss.report.dto.MemberFlowDTO;
import com.pay.poss.report.service.MemberFlowService;

public class MemberFlowServiceImpl implements MemberFlowService {

	private MemberFlowDAO memberFlowDAO;
	
	public void setMemberFlowDAO(MemberFlowDAO memberFlowDAO) {
		this.memberFlowDAO = memberFlowDAO;
	}
	
	@Override
	public Page<MemberFlowDTO> queryMemberFlow(Page<MemberFlowDTO> page,
			Map<String, Object> map) {
		return memberFlowDAO.queryMemberFlow(page, map);
	}

	@Override
	public List<MemberFlowDTO> queryMemberFlow(Map<String, Object> map) {
		return memberFlowDAO.queryMemberFlow(map);
	}

	@Override
	public Page<MemberFlowDTO> queryInnerMemberFlowDetail(
			Page<MemberFlowDTO> page, Map<String, Object> map) {
		return memberFlowDAO.queryInnerMemberFlowDetail(page, map);
	}

	@Override
	public List<MemberFlowDTO> queryInnerMemberFlowDetail(
			Map<String, Object> map) {
		return memberFlowDAO.queryInnerMemberFlowDetail(map);
	}
	
	
	@Override
	public Page<MemberFlowDTO> queryMemberSummarizingFlow(Page<MemberFlowDTO> page,
			Map<String, Object> map) {
		return memberFlowDAO.queryMemberSummarizingFlow(page, map);
	}

	@Override
	public List<MemberFlowDTO> queryMemberSummarizingFlow(Map<String, Object> map) {
		return memberFlowDAO.queryMemberSummarizingFlow(map);
	}
	

}
