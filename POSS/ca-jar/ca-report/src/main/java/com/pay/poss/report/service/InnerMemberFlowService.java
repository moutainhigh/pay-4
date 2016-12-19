package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.InnerMemberFlowDTO;

public interface InnerMemberFlowService {

	/**
	 * 查询分子公司流量
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<InnerMemberFlowDTO> queryInnerMemberFlow(
			Page<InnerMemberFlowDTO> page, Map<String, Object> map);

	/**
	 * 查询分子公司流量
	 * 
	 * @param map
	 * @return
	 */
	public List<InnerMemberFlowDTO> queryInnerMemberFlow(Map<String, Object> map);

}