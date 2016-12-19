package com.pay.fundout.withdraw.dao.autorisk;

import java.util.List;

import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface CheckLogDAO extends BaseDAO<AutoRiskDto>{
	
	/**
	 * 创建日志
	 * @param dto 日志实体
	 * @return
	 */
	public Long createCheckLog(AutoRiskDto dto);
	
	/**
	 * 修改日志
	 * @param dto 日志实体
	 */
	public void updateCheckLog(AutoRiskDto dto);
	
	/**
	 * 查看分页日志信息
	 * @param page 分页实体
	 * @param dto 查询dto
	 * @return 分页后的信息
	 */
	public Page<AutoRiskDto> getCheckLogList(Page<AutoRiskDto> page, AutoRiskDto dto);
	
	/**
	 * 查询日志信息
	 * @param dto
	 * @return
	 */
	public List<AutoRiskDto> queryCheckLogList(AutoRiskDto dto);
	
	/**
	 * 根据主键查找对应的实体信息
	 * @param recordNo
	 * @return
	 */
	public AutoRiskDto findById(Long recordNo);
	
}
