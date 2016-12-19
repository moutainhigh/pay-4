/**
 *  File: VPage.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.controller.common.pagination;

import java.io.Serializable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.inf.dao.Page;

public class VPage extends Page implements Serializable {

	private static final long serialVersionUID = 4646499128880741016L;

	private String href;

    private Object paramValue;

    private String paramName;

    // 翻页的url(路径),不带参数,参数由其它属性指定
    private String url = "";

    // 翻页参数,多个参数用&分隔
    private String param = "";

    // 模板在操作系统的实际路径
    private String realPath;

    // 模板名
    private String pageTemplate="page.html";

    // 用于显示分页标签
    private String view;

    /**
     * @return Returns the view.
     */
    public String getView() {
        return view;
    }

    /**
     * @param view
     *            The view to set.
     */
    public void setView(String view) {
        this.view = view;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return Returns the paramName.
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName
     *            The paramName to set.
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * @return Returns the pageTemplate.
     */
    public String getPageTemplate() {
        return pageTemplate;
    }

    /**
     * @param pageTemplate
     *            The pageTemplate to set.
     */
    public void setPageTemplate(String pageTemplate) {
        this.pageTemplate = pageTemplate;
    }

    /**
     * @return Returns the realPath.
     */
    public String getRealPath() {
        return realPath;
    }

    /**
     * @param realPath
     *            The realPath to set.
     */
    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    /**
     * @return Returns the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    //默认初始化方法
    public void init(HttpServletRequest request, HttpServletResponse response){
    	
    	ServletContext servletContext = request.getSession().getServletContext();
		String realPath = servletContext.getRealPath("/");
		PageHelper pageHelper = new PageHelper(request);
		// 获取请求的显示的页数,如为空,则取默认第1页
		int page = ParamUtil.getInt(request, "page", 1);
		// 设置默认每页显示记录数
		int pageSize = 20;
		pageSize = pageHelper.doCustomPerPage(pageSize);
		// 给vpage对象的pageSize属性赋值,如果pageSize为空,则取默认
		setPageSize(pageSize);
		// 给vpage对象属性赋值,如果page为空,则取默认,page代表目标页
		setTargetPage(page);
		setRealPath(realPath);
		
    }
    
    
}
