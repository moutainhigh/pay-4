/**
 *  File: FundoutChannelServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-1     darv      Changes
 *  
 *
 */
package com.pay.fundout.channel.service.channel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fundout.channel.dao.channel.FundoutChannelDAO;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * @author darv
 * 
 */
public class FundoutChannelServiceImpl implements FundoutChannelService {

	private FundoutChannelDAO fundoutChannelDAO;

	private BaseDAO<BankChannelConfig> bankChannelConfigDao;

	public void setFundoutChannelDAO(FundoutChannelDAO fundoutChannelDAO) {
		this.fundoutChannelDAO = fundoutChannelDAO;
	}

	public void setBankChannelConfigDao(
			BaseDAO<BankChannelConfig> bankChannelConfigDao) {
		this.bankChannelConfigDao = bankChannelConfigDao;
	}

	@Override
	public Long createFundoutChannel(FundoutChannel fundoutChannel)
			throws PossException {
		Long id = null;
		try {
			id = fundoutChannelDAO.createFundoutChannel(fundoutChannel);
		} catch (Exception e) {
			throw new PossException(e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
		return id;
	}

	@Override
	public Long createFundoutChannel(FundoutChannel fundoutChannel,
			BankChannelConfig bankChannelConfig) throws PossException {
		Long id = null;
		try {
			id = fundoutChannelDAO.createFundoutChannel(fundoutChannel);
			bankChannelConfig.setChannelCode(fundoutChannel.getCode());
			bankChannelConfigDao.create(bankChannelConfig);
		} catch (Exception e) {
			throw new PossException(e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
		return id;
	}

	@Override
	public FundoutChannel getFundoutChannelById(Long channelId) {
		return fundoutChannelDAO.getFundoutChannelById(channelId);
	}

	@Override
	public Page<FundoutChannelQueryDTO> getFundoutChannelList(
			Page<FundoutChannelQueryDTO> page, Map params) {
		Page<FundoutChannelQueryDTO> resultPage = fundoutChannelDAO
				.getFundoutChannelList(page, params);
		List<FundoutChannelQueryDTO> list = resultPage.getResult();
		for (FundoutChannelQueryDTO dto : list) {
			if (dto.getModeCode().equals("0")) {
				BankChannelConfig bankChannelConfig = bankChannelConfigDao
						.findById(dto.getCode());
				dto.setBankChannelConfig(bankChannelConfig);
			}
		}
		return resultPage;
	}

	@Override
	public void updateFundoutChannelById(FundoutChannel fundoutChannel)
			throws PossException {
		try {
			int result = fundoutChannelDAO
					.updateFundoutChannelById(fundoutChannel);
		} catch (Exception e) {
			throw new PossException(e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	@Override
	public void updateFundoutChannelById(FundoutChannel fundoutChannel,
			BankChannelConfig bankChannelConfig) throws PossException {
		try {
			int result = fundoutChannelDAO
					.updateFundoutChannelById(fundoutChannel);
			BankChannelConfig config = bankChannelConfigDao
					.findById(fundoutChannel.getCode());
			if (config == null) {
				// 不存在对应的渠道附件信息则新增
				bankChannelConfigDao.create(bankChannelConfig);
			} else {
				// 存在对应的渠道附件信息则修改
				bankChannelConfigDao.update(bankChannelConfig);
			}
		} catch (Exception e) {
			throw new PossException(e.getMessage(),
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	@Override
	public List getbusiIdByBankId(String bankId) {
		return this.fundoutChannelDAO.getbusiIdByBankId(bankId);
	}

	@Override
	public String getChannelId(Map params) {
		return fundoutChannelDAO.getChannelId(params);
	}

	@Override
	public List<FundoutChannelQueryDTO> getFundoutChannelListByProductCode(
			Map<String, Object> params) {
		return fundoutChannelDAO.getFundoutChannelListByProductCode(params);
	}

	@Override
	public List getFoBankList(FundoutBank foBank) {
		return fundoutChannelDAO.getFoBankList(foBank);
	}

	@Override
	public List getFoBusinessList(FundoutBusiness foBusiness) {
		return fundoutChannelDAO.getFoBusinessList(foBusiness);
	}

	@Override
	public List getFoModeList(FundoutMode foMode) {
		return fundoutChannelDAO.getFoModeList(foMode);
	}

	@Override
	public Map<String, String> getModeMap(List list) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			FundoutMode mode = (FundoutMode) list.get(i);
			map.put(mode.getCode(), mode.getName());
		}
		return map;
	}

	@Override
	public Map<String, String> getBankMap(List list) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			FundoutBank bank = (FundoutBank) list.get(i);
			map.put(bank.getBankId().toString(), bank.getBankName());
		}
		return map;
	}

	@Override
	public Map<String, String> getBusinessMap(List list) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			FundoutBusiness busi = (FundoutBusiness) list.get(i);
			map.put(busi.getCode(), busi.getName());
		}
		return map;
	}

	@Override
	public List<FundoutChannelQueryDTO> queryFoChannelList(
			Map<String, String> param) {
		return fundoutChannelDAO.queryFoChannels(param);
	}

	@Override
	public FundoutChannelQueryDTO getFundoutChannelByCode(Map param) {
		List<FundoutChannelQueryDTO> list = fundoutChannelDAO
				.getFundoutChannelByCode(param);
		if (list != null && list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public BankChannelConfig findBankChannelConfigByChannelCode(
			String channelCode) {
		return bankChannelConfigDao.findById(channelCode);
	}

	@Override
	public List<Map<String, String>> getFundoutChannel() {
		List<Map<String, String>> withdrawBankList = new ArrayList<Map<String, String>>();

		List<FundoutChannelQueryDTO> channelList = getFundoutChannelList();

		for (FundoutChannelQueryDTO channel : channelList) {
			Map<String, String> bankMap = new HashMap<String, String>();
			bankMap.put("text", channel.getChannelName());
			bankMap.put("value", channel.getCode());
			withdrawBankList.add(bankMap);
		}
		return withdrawBankList;
	}

	public List<FundoutChannelQueryDTO> getFundoutChannelList() {

		return fundoutChannelDAO.getFundoutChannelList();
	}
}
