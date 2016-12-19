package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFeeDTO;


public interface MemberFeeService{
	
	/**
	 * 查询会员费用带分页
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFeeDTO> queryMemberFee(Page<MemberFeeDTO> page, Map<String, Object> map);
	
	/**
	 * 查询会费用交易
	 * @param map
	 * @return
	 */
	public List<MemberFeeDTO> queryMemberFee(Map<String, Object> map);
}