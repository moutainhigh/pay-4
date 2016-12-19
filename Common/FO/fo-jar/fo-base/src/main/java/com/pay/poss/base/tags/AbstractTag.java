package com.pay.poss.base.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.dataservice.DateServiceFactory;
import com.pay.poss.base.dataservice.IDateService;
import com.pay.poss.base.exception.PossUntxException;

/**
 * 
 * @Description 
 * @project 	poss-base
 * @file 		AbstractTag.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-8-28		Volcano.Wu			Create
 */
public abstract class AbstractTag extends BodyTagSupport {
	
	protected final static transient Log log = LogFactory.getLog(AbstractTag.class);
	
	private static final long serialVersionUID = 1L;

	protected IDateService dataService;

	protected String typeCode; // 标识
	protected String name; // name
	protected String sytleName; // 样式
	protected List<Map<String,String>> itemList ;
	
	public void setSytleName(String sytleName) {
		this.sytleName = sytleName;
	}


	public void setItemList(List<Map<String, String>> itemList) {
		this.itemList = itemList;
	}


	public void setDataService(IDateService dataService) {
		this.dataService = dataService;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	//获取数据
	protected List<Map<String, String>> getDatas() throws PossUntxException {
		try{
			if(typeCode != null) {
				dataService = DateServiceFactory.createDateService(typeCode);
				return dataService.getDatas();
			}
		}catch (Exception e){
			log.error(this.getClass().getName()+"getDatas exception : " +e.getMessage(),e);
		}
		return null;
	}

	protected void print(String str) {
		JspWriter out = pageContext.getOut();
		try {
			out.print(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
