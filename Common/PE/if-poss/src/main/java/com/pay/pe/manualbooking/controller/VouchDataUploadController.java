package com.pay.pe.manualbooking.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.service.VouchLogService;
import com.pay.pe.manualbooking.service.VouchService;

public class VouchDataUploadController extends MultiActionController{
	
	private static final Log LOG = LogFactory.getLog(VouchDataUploadController.class);
	public static final String TEMP_VOUCH_DATA = "TEMP_VOUCH_DATA";
	
	private VouchService vouchService;
	
	private VouchLogService vouchLogService;
	
	private String mainUploadPage;
	
	private String afterUploadPage;
	
	private void removeTempVouchData(HttpServletRequest request) {
		request.getSession(true).removeAttribute(TEMP_VOUCH_DATA);
	}
	
	private VouchDataDto getTempVouchData(HttpServletRequest request) {
		return (VouchDataDto) request.getSession(true).getAttribute(TEMP_VOUCH_DATA);
	}
	
	public String getMainUploadPage() {
		return mainUploadPage;
	}

	public void setMainUploadPage(String mainUploadPage) {
		this.mainUploadPage = mainUploadPage;
	}

	public String getAfterUploadPage() {
		return afterUploadPage;
	}

	public void setAfterUploadPage(String afterUploadPage) {
		this.afterUploadPage = afterUploadPage;
	}
	
	public VouchService getVouchService() {
		return vouchService;
	}

	public void setVouchService(VouchService vouchService) {
		this.vouchService = vouchService;
	}
	
	public VouchLogService getVouchLogService() {
		return vouchLogService;
	}

	public void setVouchLogService(VouchLogService vouchLogService) {
		this.vouchLogService = vouchLogService;
	}

	public VouchDataUploadController() {}
	
	public VouchDataUploadController(VouchService vouchService) {
		this.vouchService = vouchService;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * 访问上传主页面
	 */
	public ModelAndView init(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		removeTempVouchData(request);
		
		LOG.info("End");
		return new ModelAndView(mainUploadPage)
			.addObject("appContext", request.getContextPath());
	}
	
	private String getRemoteIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 确定上传的文件数据
	 * 
	 */
	public ModelAndView confirmUploadVouchData(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		VouchDataDto vouchDataDto = getTempVouchData(request);
		vouchDataDto = vouchService.createVouchData(vouchDataDto);
		
		LOG.info(vouchDataDto.toString());
		//log
		String ip = getRemoteIP(request);
		//记录手工记账创建日志
		//vouchLogService.logCreate(vouchDataDto, vouchDataDto.getCreator(), ip, "");
		
		LOG.info("End");
		return new ModelAndView(afterUploadPage)
			.addObject("vouchData", vouchDataDto)
			.addObject("appContext", request.getContextPath());
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 下载数据模板
	 */
	public ModelAndView downloadTemplate(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xls");
		response.setHeader("Content-Disposition", "attachment; filename=\"template.xls\"");   
		
		ServletOutputStream out = null;
		try {
			byte[] value = vouchService.getVouchDataTemplate().getValue();
			out = response.getOutputStream();
			out.write(value, 0, value.length);
			out.flush();
		} catch (IOException e) {
			LOG.debug("Fails to get vouch template!", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LOG.debug("Fails to close output stream!", e);
				}
			}
		}
		LOG.info("End");
		return null;
	}

}
