/** @Description 
 * @project 	poss-base
 * @file 		CodeTableDaoImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
 */
package com.pay.poss.base.tags.codetable.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.tags.codetable.dao.CodeTableDAO;
import com.pay.poss.base.tags.codetable.dto.CodeTableDTO;
import com.pay.poss.base.tags.codetable.dto.CodeTableDefinitionDTO;

/**
 * <p>
 * 获取基础数据表的Dao
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see
 */
@SuppressWarnings("unchecked")
public class CodeTableDaoImpl extends BaseDAOImpl<CodeTableDTO> implements
		CodeTableDAO {

	@Override
	public List<CodeTableDTO> getCodeTables(CodeTableDefinitionDTO tableDef)
			throws PossUntxException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sb = null;
		String descColumnName = tableDef.getDescColumnName();
		String orderclause = tableDef.getOrderClause();
		String idColumnName = tableDef.getIdColumnName();
		String tableName = tableDef.getTableName();
		String whereClause = tableDef.getWhereClause();

		try {
			if (idColumnName == null || idColumnName.trim().length() == 0) {
				throw new PossUntxException(
						"获取基础数据表异常: CodeTableDaoImpl的参数 idColumnName"
								+ idColumnName + "为空!",
						ExceptionCodeEnum.ILLEGAL_PARAMETER);
			}

			if (tableName == null || tableName.trim().length() == 0) {
				throw new PossUntxException(
						"获取基础数据表异常: CodeTableDaoImpl的参数 tableName" + tableName
								+ "为空!", ExceptionCodeEnum.ILLEGAL_PARAMETER);
			}

			if (descColumnName == null || descColumnName.trim().length() == 0) {
				throw new PossUntxException(
						"获取基础数据表异常: CodeTableDaoImpl的参数 descColumnName"
								+ descColumnName + "为空!",
						ExceptionCodeEnum.ILLEGAL_PARAMETER);
			}

			List<CodeTableDTO> list = new ArrayList<CodeTableDTO>();

			conn = super.getSqlMapClientTemplate().getDataSource()
					.getConnection();

			sb = new StringBuilder();
			sb.append("select  ").append(idColumnName).append(",").append(" ")
					.append(descColumnName).append("  from ").append(tableName);
			if (whereClause != null && whereClause.trim().length() > 0) {
				sb.append(" where ").append(whereClause);
			}
			if (orderclause != null && orderclause.trim().length() > 0) {
				sb.append(" order by ").append(orderclause);
			} else {
				sb.append(" order by ").append(idColumnName);
			}
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();

			while (rs.next()) {
				CodeTableDTO dto = new CodeTableDTO();
				for (int i = 1; i <= numberOfColumns; i++) {
					String columnName = rsmd.getColumnName(i);
					if (columnName.equalsIgnoreCase(descColumnName)
							|| columnName.equalsIgnoreCase(idColumnName)) {
						Object columnValue = "";
						int columnType = rsmd.getColumnType(i);
						switch (columnType) {
						case Types.VARCHAR:
							if (rs.getString(columnName) != null) {
								columnValue = rs.getString(columnName);
							} else {
								columnValue = "";
							}
							break;
						case Types.NUMERIC:
							if (rs.getBigDecimal(columnName) != null) {
								columnValue = rs.getBigDecimal(columnName);
							} else {
								columnValue = "";
							}
							break;
						case Types.NULL:
							columnValue = "";
							break;

						case Types.INTEGER:
							if (rs.getBigDecimal(columnName) != null) {
								columnValue = rs.getBigDecimal(columnName);
							} else {
								columnValue = "";
							}
							break;
						case Types.FLOAT:
							if (rs.getBigDecimal(columnName) != null) {
								columnValue = rs.getBigDecimal(columnName);
							} else {
								columnValue = "";
							}
							break;
						case Types.DOUBLE:
							if (rs.getBigDecimal(columnName) != null) {
								columnValue = rs.getBigDecimal(columnName);
							} else {
								columnValue = "";
							}
							break;
						case Types.DATE:
							if (rs.getDate(columnName) != null) {
								java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								columnValue = formatter
										.format(new java.util.Date(rs
												.getTimestamp(columnName)
												.getTime()));
								;
							} else {
								columnValue = "";
							}

							break;

						default:
							break;
						}
						if (columnName.equalsIgnoreCase(descColumnName)) {
							dto.setDescription("" + columnValue);
						} else if (columnName.equalsIgnoreCase(idColumnName)) {
							dto.setCodeId("" + columnValue);
						}

					}
				}
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			throw new PossUntxException(
					"getCodeTablesError获取基础数据表异常: CodeTableDaoImpl获取基础数据表异常 ",
					ExceptionCodeEnum.ILLEGAL_PARAMETER, e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				rs = null;
				throw new PossUntxException(
						"getCodeTablesError:获取基础数据表异常 CodeTableDaoImpl关闭资源异常 ",
						ExceptionCodeEnum.ILLEGAL_PARAMETER, e);
			}
			try {
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e) {
				ps = null;
				throw new PossUntxException(
						"getCodeTablesError:获取基础数据表异常 CodeTableDaoImpl关闭资源异常 ",
						ExceptionCodeEnum.ILLEGAL_PARAMETER, e);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				conn = null;
				throw new PossUntxException(
						"getCodeTablesError:获取基础数据表异常 CodeTableDaoImpl关闭资源异常 ",
						ExceptionCodeEnum.ILLEGAL_PARAMETER, e);
			}

		}
	}

}
