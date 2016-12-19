/**
 *  File: FundoutChannelService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-1     darv      Changes
 *  
 *
 */
package com.pay.fundout.channel.service.channel;

import java.util.List;
import java.util.Map;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface FundoutChannelService {
	/**
	 * 添加渠道
	 * 
	 * @param fundoutChannel
	 * @return
	 */
	public Long createFundoutChannel(FundoutChannel fundoutChannel)
			throws PossException;

	/**
	 * 添加渠道(银企直连情况)
	 * 
	 * @param fundoutChannel
	 * @param fundoutChannel
	 * @return
	 */
	public Long createFundoutChannel(FundoutChannel fundoutChannel,
			BankChannelConfig bankChannelConfig) throws PossException;

	/**
	 * 查询渠道列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<FundoutChannelQueryDTO> getFundoutChannelList(
			Page<FundoutChannelQueryDTO> page, Map params);

	/**
	 * 根据渠道编号查询渠道
	 * 
	 * @param channelId
	 * @return
	 */
	public FundoutChannel getFundoutChannelById(Long channelId);

	/**
	 * 根据ID修改渠道
	 * 
	 * @param fundoutChannel
	 */
	public void updateFundoutChannelById(FundoutChannel fundoutChannel)
			throws PossException;

	/**
	 * 修改渠道(银企直连情况)
	 * 
	 * @param fundoutChannel
	 * @param bankChannelConfig
	 */
	public void updateFundoutChannelById(FundoutChannel fundoutChannel,
			BankChannelConfig bankChannelConfig) throws PossException;

	/**
	 * 根据银行号得到关联的业务
	 * 
	 * @param bankId
	 * @return
	 */
	public List getbusiIdByBankId(String bankId);

	/**
	 * 通过银行、业务和方式编号得到渠道编号
	 * 
	 * @param params
	 * @return
	 */
	public String getChannelId(Map params);

	public List getFoBusinessList(FundoutBusiness foBusiness);

	public List getFoBankList(FundoutBank foBank);

	public List getFoModeList(FundoutMode foMode);

	public Map<String, String> getBankMap(List list);

	/**
	 * 
	 * @param list
	 * @return
	 */
	public Map<String, String> getBusinessMap(List list);

	/**
	 * 
	 * @param list
	 * @return
	 */
	public Map<String, String> getModeMap(List list);

	/**
	 ** 【params 包含值 modeCode 出款方式 businessCode 出款业务 】
	 * 
	 * @param params
	 * @return
	 */
	public List<FundoutChannelQueryDTO> queryFoChannelList(
			Map<String, String> params);

	/**
	 * 
	 * @param param
	 * @return
	 */
	public FundoutChannelQueryDTO getFundoutChannelByCode(Map param);

	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<FundoutChannelQueryDTO> getFundoutChannelListByProductCode(
			Map<String, Object> params);

	/**
	 * 根据channelCode查找对应的渠道配置实体
	 * 
	 * @param channelCode
	 * @return
	 */
	public BankChannelConfig findBankChannelConfigByChannelCode(
			String channelCode);

	/**
	 * 获取所有出款渠道
	 * 
	 * @return
	 */
	public List<FundoutChannelQueryDTO> getFundoutChannelList();

	/**
	 * 获取提现银行信息服务
	 * 
	 * @Auther Jonathen Ni
	 */
	public List<Map<String, String>> getFundoutChannel();
}
