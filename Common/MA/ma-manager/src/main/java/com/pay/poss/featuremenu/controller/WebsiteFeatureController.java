/**
 * 
 */
package com.pay.poss.featuremenu.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.featuremenu.common.CheckUtil;
import com.pay.poss.featuremenu.dto.FeatureDto;
import com.pay.poss.featuremenu.dto.FeatureMenuDto;
import com.pay.poss.featuremenu.model.PowersModel;
import com.pay.poss.featuremenu.service.FeatureMenuService;
import com.pay.poss.featuremenu.service.FeatureService;
import com.pay.poss.featuremenu.service.MemberFeatureService;

/**
 * @Description website菜单查询
 * @project 	poss-membermanager
 * @file 		WebsiteMenueController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-3		tianqing_wang			Create
 */
public class WebsiteFeatureController extends MultiActionController {
	
	private Log log = LogFactory.getLog(WebsiteFeatureController.class);
	
    private MemberFeatureService memberFeatureService;
	private FeatureService featureService;
	private FeatureMenuService featureMenuService;
	
	
	private String featureSearchView;
	private String featureEditView;
	private String featureCreateView;
	private String authorizeView;
	
	

	
	public ModelAndView index(HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        List<FeatureDto> featureList = featureService.queryAllFeature();
        return new ModelAndView(featureSearchView)
                    .addObject("featureList",featureList);
	}
	//权限编辑显示页面
	public ModelAndView featureEditView(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
        Long featureId=request.getParameter("featureId")==null?0L:Long.parseLong(request.getParameter("featureId"));
        FeatureDto fDto=new FeatureDto();
        if(featureId>0){
            fDto=featureService.getFeature(featureId);
        }
        return new ModelAndView(featureEditView).addObject("feature", fDto);
    }
	//权限编辑保存
    public ModelAndView editSaveFeature(HttpServletRequest request,
        HttpServletResponse response,FeatureDto fDto)throws Exception{
    	//当页面按钮为disabled的时候在controller接不到数据故做次处理
    	fDto.setAppScale(fDto.getHiddenAppScale());
    	fDto.setSecurLevel(fDto.getHiddenSecurLevel());
        featureService.updateFeature(fDto);
        return new ModelAndView(featureEditView).addObject("feature", fDto);
    }
	
    //权限新增显示页面
	public ModelAndView createView(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
       return new ModelAndView(featureCreateView);
       			  
	}
	 //权限新增保存
	public ModelAndView createSaveFeature(HttpServletRequest request,
	   HttpServletResponse response,FeatureDto fDto) throws Exception {
	   //如果没有值,flag为null
	   Long flag = featureService.getFeatureIdByLvl(fDto.getSecurLevel(),fDto.getAppScale());
	   //如果存在对应的值则刷回页面并显示给用户刚填写过的值,同时提示用户不能保存
	   if(null != flag ){
		   return new ModelAndView(featureCreateView)
		   			  .addObject("fDto",fDto);	
	   }
	   featureService.createFeature(fDto);
	   return new ModelAndView(featureCreateView)
	   					.addObject("success",0);	
	}
	//角色授权页面
	public ModelAndView authorizeView(HttpServletRequest request,
	   HttpServletResponse response) throws Exception {
	   String fid = request.getParameter("featureId")==null?"":request.getParameter("featureId");
	   String appScale = request.getParameter("appScale")==null?"":request.getParameter("appScale");
	   Long featureId=0L;
       if(CheckUtil.isNumber(fid)){
           featureId=Long.valueOf(fid);
       }
       FeatureDto feature = featureService.getFeature(featureId);
       List<PowersModel> pwList = memberFeatureService.getFeatureMenuList(featureId,appScale);
      
       return new ModelAndView(authorizeView)
                   .addObject("feature",feature)
                   .addObject("pwList",pwList)
                    .addObject("appScale",appScale);
	}
	//授权保存
	public ModelAndView authorizeSave(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
        String[] menuIdArry=request.getParameterValues("menuId");
        String fid=request.getParameter("featureId");
        //保存完回刷
        String appScaleStr = request.getParameter("appScale")==null?"0":request.getParameter("appScale");
        String securLevelStr = request.getParameter("securLevel")==null?"0":request.getParameter("securLevel");
        
        int appScale =0;
		try {
			appScale = Integer.parseInt(appScaleStr);
		} catch (Exception e) {
			appScale =0;
		}
        int securLevel =0;
		try {
			securLevel = Integer.parseInt(securLevelStr);
		} catch (Exception e) {
			securLevel =0;
		}
        
        List<FeatureMenuDto> fmList=null;
        if(menuIdArry!=null && menuIdArry.length>0 && StringUtils.isNotEmpty(fid)){
            fmList=new ArrayList<FeatureMenuDto>(menuIdArry.length);
            Long featureId=Long.valueOf(fid);
            for(String menuId:menuIdArry){
                FeatureMenuDto fm=new FeatureMenuDto();
                fm.setFeatureId(featureId);
                fm.setMenuId(Long.valueOf(menuId));
                fmList.add(fm);
            }
            featureMenuService.doAuthorizeRnTx(fmList,featureId);
        }
        //刷新website 菜单缓存
        featureMenuService.fushMenuByFeature(securLevel, appScale);
        
        return this.authorizeView(request, response);
    }
	
	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}
	public void setFeatureService(FeatureService featureService) {
		this.featureService = featureService;
	}
	public void setMemberFeatureService(MemberFeatureService memberFeatureService) {
		this.memberFeatureService = memberFeatureService;
		}
	public String getFeatureSearchView() {
		return featureSearchView;
	}
	public void setFeatureSearchView(String featureSearchView) {
		this.featureSearchView = featureSearchView;
	}
	public String getFeatureEditView() {
		return featureEditView;
	}
	public void setFeatureEditView(String featureEditView) {
		this.featureEditView = featureEditView;
	}
	public String getFeatureCreateView() {
		return featureCreateView;
	}
	public void setFeatureCreateView(String featureCreateView) {
		this.featureCreateView = featureCreateView;
	}
	public String getAuthorizeView() {
		return authorizeView;
	}
	public void setAuthorizeView(String authorizeView) {
		this.authorizeView = authorizeView;
	}
}
