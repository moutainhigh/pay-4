package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFlowPackageCfgDTO;

public interface MemberFlowPackageCfgService {

	/**
	 * 新增包量配置
	 * 
	 * @param member
	 * @return
	 */
	public long createMemberFlowPackageCfg(MemberFlowPackageCfgDTO cfgDto);

	/**
	 * 修改包量配置
	 * 
	 * @param member
	 * @return
	 */
	public boolean updateMemberFlowPackageCfg(MemberFlowPackageCfgDTO cfgDto);

	/**
	 * 根据ID查找包量配置信息
	 * 
	 * @param id
	 * @return
	 */
	public MemberFlowPackageCfgDTO findById(Long id);

	/**
	 * 删除包量配置信息
	 * 
	 * @param id
	 */
	public boolean deleteById(Long id);

	/**
	 * 查询所有包量配置信息
	 * 
	 * @return
	 */
	public List<MemberFlowPackageCfgDTO> findMemberFlowPackageCfg();

	/**
	 * 查询包量配置信息带分页
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFlowPackageCfgDTO> queryMemberFlowPackageCfg(
			Page<MemberFlowPackageCfgDTO> page, Map<String, Object> map);

	/**
	 * 根据memberCode查找memberName
	 * 
	 * @param memberCode
	 * @return memberName
	 */
	public String findByMemberCode(Long memberCode);

	/**
	 * 根据memberCode和status按照begindate顺序查找列表
	 * 
	 * @param memberCode
	 * @return memberName
	 */
	public List<MemberFlowPackageCfgDTO> findOrderbyBegindateAsc(Long memberCode);

	/**
	 * 根据beginDate查找列表
	 * 
	 * @param memberCode
	 * @return memberName
	 */
	@SuppressWarnings("unchecked")
	public List<MemberFlowPackageCfgDTO> findbyBegindate(Map paramMap);
}