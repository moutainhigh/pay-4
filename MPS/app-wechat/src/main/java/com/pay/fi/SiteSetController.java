/**
 *Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
package com.pay.fi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.commons.PartnerWebsiteConfigStatusEnum;
import com.pay.fi.dto.PartnerWebsiteConfig;
import com.pay.fi.hession.CrosspayWebsiteConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.util.SpringControllerUtils;

/**
 * @author xfliang
 * @date 2014-8-26
 */
public class SiteSetController extends MultiActionController {

	private final Log logger = LogFactory.getLog(SiteSetController.class);
	private String index;
	private String recordList;
	private CrosspayWebsiteConfigService crosspayWebsiteConfigService;

	/**
	 * 网站管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView index(HttpServletRequest request,
//			HttpServletResponse response, PartnerWebsiteConfig websiteConfig)
//			throws Exception {
//
//		// 获取当前登录的商户编号
//		String memberCode = SessionHelper.getLoginSession(request)
//				.getMemberCode();
//
//		logger.info("SiteSet====================================memberCode="
//				+ memberCode);
//		String siteId = "";
//		String status = "";
//
//		// 设置状态为正常
//		// criteria.andStatusEqualTo("1");
//
//		// 设置排序
//		websiteConfig.setPartnerId(memberCode);
//		Page page = PageUtils.getPage(request);
//		List<PartnerWebsiteConfig> resultList = crosspayWebsiteConfigService
//				.merchantWebsiteQuery(websiteConfig, page);
//
//		Map<String, Object> model = new HashMap<String, Object>();
//
//		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
//				page.getTotalRecord());// 分页处理
//		model.put("pu", pu);
//		model.put("result", resultList);
//
//		return new ModelAndView(index, model);
//	}
	
	/**
	 * 添加网站
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
//	public ModelAndView saveSite(HttpServletRequest request,
//			HttpServletResponse response, PartnerWebsiteConfig websiteConfig)
//			throws Exception {
//
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//
//		String memberCode = SessionHelper.getLoginSession(request)
//				.getMemberCode();
//		JSONObject json = new JSONObject();
//		if (StringUtils.isEmpty(websiteConfig.getUrl())) {
//			json.put("result", "error");
//			json.put("message", "网址不能为空");
//		} else {
//			Page page = PageUtils.getPage(request);
//			websiteConfig.setPartnerId(memberCode);
//			List<PartnerWebsiteConfig> resultList = crosspayWebsiteConfigService
//					.merchantWebsiteQuery(websiteConfig, page);
//			if (null != resultList && !resultList.isEmpty()) {
//				json.put("result", "error");
//				json.put("message", "网址不合法或者已经存在");
//			} else {
//				websiteConfig.setPartnerId(memberCode);
//				websiteConfig.setIp(StringUtils.trim(getIp(request)));
//				boolean f = crosspayWebsiteConfigService
//						.merchantWebsiteAdd(websiteConfig);
//				if (f) {
//					json.put("result", "success");
//					json.put("message", "添加网站成功");
//				} else {
//					json.put("result", "error");
//					json.put("message", "添加失败");
//				}
//			}
//		}
//		SpringControllerUtils.renderText(response, json.toString());
//		return null;
//	}

	public ModelAndView deleteSite(HttpServletRequest request,
			HttpServletResponse response, PartnerWebsiteConfig websiteConfig)
			throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String memberCode = SessionHelper.getLoginSession(request)
				.getMemberCode();
		JSONObject json = new JSONObject();
		if (websiteConfig.getId() == null) {
			json.put("result", "error");
			json.put("message", "删除的编号不能为空!");
		} else {

			websiteConfig.setPartnerId(memberCode);
			websiteConfig.setStatus(PartnerWebsiteConfigStatusEnum.DELETE
					.getCode());

			boolean isDel = crosspayWebsiteConfigService
					.merchantWebsiteUpdate(websiteConfig);
			if (isDel) {
				json.put("result", "success");
				json.put("message", "删除网站成功");
			} else {
				json.put("result", "error");
				json.put("message", "删除网站失败,请您联系管理员!");
			}
		}
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}

	public static String getIp(final HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理
		if (null != ip && !"".equals(ip.trim())) {
			StringTokenizer st = new StringTokenizer(ip, ",");
			if (st.countTokens() > 1) {
				return st.nextToken();
			}
		}
		return ip;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setCrosspayWebsiteConfigService(
			CrosspayWebsiteConfigService crosspayWebsiteConfigService) {
		this.crosspayWebsiteConfigService = crosspayWebsiteConfigService;
	}

}
