package com.pay.channel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.channel.dao.ChannelMidAmountDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.handler.ChannelQueryHandler;
import com.pay.channel.model.ChannelMidAmount;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.service.ChannelMidAmountService;

/***
 * 
 * 根据卡组织，渠道码来选取当月交易金额最少的机构商户号
 * @author delin.dong
 *	@date 2016年05月18日
 */
public class ChannelMidAmountServiceImpl implements ChannelMidAmountService {
		
	public final Log logger = LogFactory.getLog(ChannelQueryHandler.class);
	
	private ChannelMidAmountDAO channelMidAmountDao;
	
	private ChannelSecondRelationDAO channelSecondRelationDAO;
	
	public void setChannelMidAmountDao(ChannelMidAmountDAO channelMidAmountDao) {
		this.channelMidAmountDao = channelMidAmountDao;
	}

	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	
	public ChannelSecondRelation getLeastOfMids(Long memberCode,
			List<ChannelSecondRelation> channelSecondRelations, String orgMerchantCode,
			String vom) {
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMM");
		String yearAndMonth=dayFormat.format(Calendar.getInstance().getTime());
		List<String> existsConfigMid=new ArrayList<String>();
		//先检查该商户所在渠道的mid有没有当月的统计数据，如果没有就新增
		for(ChannelSecondRelation csr:channelSecondRelations){
			ChannelMidAmount channelMidAmount = new ChannelMidAmount();
			channelMidAmount.setMid(csr.getOrgMerchantCode());
			channelMidAmount.setCurrMonth(yearAndMonth);
			channelMidAmount.setCardOrg(vom);
			existsConfigMid.add(csr.getOrgMerchantCode());
			List<ChannelMidAmount> list = channelMidAmountDao.queryChannelMidAmount(channelMidAmount);
			if(list==null || list.isEmpty()){
				ChannelMidAmount cma=new ChannelMidAmount();
				cma.setMid(csr.getOrgMerchantCode());
				cma.setCurrMonth(yearAndMonth);
				cma.setSumAmount("0");
				cma.setCardOrg(vom);
				if(StringUtils.isEmpty(vom)){
					cma.setCardOrg("OTH");
				}
				cma.setCreateDate(new Date());
				cma.setUpdateDate(cma.getCreateDate());
				channelMidAmountDao.create(cma);
			}
		}
		//再查出该商户所在渠道月交易金额最少的二级商户号
		ChannelMidAmount channelMidAmount=new ChannelMidAmount();
		channelMidAmount.setCardOrg(vom);
		channelMidAmount.setCurrMonth(yearAndMonth);
		List<ChannelMidAmount> channelMidAmounts=channelMidAmountDao.queryChannelMidAmount(channelMidAmount);
		Iterator<ChannelMidAmount> iterator = channelMidAmounts.iterator();
		while(iterator.hasNext()){
			ChannelMidAmount next = iterator.next();
			//没有配置的二级商户号也要过滤掉(之前用过后来不用的要排除掉)
			if(!existsConfigMid.contains(next.getMid())){
				iterator.remove();
			}
		}
		if(channelMidAmounts==null || channelMidAmounts.isEmpty()){
			return null;
		}
		ChannelMidAmount minMid = Collections.min(channelMidAmounts, new Comparator<ChannelMidAmount>() {
			@Override
			public int compare(ChannelMidAmount o1, ChannelMidAmount o2) {
				return new Long(Long.parseLong(o1.getSumAmount())-Long.parseLong(o2.getSumAmount())).intValue();
			}
		});
		
		logger.info("最小二级商户号- "+minMid.getMid());
		
		ChannelSecondRelation channelSecondRelation = new ChannelSecondRelation();
		channelSecondRelation.setOrgMerchantCode(minMid.getMid());
		channelSecondRelation.setMemberCode(memberCode);
		channelSecondRelation.setOrgCode(channelSecondRelations.get(0).getOrgCode());
		List<ChannelSecondRelation> data = channelSecondRelationDAO.findByChannelSecondRelation(channelSecondRelation);
		return data!=null && !data.isEmpty()?data.get(0):null;
	}

}
