package com.pay.pe.account.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.pe.account.controller.SubjectBalanceQueryCommand;
import com.pay.pe.account.dto.QueryBalanceDTO;
import com.pay.pe.account.dto.QueryEntryDTO;
import com.pay.pe.account.dto.SubjectBalanceDTO;
import com.pay.util.MfDate;

public interface EntryQueryService {

	QueryBalanceDTO getBalanceInfo(String acctCode, MfDate beginDate,
			MfDate endDate);

	List<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate);

	Page<QueryEntryDTO> getAllEntries(String acctCode, MfDate beginDate,
			MfDate endDate, Page<QueryEntryDTO> page);

	public List<QueryEntryDTO> getEntrieListByOrderId(String orderId);

	/**
	 * 按级别查询所有的科目余额总计 需要分页
	 * 
	 * @param acctCode
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @return
	 */
	public List<SubjectBalanceDTO> getSubjectBalanceList(
			SubjectBalanceQueryCommand command, Page page);

	/**
	 * 按级别查询所有的科目余额总计 不用分页
	 * 
	 * @param acctCode
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @return
	 */
	public List<SubjectBalanceDTO> getAllSubjectBalanceList(
			SubjectBalanceQueryCommand command, Integer count);

	/**
	 * 查询所有的科目余额总计
	 * 
	 * @param acctCode
	 * @param beginDate
	 * @param endDate
	 * @param subjectLevel
	 * @return
	 */
	public List<SubjectBalanceDTO> getSubjectBalanceList(String acctCode,
			MfDate beginDate, MfDate endDate);

	public Integer countSubjectBalanceListFiveLevel(
			SubjectBalanceQueryCommand queryCommand);

	public Integer countSubjectBalanceListLevel(
			SubjectBalanceQueryCommand queryCommand);

	public SubjectBalanceDTO getSubjectBalance(String acctCode,
			MfDate beginDate, MfDate endDate);

}
