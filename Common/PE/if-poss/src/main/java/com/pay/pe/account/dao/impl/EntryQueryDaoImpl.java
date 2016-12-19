package com.pay.pe.account.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.account.dao.EntryQueryDao;
import com.pay.pe.account.dto.QueryBalanceDTO;
import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.dto.SubjectBalanceQueryDto;
import com.pay.util.MfDate;

public class EntryQueryDaoImpl extends BaseDAOImpl implements EntryQueryDao {

	private JdbcTemplate jdbcTemplate;

	public QueryBalanceDTO getBalanceInfo(String acctcode, MfDate beginDate,
			MfDate endDate) {

		String sql = "select nvl(sum(a.value + b.value), 0) / 1000  beginningBalance,         "
				+ "       nvl(sum(a.value + c.value), 0) / 1000  endingBalance,               "
				+ "       nvl(sum(d.drValue), 0) / 1000  drAmount,                            "
				+ "       nvl(sum(d.crValue), 0) / 1000  crAmount                            "
				+ "                                                                           "
				// +
				// "  from (SELECT 1000000 as value from dual) a,                                    "
				+ "  from ( SELECT nvl(SUM(ac.Balance), 0) value FROM t_Acct Ac, Subject Coa WHERE Ac.Acct_code = Coa.Acct_code AND Ac.Acct_code = ?) a,                                    "
				+ "                                                                           "
				+ "       (select nvl(SUM(Decode(e.Crdr / s.Balance_by, 1, -e.value, e.value)),"
				+ "                   0) value                                                "
				+ "          FROM Entry e, Subject s                        "
				+ "         where e.POST_DATE >= to_date(?, 'yyyyMMdd')              "
				// 3 +
				// "           and e.CREATION_DATE >= to_date(?, 'yyyyMMdd')     "
				+ "           and e.POST_DATE <= sysdate+1                                     "
				// +
				// "           and e.CREATION_DATE <= sysdate+1                                 "
				+ "           AND e.Acct_code = s.Acct_code                                     "
				+ "           AND e.Acct_code = ?                                      "
				+ "           AND e.status = 1) b,                                            "
				+ "                                                                           "
				+ "       (select nvl(SUM(Decode(e.Crdr / s.Balance_by, 1, -e.value, e.value)),"
				+ "                   0) value                                                "
				+ "          FROM Entry e, Subject s                        "
				+ "         where e.POST_DATE >= to_date(?, 'yyyyMMdd')             "
				// 6 +
				// "           and e.CREATION_DATE >= to_date(?, 'yyyyMMdd')       "
				+ "           and e.POST_DATE <= sysdate+1                                     "
				// +
				// "           and e.CREATION_DATE <= sysdate+1                                 "
				+ "           AND e.Acct_code = s.Acct_code                                     "
				+ "           AND e.Acct_code = ?                                         "
				+ "           AND e.status = 1) c,                                            "
				+ "                                                                           "
				+ "       (select nvl(sum(Decode(e.Crdr, 1, e.value, 0)), 0) drValue,"
				+ "             nvl(sum(Decode(e.Crdr, 2, e.value, 0)), 0) crValue "
				+ "          from entry e                                                     "
				+ "         where e.acct_code = ?                                         "
				+ "           and e.POST_DATE >= to_date(?, 'yyyyMMdd')               "
				+ "           and e.POST_DATE <= to_date(?, 'yyyyMMdd')               "
				// 11 +
				// "           and e.CREATION_DATE >= to_date(?, 'yyyyMMdd')   "
				// 12 +
				// "           and e.CREATION_DATE <= to_date(?, 'yyyyMMdd') "
				+ "           and e.status = 1 ) d        ";

		String _beginDate = beginDate.toString("yyyyMMdd");

		beginDate.minusDays(1);
		String beginDateMinus1 = beginDate.toString("yyyyMMdd");

		endDate.addDays(1);
		String endDateAdd1 = endDate.toString("yyyyMMdd");

		endDate.minusDays(2);
		String endDateMinus1 = endDate.toString("yyyyMMdd");

		Object[] values = new Object[] { acctcode, _beginDate, acctcode,
				endDateAdd1, acctcode, acctcode, _beginDate, endDateAdd1 };

		// Object[] values = new Object[] { acctcode, _beginDate,
		// beginDateMinus1, acctcode, endDateAdd1,
		// endDateMinus1, acctcode, acctcode, _beginDate, endDateAdd1,
		// beginDateMinus1, endDateAdd1 };

		// Object[] values = new Object[] { _beginDate, beginDateMinus1,
		// acctcode, endDateAdd1,
		// endDateMinus1, acctcode, acctcode, _beginDate, endDateAdd1,
		// beginDateMinus1, endDateAdd1 };

		String[] paraNames = new String[] { "acctcode", "_beginDate",
				"beginDateMinus1", "acctcode", "endDateAdd1", "endDateMinus1",
				"acctcode", "acctcode", "_beginDate", "endDateAdd1",
				"beginDateMinus1", "endDateAdd1" };

		// Map paramMap = new HashMap();
		// paramMap.put("acctcode", acctcode);
		// paramMap.put("_beginDate", acctcode);
		// paramMap.put("beginDateMinus1", acctcode);
		// paramMap.put("endDateAdd1", acctcode);
		// paramMap.put("endDateMinus1", acctcode);

		if (jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate(this.getDataSource());
		}
		return (QueryBalanceDTO) jdbcTemplate.queryForObject(sql, values,
				new QueryBalanceRowMapper());
	}

