package com.pay.poss.memberrelation.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.exception.RelationDataInvalidException;
import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;
import com.pay.poss.memberrelation.service.MemberRelationService;
import com.pay.poss.memberrelation.service.RelationDataLoader;
import com.pay.poss.memberrelation.service.RelationValidator;
import com.pay.poss.memberrelation.service.impl.RelationDataLoaderImpl;



/**   
* @Title: UploadMemberRelationTemplateDataController.java 
* @Package com.pay.poss.memberrelation.controller 
* @Description: 上传关联用户数据并保存关联关系
* @author cf
* @date 2011-9-22 下午03:33:16 
* @version V1.0   
*/
public class UploadMemberRelationTemplateDataController extends SimpleFormController{
	private static final Log LOG = LogFactory.getLog(UploadMemberRelationTemplateDataController.class);
	private List<RelationValidator> vouchValidators;
	private MemberRelationService memberRelationService;

	private String failureView;	
	public String getFailureView() {
		return failureView;
	}
	public void setFailureView(String failureView) {
		this.failureView = failureView;
	}
	
	public void setVouchValidators(List<RelationValidator> vouchValidators) {
		this.vouchValidators = vouchValidators;
	}
	
	/**
	 * 用户上传关联用户申请文件， 系统接受并处理。
	 */
	protected ModelAndView onSubmit(
	        HttpServletRequest request,
	        HttpServletResponse response,
	        Object command,
	        BindException errors) throws Exception {
			LOG.info("Start");
		
			super.onSubmit(request, response, command, errors);
			
			ModelAndView failureModelAndView = new ModelAndView(failureView)
				.addObject("appContext", request.getContextPath());
			
			ModelAndView successModelAndView = new ModelAndView(getSuccessView())
			.addObject("appContext", request.getContextPath());

			List<VouchRelationValidateMessage> messages = new ArrayList<VouchRelationValidateMessage>();
			
	        RelationDataFileUploadBean bean = (RelationDataFileUploadBean) command;
	        MultipartFile file = bean.getFile();
	        
	        //no file or file is empty
	        if (file == null || file.isEmpty()) {
	        	LOG.debug("No file or file is empty");
	        	messages.add(VouchRelationValidateMessage.createFileNotExcelMessage());
	        	return failureModelAndView.addObject("messages", messages);
	        }

	        //file is not excel
	        String contentType = file.getContentType();
	        //if (!file.getOriginalFilename().endsWith(".xls") 
	        //		|| (contentType != null && contentType.indexOf("excel") == -1)) {
	        if (!file.getOriginalFilename().endsWith(".xls")) {	
	        	LOG.debug("File is not excel");
	        	messages.add(VouchRelationValidateMessage.createFileNotExcelMessage());
	        	return failureModelAndView.addObject("messages", messages);
	        }
	        
	        List<RelationDataDto> vouchDataDto = null;
	        try {
	        	RelationDataLoader vouchDataLoader = getVouchDataLoader(file.getInputStream());
				vouchDataDto = vouchDataLoader.getData();
			} catch (RelationDataInvalidException e) {
				LOG.debug("Vouch data is not correct!", e);
				messages.addAll(e.getMessages());
				return failureModelAndView.addObject("messages", messages);
			} catch (RelationDataBuildException e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchRelationValidateMessage.createValidateMessage("文件内容有错！"));
				return failureModelAndView.addObject("messages", messages);
			} catch (Exception e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchRelationValidateMessage.createValidateMessage("文件内容有错！"));
				return failureModelAndView.addObject("messages", messages);
			}
//			saveTempVouchData(request, vouchDataDto);
			for(RelationDataDto dto:vouchDataDto){
				System.out.println(dto.getFatherMemberCode()+"  "+dto.getAmountEmail()+ "  "+dto.getSunMemberCode() + dto.isInsertFlag());
			}
			//新增关联关系
			memberRelationService.insertMemberRelation(vouchDataDto);
			
			LOG.info("End");
			messages.add(VouchRelationValidateMessage.createValidateMessage("保存成功！"));
			return successModelAndView.addObject("messages", messages);
	    }
	
//	private void saveTempVouchData(HttpServletRequest request, RelationDataDto vouchDataDto) {
//		request.getSession(true).setAttribute(TEMP_VOUCH_DATA, vouchDataDto);
//	}

	
	
	
	protected RelationDataLoader getVouchDataLoader(InputStream inputStream) {
		RelationDataLoaderImpl vouchDataLoader = RelationDataLoaderImpl.getInstance(inputStream, vouchValidators);
		return vouchDataLoader;
	}
	
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}
}
