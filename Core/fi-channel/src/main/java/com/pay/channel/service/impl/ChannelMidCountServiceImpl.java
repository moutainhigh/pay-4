package com.pay.channel.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.dao.ChannelConfigDAO;
import com.pay.channel.dao.ChannelMidCountDAO;
import com.pay.channel.dao.ChannelSecondRelationDAO;
import com.pay.channel.model.ChannelConfig;
import com.pay.channel.model.ChannelMidCount;
import com.pay.channel.model.ChannelSecondRelation;
import com.pay.channel.service.ChannelMidCountService;

/**
 * 根据商户号，渠道码来选取当月交易次数最少的机构商户号
 * @author Bobby Guo
 * @date 2015年10月12日
 */
public class ChannelMidCountServiceImpl implements ChannelMidCountService {
	
	public final Log logger = LogFactory.getLog(ChannelMidCountServiceImpl.class);

	private ChannelMidCountDAO channelMidCountDAO;

	private ChannelSecondRelationDAO channelSecondRelationDAO;
	
	public void setChannelSecondRelationDAO(
			ChannelSecondRelationDAO channelSecondRelationDAO) {
		this.channelSecondRelationDAO = channelSecondRelationDAO;
	}

	public void setChannelMidCountDAO(ChannelMidCountDAO channelMidCountDAO) {
		this.channelMidCountDAO = channelMidCountDAO;
	}

	
	@Override
	public ChannelSecondRelation getLeastOfMids(long memberCode,List<ChannelSecondRelation> channelSecondRelations,String defaultMid) {
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMM");
		String yearAndMonth=dayFormat.format(Calendar.getInstance().getTime());
		List<String> existsConfigMid=new ArrayList<String>();
		//先检查该商户所在渠道的mid有没有当月的统计数据，如果没有就新增
		for(ChannelSecondRelation csr:channelSecondRelations){
			ChannelMidCount channelMidCount = new ChannelMidCount();
			channelMidCount.setChannelCode(channelSecondRelations.get(0).getOrgCode());
			channelMidCount.setMemberCode(memberCode);
			channelMidCount.setCountDate(yearAndMonth);
			channelMidCount.setMid(csr.getOrgMerchantCode());
			existsConfigMid.add(csr.getOrgMerchantCode());
			List<ChannelMidCount> list = channelMidCountDAO.queryChannelMidCount(channelMidCount);
			if(list==null || list.isEmpty()){
				ChannelMidCount cmc = new ChannelMidCount();
				cmc.setMemberCode(memberCode);
				cmc.setChannelCode(csr.getOrgCode());
				cmc.setMid(csr.getOrgMerchantCode());
				cmc.setTxnCount(new Long(0));
				cmc.setCountDate(yearAndMonth);
				channelMidCountDAO.create(cmc);
			}
		}
		//再查出该商户所在渠道月交易笔数最少的二级商户号
		ChannelMidCount channelMidCount = new ChannelMidCount();
		channelMidCount.setChannelCode(channelSecondRelations.get(0).getOrgCode());
		channelMidCount.setMemberCode(memberCode);
		channelMidCount.setCountDate(yearAndMonth);
		List<ChannelMidCount> channelMidCountList = channelMidCountDAO.queryChannelMidCount(channelMidCount);
		Iterator<ChannelMidCount> iterator = channelMidCountList.iterator();
		while(iterator.hasNext()){
			ChannelMidCount next = iterator.next();
			//过滤掉通道里面的二级商户号
			/*if(next.getMid().equals(defaultMid)){
				iterator.remove();
				deleted = true;
			}*/
			//没有配置的二级商户号也要过滤掉
			if(!existsConfigMid.contains(next.getMid())){
				iterator.remove();
			}
		}
		if(channelMidCountList==null || channelMidCountList.isEmpty()){
			return null;
		}
		ChannelMidCount minMid = Collections.min(channelMidCountList, new Comparator<ChannelMidCount>() {
			@Override
			public int compare(ChannelMidCount o1, ChannelMidCount o2) {
				return new Long(o1.getTxnCount()-o2.getTxnCount()).intValue();
			}
		});
		ChannelSecondRelation channelSecondRelation = new ChannelSecondRelation();
		channelSecondRelation.setMemberCode(memberCode);
		channelSecondRelation.setOrgMerchantCode(minMid.getMid());
		channelSecondRelation.setOrgCode(channelSecondRelations.get(0).getOrgCode());
		List<ChannelSecondRelation> data = channelSecondRelationDAO.findByChannelSecondRelation(channelSecondRelation);
		return data!=null && !data.isEmpty()?data.get(0):null;
	}

	@Override
	public boolean updateChannelMidCount(ChannelMidCount channelMidCount) {
		logger.info("开始执行updateChannelMidCount:"+channelMidCount);
		List<ChannelMidCount> list = channelMidCountDAO.findByCriteria(channelMidCount);
		if(list != null && !list.isEmpty()){
			channelMidCount.setId(list.get(0).getId());
			channelMidCount.setTxnCount(list.get(0).getTxnCount()+1);
			return channelMidCountDAO.update(channelMidCount);
		}
		return false;
	}

	@Override
	public void initChannelMidCount(ChannelMidCount channelMidCount) {
		logger.info("开始执行initChannelMidCount:"+channelMidCount);
		List<ChannelMidCount> list = channelMidCountDAO.queryChannelMidCount(channelMidCount);
		if(list==null || list.isEmpty()){
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMM");
			String yearAndMonth=dayFormat.format(Calendar.getInstance().getTime());
			channelMidCount.setCountDate(yearAndMonth);
			channelMidCountDAO.create(channelMidCount);
		}
		
	}
}