package com.pay.poss.memberrelation.service;

import java.util.List;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.exception.RelationDataInvalidException;


/**   
* @Title: RelationDataLoader.java 
* @Package com.pay.poss.memberrelation.service 
* @Description: 关联名单数据载入器
* @author cf
* @date 2011-9-22 下午01:31:37 
* @version V1.0   
*/
public interface RelationDataLoader {
	/**
	 * @return
	 * @throws VouchDataInvalidException
	 * 载入关联名单数据
	 */
	List<RelationDataDto> getData() throws RelationDataInvalidException, RelationDataBuildException;
}
