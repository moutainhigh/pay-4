/** @Description 
 * @project 	fo-channel-web
 * @file 		AbstractChannelController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
 */
package com.pay.fo.controller.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.service.inf.input.BankInfoFacadeService;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see
 */
public abstract class AbstractChannelController extends AbstractBaseController {

	protected List<Map<String, String>> channelStatus = null;

	protected BankInfoFacadeService bankInfoFacadeService;

	public void setBankInfoFacadeService(final BankInfoFacadeService param) {
		this.bankInfoFacadeService = param;
	}

	protected void loadChannelStatus() {
		channelStatus = new ArrayList<Map<String, String>>();
		Map<String, String> mapStatus = new HashMap<String, String>();
		// mapStatus.put("text", "全部");
		// mapStatus.put("value", "");
		// channelStatus.add(mapStatus);
		// mapStatus = new HashMap<String, String>();
		mapStatus.put("text", "开启");
		mapStatus.put("value", "1");
		channelStatus.add(mapStatus);
		mapStatus = new HashMap<String, String>();
		mapStatus.put("text", "关闭");
		mapStatus.put("value", "0");
		channelStatus.add(mapStatus);
	}

}
