package com.pay.poss.memberrelation.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.memberrelation.service.MemberRelationService;

public class RelationDataUploadController extends MultiActionController{
	
	private static final Log LOG = LogFactory.getLog(RelationDataUploadController.class);
	public static final String TEMP_VOUCH_DATA = "TEMP_VOUCH_DATA";
	
	private MemberRelationService memberRelationService;	


	private String mainUploadPage;
	
	private String afterUploadPage;
	
	private void removeTempVouchData(HttpServletRequest request) {
		request.getSession(true).removeAttribute(TEMP_VOUCH_DATA);
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
	


	public RelationDataUploadController() {}
	

	
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
	 * 下载数据模板
	 */
	public ModelAndView downloadTemplate(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xls");
		response.setHeader("Content-Disposition", "attachment; filename=\"template.xls\"");   
		
		ServletOutputStream out = null;
		try {
			byte[] value = memberRelationService.getVouchDataTemplate().getValue();
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
	
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}
	
	
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 * 确定上传的文件数据
//	 */
//	public ModelAndView confirmUploadVouchData(final HttpServletRequest request, final HttpServletResponse response) {
//		LOG.info("Start");
//		VouchDataDto vouchDataDto = getTempVouchData(request);
//		vouchDataDto = vouchService.createVouchData(vouchDataDto);
//		
//		//log
//		String ip = getRemoteIP(request);
//		//记录手工记账创建日志
//		//vouchLogService.logCreate(vouchDataDto, vouchDataDto.getCreator(), ip, "");
//		
//		LOG.info("End");
//		return new ModelAndView(afterUploadPage)
//			.addObject("vouchData", vouchDataDto)
//			.addObject("appContext", request.getContextPath());
//	}
	

}
