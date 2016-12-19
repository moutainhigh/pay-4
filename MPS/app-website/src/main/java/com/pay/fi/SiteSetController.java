/**
 *Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
package com.pay.fi;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.commons.PartnerWebsiteConfigStatusEnum;
import com.pay.fi.dto.PartnerWebsiteConfig;
import com.pay.fi.hession.CrosspayWebsiteConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.jms.notification.request.MerchantWebsiteCheckRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.ExcelUtils;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
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
	private JmsSender jmsSender;

	/**
	 * 网站管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response, final PartnerWebsiteConfig websiteConfig)
			throws Exception {
		// 获取当前登录的商户编号
		String memberCode = SessionHelper.getLoginSession(request)
				.getMemberCode();

		logger.info("SiteSet====================================memberCode="
				+ memberCode);
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 20; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 20;// 此处为避免分页计算时出现被0除的情况
		}

		// 设置状态为正常
		// criteria.andStatusEqualTo("1");

		// 设置排序
		websiteConfig.setPartnerId(memberCode);
		//Page page = PageUtils.getPage(request);
		
		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		
		Map resultMap = crosspayWebsiteConfigService.merchantWebsiteQuery(websiteConfig, page);
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("result");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);

		List<PartnerWebsiteConfig> resultList = MapUtil.map2List(
				PartnerWebsiteConfig.class, returnMap);

		Map<String, Object> model = new HashMap<String, Object>();

		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
				page.getTotalRecord());// 分页处理
		model.put("pu", pu);
		model.put("result", resultList);
		model.put("status", websiteConfig.getStatusIn()==null?"" : websiteConfig.getStatusIn());
		model.put("startTime", websiteConfig.getStartTime()==null?"":websiteConfig.getStartTime());
		model.put("endTime", websiteConfig.getEndTime()==null?"":websiteConfig.getEndTime());
		model.put("url", websiteConfig.getUrl()==null?"":websiteConfig.getUrl());

		return new ModelAndView(index, model);
	}
	/**
	 * 域名上传模板下载
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downLoadFile(final HttpServletRequest request,
			final HttpServletResponse response, final PartnerWebsiteConfig websiteConfig)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		// MultiUploadForm multiUploadForm = (MultiUploadForm) form;
		String path = getServletContext().getRealPath(
				"/ftl/crosspay/siteset");

		String fileName = request.getParameter("fileName");
		FileInputStream fi = new FileInputStream(path + "//" + fileName);
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}
	/**
	 * 域名批量添加
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * modified by PengJiangbo 2016-05-10:->批量域名添加增加数据校验：已存在的正常域名不允许添加， 已存在但逻辑已删除的域名允许添加
	 * ->对不符合要求的域名自动过滤，不进行添加，符合要求添加
	 * ->批量添加不对域名格式进行校验
	 */
	public ModelAndView submitUploade(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String memberCode = (String) request.getSession().getAttribute("memberCode");
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter() ;
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
			//上传的文件
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file_siteuploade");
			Workbook book = Workbook.getWorkbook(file.getInputStream());
			@SuppressWarnings("unchecked")
			List<PartnerWebsiteConfig> list = ExcelUtils.getListByReadShell(book.getSheet(0), 1, 0, 1,new String[] {"url" },
					PartnerWebsiteConfig.class);
			List<PartnerWebsiteConfig> toAddList = new ArrayList<PartnerWebsiteConfig>() ;
			if(CollectionUtils.isNotEmpty(list)){
				
				for(PartnerWebsiteConfig websiteConfig : list){
					//查询
					websiteConfig.setStatusIn("'0','1','2','3','5','6'");	//已删除网站允许重复添加 
					websiteConfig.setPartnerId(memberCode);
					websiteConfig.setUrlQueryModel("equal");        //精确查询
					List<PartnerWebsiteConfig> resultList = crosspayWebsiteConfigService
							.merchantWebsiteQuery1(websiteConfig, null);
					//可以添加的域名,重构
					if(CollectionUtils.isEmpty(resultList)){
						websiteConfig.setIp(StringUtils.trim(getIp(request)));
						toAddList.add(websiteConfig) ;
					}
					//网址已存在，并且状态非逻辑已删除
					else{
						//按照需要文档，对不符合要求的文档只是进行自动过滤，不进行添加操作。不做任何错误提示处理
					}
				}
				if(CollectionUtils.isNotEmpty(toAddList)){
					boolean b = this.crosspayWebsiteConfigService.merchantWebsiteAddList(toAddList) ;
					if (b) {
						/****
						 * 网站域名批量添加的时候发送MQ进行批量的系统审核 delin.dong 2016年6月15日15:21:24
						 */
						try {
							for (PartnerWebsiteConfig partnerWebsiteConfig : toAddList) {
								partnerWebsiteConfig.setStatusIn("");//把status 设为空值，防止json转换的时候报错。delin 2016年7月27日20:14:22
							}
							String reqMsg = JSonUtil.toJSonString(toAddList); //转json发送保存的数据
							MerchantWebsiteCheckRequest notifyRequest=new  MerchantWebsiteCheckRequest();
							Map<String, String> notifyMap =new HashMap<String, String>();
							notifyMap.put("batch", "Y");
							notifyMap.put("list", reqMsg);
							notifyRequest.setData(notifyMap);
							notifyRequest.setMerchantCode(memberCode);
							jmsSender.send("notify.forpay",notifyRequest);
						} catch (Exception e) {
							e.printStackTrace();
						}
						out.println("<script>parent.callback('Y|添加网站成功')</script>");
					} else {
						out.println("<script>parent.callback('N|添加失败')</script>");
					}
				}else{
					out.println("<script>parent.callback('N|批量上传网址全部已存在！')</script>");//网址已经存在并且状态非已删除
				}
				
			}else{
				out.println("<script>parent.callback('N|网址不能为空')</script>");
			}
		} catch (Exception e) {
							e.printStackTrace();
			response.getWriter().println("<script>parent.callback('N|网站域名批量上传异常')</script>");
		}
		return null;
	}
	
	/**
	 * 单个域名添加
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 * modified by Jiangbo.Peng 2016.05.10, 域名单条添加逻辑修改，商户逻辑删除的域名可以允许重新再次上传
	 */
	public ModelAndView saveSite(final HttpServletRequest request,
			final HttpServletResponse response, final PartnerWebsiteConfig websiteConfig)
			throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String memberCode = SessionHelper.getLoginSession(request)
				.getMemberCode();
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(websiteConfig.getUrl())) {
			json.put("result", "error");
			json.put("message", "网址不能为空");
		} else {
			Page page = PageUtils.getPage(request);
			//modified by Jiangbo.Peng 
			//"0：冻结 1：正常 2：待审核 3：审核未通过 4：已删除"
			websiteConfig.setStatusIn("'0','1','2','3','5','6'");	//已删除网站允许重复添加 
			websiteConfig.setUrlQueryModel("equal");        //精确查询
			websiteConfig.setPartnerId(memberCode);
			List<PartnerWebsiteConfig> resultList = crosspayWebsiteConfigService
					.merchantWebsiteQuery1(websiteConfig, page);
			if (null != resultList && !resultList.isEmpty()) {
				json.put("result", "error");
				json.put("message", "网址已经存在!");//网址已经存在并且状态非已删除
			} else {
				websiteConfig.setPartnerId(memberCode);
				websiteConfig.setIp(StringUtils.trim(getIp(request)));
				boolean f = crosspayWebsiteConfigService
						.merchantWebsiteAdd(websiteConfig);
				if (f) {
					/****
					 * 网站域名添加的时候发送MQ进行批量的系统审核 delin.dong 2016年6月15日15:21:24
					 */
						try {
							MerchantWebsiteCheckRequest notifyRequest=new  MerchantWebsiteCheckRequest();
							Map<String, String> notifyMap =  MapUtil.bean2map(websiteConfig);
							notifyMap.put("batch", "N");
							notifyRequest.setData(notifyMap);
							notifyRequest.setMerchantCode(memberCode);
							jmsSender.send("notify.forpay",notifyRequest);
						} catch (Exception e) {
							e.printStackTrace();
						}
					json.put("result", "success");
					json.put("message", "添加网站成功");
				} else {
					json.put("result", "error");
					json.put("message", "添加失败");
				}
			}
		}
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}

	public ModelAndView deleteSite(final HttpServletRequest request,
			final HttpServletResponse response, final PartnerWebsiteConfig websiteConfig)
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
	public void setIndex(final String index) {
		this.index = index;
	}

	public void setRecordList(final String recordList) {
		this.recordList = recordList;
	}

	public void setCrosspayWebsiteConfigService(
			final CrosspayWebsiteConfigService crosspayWebsiteConfigService) {
		this.crosspayWebsiteConfigService = crosspayWebsiteConfigService;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

}
