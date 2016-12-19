package com.pay.pe.account.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccumulatedResourcesDTO;

/**
 * ACCUMULATED_RESOURCES 累计资源配制表
 * 
 * @author 戴德荣 2012-02-28
 */
public interface AccumulatedResourcesDao extends
		BaseDAO<AccumulatedResourcesDTO> {

	/**
	 * 重复查询条数,在新增和修改的时候用
	 * 
	 * @param dto
	 * @return 重复的条数
	 */
	public int repeatCount(AccumulatedResourcesDTO dto);

	/**
	 * 分页查询
	 * 
	 * @param dto
	 * @param pageParam
	 * @return
	 */
	public Page<AccumulatedResourcesDTO> search(AccumulatedResourcesDTO dto,
			Page<AccumulatedResourcesDTO> pageParam);

}
