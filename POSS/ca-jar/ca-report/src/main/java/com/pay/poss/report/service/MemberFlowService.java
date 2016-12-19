package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFlowDTO;

public interface MemberFlowService {

	/**
	 * 查询会员信息带分页
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFlowDTO> queryMemberFlow(Page<MemberFlowDTO> page,
			Map<String, Object> map);

	/**
	 * 查询会员日交易统计
	 * 
	 * @param map
	 * @return
	 */
	public List<MemberFlowDTO> queryMemberFlow(Map<String, Object> map);

	/**
	 * 查询份子公司明细
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFlowDTO> queryInnerMemberFlowDetail(
			Page<MemberFlowDTO> page, Map<String, Object> map);

	/**
	 * 查询份子公司明细
	 * 
	 * @param map
	 * @return
	 */
	public List<MemberFlowDTO> queryInnerMemberFlowDetail(
			Map<String, Object> map);

	/**
	 * 查询会员汇总信息
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFlowDTO> queryMemberSummarizingFlow(
			Page<MemberFlowDTO> page, Map<String, Object> map);

	/**
	 * 查询会员汇总信息
	 * 
	 * @param map
	 * @return
	 */
	public List<MemberFlowDTO> queryMemberSummarizingFlow(
			Map<String, Object> map);

}