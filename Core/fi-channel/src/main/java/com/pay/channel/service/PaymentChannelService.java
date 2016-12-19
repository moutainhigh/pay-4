package com.pay.channel.service;

import java.util.List;
import java.util.Map;

import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.model.ChannelSndRelation;
import com.pay.channel.model.PaymentChannel;
import com.pay.channel.model.PaymentChannelCategory;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.channel.model.PaymentChannelItemDefault;

/**
 * @author
 * 
 */
public interface PaymentChannelService {
	
	boolean checkPaymentChannelItemConfigExists(PaymentChannelItemConfig config);

	/**
	 * 判断渠道类别是否存在
	 * 
	 * @param categoryCode
	 * @return
	 */
	boolean checkCategoryExists(String categoryCode);

	/**
	 * 获取所有支付渠道类别
	 * 
	 * @return
	 */
	List<PaymentChannelCategory> queryPaymentChannelCategory();

	/**
	 * 添加支付渠道类别
	 * 
	 * @param category
	 * @return
	 */
	Long addPaymentChannelCategory(PaymentChannelCategory category);

	/**
	 * 更新渠道类别
	 * 
	 * @param category
	 * @return
	 */
	boolean updatePaymentChannelCategory(PaymentChannelCategory category);

	/**
	 * 删除支付渠道类别
	 * 
	 * @param id
	 * @return
	 */
	boolean delPaymentChannelCategory(Long id);

	/**
	 * 
	 * @param channelCode
	 * @param channelName
	 * @param status
	 * @return
	 */
	List<PaymentChannel> queryPaymentChannel(String channelCode,
			String channelName, String status,String id);

	/**
	 * 添加渠道
	 * 
	 * @param paymentChanel
	 * @return
	 */
	Long addPaymentChannel(PaymentChannel paymentChannel);

	/**
	 * 删除渠道
	 * 
	 * @param id
	 * @return
	 */
	boolean delPaymentChannel(Long id);

	/**
	 * 修改支付渠道
	 * 
	 * @param paymentChannel
	 * @return
	 */
	boolean updatePaymentChannel(PaymentChannel paymentChannel);

	/**
	 * 添加通道
	 * 
	 * @param paymentChannelItem
	 * @return
	 */
	Long addPaymentChannelItem(PaymentChannelItem paymentChannelItem);

	/**
	 * 删除支付通道
	 * 
	 * @param id
	 * @return
	 */
	boolean delPaymentChannelItem(Long id);

	/**
	 * 修改支付通道
	 * 
	 * @param paymentChannelItem
	 * @return
	 */
	boolean updatePaymentChannelItem(PaymentChannelItem paymentChannelItem);

	/**
	 * 获取支付通道
	 * 
	 * @param paymentChannelItem
	 * @return
	 */
	List<PaymentChannelItem> queryPaymentChannelItem(
			PaymentChannelItem paymentChannelItem);

	/**
	 * 配置商户通道
	 * 
	 * @param paymentChannelItemConfig
	 * @return
	 */
	Long addPaymentChannelItemConfig(
			PaymentChannelItemConfig paymentChannelItemConfig);

	/**
	 * 批量配置商户通道
	 * 
	 * @param paymentChannelItemConfigs
	 */
	void addPaymentChannelItemConfig(
			List<PaymentChannelItemConfig> paymentChannelItemConfigs,String auto);

	/**
	 * 删除商户配置通道
	 * 
	 * @param id
	 * @return
	 */
	boolean delPaymentChannelItemConfig(Long id);

	/**
	 * 批量删除商户通道配置
	 * 
	 * @param ids
	 */
	void delPaymentChannelItemConfig(
			List<PaymentChannelItemConfig> paymentChannelItemConfigs);

	/**
	 * 获取商户配置通道
	 * 
	 * @param paymentType
	 * @param memberCode
	 * @return
	 */
	List<PaymentChannelItem> queryConfigPaymentChannelItem(String paymentType,
			String memberCode);

	/**
	 * 
	 * @param orgCode
	 * @return
	 */
	PaymentChannelItem queryPaymentChannelItemByOrgCode(String orgCode);

	/**
	 * 根据机构号和机构商户号获取配置
	 * 
	 * @param orgCode
	 * @param orgMerchantCode
	 * @return
	 */
	PaymentChannelItem queryPaymentChannelItemByOrgCode(String orgCode,
			String orgMerchantCode);

	/**
	 * 添加默认通道
	 * 
	 * @param paymentChannelItemDefault
	 * @return
	 */
	Long addPaymentChannelItemDefault(
			PaymentChannelItemDefault paymentChannelItemDefault);

	/**
	 * 批量添加默认通道配置
	 * 
	 * @param paymentChannelItemDefaults
	 */
	void addPaymentChannelItemDefault(
			List<PaymentChannelItemDefault> paymentChannelItemDefaults);

	/**
	 * 删除默认通道配置
	 * 
	 * @param id
	 * @return
	 */
	boolean delPaymentChannelItemDefault(Long id);

	/**
	 * 批量删除默认通道配置
	 * 
	 * @param ids
	 * @return
	 */
	void delPaymentChannelItemDefault(
			List<PaymentChannelItemDefault> paymentChannelItemDefaults);

	/**
	 * 获取指定类型默认通道
	 * 
	 * @param paymentType
	 * @param memberType
	 * @return
	 */
	List<PaymentChannelItem> queryDefaultPaymentChannelItem(String paymentType,
			String memberType);

	/**
	 * 批量添加二级商户映射
	 * 
	 * @param list
	 */
	void addChannelSecondRelation(List<ChannelSecondRelation> list);

	/**
	 * 批量删除二级商户映射
	 * 
	 * @param list
	 */
	void delChannelSecondRelation(List<ChannelSecondRelation> list);

	/**
	 * 查询配置的二级商户
	 * 
	 * @param memberCode
	 * @param secondMemberCode
	 * @return
	 */
	List<ChannelSecondRelation> queryChannelSecondRelation(String memberCode,
			String orgCode, String orgMerchantCode, String currencyCode);
	
	List<PaymentChannelItemConfig> queryPaymentChannelItemConf(String paymentType, String memberCode);
	/**
	 * 查询渠道类别
	 * 
	 * @param requestMap
	 * @return
	 * @author delin.dong
	 */
	List<PaymentChannelCategory>  queryPaymentChannelCategory(Map requestMap);
	/**
	 * 本地化获取商户配置通道
	 * 
	 * @param paymentType
	 * @param memberCode
	 * @return
	 */
	List<PaymentChannelItem> queryConfigPaymentChannelItem(String paymentType,
			String memberCode, String tradeType);
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 新增
	 * @param param
	 * @return
	 */
	List<ChannelSndRelation> findSndrelationNew(Map param);
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 已删除
	 * @param param
	 * @return
	 */
	List<ChannelSndRelation> findSndrelationDeleted(Map param);
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 已关联
	 * @param param
	 * @return
	 */
	List<ChannelSndRelation> findSndrelationConnected(Map param);
}
