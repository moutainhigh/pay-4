/**
 *  File: ElecBillsController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-5   Sany        Create
 *
 */
package com.pay.fo.elecbills.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fo.elecbills.ElecBillsService;
import com.pay.fo.elecbills.PositionDto;

/**
 * 电子回单
 */
public class ElecBillsController extends MultiActionController {
	
	protected transient Log log = LogFactory.getLog(getClass());

	private ElecBillsService elecBillsService;
	
	/** 下载电子凭证文件名 **/
	private String imgName;

	/**
	 * 获取电子回单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = elecBillsService.layoutBills(setPara(request));
		return new ModelAndView(map.get("pagePath").toString()).addObject(
				"viewData", map.get("viewData")).addObject("map",setPara(request)).addObject("url", request.getRequestURI());
	}

	/**
	 * 下载电子回单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downloadBills(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String fields = request.getParameter("fields");
			String positionX = request.getParameter("positionX");
			String positionY = request.getParameter("positionY");
			String field[] = fields.split(",");
			String x[] = positionX.split(",");
			String y[] = positionY.split(",");
			List<PositionDto> list = new ArrayList<PositionDto>();
			for (int i = 0; i < field.length; i++) {
				PositionDto position = new PositionDto();
				position.setFiledName(field[i]);
				position.setX(Integer.valueOf(x[i]));
				position.setY(Integer.valueOf(y[i]));
				list.add(position);
			}
			Map<String,Object> map = setPara(request);
			map.put("position", list);
			downloadImg(response, elecBillsService.generateBills(map));
		}catch (Exception e) {
			log.error("生成电子回单出错!",e);
		}
		return null;
	}
	
	/**
	 * 设置请求头/编码
	 * @param response
	 * @param image
	 */
	private void downloadImg(HttpServletResponse response,BufferedImage image) {
		try {
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("image/x-png");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(imgName + ".jpg", "UTF8"));
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "png", out);
			out.flush();
			out.close();
		}catch(Throwable e) {
			log.error("下载单子回单出错!",e);
		}
	}
	
	/**
	 * 组织入参
	 * @param request
	 * @param paraMap
	 */
	private Map<String,Object> setPara(HttpServletRequest request) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		String serialNo = request.getParameter("serialNo");
		String channel = request.getParameter("channel");
		String batchNo = request.getParameter("batchNo");
//		paraMap.put("request", request);
		if (StringUtils.isNotBlank(batchNo)) {
			paraMap.put("batchNo", batchNo);
		}
		if (StringUtils.isNotBlank(serialNo)) {
			paraMap.put("serialNo", serialNo);
		}
		if (StringUtils.isNotBlank(channel)) {
			paraMap.put("channel", channel);
		}
		return paraMap;
	}
	/**
	 * @param elecBillsService the elecBillsService to set
	 */
	public void setElecBillsService(ElecBillsService elecBillsService) {
		this.elecBillsService = elecBillsService;
	}
	
	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
