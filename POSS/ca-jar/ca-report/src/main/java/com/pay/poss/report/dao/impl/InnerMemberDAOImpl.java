package com.pay.poss.report.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.InnerMemberDAO;
import com.pay.poss.report.dto.InnerMemberDTO;

@SuppressWarnings("unchecked")
public class InnerMemberDAOImpl extends BaseDAOImpl<InnerMemberDTO> implements
		InnerMemberDAO {

	@Override
	public Page<InnerMemberDTO> queryInnerMember(Page<InnerMemberDTO> page,
			Map<String, Object> map) {
		return super.findByQuery("queryInnerMember",
				page, map);
	}

	@Override
	public InnerMemberDTO findByParentId(Long memberCode) {
		return super.findObjectByCriteria(
				"findByParentId", memberCode);
	}
}