	class QueryBalanceRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			QueryBalanceDTO balance = new QueryBalanceDTO();
			balance.setBeginningBalance(((BigDecimal) rs
					.getBigDecimal("BEGINNINGBALANCE")));
			balance.setEndingBalance(((BigDecimal) rs
					.getBigDecimal("ENDINGBALANCE")));
			balance.setCrAmount(((BigDecimal) rs.getBigDecimal("CRAMOUNT")));
			balance.setDrAmount(((BigDecimal) rs.getBigDecimal("DRAMOUNT")));
			return balance;
		}
	}

	public static final int FIRST_PAGE_OFFSET = 0;

	public static final String COUNT_SUBFIX = "_COUNT";

	// @Override
	// public <T> Page<T> findByQuery(String stmtId, final Page<T> page,
	// Object... params) throws PlatformDaoException {
	// int offset = page.getFirst();
	// int pageSize = page.getPageSize();
	// QueryResult temp = findByQuery(stmtId, offset, pageSize, params);
	// page.setResult(temp.getResult());
	// if (offset == FIRST_PAGE_OFFSET) {
	// page.setTotalCount(temp.getTotalCount());
	// }
	// return page;
	// }

	@Override
	public Page<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate, Page<QueryEntryDTO> page) {

		String _beginDate = beginDate.toString("yyyyMMdd");

		beginDate.minusDays(1);
		String beginDateMinus1 = beginDate.toString("yyyyMMdd");

		String _endDate = endDate.toString("yyyyMMdd");

		endDate.addDays(1);
		String endDateAdd1 = endDate.toString("yyyyMMdd");

		Object[] values = new Object[] { acctCode, _beginDate, endDateAdd1,
				beginDateMinus1, endDateAdd1 };
		String[] paraNames = new String[] { "acctcode", "_beginDate",
				"endDateAdd1", "beginDateMinus1", "endDateAdd1" };

		Map map = new HashMap();
		map.put("acctCode", acctCode);
		map.put("beginDate", _beginDate);
		map.put("endDate", _endDate);

		map.put("endDateAdd1", endDateAdd1);
		map.put("beginDateMinus1", beginDateMinus1);

		return findByQuery("getAllEntries", page, map);
	}

	@Override
	public List<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate) {

		String _beginDate = beginDate.toString("yyyyMMdd");
		String _endDate = endDate.toString("yyyyMMdd");

		beginDate.minusDays(1);
		String beginDateMinus1 = beginDate.toString("yyyyMMdd");

		endDate.addDays(1);
		String endDateAdd1 = endDate.toString("yyyyMMdd");

		Object[] values = new Object[] { acctCode, _beginDate, endDateAdd1,
				beginDateMinus1, endDateAdd1 };
		String[] paraNames = new String[] { "acctcode", "_beginDate",
				"endDateAdd1", "beginDateMinus1", "endDateAdd1" };

		Map map = new HashMap();
		map.put("acctCode", acctCode);
		map.put("beginDate", _beginDate);
		map.put("endDate", _endDate);

		map.put("endDateAdd1", endDateAdd1);
		map.put("beginDateMinus1", beginDateMinus1);

		return getSqlMapClientTemplate().queryForList(
				"acctountMix.getAllEntries", map);
	}

	@Override
	public List<SubjectBalanceDTO> getSubjectBalanceList(
			SubjectBalanceQueryDto command) {
		return getSqlMapClientTemplate().queryForList(
				"acctountMix.getSubjectBalanceListLevel", command);
	}

	@Override
	public List<SubjectBalanceDTO> getSubjectBalanceListFiveLevel(
			SubjectBalanceQueryDto command) {
		return getSqlMapClientTemplate().queryForList(
				"acctountMix.getSubjectBalanceListFiveLevel", command);
	}

	@Override
	public Integer countSubjectBalanceListFiveLevel(
			SubjectBalanceQueryDto queryCommand) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"acctountMix.countSubjectBalanceListFiveLevel", queryCommand);
	}

	@Override
	public Integer countSubjectBalanceListLevel(
			SubjectBalanceQueryDto queryCommand) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"acctountMix.countSubjectBalanceListLevel", queryCommand);
	}

	@Override
	public List<SubjectBalanceDTO> getSubjectBalanceList(String acctCode,
			MfDate beginDate, MfDate endDate) {
		String beginDateStr = beginDate.toString("yyyyMMdd");
		String endDateStr = endDate.toString("yyyyMMdd");
		Map map = new HashMap();
		map.put("acctCode", acctCode);
		map.put("beginDate", beginDateStr);
		map.put("endDate", endDateStr);

		return getSqlMapClientTemplate().queryForList(
				"acctountMix.getSubjectBalanceList", map);
	}

	@Override
	public List<QueryEntryDTO> getEntrieListByOrderId(String orderId) {
		Map map = new HashMap();
		map.put("orderId", orderId);
		return getSqlMapClientTemplate().queryForList(
				"acctountMix.getEntrieListByOrderId", map);
	}

	@Override
	public SubjectBalanceDTO getSubjectBalance(String acctCode,
			MfDate beginDate, MfDate endDate) {
		String beginDateStr = beginDate.toString("yyyyMMdd");
		String endDateStr = endDate.toString("yyyyMMdd");
		Map map = new HashMap();
		map.put("acctCode", acctCode);
		map.put("beginDate", beginDateStr);
		map.put("endDate", endDateStr);
		return (SubjectBalanceDTO) getSqlMapClientTemplate().queryForObject(
				"acctountMix.getSubjectBalance", map);
	}

}
