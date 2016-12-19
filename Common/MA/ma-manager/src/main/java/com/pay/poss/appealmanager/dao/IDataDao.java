package com.pay.poss.appealmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;
import com.pay.poss.appealmanager.model.AppealBaseData;
import com.pay.poss.appealmanager.model.AppealBaseDataRelation;

public interface IDataDao extends BaseDAO<AppealBaseData> {

	public List<BaseDataDto> getBaseDataByType(String type);

	public List<BaseDataDto> getAllBaseData();

	public List<BaseDataDto> getAppealStatus();

	public List<BaseDataDto> getDept();

	public List<BaseDataDto> queryBaseData(BaseDataDto baseDataDto);

	public Integer queryBaseDataCount(BaseDataDto baseDataDto);

	public List<BaseDataRelationDto> queryBaseDataRelation(
			BaseDataRelationDto baseDataRelationDto);

	public Integer queryBaseDataRelationCount(
			BaseDataRelationDto baseDataRelationDto);

	public String isBaseDataCodeExist(String baseDataCode);

	public String isRelationExist(String fatherDataCode, String sonDataCode);

	public void insertAppealBaseData(AppealBaseData appealBaseData);

	public void insertAppealBaseDataRelation(
			AppealBaseDataRelation baseDataRelation);

	public void deleteAppealBaseData(String baseDataId);

	public void deleteAppealBaseDataRelation(String baseDataRelationId);

	public List<BaseDataRelationDto> getAllBaseDataRelation();
}