package com.pay.pe.manualbooking.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.manualbooking.dao.VouchDetailDataDao;
import com.pay.pe.manualbooking.dto.VouchDataDetailSearchDto;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;

/**
 * 手工记账申请明细访问实现
 */
public class VouchDetailDataDaoImpl extends BaseDAOImpl<VouchDetailData>
		implements VouchDetailDataDao {
	public static final int FIRST_PAGE_OFFSET = 0;
	public static final String COUNT_SUBFIX = "_COUNT";
	private static final Log LOG = LogFactory
			.getLog(VouchDetailDataDaoImpl.class);
	private static final String count_sql = "select count(*) from t_manualaccount_vouchdata t "
			+ "left join t_manualaccount_vouchdetail d on t.vouchdataid = d.vouchdataid "
			+ "where t.status = '1' ";

	private static final String select_sql = "select t.accountingdate accountingdate, t.createdate createdate, t.creator creator, t.vouchcode vouchcode, d.accountcode accountcode, d.crdr crdr, d.amount amount, d.remark remark  from t_manualaccount_vouchdata t "
			+ "left join t_manualaccount_vouchdetail d on t.vouchdataid = d.vouchdataid "
			+ "where t.status = '1' ";

	// DataSource dataSource;
	//
	// public DataSource getDataSource() {
	// return dataSource;
	// }
	//
	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }
	public void createVouchDetail(VouchDetailData vdd) {
		this.getSqlMapClientTemplate().insert("vouchdetail.create", vdd);
	}

	public VouchData insertVouchData(VouchData vd) {
		super.getSqlMapClientTemplate().insert("vouchdata.create", vd);

		List vdlist = super.getSqlMapClientTemplate().queryForList(
				"vouchdata.createId", vd.getApplicationCode());
		String vdId = "";
		VouchData voud = null;
		if (vdlist != null && vdlist.size() > 0) {
			for (Iterator it = vdlist.iterator(); it.hasNext();) {
				voud = (VouchData) it.next();

			}
		}
		return voud;
	}

	public List<VouchDataDetailSearchDto> findVouchDetailDatasWithPage(
			VouchDetailSearchCriteria vouchDetailSearchCriteria)
			throws SQLException {
		LOG.info("Start");

		Date dateTo = null;
		if (vouchDetailSearchCriteria.getDateTo() != null) {
			dateTo = new Date(vouchDetailSearchCriteria.getDateTo().getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dateTo = calendar.getTime();
		}

		StringBuffer countSql = new StringBuffer(count_sql);
		StringBuffer selectSql = new StringBuffer(select_sql);
		if (StringUtils.isNotEmpty(vouchDetailSearchCriteria.getAccountCode())) {
			countSql.append("and d.accountcode = ? ");
			selectSql.append("and d.accountcode = ? ");
		}
		if (vouchDetailSearchCriteria.getDateFrom() == null && dateTo != null) {
			countSql.append("and t.accountingdate <= trunc(?) ");
			selectSql.append("and t.accountingdate <= trunc(?) ");
		}
		if (vouchDetailSearchCriteria.getDateFrom() != null && dateTo == null) {
			countSql.append("and t.accountingdate >= trunc(?) ");
			selectSql.append("and t.accountingdate >= trunc(?) ");
		}
		if (vouchDetailSearchCriteria.getDateFrom() != null && dateTo != null) {
			countSql.append("and t.accountingdate >= trunc(?) and t.accountingdate <= trunc(?) ");
			selectSql
					.append("and t.accountingdate >= trunc(?) and t.accountingdate <= trunc(?) ");
		}

		selectSql.append(" order by t.accountingdate asc");

		LOG.info("Count sql : " + countSql.toString());
		LOG.info("Select sql : " + selectSql.toString());

		Connection conn = null;
		PreparedStatement count_pstmt = null;
		PreparedStatement select_pstmt = null;
		ResultSet count_rs = null;
		ResultSet select_rs = null;
		List<VouchDataDetailSearchDto> vouchDetailSearchDtos = new ArrayList<VouchDataDetailSearchDto>();
		try {
			conn = super.getDataSource().getConnection();
			count_pstmt = conn.prepareStatement(countSql.toString());
			select_pstmt = conn.prepareStatement(selectSql.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if (StringUtils.isNotEmpty(vouchDetailSearchCriteria
					.getAccountCode())) {
				count_pstmt.setString(i, vouchDetailSearchCriteria
						.getAccountCode().trim());
				select_pstmt.setString(i, vouchDetailSearchCriteria
						.getAccountCode().trim());
				i++;
			}
			if (vouchDetailSearchCriteria.getDateFrom() == null
					&& dateTo != null) {
				count_pstmt.setDate(i, new java.sql.Date(dateTo.getTime()));
				select_pstmt.setDate(i, new java.sql.Date(dateTo.getTime()));
			}
			if (vouchDetailSearchCriteria.getDateFrom() != null
					&& dateTo == null) {
				count_pstmt.setDate(i, new java.sql.Date(
						vouchDetailSearchCriteria.getDateFrom().getTime()));
				select_pstmt.setDate(i, new java.sql.Date(
						vouchDetailSearchCriteria.getDateFrom().getTime()));
			}
			if (vouchDetailSearchCriteria.getDateFrom() != null
					&& dateTo != null) {
				count_pstmt.setDate(i, new java.sql.Date(
						vouchDetailSearchCriteria.getDateFrom().getTime()));
				select_pstmt.setDate(i, new java.sql.Date(
						vouchDetailSearchCriteria.getDateFrom().getTime()));
				count_pstmt.setDate(i + 1, new java.sql.Date(dateTo.getTime()));
				select_pstmt
						.setDate(i + 1, new java.sql.Date(dateTo.getTime()));
			}
			LOG.info("Before count query");
			count_rs = count_pstmt.executeQuery();
			LOG.info("After count query");

			int count = 0;
			if (count_rs.next()) {
				count = count_rs.getShort(1);
			}

			// page.setTotalRecord(count);
			// if (count == 0) {
			// LOG.info("Count is zero!");
			// return new ArrayList<VouchDataDetailSearchDto>();
			// }
			//
			// if (((page.getTargetPage() - 1) * page.getPageSize()) >= count) {
			// page.setTargetPage(1);
			// }
			//
			// LOG.info("Before select query");
			// select_rs = select_pstmt.executeQuery();
			// LOG.info("After select query");
			//
			// int startIndex = (page.getTargetPage() - 1) * page.getPageSize();
			// int endIndex = page.getTargetPage() * page.getPageSize();
			int startIndex = (1 - 1) * 20;
			int endIndex = 1 * 20;
			if (startIndex != 0) {
				select_rs.absolute(startIndex);
			}
			while (select_rs.next() && startIndex <= endIndex) {
				startIndex++;
				Date accountDate = select_rs.getTimestamp("accountingdate");
				String creator = select_rs.getString("creator");
				String vouchcode = select_rs.getString("vouchcode");
				String accountcode = select_rs.getString("accountcode");
				int crdr = select_rs.getInt("crdr");
				String amount = select_rs.getString("amount");
				String remark = select_rs.getString("remark");

				VouchDataDetailSearchDto vouchDetailSearchDto = new VouchDataDetailSearchDto();
				vouchDetailSearchDto.setAccountCode(accountcode);
				vouchDetailSearchDto.setApplyDate(accountDate);
				vouchDetailSearchDto.setOperator(creator);
				vouchDetailSearchDto.setRemark(remark);
				vouchDetailSearchDto.setVouchCode(vouchcode);
				if (crdr == 1) {
					vouchDetailSearchDto.setCrAmount(amount);
					vouchDetailSearchDto.setDrAmount("");
				}
				if (crdr == 2) {
					vouchDetailSearchDto.setCrAmount("");
					vouchDetailSearchDto.setDrAmount(amount);
				}
				vouchDetailSearchDtos.add(vouchDetailSearchDto);
			}

			LOG.info("Result list size : " + vouchDetailSearchDtos.size());
		} catch (SQLException e) {
			LOG.debug("fail to query vouch detail!", e);
			throw e;
		} finally {
			if (count_rs != null) {
				count_rs.close();
			}
			if (select_rs != null) {
				select_rs.close();
			}
			if (count_pstmt != null) {
				count_pstmt.close();
			}
			if (select_pstmt != null) {
				select_pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		LOG.debug("End");
		return vouchDetailSearchDtos;
	}

	public Class getModelClass() {
		return VouchDetailData.class;
	}

}
