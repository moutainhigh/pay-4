/**
 *
 * auto generated by ibatis tools 
 *
 **/
package com.pay.fundout.channel.dao.channel;

import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface FundoutChannelDAO extends BaseDAO {
	/**
	 * 添加渠道
	 * 
	 * @param fundoutChannel
	 * @return
	 */
	public Long createFundoutChannel(FundoutChannel fundoutChannel);

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
	public int updateFundoutChannelById(FundoutChannel fundoutChannel);

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

	public List<FundoutChannelQueryDTO> queryFoChannels(
			Map<String, String> param);

	public List<FundoutChannelQueryDTO> getFundoutChannelByCode(Map param);

	public FundoutBank getFoBankByBankId(FundoutBank fundoutBank);

	public List<FundoutChannelQueryDTO> getFundoutChannelListByProductCode(
			Map params);

	public List<FundoutChannelQueryDTO> getFundoutChannelList();
}