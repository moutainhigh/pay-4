/**
 *  File: WithholdingContorller.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-10-17   Sany        Create
 *
 */
package com.pay.fo.controller.fundout.withholding;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;

/**
 * 利安代扣
 */
public class WithholdingContorller extends AbstractBaseController {
	
	//private WithholdingQueryService withholdingQueryService;
	
	/**
	 * @param withholdingQueryService the withholdingQueryService to set
	 */
//	public void setWithholdingQueryService(
//			WithholdingQueryService withholdingQueryService) {
//		this.withholdingQueryService = withholdingQueryService;
//	}
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("withholdingInit"));
	}
	
	/**
	 * 查询文件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryWithholdingSummary(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String batchNo = request.getParameter("batchNo");
		String status = request.getParameter("status");
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
		}
		if (StringUtils.isNotBlank(batchNo)) {
			map.put("batchNo", batchNo);
		}
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
		}
//		Page<WithholdingFileInfoDto> page = PageUtils.getPage(request);
//		page = withholdingQueryService.withholdingSummaryQuery(map, page.getPageNo(), 20);
		return new ModelAndView(URL("withholdingList"))
		//.addObject("page", page)
		;
	}
	
	/**
	 * 查询代扣详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryWithholdingDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fileKy = request.getParameter("fileKy");
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(fileKy)) {
			map.put("fileKy", fileKy);
		}
//		Page<WithholdingFileDetailDto> page = PageUtils.getPage(request);
//		page = withholdingQueryService.withholdingDetailQuery(map, page.getPageNo(), 20);
		return new ModelAndView(URL("withholdingDetailList"))
//		.addObject("page", page)
		.addObject("fileKy",fileKy);
	}
	
	/**
	 * 下载原始文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long fileKy = Long.valueOf(request.getParameter("fileKy"));

//		WithholdingFileInfoDto fileInfo = withholdingQueryService
//				.withholdingSummaryQuery(fileKy);

		InputStream inputStream = null;
		OutputStream os = null;
		try {
//			inputStream = new FileInputStream(new File(fileInfo.getFileName()));

			response.reset();
			response.setContentType("text/html;charset=UTF-8");
//			response.setHeader("Content-disposition", "attachment; filename="
//					+ fileInfo.getFileNameDesc());
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
			return null;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			os = null;
			inputStream = null;
		}
	}
	
}
