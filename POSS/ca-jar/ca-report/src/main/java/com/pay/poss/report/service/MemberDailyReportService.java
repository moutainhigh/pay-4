package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberDailyReportDTO;

public interface MemberDailyReportService {

	/**
	 * 查询会员综合报表列表
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<MemberDailyReportDTO> queryMemberDailyReport(
			Page<MemberDailyReportDTO> page, Map<String, Object> map);

	/**
	 * 查询会员综合报表列表（下载）
	 * 
	 * @param map
	 * @return
	 */
	public List<MemberDailyReportDTO> queryMemberDailyReport(
			Map<String, Object> map);

}