/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.app.common.template;

import java.net.URLEncoder;
import java.util.List;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.inf.service.IMessageDigest;
import com.pay.inf.service.impl.MD5MessageDigestImple;
import com.pay.util.StringUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @author fjl
 * @date 2011-12-21
 */
public class OnlineServiceTemplate implements TemplateMethodModel {

	/** 企业会员基本信息服务 */
	private static EnterpriseBaseService enterpriseBaseService = ServiceLocator.getService(EnterpriseBaseService.class,"base-enterpriseBaseService");

	private static final String KEY_NAME = "live800.key";
	private static final String ALG = "MD5";

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		LoginSession loginSession = SessionHelper.getLoginSession();
		if (null == loginSession) {
			// 未登录
			return "";
		} else {
			// 已经登录
			String userId = loginSession.getMemberCode();
			String name = null;
			String memo = "";
			String infoValue = "";
			if (SessionHelper.isCorpLogin()) {
				EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.parseLong(userId));
				name = enterpriseBase.getEnName();
				if(StringUtil.isEmpty(name)){
					name = enterpriseBase.getZhName();
				}
			} else {
				name = loginSession.getVerifyName();
			}

			IMessageDigest iMessageDigest = new MD5MessageDigestImple();
			String key = MessageConvertFactory.getProperties(KEY_NAME);

			long timestamp = System.currentTimeMillis();// 时间戳;
			try {
				String hashCode = iMessageDigest.genMessageDigest(URLEncoder.encode(
						userId + name + memo + timestamp + key, "UTF-8").getBytes());
				
				infoValue = URLEncoder.encode("userId=" + userId + "&name=" + name
						+ "&memo=" + memo + "&hashCode=" + hashCode + "&timestamp="
						+ timestamp + "&alg=" + ALG, "UTF-8");
				return "&info=" + infoValue;
			} catch (Exception e) {	}
			return "";
		}
	}
}
