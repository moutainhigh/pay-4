package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.MemberFeeDAO;
import com.pay.poss.report.dto.MemberFeeDTO;
import com.pay.poss.report.service.MemberFeeService;

public class MemberFeeServiceImpl implements MemberFeeService {

	

	private MemberFeeDAO memberFeeDAO;
	
	public void setMemberFeeDAO(MemberFeeDAO memberFeeDAO) {
		this.memberFeeDAO = memberFeeDAO;
	}

	@Override
	public Page<MemberFeeDTO> queryMemberFee(Page<MemberFeeDTO> page,
			Map<String, Object> map) {
		return memberFeeDAO.queryMemberFee(page, map);
	}

	@Override
	public List<MemberFeeDTO> queryMemberFee(Map<String, Object> map) {
		return memberFeeDAO.queryMemberFee(map);
	}
	
	
	

}
