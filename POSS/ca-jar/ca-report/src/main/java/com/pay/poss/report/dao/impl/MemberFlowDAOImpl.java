package com.pay.poss.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.MemberFlowDAO;
import com.pay.poss.report.dto.MemberFlowDTO;

@SuppressWarnings("unchecked")
public class MemberFlowDAOImpl extends BaseDAOImpl<MemberFlowDTO> implements
		MemberFlowDAO {

	@Override
	public Page<MemberFlowDTO> queryMemberFlow(Page<MemberFlowDTO> page,
			Map<String, Object> map) {
		return super
				.findByQuery("queryMemberFlow", page, map);
	}

	@Override
	public List<MemberFlowDTO> queryMemberFlow(Map<String, Object> map) {
		return super.findByQuery("queryMemberFlow", map);
	}

	@Override
	public Page<MemberFlowDTO> queryInnerMemberFlowDetail(
			Page<MemberFlowDTO> page, Map<String, Object> map) {
		return super.findByQuery(
				"queryInnerMemberFlowDetail", page, map);
	}

	@Override
	public List<MemberFlowDTO> queryInnerMemberFlowDetail(
			Map<String, Object> map) {
		return super.findByQuery(
				"queryInnerMemberFlowDetail", map);
	}

	@Override
	public Page<MemberFlowDTO> queryMemberSummarizingFlow(
			Page<MemberFlowDTO> page, Map<String, Object> map) {
		return super.findByQuery(
				"queryMemberSummarizingFlow", page, map);
	}

	@Override
	public List<MemberFlowDTO> queryMemberSummarizingFlow(
			Map<String, Object> map) {
		return super.findByQuery(
				"queryMemberSummarizingFlow", map);
	}

}