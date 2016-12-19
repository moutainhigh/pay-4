package com.pay.api.service;

import java.util.List;

import com.pay.api.model.MerchantConfigure;
import com.pay.inf.dao.Page;

public interface MerchantConfigureService {

	/**
	 * 新增配置
	 * @param merchantConfigure
	 * @return
	 */
	public Integer create(MerchantConfigure merchantConfigure);
	
	/**
	 * 修改配置
	 * @param merchantConfigure
	 * @return
	 */
	public boolean update(MerchantConfigure merchantConfigure);
	
	/**
	 * 通过id查找配置实体
	 * @param id
	 * @return
	 */
	public MerchantConfigure findById(Integer id);
	
	/**
	 * 分页查询配置信息
	 * @param resultPage
	 * @param merchantConfigure
	 * @return
	 */
	public List<MerchantConfigure> query(Page page, Object criteria);
	
	/**
	 * 删除配置
	 * @param id
	 * @return
	 */
	public boolean delete(Long id);
	
	/**
	 * 根据条件查找MerchantConfigure实体
	 * @param criteria
	 * @return
	 */
	public MerchantConfigure findObjectByCriteria(Object criteria);
}
