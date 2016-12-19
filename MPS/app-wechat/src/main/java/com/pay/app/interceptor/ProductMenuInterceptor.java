/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.app.interceptor;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.OperatorMenu;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.featuremenu.MemberProductService;
import com.pay.base.service.operator.OperatorMenuService;

/**
 * 产品菜单检查
 * @author zhi.wang
 * @version $Id: ProductMenuInterceptor.java, v 0.1 2011-1-20 下午02:24:38 zhi.wang Exp $
 */
public class ProductMenuInterceptor extends HandlerInterceptorAdapter {
	private static final Log logger = LogFactory.getLog(ProductMenuInterceptor.class);
	private MemberProductService memberProductService;
	private FeatureMenuService   featureMenuService;
	private OperatorMenuService  operatorMenuService;
	private static final String forwadrUrl="/error.htm?method=noFeature";
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
		String path = request.getRequestURI(); 
		boolean result = false;
	    if(logger.isDebugEnabled()){
	        logger.debug("ProductMenuInterceptor  will start  intercept ..... requestPath is ["+path+"]");
        }
	    // 检查当前访问链接是否为产品菜单链接
	    boolean isProudctMenuUrl = false;
	    List<MenuDto> menuList = featureMenuService.findMenuByTypesArray(new Integer[]{8,9});
	    if(menuList!=null && menuList.size()>0){
	    	for (MenuDto menuDto : menuList) {
				if(StringUtils.isNotBlank(menuDto.getUrl()) && StringUtils.contains(path.toLowerCase(), menuDto.getUrl().toLowerCase())){
					isProudctMenuUrl = true;
					break;
				}
			}
	    }
	    // 非产品菜单URL，不检查访问权限
	    if(!isProudctMenuUrl){
	    	if(logger.isInfoEnabled()){
		        logger.info("ProductMenuInterceptor requestPath ["+path+"] not is ProudctMenuUrl!");
	        }
	    	return true;
	    }
	    // 检查产品菜单URL访问权限
	    LoginSession loginSession = SessionHelper.getLoginSession();
        if (loginSession != null && loginSession.getMemberCode() != null) {
            // 查询会员产品类菜单
            List<MenuDto> proMenuList = memberProductService.findProductMenuByMemCode(Long.valueOf(loginSession.getMemberCode()));
            if(proMenuList != null && proMenuList.size()>0){
            top : for (MenuDto menuDto : proMenuList) {
					if(StringUtils.isNotBlank(menuDto.getUrl()) && StringUtils.contains(path.toLowerCase(), menuDto.getUrl().toLowerCase())){
						// 是否为普通操作员
						if(loginSession.getOperatorId() != null && loginSession.getOperatorId() > 0){
							// 检查该操作员是否有当前菜单的权限
							OperatorMenu operatorMenu = operatorMenuService.getByOperateId(loginSession.getOperatorId());
							if(operatorMenu != null && operatorMenu.getMenuArray() != null){
								String[] menuArray = StringUtils.split(operatorMenu.getMenuArray(), ",");
								if(menuArray != null && menuArray.length >0){
									for (String str : menuArray) {
										if(StringUtils.equals(str, menuDto.getMenuId().toString())){
											// 有当前菜单的权限
											result = true;
											break top;
										}
									}
								}
							}
							// 没有当前菜单的权限
							result = false;
							break;
						}
						// 非普通操作员，且有当前菜单的权限
						result = true;
						break;
					}
				}
            }
        } 
        if(!result){
        	// 检查产品菜单URL访问权限不通过
       	 	RequestDispatcher rd=request.getRequestDispatcher(forwadrUrl);
            rd.forward(request, response);
        }
        if(logger.isDebugEnabled()){
        	logger.debug("ProductMenuInterceptor is end intercept ..... requestPath is ["+path+"]");
        }
		return result;
	}
	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}
	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}
	public void setOperatorMenuService(OperatorMenuService operatorMenuService) {
		this.operatorMenuService = operatorMenuService;
	}
	
}
