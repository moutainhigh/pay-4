package com.pay.pe.account.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.QueryBalanceDTO;
import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.pe.account.dto.SubjectBalanceQueryDto;
import com.pay.util.MfDate;

public interface EntryQueryDao extends BaseDAO {

	public QueryBalanceDTO getBalanceInfo(String acctcode, MfDate beginDate,
			MfDate endDate);

	public Page<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate, Page<QueryEntryDTO> page);

	public List<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate);

	public List<QueryEntryDTO> getEntrieListByOrderId(String orderId);

	/**
	 * 按级别查询余额统计
	 * 
	 * @param acctCode
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @return
	 */
	public List<SubjectBalanceDTO> getSubjectBalanceList(
			SubjectBalanceQueryDto queryCommand);

	public Integer countSubjectBalanceListLevel(
			SubjectBalanceQueryDto queryCommand);

	/**
	 * 得到五级科目余额统计
	 * 
	 * @param acctCode
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @return
	 */
	public List<SubjectBalanceDTO> getSubjectBalanceListFiveLevel(
			SubjectBalanceQueryDto queryCommand);

	public Integer countSubjectBalanceListFiveLevel(
			SubjectBalanceQueryDto queryCommand);

	public List<SubjectBalanceDTO> getSubjectBalanceList(String acctCode,
			MfDate beginDate, MfDate endDate);

	public SubjectBalanceDTO getSubjectBalance(String acctCode,
			MfDate beginDate, MfDate endDate);
}