package com.pay.pe.accumulated.resources.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;

/**
 * 
 * @ClassName: AccumulatedResourcesDao
 * @Description: 累计资源配制
 * @author cf
 * @date 2012-3-1 上午9:26:38
 * 
 */
public interface AccumulatedResourcesDao extends
		BaseDAO<AccumulatedResourcesDto> {

	/**
	 * @Title: queryAccumulatedResourcesList
	 * @Description: 查询累计资源配制信息
	 * @param @return 设定文件
	 * @return AccumulatedResourcesDto 返回类型
	 * @throws
	 */
	List<AccumulatedResourcesDto> queryAccumulatedResourcesList();
}
