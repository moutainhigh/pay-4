package com.pay.fundout.channel.service.channel.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fundout.channel.dao.channel.ChannelDAO;
import com.pay.fundout.channel.dao.channel.impl.ChannelDAOImpl;
import com.pay.fundout.channel.model.channel.ChannelObject;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.fundout.channel.service.channel.ChannelService;
import com.pay.fundout.channel.service.channel.ValidateChannelRequest;
import com.pay.inf.dao.BaseDAO;

public class ChannelServiceImpl implements ChannelService {

	private ChannelDAO channelDao = new ChannelDAOImpl();

	private BaseDAO<BankChannelConfig> bankChannelConfigDao;

	public void setBankChannelConfigDao(
			BaseDAO<BankChannelConfig> bankChannelConfigDao) {
		this.bankChannelConfigDao = bankChannelConfigDao;
	}

	public void setChannelDao(ChannelDAO channelDao) {
		this.channelDao = channelDao;
	}

	@Override
	public ChannelObject getChannelObject(ChannelRequest request) {
		// 验证查询参数是否合规
		if (StringUtils.isBlank(ValidateChannelRequest.validate(request))) {
			// 得到不考虑amount值，也就是只是通过productCode、targetBank以及channelFlag（不是必须要输）三个参数查询的结果列表
			List<ChannelObject> channelList = channelDao
					.findBySelective(request);

			// 为不输入出款状态准备
			ChannelObject enterpriseChannel = null;
			ChannelObject manualChannel = null;

			if (channelList != null && channelList.size() > 0) {
				for (int i = 0; i < channelList.size(); i++) {
					// 1:手工,0:银企直连,空默认先看有没有银企直连，有选银企通道，没有走手工通道
					if (new Integer(1).equals(request.getChannelFlag())) {
						// 手工直接返回查询结果，结果只会是唯一的
						return channelList.get(0);
					} else if (new Integer(0).equals(request.getChannelFlag())) {
						ChannelObject object = channelList.get(0);
						if ((request.getAmount() == null)) {
							return object;
						} else {
							// 此处应该对金额进行判断
							BankChannelConfig bankChannelConfig = bankChannelConfigDao
									.findById(object.getChannelCode());
							boolean passFlag = true;
							if (bankChannelConfig.getLowerLimit() != null) {
								if (request.getAmount() < bankChannelConfig
										.getLowerLimit()) {
									passFlag = false;
								}
							}
							if (bankChannelConfig.getUpperLimit() != null) {
								if (request.getAmount() > bankChannelConfig
										.getUpperLimit()) {
									passFlag = false;
								}
							}
							if (passFlag == true) {
								return object;
							} else {
								return null;
							}
						}
					} else {
						ChannelObject channelObject = channelList.get(i);
						if (channelObject.getModeCode().equals("0")) {
							if (request.getAmount() == null) {
								enterpriseChannel = channelObject;
							} else {
								// 此处应该对金额进行判断
								BankChannelConfig bankChannelConfig = bankChannelConfigDao
										.findById(channelObject
												.getChannelCode());
								boolean passFlag = true;
								if (bankChannelConfig.getLowerLimit() != null) {
									if (request.getAmount() < bankChannelConfig
											.getLowerLimit()) {
										passFlag = false;
									}
								}
								if (bankChannelConfig.getUpperLimit() != null) {
									if (request.getAmount() > bankChannelConfig
											.getUpperLimit()) {
										passFlag = false;
									}
								}
								if (passFlag == true) {
									enterpriseChannel = channelObject;
								}
							}
						}
						if (channelObject.getModeCode().equals("1")) {
							manualChannel = channelObject;
						}
					}
				}
				if (enterpriseChannel != null) {
					return enterpriseChannel;
				} else if (manualChannel != null) {
					return manualChannel;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public String getFundoutChannel(ChannelRequest request) {
		ChannelObject channelObject = this.getChannelObject(request);
		if (channelObject != null) {
			return channelObject.getChannelCode();
		} else {
			return null;
		}
	}

	@Override
	public List<ChannelObject> getValidChannels(ChannelRequest request) {
		// 验证查询参数是否合规
		if (StringUtils.isBlank(ValidateChannelRequest.validate(request))) {
			// 得到不考虑amount值，也就是只是通过productCode、targetBank以及channelFlag（不是必须要输）三个参数查询的结果列表
			List<ChannelObject> channelList = channelDao
					.findBySelective(request);
			List<ChannelObject> resultList = new ArrayList<ChannelObject>();
			for (ChannelObject channelObject : channelList) {
				if (channelObject.getModeCode().equals("1")) {
					resultList.add(channelObject);
				} else if (channelObject.getModeCode().equals("0")) {
					if (request.getAmount() == null) {
						resultList.add(channelObject);
					} else {
						// 此处应该对金额进行判断
						BankChannelConfig bankChannelConfig = bankChannelConfigDao
								.findById(channelObject.getChannelCode());
						boolean passFlag = true;
						if (bankChannelConfig.getLowerLimit() != null) {
							if (request.getAmount() < bankChannelConfig
									.getLowerLimit()) {
								passFlag = false;
							}
						}
						if (bankChannelConfig.getUpperLimit() != null) {
							if (request.getAmount() > bankChannelConfig
									.getUpperLimit()) {
								passFlag = false;
							}
						}
						if (passFlag == true) {
							resultList.add(channelObject);
						}
					}
				}
			}
			return resultList;
		}
		return null;
	}

}
