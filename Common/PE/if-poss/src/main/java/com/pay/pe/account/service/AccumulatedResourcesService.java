package com.pay.pe.account.service;

import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccumulatedResourcesDTO;

/**
 * 
 * @author ddr
 * 
 */
public interface AccumulatedResourcesService {

	/**
	 * 通过id取一个对象
	 * 
	 * @param id
	 * @return AccumulatedResourcesDTO
	 */
	public AccumulatedResourcesDTO findById(Long id);

	/**
	 * 根据条件查询
	 * 
	 * @param pageParam
	 * @param dto
	 * @return list
	 */
	public Page<AccumulatedResourcesDTO> searchPage(
			AccumulatedResourcesDTO dto, Page<AccumulatedResourcesDTO> pageParam);

	/**
	 * 创建一个新的
	 * 
	 * @param dto
	 * @return
	 */
	public Long addAccumulatedResRdTx(AccumulatedResourcesDTO dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 * @return
	 */
	public boolean modifyAccumulatedResRdTx(AccumulatedResourcesDTO dto);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public boolean removeAccuResRdTx(Long id);

}
