package com.pay.fo.controller.sharedata.blacklist;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.batchinfo.service.model.BlackListFile;
import com.pay.fundout.batchinfo.service.sharedata.ShareDataService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;

/**
 * 数据共享之黑名单文件控制器
 * @author limeng
 *
 */
public class BlackListFileController extends AbstractBaseController{
	
	/*
	 * 指定黑名单文件的生成路径，为/opt/pay/sharedata/blacklist/
	 */
	public static String BLACK_LIST_PATH = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("sharedata").append(File.separator).append("blacklist").append(File.separator).toString(); 
	
	private ShareDataService shareDataService;
	
	public void setShareDataService(ShareDataService shareDataService){
		this.shareDataService = shareDataService;
	}
	
	/**
	 * 初始化查询化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("initSearch");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 查询黑名单文件
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		Map params = new HashMap();
		String downloadStatus = request.getParameter("downloadStatus");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotBlank(downloadStatus)){
			params.put("downloadStatus", downloadStatus);
		}
		if(StringUtils.isNotBlank(startTime)){
			params.put("startTime", startTime);
		}
		if(StringUtils.isNotBlank(endTime)){
			params.put("endTime", endTime);
		}
		Page<BlackListFile> page = PageUtils.getPage(request);
		page = this.shareDataService.queryBlackListFile(page, params);
		String viewName = urlMap.get("searchResult");
		return new ModelAndView(viewName).addObject("page", page);
	}
	
	/**
	 * 下载黑名单文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downloadBlackListFile(HttpServletRequest request , HttpServletResponse response){
		long id = Long.valueOf(request.getParameter("id"));
		BlackListFile blackListFile = shareDataService.findBlackListFileById(id);
		InputStream inputStream = null;
		OutputStream os = null;
		try {
			inputStream = new FileInputStream(new File(BLACK_LIST_PATH.concat(blackListFile.getName())));

			response.reset();
			response.setContentType("text/xml;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment; filename="+ blackListFile.getName());
			response.setBufferSize(1024);

			os = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
				os.flush();
			}
			os.close();
			inputStream.close();
			
			// 标记为已下载
			blackListFile.setDownloadStatus("1");
			shareDataService.updateBlackListFile(blackListFile);
			return null;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			os = null;
			inputStream = null;
		}
	}
}
