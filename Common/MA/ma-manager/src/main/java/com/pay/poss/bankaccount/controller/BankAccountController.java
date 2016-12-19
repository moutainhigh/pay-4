/**
 * 
 */
package com.pay.poss.bankaccount.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.ibm.wsdl.util.StringUtils;
import com.pay.acc.member.model.LiquidateInfo;
import com.pay.acc.member.service.LiquidateInfoService;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;


/**
 * poss提现账号审核
 *@author PengJiangbo
 *
 */
public class BankAccountController extends MultiActionController {

	private Log logger = LogFactory.getLog(BankAccountController.class) ;
	
	private String indexView ;
	private String listView ;
	private LiquidateInfoService liquidateInfoService ;
	private String downLoadPathPrefix ;
	//阿里云存储密钥
	private String ossKey;
	//阿里云存储子目录
	private String ossSubDir;
	//阿里云存储根目录
	private String ossRootDir;
	
	/**
	 * 默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response){
		ModelAndView view = new ModelAndView(indexView, DateUtil.getInitTime()) ;
		return view ;
	}
	/**
	 * 数据列表查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView search(final HttpServletRequest request, final HttpServletResponse response){
		if (logger.isInfoEnabled()) {
			logger.info("下载委托授权书前缀路径地址为：" + this.downLoadPathPrefix);
		}
		ModelAndView view = new ModelAndView(listView) ;
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String startDate = StringUtil.null2String(request.getParameter("startDate")) ;
		String endDate = StringUtil.null2String(request.getParameter("endDate")) ;
		String auditStatus = StringUtil.null2String(request.getParameter("auditStatus")) ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("startDate", startDate) ;
		hMap.put("endDate", endDate) ;
		hMap.put("auditStatus", auditStatus) ;
		Page<?> page = PageUtils.getPage(request) ;
		List<LiquidateInfo> liquidateInfos = this.liquidateInfoService.findByCriteria(hMap, page) ;
		
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken=null;
		OSSClient client=null;
		try {
			rawToken = oss.init(ossSubDir);
			client = oss.getOSSClient();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			logger.error("oss error:"+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyOSSException e) {
			logger.error("oss error:"+e.getMessage());
		}
		Date date=new Date(System.currentTimeMillis()+3600*1000*5);
		java.net.URL url=null;
		for(LiquidateInfo liquidateInfo : liquidateInfos){
			liquidateInfo.setBankAcct(DESUtil.decrypt(liquidateInfo.getBankAcct()));
			if(client!=null&&rawToken!=null&&!org.apache.commons.lang.StringUtils.isBlank(liquidateInfo.getDbRelativePath())){
				url=client.generatePresignedUrl(rawToken.getString("bucket"), ossRootDir+"/"+ossSubDir+"/"+liquidateInfo.getDbRelativePath(), date);
				liquidateInfo.setDbRelativePath(url.toString());
			}
		}
		return view.addObject("liquidateInfos", liquidateInfos).addObject("page", page)
				.addObject("downLoadPathPrefix", downLoadPathPrefix);
	}
	
	/**
	 * 批量操作
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView audit(final HttpServletRequest request, final HttpServletResponse response){
		JSONObject json = new JSONObject() ;
		try {
			String[] wkKeys = request.getParameterValues("wkKey") ;
			if(null == wkKeys){
				String wkKey = request.getParameter("wkkey") ;
				wkKeys= new String[1] ;
				wkKeys[0] = wkKey ;
			}
			int auditStatus = Integer.parseInt(StringUtil.null2String(request.getParameter("auditStatus"))) ;
			String auditRemark = StringUtil.null2String(request.getParameter("auditRemark")) ;
			Map<String ,Object> map = new HashMap<String, Object>() ;
			map.put("auditStatus", auditStatus) ;
			map.put("auditRemark", auditRemark) ;
			StringBuilder sb = new StringBuilder() ;
			for(String s : wkKeys){
				map.put("liquidateId", s) ;
				int updateResult = 0 ;
				try {
					updateResult = this.liquidateInfoService.updateLiquidateInfoStatus(map) ;
				} catch (Exception e) {
					logger.error("update LiquidateInfo error..., mybe this record have been updated, LiquidateInfoId is:" + s);
					e.printStackTrace();
				}
				if(updateResult != 1)
					sb.append(s).append(",") ;
			}
			String result = sb.toString() ;
			if(result.length() > 0)
				result = result.substring(0, sb.toString().length() - 1) ;
			if(null == result || "".equals(result))
				json.put("isSuccess", true) ;
			else
				json.put("isSuccess", false) ;
			
		} catch (Exception e) {
			if(logger.isErrorEnabled()){
				logger.error(e.getMessage(), e);
			}
			json.put("isSuccess", false) ;
			
		}finally{
			try {
				PrintWriter out = response.getWriter() ;
				out.println(json.toString());
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return null ;
	}
	//----------------------setter
	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}

	public void setListView(final String listView) {
		this.listView = listView;
	}
	public void setLiquidateInfoService(final LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}
	public void setDownLoadPathPrefix(String downLoadPathPrefix) {
		this.downLoadPathPrefix = downLoadPathPrefix;
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	public String getOssSubDir() {
		return ossSubDir;
	}
	public void setOssSubDir(String ossSubDir) {
		this.ossSubDir = ossSubDir;
	}
	public String getOssRootDir() {
		return ossRootDir;
	}
	public void setOssRootDir(String ossRootDir) {
		this.ossRootDir = ossRootDir;
	}
	
	
}
