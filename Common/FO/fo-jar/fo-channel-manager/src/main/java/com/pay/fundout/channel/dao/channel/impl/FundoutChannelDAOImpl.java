package com.pay.fundout.channel.dao.channel.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.dao.channel.FundoutChannelDAO;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class FundoutChannelDAOImpl extends BaseDAOImpl implements
		FundoutChannelDAO {

	@Override
	public Long createFundoutChannel(FundoutChannel fundoutChannel) {
		return (Long) this.create("fundoutchannel.create", fundoutChannel);
	}

	@Override
	public FundoutChannel getFundoutChannelById(Long channelId) {
		return (FundoutChannel) getSqlMapClientTemplate().queryForObject(
				"fundoutchannel.getFundoutChannelById", channelId);
	}

	@Override
	public Page<FundoutChannelQueryDTO> getFundoutChannelList(
			Page<FundoutChannelQueryDTO> page, Map params) {
		return this.findByQuery("fundoutchannel.getFundoutChannelList", page,
				params);
	}

	@Override
	public List<FundoutChannelQueryDTO> getFundoutChannelByCode(Map params) {
		return this.findByQuery("fundoutchannel.getFundoutChannelList", params);
	}

	/**
	 * 查询渠道 ByProductCode
	 */
	@Override
	public List<FundoutChannelQueryDTO> getFundoutChannelListByProductCode(
			Map params) {
		return this.findByQuery(
				"fundoutchannel.getFundoutChannelListByProductCode", params);
	}

	@Override
	public int updateFundoutChannelById(FundoutChannel fundoutChannel) {
		return getSqlMapClientTemplate().update(
				"fundoutchannel.updateFundoutChannelById", fundoutChannel);
	}

	@Override
	public List getbusiIdByBankId(String bankId) {
		return getSqlMapClientTemplate().queryForList(
				"fundoutchannel.getbusiIdByBankId", bankId);
	}

	@Override
	public String getChannelId(Map params) {
		return (String) getSqlMapClientTemplate().queryForObject(
				"fundoutchannel.getChannelId", params);
	}

	@Override
	public List getFoBankList(FundoutBank foBank) {
		return getSqlMapClientTemplate().queryForList(
				"fundoutbank.findBySelective", foBank);
	}

	@Override
	public List getFoBusinessList(FundoutBusiness foBusiness) {
		return getSqlMapClientTemplate().queryForList(
				"fundoutbusiness.queryBusinessList", foBusiness);
	}

	@Override
	public List getFoModeList(FundoutMode foMode) {
		return getSqlMapClientTemplate().queryForList(
				"fundoutmode.queryModeList", foMode);
	}

	@Override
	public List<FundoutChannelQueryDTO> queryFoChannels(
			Map<String, String> param) {
		return getSqlMapClientTemplate().queryForList(
				"fundoutchannel.getFundoutChannelList", param);
	}

	@Override
	public FundoutBank getFoBankByBankId(FundoutBank fundoutBank) {
		return (FundoutBank) getSqlMapClientTemplate().queryForObject(
				"fundoutbank.findBySelective", fundoutBank);
	}

	public List<FundoutChannelQueryDTO> getFundoutChannelList() {
		return this.findAll("fundoutchannel.getFundoutChannelList");
	}
}