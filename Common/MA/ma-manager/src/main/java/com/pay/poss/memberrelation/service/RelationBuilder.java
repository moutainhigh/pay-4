package com.pay.poss.memberrelation.service;

import java.util.List;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;

/**   
* @Title: RelationBuilder.java 
* @Package com.pay.poss.memberrelation.service 
* @Description: 关联名单申请构建器 
* @author cf
* @date 2011-9-22 下午01:31:16 
* @version V1.0   
*/
public interface RelationBuilder {
	
	/**
	 * @return
	 * @throws RelationDataBuildException
	 * 构建关联名单数据
	 */
	List<RelationDataDto> buildRelationList() throws RelationDataBuildException;
	
}
