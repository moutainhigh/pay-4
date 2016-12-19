/** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.service.configchannelrelation.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.configchannelrelation.ConfigChannelRelationDAO;
import com.pay.fundout.channel.dto.configchannelrelation.FundoutConfigChannelRelationDTO;
import com.pay.fundout.channel.model.configchannelrelation.FundoutConfigChannelRelation;
import com.pay.fundout.channel.service.configchannelrelation.ConfigChannelRelationService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see
 */
public class ConfigChannelRelationServiceImpl implements ConfigChannelRelationService {

	protected transient Log log = LogFactory.getLog(getClass());

	private transient ConfigChannelRelationDAO configChannelRelationDAO;

	public void setConfigChannelRelationDAO(final ConfigChannelRelationDAO configChannelRelationDAO) {
		this.configChannelRelationDAO = configChannelRelationDAO;
	}

	@Override
	public Map<String, Object> addConfigChannelRelation(FundoutConfigChannelRelationDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			FundoutConfigChannelRelation model = new FundoutConfigChannelRelation();
			BeanUtils.copyProperties(dto, model);
			long configId = configChannelRelationDAO.addConfigChannelRelation(model);
			map.put("configId", configId);
			map.put("info", "添加配置出款产品与出款渠道成功,配置名称:【" + configId + "=" + dto.getConfigName() + "】");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			map.put("info", "添加配置出款产品与出款渠道失败,配置名称:" + dto.getConfigName());
		}
		return map;
	}

	@Override
	public int modifyConfigChannelRelationRdTx(FundoutConfigChannelRelationDTO dto) {
		FundoutConfigChannelRelation model = new FundoutConfigChannelRelation();
		BeanUtils.copyProperties(dto, model);
		int result = configChannelRelationDAO.modifyConfigChannelRelation(model);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryConfigChannelRelation(Page<FundoutConfigChannelRelationDTO> page, FundoutConfigChannelRelationDTO dto) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		FundoutConfigChannelRelation model = new FundoutConfigChannelRelation();

		BeanUtils.copyProperties(dto, model);

		Page<FundoutConfigChannelRelation> pageModel = new Page<FundoutConfigChannelRelation>();

		PageUtils.setServicePage(pageModel, page);

		pageModel = configChannelRelationDAO.queryConfigChannelRelation(pageModel, model);

		FundoutConfigChannelRelationDTO fundoutConfigChannelRelationDTO = new FundoutConfigChannelRelationDTO();

		page.setResult((List<FundoutConfigChannelRelationDTO>) PageUtils.changePageList(pageModel.getResult(), fundoutConfigChannelRelationDTO, null));

		PageUtils.setServicePage(page, pageModel);

		resultMap.put("page", page);

		return resultMap;
	}

	@Override
	public FundoutConfigChannelRelationDTO queryConfigChannelRelationById(Long configId) {
		FundoutConfigChannelRelationDTO fundoutConfigChannelRelationDTO = new FundoutConfigChannelRelationDTO();
		BeanUtils.copyProperties(configChannelRelationDAO.queryConfigChannelRelationById(configId), fundoutConfigChannelRelationDTO);
		return fundoutConfigChannelRelationDTO;
	}

	@Override
	public List<FundoutConfigChannelRelation> queryConfigChannelRelation(FundoutConfigChannelRelation pojo) {
		return configChannelRelationDAO.queryConfigChannelRelation(pojo);
	}

	@Override
	public boolean delConfigChannelRelationRdTx(Long configId) {
		return configChannelRelationDAO.delete(configId);
	}

}
