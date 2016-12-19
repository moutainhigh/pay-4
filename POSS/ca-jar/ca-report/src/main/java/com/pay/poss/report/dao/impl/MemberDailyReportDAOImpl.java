package com.pay.poss.report.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.MemberDailyReportDAO;
import com.pay.poss.report.dto.MemberDailyReportDTO;

@SuppressWarnings("unchecked")
public class MemberDailyReportDAOImpl extends BaseDAOImpl<MemberDailyReportDTO>
		implements MemberDailyReportDAO {

	@Override
	public Page<MemberDailyReportDTO> queryMemberDailyReport(
			Page<MemberDailyReportDTO> page, Map<String, Object> map) {
		return super.findByQuery("queryMemberDailyReport",
				page, map);
	}

	@Override
	public List<MemberDailyReportDTO> queryMemberDailyReport(
			Map<String, Object> map) {
		return super.findByQuery("queryMemberDailyReport",
				map);
	}

}