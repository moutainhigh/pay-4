package com.pay.app.controller.external;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.app.dto.ExternalRegisterDto;
import com.pay.app.dto.ExternalRegisterResultDto;
import com.pay.app.service.external.ExternalRegisterService;
import com.pay.util.BeanUtil;

public class ExternalRegisterController extends SimpleFormController{

	private ExternalRegisterService externalRegisterService;
	
	
	public void setExternalRegisterService(
			ExternalRegisterService externalRegisterService) {
		this.externalRegisterService = externalRegisterService;
	}

	public ExternalRegisterController(){
		setCommandClass(ExternalRegisterDto.class);
	}
	
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		return map;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		ExternalRegisterDto registerDto = (ExternalRegisterDto) command;
//		Map<String,Object> map = new HashMap<String, Object>();
		ExternalRegisterResultDto resultDto = externalRegisterService.processRegisterRdTx(registerDto);
		
		response.setContentType("text/plain;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = response.getWriter();
		String json = BeanUtil.bean2JSON(resultDto);
		logger.info(json);
		pw.write(json);
		pw.close();
		return null;
	}
	
}
