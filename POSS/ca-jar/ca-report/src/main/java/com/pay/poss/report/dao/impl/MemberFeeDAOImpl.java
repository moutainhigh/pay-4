package com.pay.poss.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.MemberFeeDAO;
import com.pay.poss.report.dto.MemberFeeDTO;

@SuppressWarnings("unchecked")
public class MemberFeeDAOImpl extends BaseDAOImpl<MemberFeeDTO> implements
		MemberFeeDAO {

	@Override
	public Page<MemberFeeDTO> queryMemberFee(Page<MemberFeeDTO> page,
			Map<String, Object> map) {
		return super.findByQuery("queryMemberFee", page, map);
	}

	@Override
	public List<MemberFeeDTO> queryMemberFee(Map<String, Object> map) {
		return super.findByQuery("queryMemberFee", map);
	}

}