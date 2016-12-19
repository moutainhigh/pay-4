/**
 * 
 */
package com.pay.poss.merchantmanager.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.comm.SearchKeyUtils;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.merchantmanager.dto.SignMessageDto;
import com.pay.poss.merchantmanager.service.ISignMessageService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.util.SpringControllerUtils;

/**
 * @Description 频道(部门)配置
 * @project 	ma-manager
 * @file 		SignMessageController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */
public class SignMessageController extends MultiActionController {
	private Log log = LogFactory.getLog(SignMessageController.class);
	private String signMessageCreateView;
	private String signMessageSearchView; 
	private String signMessageSearchList;
	private String signMessageUpdateView;

	private ISignMessageService iSignMessageService;
	
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return null;
    }
	//频道新增页面
	public ModelAndView createView(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		return new ModelAndView(signMessageCreateView);
	}
	//频道新增保存
	public ModelAndView createViewSave(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		//验证频道名称唯一
		Integer count = iSignMessageService.countSignMessageByCondition(dto); 
		if(count>0){
			String message = "1";
			return new ModelAndView(signMessageCreateView)
			.addObject("message",message)
			.addObject("dto",dto);
		}else{
			SessionUserHolder user = SessionUserUtils.getUserInfo(request);
			Boolean success = false;
			String searchKey = this.getSearchKey(dto.getDepartmentName());
			dto.setSearchKey(searchKey);
			dto.setCreator(user.getUsername());
			dto.setModifier(user.getUsername());
			iSignMessageService.createSignMessage(dto);
			success = true;
			return new ModelAndView(signMessageCreateView)
						.addObject("success",success)
						//.addObject("dto",dto)
						;
		}	
	}
	
	//频道搜索页面
	public ModelAndView searchView(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		
		return new ModelAndView(signMessageSearchView);
	}
	//频道搜索List展示页面
	@SuppressWarnings("unchecked")
	public ModelAndView searchList(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		Page  page = PageUtils.getPage(request);
		List<SignMessageDto> info = iSignMessageService.querySignMessageList(dto,page);
		page.setResult(info);
		return new ModelAndView(signMessageSearchList)
					.addObject("page", page);
	}
	
	//频道修改页面
	public ModelAndView updateView(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		dto = iSignMessageService.querySignMessageById(dto);
		return new ModelAndView(signMessageUpdateView)
				.addObject("dto",dto);
	}
	//频道修改保存
	public ModelAndView updateSave(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		//先查询自己  --由id和name查询
		List<SignMessageDto> checDtoList = iSignMessageService.validateDepName(dto);
		//没有修改名称修改其他字段点击保存
		if(checDtoList!=null && checDtoList.size()>0){
			SessionUserHolder user = SessionUserUtils.getUserInfo(request);
			dto.setModifier(user.getUsername());
			iSignMessageService.updateSignMessage(dto);
			SignMessageDto resultDto = iSignMessageService.querySignMessageById(dto);
			return new ModelAndView(signMessageUpdateView)
					.addObject("dto",resultDto);
		}else{
			dto.setUpdateFlag(null);
			//按名称查询
			List<SignMessageDto> list = iSignMessageService.validateDepName(dto);
			if(list!=null && list.size()>0){
				String message = "1";
				return new ModelAndView(signMessageUpdateView)
							.addObject("message",message)
							.addObject("dto",dto);
			}else{
				SessionUserHolder user = SessionUserUtils.getUserInfo(request);
				dto.setModifier(user.getUsername());
				iSignMessageService.updateSignMessage(dto);
				SignMessageDto resultDto = iSignMessageService.querySignMessageById(dto);
				return new ModelAndView(signMessageUpdateView)
						.addObject("dto",resultDto);
			}
		}
	}
	//频道信息删除
	public ModelAndView deleteSignMessage(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		iSignMessageService.deleteSignMessage(dto);
		return new ModelAndView(signMessageSearchView);
	}
	//商户准入签约频道搜索
	public ModelAndView signDepSearchAsyn(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		if(dto!=null){
			if(Pattern.compile("^[a-z].+$").matcher(dto.getDepartmentName()).matches()){
				dto.setSearchKey(dto.getDepartmentName());
				dto.setDepartmentName(null);
				String str = iSignMessageService.querySignMessageByConditionToStr(dto);
				SpringControllerUtils.renderText(response, str);  
			}else{
				dto.setSearchKey(null);
				dto.setDepartmentName(dto.getDepartmentName());
				String str = iSignMessageService.querySignMessageByConditionToStr(dto);
				SpringControllerUtils.renderText(response, str);  
			}
		}
		return null;
	}
	//签约部门姓名不能重复
	public ModelAndView validateDepName(HttpServletRequest request,
            HttpServletResponse response,SignMessageDto dto) throws Exception {
		String str = null;
		List<SignMessageDto> info = iSignMessageService.validateDepName(dto); 
		int count =0;
		if(info!=null && info.size()>0){
			for(SignMessageDto resultDto:info){
				if(!dto.getDepartmentName().equals(resultDto.getDepartmentName())){
					count++;
				}
			}
			if(count>1)str = "1";
			SpringControllerUtils.renderText(response, str);  
		}                  
		return null;
	}
	
	private String getSearchKey(String zhName){
		SearchKeyUtils skUtils = new SearchKeyUtils();
		String searchKey = skUtils.getAllFirstLetter(zhName);
		return searchKey;
	}
	public ISignMessageService getiSignMessageService() {
		return iSignMessageService;
	}
	public void setiSignMessageService(ISignMessageService iSignMessageService) {
		this.iSignMessageService = iSignMessageService;
	}
	public String getSignMessageCreateView() {
		return signMessageCreateView;
	}
	public void setSignMessageCreateView(String signMessageCreateView) {
		this.signMessageCreateView = signMessageCreateView;
	}
	public String getSignMessageSearchView() {
		return signMessageSearchView;
	}
	public void setSignMessageSearchView(String signMessageSearchView) {
		this.signMessageSearchView = signMessageSearchView;
	}
	public String getSignMessageSearchList() {
		return signMessageSearchList;
	}

	public void setSignMessageSearchList(String signMessageSearchList) {
		this.signMessageSearchList = signMessageSearchList;
	}
	public String getSignMessageUpdateView() {
		return signMessageUpdateView;
	}
	public void setSignMessageUpdateView(String signMessageUpdateView) {
		this.signMessageUpdateView = signMessageUpdateView;
	}
}
