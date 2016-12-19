package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.MemberDailyReportDAO;
import com.pay.poss.report.dto.MemberDailyReportDTO;
import com.pay.poss.report.service.MemberDailyReportService;

public class MemberDailyReportServiceImpl implements MemberDailyReportService {

	private MemberDailyReportDAO memberDailyReportDAO;

	public void setMemberDailyReportDAO(
			MemberDailyReportDAO memberDailyReportDAO) {
		this.memberDailyReportDAO = memberDailyReportDAO;
	}

	@Override
	public Page<MemberDailyReportDTO> queryMemberDailyReport(
			Page<MemberDailyReportDTO> page, Map<String, Object> map) {
		return memberDailyReportDAO.queryMemberDailyReport(page, map);
	}

	@Override
	public List<MemberDailyReportDTO> queryMemberDailyReport(
			Map<String, Object> map) {
		return memberDailyReportDAO.queryMemberDailyReport(map);
	}

}
