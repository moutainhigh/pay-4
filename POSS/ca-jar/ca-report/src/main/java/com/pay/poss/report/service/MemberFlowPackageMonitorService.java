package com.pay.poss.report.service;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFlowPackageMonitorDTO;

public interface MemberFlowPackageMonitorService {

	/**
	 * 查询会员包量监控带分页
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberFlowPackageMonitorDTO> queryMemberFlowPackageMonitor(
			Page<MemberFlowPackageMonitorDTO> page, Map<String, Object> map);

}