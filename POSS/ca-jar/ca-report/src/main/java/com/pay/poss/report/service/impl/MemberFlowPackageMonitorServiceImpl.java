package com.pay.poss.report.service.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.MemberFlowPackageMonitorDAO;
import com.pay.poss.report.dto.MemberFlowPackageMonitorDTO;
import com.pay.poss.report.service.MemberFlowPackageMonitorService;

public class MemberFlowPackageMonitorServiceImpl implements
		MemberFlowPackageMonitorService {

	private MemberFlowPackageMonitorDAO memberFlowPackageMonitorDAO;

	public void setMemberFlowPackageMonitorDAO(
			MemberFlowPackageMonitorDAO memberFlowPackageMonitorDAO) {
		this.memberFlowPackageMonitorDAO = memberFlowPackageMonitorDAO;
	}

	@Override
	public Page<MemberFlowPackageMonitorDTO> queryMemberFlowPackageMonitor(
			Page<MemberFlowPackageMonitorDTO> page, Map<String, Object> map) {
		return memberFlowPackageMonitorDAO.queryMemberFlowPackageMonitor(page,
				map);
	}

}
