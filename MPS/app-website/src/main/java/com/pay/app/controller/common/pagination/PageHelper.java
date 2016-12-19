/**
 *  File: PageHelper.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.controller.common.pagination;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pay.util.StringUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class PageHelper {
    private HttpServletRequest request;

    private HttpSession session;

    private String param;

    private String url;
    private String divId;
    private int paramPageSize;
    private int paramPage;


    public PageHelper(HttpServletRequest request) {
        this.request = request;
        this.session = this.request.getSession();        
        this.divId=request.getParameter("id");
        this.paramPageSize=ParamUtil.getInt(request, "size", 10);
        this.paramPage=ParamUtil.getInt(request, "page", 1);
    }

    /**
     * 用于计算用户自定义每页显示记录数
     * 
     * @return
     */
    public int doCustomPerPage(int defaultPerPage) {
        int perPage = defaultPerPage;
        String cUserPerPage = null;
        String cPerPage = ParamUtil.getString(this.request, "size", "");
        if (!"".equals(cPerPage)) {
            this.session.setAttribute("cPerPage", cPerPage);
        }
        cUserPerPage = (String) this.session.getAttribute("cPerPage");
        if (null != cUserPerPage) {
            perPage = Integer.parseInt(cUserPerPage);
        }
        return perPage;
    }

    public String getPageBreakStr(VPage pageObject) {
        int page = 1;
        int total = 0;
        int perPage = 10;
        // 取分页器参数
        page = pageObject.getTargetPage();
        total = pageObject.getTotalRecord();
        if (total != 0 || total != 1)
            perPage = pageObject.getPageSize();

        boolean display1 = false;
        boolean display2 = false;
        boolean display3 = false;
        boolean display4 = false;
        boolean isHaveParam = false;

        // 设置URL参数,如果pageObject的ParamName和Url为空,则重request对象中取值
        if (StringUtil.isNull(pageObject.getUrl())) {
            url = request.getRequestURI();
        } else {
            url = pageObject.getUrl();
        }
        param = pageObject.getParamName();
        if (StringUtil.isNull(param)) {
            param = request.getQueryString();
            if (!StringUtil.isNull(param)) {
                if (param.indexOf("&size=" + paramPageSize) != -1) {
                    param = param.replace("&size=" + paramPageSize, "");
                }
                if (param.indexOf("size=" + paramPageSize + "&") != -1) {
                    param = param.replace("size=" + paramPageSize + "&", "");
                }
                if (param.indexOf("&page=" + paramPage) != -1) {
                    param = param.replace("&page=" + paramPage, "");
                }
                if (param.indexOf("page=" + paramPage + "&") != -1) {
                    param = param.replace("page=" + paramPage + "&", "");
                }
                isHaveParam = true;
            }
        }else{
        	isHaveParam = true;
        }

        // 绝对路径
        String realPath = pageObject.getRealPath();
        // 翻页模板名
        String pageTemplate = pageObject.getPageTemplate();

        int totalPage = (total + perPage - 1) / perPage;

        if (page > totalPage || page < 0) {
            page = 1;
        }
        if (divId!=null)
        param=param+"&id="+divId;
        // 首页
        StringBuffer firstPageBuffer = new StringBuffer();
        if (totalPage > 1&& page>1) {
            firstPageBuffer.append(url + "?page=1");
            if (isHaveParam) {
                firstPageBuffer.append("&" + param);
            }
            display1 = true;
        }

        // 上一页
        StringBuffer perPageBuffer = new StringBuffer();
        if (page > 1) {
            perPageBuffer.append(url + "?page=" + (page - 1));
            if (isHaveParam) {
                perPageBuffer.append("&" + param);
            }
            display2 = true;
        }

        // 下一页
        StringBuffer nextPageBuffer = new StringBuffer();
        if (page < totalPage) {
            nextPageBuffer.append(url + "?page=" + (page + 1));
            if (isHaveParam) {
                nextPageBuffer.append("&" + param);
            }
            display3 = true;
        }

        // 最后一页
        StringBuffer lastPageBuffer = new StringBuffer();
        if (totalPage > 1) {
            lastPageBuffer.append(url + "?page=" + totalPage);
            if (isHaveParam) {
                lastPageBuffer.append("&" + param);
            }
            display4 = true;
        }

        // 每页显示记录数
        StringBuffer numPageBuffer = new StringBuffer();
        numPageBuffer.append("<select id=\"size\" name=\"size\" ");
        numPageBuffer.append(" onchange=\"");
        numPageBuffer.append(" if(this.options[this.selectedIndex]");
        numPageBuffer.append(".value!=''){");
        numPageBuffer.append(" pagination({url:'" + url + "?" + param + "&");
        numPageBuffer.append("page=1");
        numPageBuffer.append("&size='+this.options[this.selectedIndex]");
        numPageBuffer.append(".value,id:'"+divId+"'});}\">");
        for (int i = 1; i <= 4; i++) {
            numPageBuffer.append("<option value=\"" + (i * 10) + "\"");
            if ((i * 10) == perPage) {
                numPageBuffer.append(" selected>" + (i * 10) + "</option>");
            } else {
                numPageBuffer.append(">" + (i * 10) + "</option>");
            }
        }
        numPageBuffer.append("<option value=\"50\"");
        if (50==perPage){
        	numPageBuffer.append(" selected>50</option>");
	}else{
		numPageBuffer.append(">50</option>");
	}
	
    numPageBuffer.append("<option value=\"90\"");
	 if (90==perPage){  
	    	numPageBuffer.append(" selected>90</option>");
	}else{
		numPageBuffer.append(">90</option>");
	}
    
	if(request != null && request.getContextPath().indexOf("/intra") == 0){
		numPageBuffer.append("<option value=\"400\"");
		 if (400==perPage){
		    	numPageBuffer.append(" selected>400</option>");
		}else{
			numPageBuffer.append(">400</option>");
		}
	}
	//  zhiyong.rao 08/07/14 为了后台可以显示每页1000，2000笔。
	if(request != null && request.getContextPath().indexOf("/intra") == 0){
		numPageBuffer.append("<option value=\"1000\"");
		 if (1000==perPage){
		    	numPageBuffer.append(" selected>1000</option>");
		}else{
			numPageBuffer.append(">1000</option>");
		}
	}
	if(request != null && request.getContextPath().indexOf("/intra") == 0){
		numPageBuffer.append("<option value=\"2000\"");
		 if (2000==perPage){
		    	numPageBuffer.append(" selected>2000</option>");
		}else{
			numPageBuffer.append(">2000</option>");
		}
	}
     
	/*
	numPageBuffer.append("<option value=\"200\"");
        if (200==perPage){
        	numPageBuffer.append(" selected>200</option>");
	}else{
		numPageBuffer.append(">200</option>");
	} 
	*/
numPageBuffer.append("</select>");
        // 跳转到第几页
        StringBuffer jumpPageBuffer = new StringBuffer();
        jumpPageBuffer.append("<input id=\"gopage\" class=\"gopage\" type=\"text\" size=\"5\" value=\"\"  ");
        jumpPageBuffer.append("onkeypress=\"javascript:if (event.keyCode==13){ if ((this.value<1)||(this.value>"+totalPage+") ){ return;} goPage({url:'" + url + "?" + param +"'");
        jumpPageBuffer.append(",id:'"+divId+"'});} \" />");
        //跳转的链接
        StringBuffer goToPageBuffer=new StringBuffer(); 
        
        goToPageBuffer.append("<a href='#' onclick=\"javascript:goPage({url:'" + url + "?" + param +"'");        
        goToPageBuffer.append(",id:'"+divId+"'})\">");
        goToPageBuffer.append("跳转</a>");
        
       
        Configuration cfg = new Configuration();
        Template newsTemplate = null;
        String deTemplateString = "";
        try {
            cfg.setDirectoryForTemplateLoading(new File(realPath
                    + "/templates/page"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());

            newsTemplate = cfg.getTemplate(pageTemplate, "UTF-8");

            deTemplateString = newsTemplate.toString();
            deTemplateString = deTemplateString.replace("${total}", String
                    .valueOf(total));
            deTemplateString = deTemplateString.replace("${page}", String
                    .valueOf(page));
             if (totalPage==0)
                totalPage=1; 
            deTemplateString = deTemplateString.replace("${allPage}", String
                    .valueOf(totalPage));
            if ((display1==true)&&(display2==true)) {
                deTemplateString = deTemplateString.replace(
                        "${firstPageStart}",
                        "<a href=\"javascript:pagination({url:'"
                                + firstPageBuffer.toString() + "',id:'"+divId+"'})\" >");
                deTemplateString = deTemplateString.replace("${firstPageEnd}",
                        "</a>");
            } else {
                deTemplateString = deTemplateString.replace(
                        "${firstPageStart}", "");
                deTemplateString = deTemplateString.replace("${firstPageEnd}",
                        "");
            }
            if (display2) {
                deTemplateString = deTemplateString.replace("${perPageStart}",
                        "<a href=\"javascript:pagination({url:'"
                                + perPageBuffer.toString() + "',id:'"+divId+"'})\" >");
                deTemplateString = deTemplateString.replace("${perPageEnd}",
                        "</a>");
            } else {
                deTemplateString = deTemplateString.replace("${perPageStart}",
                        "");
                deTemplateString = deTemplateString
                        .replace("${perPageEnd}", "");
            }
            if (display3) {
                deTemplateString = deTemplateString.replace("${nextPageStart}",
                        "<a href=\"javascript:pagination({url:'"
                                + nextPageBuffer.toString() + "',id:'"+divId+"'})\" >");
                deTemplateString = deTemplateString.replace("${nextPageEnd}",
                        "</a>");
            } else {
                deTemplateString = deTemplateString.replace("${nextPageStart}",
                        "");
                deTemplateString = deTemplateString.replace("${nextPageEnd}",
                        "");
            }
            if ((display3==true)&&(display4==true)){
                deTemplateString = deTemplateString.replace("${lastPageStart}",
                        "<a href=\"javascript:pagination({url:'"
                                + lastPageBuffer.toString() + "',id:'"+divId+"'})\" >");
                deTemplateString = deTemplateString.replace("${lastPageEnd}",
                        "</a>");
            } else {
                deTemplateString = deTemplateString.replace("${lastPageStart}",
                        "");
                deTemplateString = deTemplateString.replace("${lastPageEnd}",
                        "");
            }
            deTemplateString = deTemplateString.replace("${numPage}",
                    numPageBuffer.toString());
            deTemplateString = deTemplateString.replace("${jumpPage}",
                    jumpPageBuffer.toString());
            deTemplateString = deTemplateString.replace("${gotoPage}",
                    goToPageBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deTemplateString;
    }
}
