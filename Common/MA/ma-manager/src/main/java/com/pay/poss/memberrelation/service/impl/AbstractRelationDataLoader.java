package com.pay.poss.memberrelation.service.impl;

import java.util.List;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.exception.RelationDataInvalidException;
import com.pay.poss.memberrelation.service.RelationDataLoader;




/**
 * 抽象关联名单数据载入器
 */
public abstract class AbstractRelationDataLoader implements RelationDataLoader {
	
	/**
	 * @return
	 * @throws RelationDataBuildException
	 * 载入关联名单数据
	 */
	protected abstract List<RelationDataDto> loadRelationData() throws RelationDataBuildException;
	
	/**
	 * @param RelationDataDto
	 * @throws RelationDataInvalidException
	 * 验证关联名单数据
	 */
	protected abstract void validateRelationData(List<RelationDataDto> RelationDataDto) throws RelationDataInvalidException;
	
	/**
	 * @return
	 * 取得关联名单数据
	 */
	protected abstract  List<RelationDataDto> retriveRelationData();
	
	/**
	 * @return
	 * @throws RelationDataInvalidException
	 * 载入关联名单数据
	 */
	public List<RelationDataDto> getData() throws RelationDataInvalidException, RelationDataBuildException {
		List<RelationDataDto> RelationDataDto = loadRelationData();
		validateRelationData(RelationDataDto);
		return retriveRelationData();
	}

}
