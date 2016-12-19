package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.MemberFlowPackageCfgDAO;
import com.pay.poss.report.dto.MemberFlowPackageCfgDTO;
import com.pay.poss.report.service.MemberFlowPackageCfgService;

public class MemberFlowPackageCfgServiceImpl implements
		MemberFlowPackageCfgService {

	private MemberFlowPackageCfgDAO memberFlowPackageCfgDAO;

	public void setMemberFlowPackageCfgDAO(
			MemberFlowPackageCfgDAO memberFlowPackageCfgDAO) {
		this.memberFlowPackageCfgDAO = memberFlowPackageCfgDAO;
	}

	@Override
	public long createMemberFlowPackageCfg(MemberFlowPackageCfgDTO cfgDto) {
		return (Long) memberFlowPackageCfgDAO.create(cfgDto);
	}

	@Override
	public boolean updateMemberFlowPackageCfg(MemberFlowPackageCfgDTO cfgDto) {
		return memberFlowPackageCfgDAO.update(cfgDto);
	}

	@Override
	public boolean deleteById(Long id) {
		return memberFlowPackageCfgDAO.delete(id);
	}

	@Override
	public MemberFlowPackageCfgDTO findById(Long id) {
		return (MemberFlowPackageCfgDTO) memberFlowPackageCfgDAO.findById(id);
	}

	@Override
	public List<MemberFlowPackageCfgDTO> findMemberFlowPackageCfg() {
		return memberFlowPackageCfgDAO.findBySelective(null);
	}

	@Override
	public Page<MemberFlowPackageCfgDTO> queryMemberFlowPackageCfg(
			Page<MemberFlowPackageCfgDTO> page, Map<String, Object> map) {
		return memberFlowPackageCfgDAO.queryMemberFlowPackageCfg(page, map);
	}

	@Override
	public String findByMemberCode(Long memberCode) {
		return memberFlowPackageCfgDAO.findByMemberCode(memberCode);
	}

	@Override
	public List<MemberFlowPackageCfgDTO> findOrderbyBegindateAsc(Long memberCode) {
		return memberFlowPackageCfgDAO.findOrderbyBegindateAsc(memberCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberFlowPackageCfgDTO> findbyBegindate(Map paramMap) {
		return memberFlowPackageCfgDAO.findbyBegindate(paramMap);
	}
}
