/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.channel.handler;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.channel.dao.MemberCurrentConnectDAO;
import com.pay.channel.dto.MemberCurrentConnectDTO;
import com.pay.channel.model.MemberCurrentConnect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.ChannelConfig;
//import com.pay.channel.model.ChannelMidCount;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.service.ChannelMidAmountService;
import com.pay.channel.service.PaymentChannelService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 获取可用渠道
 * 
 * @author chma
 */
public class ChannelQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ChannelQueryHandler.class);
//	private ChannelMidCountService channelMidCountService; 
	private PaymentChannelService paymentChannelService;
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	private ChannelConfigDAO channelConfigDAO;
	private ChannelMidAmountService channelMidAmountService;
	private EnterpriseBaseService enterpriseBaseService;
	private MemberCurrentConnectDAO memberCurrentConnectDAO;

	public void setMemberCurrentConnectDAO(MemberCurrentConnectDAO memberCurrentConnectDAO) {
		this.memberCurrentConnectDAO = memberCurrentConnectDAO;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	/*public void setChannelMidCountService(
			ChannelMidCountService channelMidCountService) {
		this.channelMidCountService = channelMidCountService;
	}*/

	public void setChannelMidAmountService(
			ChannelMidAmountService channelMidAmountService) {
		this.channelMidAmountService = channelMidAmountService;
	}

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
		this.channelConfigDAO = channelConfigDAO;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		logger.info("dataMsg : "+dataMsg);
		Map resultMap = new HashMap();
		Map<String, String> paraMap = null;

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());
		} catch (Exception e) {
			logger.error("ChannelQueryHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}

		String paymentType = paraMap.get("paymentType");
		String memberType = paraMap.get("memberType");
		String memberCode = paraMap.get("memberCode");
		String transType = paraMap.get("transType");
		String mcc = paraMap.get("mcc");
		String vom = paraMap.get("vom");
		String currencyCode = paraMap.get("currencyCode");
		String prdtCode = paraMap.get("prdtCode");
		String siteId = paraMap.get("siteId");
		String paymentChannelCode = paraMap.get("paymentChannelCode");//添加“渠道编号”查询条件 add by davis.guo 2016-08-22
		//add by zhaoyang at 20161017
		String labelClass = paraMap.get("labelClass");
		if(isDisguisedDCC(prdtCode))transType = "EDC";
		List<PaymentChannelItem> itemList = new ArrayList<PaymentChannelItem>();
		boolean gcMember = false;
		if(StringUtil.isNumber(memberCode)){
			EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.parseLong(memberCode));
			String merchantCode = String.valueOf(enterpriseBaseDto.getMerchantCode());
			gcMember = merchantCode.startsWith("800");
		}

		// 获取商户配置了哪些渠道
		List<PaymentChannelItem> configItems = paymentChannelService
				.queryConfigPaymentChannelItem(paymentType, memberCode);
		
		//1.根据卡组织筛选
		if(!StringUtil.isEmpty(vom)){
			Iterator<PaymentChannelItem> list= configItems.iterator();
			while(list.hasNext()){
				PaymentChannelItem paymentChannelItem = list.next();
				if(!paymentChannelItem.getPaymentCategoryCode().equalsIgnoreCase(vom)){
					list.remove();
				}
			}
		}
		if (null != configItems && !configItems.isEmpty()) {
			for (PaymentChannelItem item : configItems) {
				if(!StringUtil.isEmpty(paymentChannelCode)){
					if(!paymentChannelCode.equalsIgnoreCase(item.getPaymentChannelCode())){
						continue;//根据“渠道名称”查询条件进行过滤 add by davis.guo 2016-08-22
					}
				}
				if(!StringUtil.isEmpty(labelClass)){
					if(!labelClass.equalsIgnoreCase(item.getLabelClass())){
						continue;//根据“渠道名称”查询条件进行过滤 add by davis.guo 2016-08-22
					}
				}
				String pattern = item.getPattern();
				item.setType("2");
				//logger.info("PaymentChannelItem item:"+item);
				//如果渠道是Credorax根据交易网址来查找对应的二级商户号
				if (ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(item.getOrgCode())) {
					if(!StringUtil.isEmpty(siteId))siteId=siteId.replaceFirst("https://", "").replaceFirst("http://", "").replaceFirst("www.", "");
					ChannelConfig channelConfig = channelConfigDAO
							.findByMerchantCode(item.getOrgCode(),siteId);
					if (null != channelConfig) {
						BeanUtils.copyProperties(channelConfig, item,new String[]{"id"});
						item.setChannelConfigId(String.valueOf(channelConfig.getId()));
						if(StringUtil.isEmpty(item.getPattern()))item.setPattern(pattern);
						item.setGetGcCurrentFlag("1");
						itemList.add(item);
					}
				} else {
					if(gcMember){
						MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
						searchDto.setPartnerId(new BigDecimal(memberCode));
						searchDto.setPaymentChannelItemId(new BigDecimal(item.getId()));
						List<MemberCurrentConnectDTO> memberCurrentConnects = memberCurrentConnectDAO.findPageResult(searchDto);
						if(null != memberCurrentConnects && memberCurrentConnects.size() >0){
							MemberCurrentConnectDTO dto = memberCurrentConnects.get(0);
							logger.info("dto:" + String.valueOf(dto.getId().longValue()));
							PaymentChannelItem item2 = new PaymentChannelItem();
							BeanUtils.copyProperties(item, item2);
							if(null != dto.getChannelConfigId()){
								ChannelConfig channelConfig = channelConfigDAO.findById(dto.getChannelConfigId().longValue());
								item2.setChannelConfigId(String.valueOf(channelConfig.getId()));
								BeanUtils.copyProperties(channelConfig, item2,new String[]{"id"});
							}
							item2.setMemberCurrentConnectId(String.valueOf(dto.getId().longValue()));
							item2.setGetGcCurrentFlag("1");
							itemList.add(item2);

						}else{
							PaymentChannelItem item2 = new PaymentChannelItem();
							BeanUtils.copyProperties(item, item2);
							itemList.add(item2);
						}
					}else {
						//非Credorax渠道先根据机构码，商户号，交易类型和币种来查询通道二级商户号
						//然后再根据二级商户号找到对应的渠道二级商户号信息
						List<ChannelSecondRelation> relations = channelSecondRelationDAO
								.findRelation(memberCode, item.getOrgCode(),
										transType, currencyCode, mcc, item.getId());

						if (null != relations && !relations.isEmpty()) {
							// add by Delin for 按量负载均衡 2016年5月19日16:30:01
							//logger.info("relations : "+relations +"relations size"+relations.size()+"vom"+vom); //add logs ddl
							ChannelSecondRelation channelSecondRelation = null;
							try {
								channelSecondRelation = channelMidAmountService.getLeastOfMids(Long.valueOf(memberCode)
										, relations, item.getOrgMerchantCode(), vom);
							} catch (Exception e) {
								e.printStackTrace();
							}
							/*ChannelSecondRelation channelSecondRelation = channelMidCountService.getLeastOfMids(Long.valueOf(memberCode)
									,relations,item.getOrgMerchantCode());*/
							logger.info("channelMidCountService getLeastOfMids : " + channelSecondRelation);
							if (null != channelSecondRelation) {
								//change below line to find channel by merchantcode and org code by Mack 2016年6月18日
								ChannelConfig channelConfig = channelConfigDAO.findByMerchantCodeOrgCode(channelSecondRelation.getOrgMerchantCode(), channelSecondRelation.getOrgCode());
								if (channelConfig != null) {
									PaymentChannelItem item2 = new PaymentChannelItem();
									BeanUtils.copyProperties(item, item2);
									BeanUtils.copyProperties(channelConfig, item2,new String[]{"id"});
									item2.setChannelConfigId(String.valueOf(channelConfig.getId()));
									item2.setMerchantBillName(channelSecondRelation.getMerchantBillName());
									if (StringUtil.isEmpty(item2.getPattern())) item2.setPattern(pattern);
									itemList.add(item2);
								}
							} else {
								if (StringUtil.isEmpty(item.getPattern())) item.setPattern(pattern);
								item.setTransType(transType);
								itemList.add(item);
							}

						} else {
							//创建PaymentChannelItem中二级商户号的当月统计记录
							// comment by Deliin for 按二级商户号金额负载均衡
							/*if(StringUtils.hasText(memberCode)){
								channelMidCountService.initChannelMidCount(new ChannelMidCount(
									Long.valueOf(memberCode), item.getOrgCode(), item.getOrgMerchantCode(),(long)0));
							}
							*/
							if (StringUtil.isEmpty(item.getPattern())) item.setPattern(pattern);
							item.setTransType(transType);
							itemList.add(item);
						}
					}
				}

			}
		}

		// 获取默认渠道
		List<PaymentChannelItem> defaultItems = paymentChannelService
				.queryDefaultPaymentChannelItem(paymentType, memberType);
		if (null != defaultItems && !defaultItems.isEmpty()) {
			for (PaymentChannelItem item : defaultItems) {
				if (!itemList.contains(item)) {
					item.setType("1");
					itemList.add(item);
				}
			}
		}
		//这里得到的交易渠道有多个，要根据优先级做个排序，然后返回个LinkedList
		//TO DO
		//
		resultMap.put("paymentChannelItems", itemList);
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		logger.info("end ##############@@@@@@@@@@@@@@@@@@"+new Date());
		return JSonUtil.toJSonString(resultMap);
	}
	
	private boolean isDisguisedDCC(String prdtCode){
		if(StringUtil.isEmpty(prdtCode)){
			return false;
		}else if(DCCEnum.CUSTOM_STANDARD.getCode().equals(prdtCode)||
				 DCCEnum.CUSTOM_FORCED.getCode().equals(prdtCode)||
				 DCCEnum.CUSTOM_HIDDEN.getCode().equals(prdtCode)||
				 DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(prdtCode)){
			return true;
		}else{
			return false;
		}
	}
}
