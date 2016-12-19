/**
 * 
 */
package com.pay.channel.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.channel.redis.model.ChannelObj;
import com.pay.channel.service.AutoFitEngineService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.dao.PaymentChannelCategoryDAO;
import com.pay.channel.dao.PaymentChannelDAO;
import com.pay.channel.dao.PaymentChannelItemConfigDAO;
import com.pay.channel.dao.PaymentChannelItemDAO;
import com.pay.channel.dao.PaymentChannelItemDefaultDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.model.ChannelSndRelation;
import com.pay.channel.model.PaymentChannel;
import com.pay.channel.model.PaymentChannelCategory;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.model.PaymentChannelItemConfig;
import com.pay.channel.model.PaymentChannelItemDefault;
import com.pay.channel.service.PaymentChannelService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentChannelServiceImpl implements PaymentChannelService {

	private PaymentChannelCategoryDAO paymentChannelCategoryDAO;
	private PaymentChannelDAO paymentChannelDAO;
	private PaymentChannelItemDAO paymentChannelItemDAO;
	private PaymentChannelItemConfigDAO paymentChannelItemConfigDAO;
	private PaymentChannelItemDefaultDAO paymentChannelItemDefaultDAO;
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	private ChannelConfigDAO channelConfigDAO;


	private AutoFitEngineService autoFitEngineService;


	public void setAutoFitEngineService(AutoFitEngineService autoFitEngineService) {
		this.autoFitEngineService = autoFitEngineService;
	}

	public void setPaymentChannelCategoryDAO(
			PaymentChannelCategoryDAO paymentChannelCategoryDAO) {
		this.paymentChannelCategoryDAO = paymentChannelCategoryDAO;
	}

	public void setPaymentChannelDAO(PaymentChannelDAO paymentChannelDAO) {
		this.paymentChannelDAO = paymentChannelDAO;
	}

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	public void setPaymentChannelItemDAO(
			PaymentChannelItemDAO paymentChannelItemDAO) {
		this.paymentChannelItemDAO = paymentChannelItemDAO;
	}

	public void setPaymentChannelItemConfigDAO(
			PaymentChannelItemConfigDAO paymentChannelItemConfigDAO) {
		this.paymentChannelItemConfigDAO = paymentChannelItemConfigDAO;
	}

	public void setPaymentChannelItemDefaultDAO(
			PaymentChannelItemDefaultDAO paymentChannelItemDefaultDAO) {
		this.paymentChannelItemDefaultDAO = paymentChannelItemDefaultDAO;
	}

	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	@Override
	public boolean checkPaymentChannelItemConfigExists(
			PaymentChannelItemConfig config) {
		List<PaymentChannelItemConfig> list = paymentChannelItemConfigDAO.findByCriteria("findByCriteria", config);
		return list!=null && !list.isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#checkCategoryExists(java
	 * .lang.String)
	 */
	@Override
	public boolean checkCategoryExists(String categoryCode) {

		PaymentChannelCategory paymentChannelCategory = paymentChannelCategoryDAO
				.queryByCode(categoryCode);
		return null != paymentChannelCategory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#queryPaymentChannelCategory
	 * ()
	 */
	@Override
	public List<PaymentChannelCategory> queryPaymentChannelCategory() {
		return paymentChannelCategoryDAO.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#addPaymentChannelCategory
	 * (com.pay.channel.model.PaymentChannelCategory)
	 */
	@Override
	public Long addPaymentChannelCategory(PaymentChannelCategory category) {
		return (Long) paymentChannelCategoryDAO.create(category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#updatePaymentChannelCategory
	 * (com.pay.channel.model.PaymentChannelCategory)
	 */
	@Override
	public boolean updatePaymentChannelCategory(PaymentChannelCategory category) {

		return paymentChannelCategoryDAO.update(category);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#delPaymentChannelCategory
	 * (com.pay.channel.model.PaymentChannelCategory)
	 */
	@Override
	public boolean delPaymentChannelCategory(Long id) {
		return paymentChannelCategoryDAO.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.channel.service.PaymentChannelService#queryPaymentChannel()
	 */
	public List<PaymentChannel> queryPaymentChannel(String channelCode,
			String channelName, String status,String id) {

		Map paraMap = new HashMap();
		if (!StringUtil.isEmpty(channelCode)) {
			paraMap.put("code", channelCode);
		}
		if (!StringUtil.isEmpty(channelName)) {
			paraMap.put("name", channelName);
		}
		if (!StringUtil.isEmpty(status)) {
			paraMap.put("status", status);
		}
		 /**解决修改渠道的bug**/
		if (!StringUtil.isEmpty(id)) {
			paraMap.put("id", id);
		}
		return paymentChannelDAO.findByCriteria(paraMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#addPaymentChannel(com.pay
	 * .channel.model.PaymentChannel)
	 */
	@Override
	public Long addPaymentChannel(PaymentChannel paymentChannel) {
		return (Long) paymentChannelDAO.create(paymentChannel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#updatePaymentChannel(com
	 * .pay.channel.model.PaymentChannel)
	 */
	@Override
	public boolean updatePaymentChannel(PaymentChannel paymentChannel) {
		return paymentChannelDAO.update(paymentChannel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#addPaymentChannelItem(com
	 * .pay.channel.model.PaymentChannelItem)
	 */
	@Override
	public Long addPaymentChannelItem(PaymentChannelItem paymentChannelItem) {
		return (Long) paymentChannelItemDAO.create(paymentChannelItem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#delPaymentChannelItem(java
	 * .lang.Long)
	 */
	@Override
	public boolean delPaymentChannelItem(Long paymentChannelItemId) {
		return paymentChannelItemDAO.delete(paymentChannelItemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.PaymentChannelService#updatePaymentChannelItem
	 * (com.pay.channel.model.PaymentChannelItem)
	 */
	@Override
	public boolean updatePaymentChannelItem(
			PaymentChannelItem paymentChannelItem) {
		return paymentChannelItemDAO.update(paymentChannelItem);
	}

	@Override
	public List<PaymentChannelItem> queryPaymentChannelItem(
			PaymentChannelItem paymentChannelItem) {
		return paymentChannelItemDAO.findByCriteria(paymentChannelItem);
	}

	@Override
	public Long addPaymentChannelItemConfig(
			PaymentChannelItemConfig paymentChannelItemConfig) {
		return (Long) paymentChannelItemConfigDAO
				.create(paymentChannelItemConfig);
	}

	@Override
	public void addPaymentChannelItemConfig(
			List<PaymentChannelItemConfig> paymentChannelItemConfigs,String auto) {
		if(paymentChannelItemConfigs != null && paymentChannelItemConfigs.size() >0 && "1".equals(auto)){
			for (PaymentChannelItemConfig paymentChannelItemConfig:paymentChannelItemConfigs
				 ) {
				ChannelObj obj = new ChannelObj();
				obj.setDealType("2");
				obj.setSubDealType("3");
				obj.setPartnerId(new BigDecimal(paymentChannelItemConfig.getMemberCode()));
				obj.setPaymentChannelItemId(new BigDecimal(paymentChannelItemConfig.getPaymentChannelItemId()));
				autoFitEngineService.product2Redis(obj);
			}
		}
		paymentChannelItemConfigDAO.batchCreate(paymentChannelItemConfigs);
	}

	@Override
	public boolean delPaymentChannelItemConfig(Long id) {
		return paymentChannelItemConfigDAO.delete(id);
	}

	@Override
	public void delPaymentChannelItemConfig(
			List<PaymentChannelItemConfig> paymentChannelItemConfigs) {
		paymentChannelItemConfigDAO.batchDelete(paymentChannelItemConfigs);
	}

	@Override
	public Long addPaymentChannelItemDefault(
			PaymentChannelItemDefault paymentChannelItemDefault) {
		return (Long) paymentChannelItemDefaultDAO
				.create(paymentChannelItemDefault);
	}

	@Override
	public void addPaymentChannelItemDefault(
			List<PaymentChannelItemDefault> paymentChannelItemDefaults) {
		paymentChannelItemDefaultDAO.batchCreate(paymentChannelItemDefaults);
	}

	@Override
	public boolean delPaymentChannelItemDefault(Long id) {
		return paymentChannelItemDefaultDAO.delete(id);
	}

	@Override
	public void delPaymentChannelItemDefault(
			List<PaymentChannelItemDefault> paymentChannelItemDefaults) {
		paymentChannelItemDefaultDAO.batchDelete(paymentChannelItemDefaults);
	}

	@Override
	public List<PaymentChannelItem> queryDefaultPaymentChannelItem(
			String paymentType, String memberType) {

		List<PaymentChannelItem> items = null;
		Map paraMap = new HashMap();
		if (!StringUtil.isEmpty(paymentType)) {
			paraMap.put("paymentType", paymentType);
		}
		if (!StringUtil.isEmpty(memberType)) {
			paraMap.put("memberType", memberType);
		}

		List<PaymentChannelItemDefault> defaults = paymentChannelItemDefaultDAO
				.findByCriteria(paraMap);
		if (null != defaults && !defaults.isEmpty()) {
			items = new ArrayList<PaymentChannelItem>();
			for (PaymentChannelItemDefault df : defaults) {
				PaymentChannelItem item = paymentChannelItemDAO.findById(df
						.getPaymentChannelItemId());
				if (null != item && item.getStatus() == 1) {
					item.setPaymentType(df.getPaymentType());
					item.setMemberType(df.getMemberType());
					items.add(item);
				}
			}
		}
		return items;
	}
	
	@Override
	public List<PaymentChannelItem> queryConfigPaymentChannelItem(
			String paymentType, String memberCode) {

		List<PaymentChannelItem> items = null;
		List<PaymentChannelItemConfig> configs = paymentChannelItemConfigDAO
				.queryPaymentChannelItemConfig(paymentType, memberCode);
		if (null != configs && !configs.isEmpty()) {
			List<PaymentChannelItemConfig> havePriorityConfigs = new ArrayList<PaymentChannelItemConfig>();
			List<PaymentChannelItemConfig> notHavePriorityConfigs = new ArrayList<PaymentChannelItemConfig>();
			for(PaymentChannelItemConfig pcic : configs){
				if(StringUtils.hasText(pcic.getChannelPriority())){
					havePriorityConfigs.add(pcic);
				}else{
					notHavePriorityConfigs.add(pcic);
				}
			}
			
			Collections.sort(havePriorityConfigs, new Comparator<PaymentChannelItemConfig>() {
				@Override
				public int compare(PaymentChannelItemConfig o1,
						PaymentChannelItemConfig o2) {
					return Integer.valueOf(o1.getChannelPriority())-Integer.valueOf(o2.getChannelPriority());
				}
			});
			havePriorityConfigs.addAll(notHavePriorityConfigs);
			items = new ArrayList<PaymentChannelItem>();
			for (PaymentChannelItemConfig df : havePriorityConfigs) {
				PaymentChannelItem item = paymentChannelItemDAO.findById(df
						.getPaymentChannelItemId());
				if (null != item && item.getStatus() == 1) {
					item.setMemberCode(df.getMemberCode());
					item.setPaymentType(df.getPaymentType());
					item.setPaymentChannelItemConfigId(df.getId()+"");
					item.setChannelPriority(df.getChannelPriority());
					items.add(item);
				}
			}
		}
		return items;
	}

	@Override
	public boolean delPaymentChannel(Long id) {
		return paymentChannelDAO.delete(id);
	}

	@Override
	public void addChannelSecondRelation(List<ChannelSecondRelation> list) {
		for (ChannelSecondRelation channelSecondRelation : list) {
			Long id = (Long)channelSecondRelationDAO.create(channelSecondRelation);
			ChannelObj obj = new ChannelObj();
			obj.setDealType("2");
			obj.setSubDealType("2");
			obj.setPaymentChannelItemId(new BigDecimal(channelSecondRelation.getPaymentChannelItemId()));
			obj.setPartnerId(new BigDecimal(channelSecondRelation.getMemberCode()));
			obj.setChannelConfigId(new BigDecimal(channelSecondRelation.getChannelConfigId()));
			obj.setChannelSecondRelationId(new BigDecimal(id));
			autoFitEngineService.product2Redis(obj);
		}
	}

	@Override
	public void delChannelSecondRelation(List<ChannelSecondRelation> list) {
		for (ChannelSecondRelation channelSecondRelation : list) {
			channelSecondRelationDAO.update("updateChannelSndrelationDateDel",channelSecondRelation);
		}
		channelSecondRelationDAO.batchDelete(list);
	}

	@Override
	public List<ChannelSecondRelation> queryChannelSecondRelation(
			String memberCode, String orgCode, String orgMerchantCode,
			String currencyCode) {
		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode);
		}
		if (!StringUtil.isEmpty(orgCode)) {
			paraMap.put("orgCode", orgCode);
		}
		if (!StringUtil.isEmpty(orgMerchantCode)) {
			paraMap.put("orgMerchantCode", orgMerchantCode);
		}
		if (!StringUtil.isEmpty(currencyCode)) {
			paraMap.put("currencyCode", currencyCode);
		}

		List<ChannelSecondRelation> list = channelSecondRelationDAO
				.findByCriteria(paraMap);

		return list;
	}

	@Override
	public PaymentChannelItem queryPaymentChannelItemByOrgCode(String orgCode) {

		List<PaymentChannelItem> paymentChannelItem = paymentChannelItemDAO
				.findByCriteria("findByPaymentChannelItemByOrgCode",
						orgCode);
		if(paymentChannelItem!=null&&paymentChannelItem.size()>0)
			return paymentChannelItem.get(0);
		
		return null;
	}

	@Override
	public PaymentChannelItem queryPaymentChannelItemByOrgCode(String orgCode,
			String orgMerchantCode) {

		PaymentChannelItem paymentChannelItem = paymentChannelItemDAO
				.findObjectByCriteria("findByPaymentChannelItemByOrgCode",
						orgCode);

		if (null != paymentChannelItem
				&& !paymentChannelItem.getOrgMerchantCode().equals(
						orgMerchantCode)) {
			//change below by Mack for ABC and CTV share the same merchant code
			/*ChannelConfig channelConfig = channelConfigDAO.findByMerchantCode(orgMerchantCode);*/
			ChannelConfig channelConfig = channelConfigDAO.findByMerchantCodeOrgCode(orgMerchantCode,orgCode);
			if (null != channelConfig) {
				BeanUtils.copyProperties(channelConfig, paymentChannelItem);
			}
		}
		return paymentChannelItem;
	}

	@Override
	public List<PaymentChannelItemConfig> queryPaymentChannelItemConf(
			String paymentType, String memberCode) {
		return paymentChannelItemConfigDAO.findPaymentChannelItemConfig(paymentType, memberCode);
	}

	@Override
	public List<PaymentChannelCategory> queryPaymentChannelCategory(
			Map requestMap) {
		return paymentChannelCategoryDAO.findByCriteria("queryPaymentChannelCategory",requestMap);
	}
	/**
	 * 获取渠道配置
	 * @author delin.dong
	 * 2016年5月7日 17:16:12
	 */
	@Override
	public List<PaymentChannelItem> queryConfigPaymentChannelItem(
			String paymentType, String memberCode, String tradeType) {
		String orgCode="";
		if(tradeType.equals("4001")){
			orgCode=ChannelItemOrgCodeEnum.CASHU.getCode();
		}else if(tradeType.equals("4002")){
			orgCode=ChannelItemOrgCodeEnum.BELTO.getCode();
			
		}else if(tradeType.equals("4003")){
			orgCode=ChannelItemOrgCodeEnum.HNAPAY.getCode();
			
		}else if(tradeType.equals("4004")){
			orgCode=ChannelItemOrgCodeEnum.HNAPAY.getCode();
			
		}else{
			return null;
		}
		List<PaymentChannelItem> items = paymentChannelItemDAO
				.queryPaymentChannelItemConfig(paymentType, memberCode,orgCode);
		return items;
	}
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 新增
	 */
	@Override
	public List<ChannelSndRelation> findSndrelationNew(Map param) {
		return channelSecondRelationDAO.findByCriteria("findSndrelationNew",param);
	}
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 删除
	 */
	@Override
	public List<ChannelSndRelation> findSndrelationDeleted(Map param) {
		return channelSecondRelationDAO.findByCriteria("findSndrelationDeleted",param);
	}
	/**
	 * 通道二级商户号添加查询可配置的二级商户号 关联
	 */
	@Override
	public List<ChannelSndRelation> findSndrelationConnected(Map param) {
		return channelSecondRelationDAO.findByCriteria("findSndrelationConnected",param);
	}
}
