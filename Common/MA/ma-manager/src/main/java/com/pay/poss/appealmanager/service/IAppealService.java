package com.pay.poss.appealmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.model.AppealHistory;
import com.pay.poss.base.exception.PossException;

/**
 * 
 * @Description
 * @project ma-manager
 * @file IAppealService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-12-19 gungun_zhang Create
 */

public interface IAppealService {
	public String insertAppealTrans(AppealDto appealDto,
			Map<String, MultipartFile> multipartFileMap) throws PossException;

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

	public String getAppealCode();

	public AppealDto getAppealById(String appealId);

	public void updateAppealTrans(AppealDto appealDto) throws PossException;

	public List<AppealHistory> getAppealHistoryByAppealId(String appealId);

	public String isAppealStateValidate(String appealId, String appealState);

	public String insertAppealPictureValidate(
			Map<String, MultipartFile> multipartFileMap);

}
