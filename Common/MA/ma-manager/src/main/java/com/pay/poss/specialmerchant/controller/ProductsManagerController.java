package com.pay.poss.specialmerchant.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.amountma.dto.FrozenLogDto;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;
import com.pay.poss.specialmerchant.service.ISpEnumInfoService;

public class ProductsManagerController extends MultiActionController{

	private String indexView;
	private String editView;
	private String listView;
	private String detailView;
		
	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getEditView() {
		return editView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}
	
	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}
	
	private ISpEnumInfoService spEnumInfoService;
	
	public ISpEnumInfoService getSpEnumInfoService() {
		return spEnumInfoService;
	}

	public void setSpEnumInfoService(ISpEnumInfoService spEnumInfoService) {
		this.spEnumInfoService = spEnumInfoService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response,FrozenLogDto frozenLogDto){
		return new ModelAndView(indexView);
	}
	
	public ModelAndView searchSpEnumInfo(HttpServletRequest request,
			HttpServletResponse response,SpEnumInfoDto spEnumInfoDto){
		Page<SpEnumInfoDto> paramPage = PageUtils.getPage(request);
		spEnumInfoDto.setEnumType(2L);
		Page<SpEnumInfoDto> page =  spEnumInfoService.search(paramPage, spEnumInfoDto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return new ModelAndView(listView,map);
	}
	

	public ModelAndView addSpEnumInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException, IOException{
		
		String enumName = ServletRequestUtils.getStringParameter(request,"enumName");
		
	    int maxEnumCode = spEnumInfoService.findMaxEnumCode(2);
		
		String enumCode =null;
		if(maxEnumCode==0){
			enumCode = "20000";
		}else{
			enumCode = String.valueOf(maxEnumCode + 1);
		}
		
		Long enumType = 2L;
		String value1 = ServletRequestUtils.getStringParameter(request,"value1");
		String value2 = ServletRequestUtils.getStringParameter(request,"value2");
		
		Page<SpEnumInfoDto> paramPage = PageUtils.getPage(request);
		SpEnumInfoDto dto = new SpEnumInfoDto();
		dto.setEnumName(enumName);
		dto.setEnumType(enumType);
		Page<SpEnumInfoDto> page =  spEnumInfoService.search(paramPage, dto);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		boolean isok = false;
		String exceptionMsg = "";
		try{
			if(page.getResult().size()==1){
				isok = false;
				exceptionMsg ="，名称重复";
			}else{
				isok =  spEnumInfoService.addSpEnumInfoRnTx(enumName, enumCode,enumType, value1, value2);	
			}			 
		}catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
			logger.error("ProductsManagerController.addSpEnumInfo throws error", e);
			
			//e.printStackTrace();
		}
		exceptionMsg=(isok?"S":"添加不成功"+exceptionMsg);
		response.getWriter().write(exceptionMsg);
		return null;		
	}
	
	public ModelAndView updateSpEnumInfo(HttpServletRequest request,
			HttpServletResponse response) throws ServletRequestBindingException, IOException{
		Long id = ServletRequestUtils.getLongParameter(request, "id",0L);
		String enumName = ServletRequestUtils.getStringParameter(request,"enumName");
		String value1 = ServletRequestUtils.getStringParameter(request,"value1");
		String value2 = ServletRequestUtils.getStringParameter(request,"value2");
		
		Page<SpEnumInfoDto> paramPage = PageUtils.getPage(request);
		SpEnumInfoDto dto = new SpEnumInfoDto();
		dto.setEnumId(id);
		dto.setEnumName(enumName);
		dto.setEnumType(2L);
		Page<SpEnumInfoDto> page =  spEnumInfoService.search(paramPage, dto);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		boolean isok = false;
		String exceptionMsg = "";
		try{
			if(page.getResult().size()==1){
				isok = false;
				exceptionMsg ="，名称重复";
			}else{
			   isok =  spEnumInfoService.updateSpEnumInfoRnTx(id,enumName,value1, value2);
			}	
		}catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
			logger.error("ProductsManagerController.updateSpEnumInfo throws error", e);
			//e.printStackTrace();
		}
		exceptionMsg=(isok?"S":"更新不成功"+exceptionMsg);
		response.getWriter().write(exceptionMsg);
		return null;		
	}
	
	public ModelAndView delSpEnumInfo(HttpServletRequest request,
			HttpServletResponse response) throws  IOException{
		Long id = ServletRequestUtils.getLongParameter(request, "id",0L);		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		boolean isok = false;
		String exceptionMsg = "";
		try{
			 isok =  spEnumInfoService.delSpEnumInfoRnTx(id);
			 if(! isok){
				 exceptionMsg = "确认是否已被删除";
			 }
		}catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
			logger.error("ProductsManagerController.delSpEnumInfo throws error", e);
		}
		exceptionMsg=(isok?"S":("删除不成功，"+exceptionMsg));
		response.getWriter().write(exceptionMsg);
		return null;
	}
	
	public ModelAndView getSpEnumInfoDetail(HttpServletRequest request,
			HttpServletResponse response){
		
		Long id = ServletRequestUtils.getLongParameter(request, "id",0L);
		SpEnumInfoDto dto =  spEnumInfoService.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		return new ModelAndView(detailView,map);
	}
}

