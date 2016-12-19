package com.pay.pe.manualbooking.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.service.VouchSeqGeneratorService;

/**
 * 
 * 手工记账申请号产生器实现
 */
public class VouchSeqGeneratorServiceImpl implements VouchSeqGeneratorService {	
	private static final Log LOG = LogFactory.getLog(VouchSeqGeneratorServiceImpl.class);
	private static final String GET_VOUCH_SEQ_SQL = "select SEQ_MANUALACC_SEQ_ID.nextval from dual";
	
	/**
	 * 数据源
	 */
	DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public VouchSeqGeneratorServiceImpl() { }
	
	public String generatorSeq() {
		LOG.info("Start");
		String seq = getSeqFromDatabase();
		String s = formatSeq(seq);
		LOG.info("End");
		return s;
	}
	
	private String getSeqFromDatabase() {
		LOG.info("Start");
		
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		String seq = "";
		try {
			conn = dataSource.getConnection();
			statement = conn.createStatement();
			
			LOG.info("Sql : " + GET_VOUCH_SEQ_SQL);
			LOG.info("Before query");
			rs = statement.executeQuery(GET_VOUCH_SEQ_SQL);
			LOG.info("After query");
			rs.next();
			seq = rs.getString(1);
		} catch (SQLException e) {
			LOG.debug("fails to generate seq", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOG.debug("fails to close resource");
			}
		}
		LOG.info("End");
		return seq;
	}
	
	private String formatSeq(String str) {
		LOG.info("Start");
		int length = str.length();
		
		String suffix = null;
		switch (length) {
			case 0:
				suffix = "00000";
				break;
			case 1:
				suffix = "0000" + str;
				break;
			case 2:
				suffix = "000" + str;
				break;
			case 3:
				suffix = "00" + str;
				break;
			case 4:
				suffix = "0" + str;
				break;
			default:
				suffix = str;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String prefix = sdf.format(new Date());

		LOG.info("End");
		return prefix + suffix;
	}

}
