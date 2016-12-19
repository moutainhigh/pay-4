package com.pay.base.controller.matrixcard.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.filter.AppFilterCommon;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.util.WebUtil;

public class MatrixCardClientUtil {

	public static OperatorInfo getOperatorInfo(HttpServletRequest request) {
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setIp(WebUtil.getClientIP(request));
		operatorInfo.setSessionId(request.getSession().getId());
		operatorInfo.setSecurityLevel(0);
		if (AppFilterCommon.isSecurityLogin(request)) {
			LoginSession loginSession = null;
			try {
				loginSession = SessionHelper.getLoginSession(request);
				if(StringUtils.isNotBlank(loginSession.getMemberCode())){
					operatorInfo.setMemberCode(Long.parseLong(loginSession.getMemberCode()));
				}
				operatorInfo.setU_id(loginSession.getOperatorId());
				operatorInfo.setSecurityLevel(loginSession.getSecurityLvl());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return operatorInfo;
	}


}
