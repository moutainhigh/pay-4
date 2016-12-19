package com.pay.pe.accumulated.memberpackagemonitor.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.pe.accumulated.memberpackagemonitor.dao.MemberFlowPackageCfgDao;
import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageCfgDto;
import com.pay.pe.accumulated.memberpackagemonitor.service.MemberFlowPackageCfgService;

public class MemberFlowPackageCfgServiceImpl implements
		MemberFlowPackageCfgService {
	private MemberFlowPackageCfgDao memberFlowPackageCfgDao;
	
	public void setMemberFlowPackageCfgDao(
			MemberFlowPackageCfgDao memberFlowPackageCfgDao) {
		this.memberFlowPackageCfgDao = memberFlowPackageCfgDao;
	}
	/**
	 * 根据条件查询会员包量流量配置列表
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<MemberFlowPackageCfgDto> queryMemFlowPckCfgList(
			Map<String, Object> paramMap) {
		return memberFlowPackageCfgDao.findByTemplate("selectMemberFlowCfgList", paramMap);
	}
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<MemberFlowPackageCfgDto> queryMemFlowPckCfgList(
			Map<String, Object> paramMap, String sql) {
		return memberFlowPackageCfgDao.findByTemplate(sql, paramMap);
	}
	/**
	 * 更新会员包量配置信息
	 * @param MemberFlowPackageCfgDto
	 */
	@Override
	public void updateMemFlowPckRnTx(MemberFlowPackageCfgDto memberFlowPackageCfgDto) {
		memberFlowPackageCfgDao.update(memberFlowPackageCfgDto);
	}
}
