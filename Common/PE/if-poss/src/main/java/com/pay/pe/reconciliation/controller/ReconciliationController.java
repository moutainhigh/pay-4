package com.pay.pe.reconciliation.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.reconciliation.service.ReconciliationService;
import com.pay.util.MfDate;

/**
 * 
 * @ClassName: ReconciliationController
 * @Description: 预付卡对账
 * @author cf
 * @date Mar 28, 2012 2:18:21 PM
 * 
 */
public class ReconciliationController extends MultiActionController {
	private static final Log log = LogFactory
			.getLog(ReconciliationController.class);

	private String indexView;
	private ReconciliationService reconciliationService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("today", MfDate.today().toString());
		MfDate date = MfDate.today();
		date.minusDays(1);
		model.put("before", date.toString());
		return new ModelAndView(indexView, model);
	}

	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(MfDate.defaultDatePattern); 
			Date datebegin = formatter.parse(beginDate);
			Date dateend = formatter.parse(endDate);
			dateend = new Date(dateend.getTime() + 24*60*60*1000);
			SimpleDateFormat formatterFull = new SimpleDateFormat(MfDate.strPattern);  
			beginDate = formatterFull.format(datebegin);
			endDate = formatterFull.format(dateend);

			String FileName = null;//reconciliationService.getFileNameByDate(beginDate, endDate);
			if(null == FileName){
				 model.put("error",  "尊敬的用户，下载文件失败，请稍后再试.");
				 model.put("today", MfDate.today().toString());
					MfDate date = MfDate.today();
					date.minusDays(1);
					model.put("before", date.toString());
					return new ModelAndView(indexView, model);
			}
			
			
			String localFilePath = reconciliationService
					.generateLocalFilePath(FileName.trim());
			if(null == localFilePath){
				 model.put("error",  "尊敬的用户，下载文件失败，请稍后再试.");
				 model.put("today", MfDate.today().toString());
					MfDate date = MfDate.today();
					date.minusDays(1);
					model.put("before", date.toString());
					return new ModelAndView(indexView, model);
			}
			File f = null;
			if (null != localFilePath) {
				f = new java.io.File(localFilePath);			
			}
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ java.net.URLEncoder.encode("download.txt", "UTF8"));
			response.setContentLength((int) f.length()); // 设置下载内容大小
			if (f.exists() && f.canRead()) {
				byte[] buffer = new byte[4096]; // 缓冲区
				BufferedOutputStream output = null;
				BufferedInputStream input = null;
				try {
					output = new BufferedOutputStream(
							response.getOutputStream());
					input = new BufferedInputStream(new FileInputStream(f));
					int n = (-1);
					while ((n = input.read(buffer, 0, 4096)) > -1) {
						output.write(buffer, 0, n);
					}
					response.flushBuffer();
				} catch (Exception e) {
					
				}finally {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
				}
			}
		} catch (Exception e) {
			 model.put("error",  "尊敬的用户，下载文件失败，请稍后再试.");
			 model.put("today", MfDate.today().toString());
				MfDate date = MfDate.today();
				date.minusDays(1);
				model.put("before", date.toString());
				return new ModelAndView(indexView, model);
		}
		// return new ModelAndView(indexView, model);
		return null;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setReconciliationService(
			ReconciliationService reconciliationService) {
		this.reconciliationService = reconciliationService;
	}

	public String getIndexView() {
		return indexView;
	}
}
