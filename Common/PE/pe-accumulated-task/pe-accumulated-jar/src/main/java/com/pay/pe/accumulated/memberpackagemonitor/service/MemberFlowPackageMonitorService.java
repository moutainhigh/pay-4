package com.pay.pe.accumulated.memberpackagemonitor.service;

import java.util.List;
import java.util.Map;

import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageMonitorDto;

/**
 * 
 * 会员流量监控
 * @author 
 *
 */
public interface MemberFlowPackageMonitorService {
	/**
	 * 新增会员流量监控记录
	 * @param memberFlowPackageMonitorDto
	 */
	public void addMemFlowPckMonitorRnTx(MemberFlowPackageMonitorDto memberFlowPackageMonitorDto);
	/**
	 * 查询会员流量监控记录
	 * @param paramMap
	 * @return
	 */
	public List<MemberFlowPackageMonitorDto> queryMemFlowPckMonitorList(Map<String, Object> paramMap);
	/**
	 * 处理会员流量监控
	 * @param memberFlowPackageMonitorDto
	 */
	public void handleMemFlowPckMonitorRnTx(MemberFlowPackageMonitorDto memberFlowPackageMonitorDto);
	/**
	 * 删除监控记录
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	public boolean deleteMemFlowPckMonitor(MemberFlowPackageMonitorDto memberFlowPackageMonitorDto);
}
