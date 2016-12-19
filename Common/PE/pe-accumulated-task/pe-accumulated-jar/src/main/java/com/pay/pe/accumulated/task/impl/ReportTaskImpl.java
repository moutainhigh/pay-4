package com.pay.pe.accumulated.task.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReportTaskImpl extends AbstractTask {

	private final Log log = LogFactory.getLog(ReportTaskImpl.class);
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	protected void doTask() {

		log.info("-----------运行报表开始-------");
		this.jdbcTemplate.execute("call PROD_CALL_REPORT_member_flow()");
		log.info("------------运行报表结束-----------------------");
	}

	@Override
	protected String getTaskName() {
		return "ReportTaskImpl";
	}

}
