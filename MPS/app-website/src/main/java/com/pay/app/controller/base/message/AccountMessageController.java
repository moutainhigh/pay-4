/**
 *  File: UpdateAccountInfo.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-11-18   single_zhang     Create
 *
 */
package com.pay.app.controller.base.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.MessageReceiveDTO;
import com.pay.app.service.messagebox.MessageReceiveService;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.app.filter.AppFilterCommon;

public class AccountMessageController  extends MultiActionController {
	
	 /**站内信消息*/
	private MessageReceiveService messageReceiveService;
	
	private String messageView;
	
	private String unmaxtrixView;
	
	public ModelAndView message(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(AppFilterCommon.isNormalUser(request)){
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			if(loginSession.getSecurityLvl()==SecurityLvlEnum.UNMAXTRIX.getValue()){
				return new ModelAndView(unmaxtrixView);
			}
			String memberCode = null == loginSession.getMemberCode()
			? ""
			: loginSession.getMemberCode();
	
			//查询站内信消息
			Map<String, Object> paraMap = new HashMap<String, Object>(12);
			List<MessageReceiveDTO> listmsg = messageReceiveService.queryTopFMessage(Long.valueOf(memberCode));
			paraMap.put("listmsg",listmsg);  //总联系人数
			return new ModelAndView(messageView,paraMap);
		}
		return new ModelAndView(messageView).addObject("timeOut","true");
	
	}

	public void setMessageReceiveService(MessageReceiveService messageReceiveService) {
		this.messageReceiveService = messageReceiveService;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}

	public void setUnmaxtrixView(String unmaxtrixView) {
		this.unmaxtrixView = unmaxtrixView;
	}
}
