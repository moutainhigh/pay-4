package com.pay.poss.appealmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.appealmanager.dao.IDataDao;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;
import com.pay.poss.appealmanager.model.AppealBaseData;
import com.pay.poss.appealmanager.model.AppealBaseDataRelation;

public class DataDaoImpl extends BaseDAOImpl<AppealBaseData> implements
		IDataDao {

	@Override
	public List<BaseDataDto> getBaseDataByType(String type) {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.getBaseDataByType", type);
	}

	@Override
	public List<BaseDataDto> getAppealStatus() {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.getAppealStatus");
	}

	@Override
	public List<BaseDataDto> getDept() {
		return this.getSqlMapClientTemplate().queryForList("baseData.getDept");

	}

	@Override
	public List<BaseDataDto> queryBaseData(BaseDataDto baseDataDto) {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.queryBaseData", baseDataDto);
	}

	@Override
	public Integer queryBaseDataCount(BaseDataDto baseDataDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"baseData.queryBaseDataCount", baseDataDto);
	}

	@Override
	public List<BaseDataRelationDto> queryBaseDataRelation(
			BaseDataRelationDto baseDataRelationDto) {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.queryBaseDataRelation", baseDataRelationDto);
	}

	@Override
	public Integer queryBaseDataRelationCount(
			BaseDataRelationDto baseDataRelationDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"baseData.queryBaseDataRelationCount", baseDataRelationDto);
	}

	@Override
	public String isBaseDataCodeExist(String baseDataCode) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("baseData.isBaseDataCodeExist", baseDataCode);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public String isRelationExist(String fatherDataCode, String sonDataCode) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("fatherDataCode", fatherDataCode);
		paraMap.put("sonDataCode", sonDataCode);
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("baseData.isRelationExist", paraMap);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public void insertAppealBaseData(AppealBaseData appealBaseData) {
		this.getSqlMapClientTemplate().insert("baseData.insertAppealBaseData",
				appealBaseData);

	}

	@Override
	public void insertAppealBaseDataRelation(
			AppealBaseDataRelation baseDataRelation) {
		this.getSqlMapClientTemplate().insert(
				"baseData.insertAppealBaseDataRelation", baseDataRelation);

	}

	@Override
	public void deleteAppealBaseData(String baseDataId) {
		this.getSqlMapClientTemplate().delete("baseData.deleteAppealBaseData",
				baseDataId);

	}

	@Override
	public void deleteAppealBaseDataRelation(String baseDataRelationId) {
		this.getSqlMapClientTemplate().delete(
				"baseData.deleteAppealBaseDataRelation", baseDataRelationId);
	}

	@Override
	public List<BaseDataDto> getAllBaseData() {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.getAllBaseData");
	}

	@Override
	public List<BaseDataRelationDto> getAllBaseDataRelation() {
		return this.getSqlMapClientTemplate().queryForList(
				"baseData.getAllBaseDataRelation");
	}

}
