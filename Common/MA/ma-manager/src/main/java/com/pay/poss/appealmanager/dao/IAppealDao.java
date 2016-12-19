package com.pay.poss.appealmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.model.Appeal;
import com.pay.poss.appealmanager.model.AppealHistory;

public interface IAppealDao extends BaseDAO<Appeal> {
	
	public Long insertAppeal(Appeal appeal);

	public void updateAppeal(AppealDto appealDto);

	public void insertAppealHistory(AppealHistory appealHistory);

	public String getAppealCode();

	public List<AppealTaskListDto> getAppealListForDispense(
			AppealTaskSearchDto appealTaskSearchDto);

	public List<AppealTaskListDto> getAppealListForDept(
			AppealTaskSearchDto appealTaskSearchDto);

	public List<AppealTaskListDto> getAppealListForCallBack(
			AppealTaskSearchDto appealTaskSearchDto);

	public List<AppealTaskListDto> getAppealListForFinish(
			AppealTaskSearchDto appealTaskSearchDto);

	public List<AppealTaskListDto> getAppealListForSearch(
			AppealTaskSearchDto appealTaskSearchDto);

	public Integer getAppealListForDispenseCount();

	public Integer getAppealListForDeptCount(
			AppealTaskSearchDto appealTaskSearchDto);

	public Integer getAppealListForCallBackCount(
			AppealTaskSearchDto appealTaskSearchDto);

	public Integer getAppealListForFinishCount(
			AppealTaskSearchDto appealTaskSearchDto);

	public Integer getAppealListForSearchCount(
			AppealTaskSearchDto appealTaskSearchDto);

	public AppealDto getAppealById(Long appealId);

	public List<AppealHistory> getAppealHistoryByAppealId(Long appealId);

	public String vaildateDispense(String appealId);

	public String vaildateDept(String appealId);

	public String vaildateCallBack(String appealId);

	public String vaildateFinish(String appealId);
}