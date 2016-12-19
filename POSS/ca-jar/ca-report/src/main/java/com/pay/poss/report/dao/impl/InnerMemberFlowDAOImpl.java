package com.pay.poss.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.InnerMemberFlowDAO;
import com.pay.poss.report.dto.InnerMemberFlowDTO;

@SuppressWarnings("unchecked")
public class InnerMemberFlowDAOImpl extends BaseDAOImpl<InnerMemberFlowDTO>
		implements InnerMemberFlowDAO {

	@Override
	public Page<InnerMemberFlowDTO> queryInnerMemberFlow(
			Page<InnerMemberFlowDTO> page, Map<String, Object> map) {
		return super.findByQuery("queryInnerMemberFlow",
				page, map);
	}

	@Override
	public List<InnerMemberFlowDTO> queryInnerMemberFlow(Map<String, Object> map) {
		return super.findByQuery("queryInnerMemberFlow", map);
	}

}